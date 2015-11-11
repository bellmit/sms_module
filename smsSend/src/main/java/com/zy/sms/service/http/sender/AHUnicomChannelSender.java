/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender;

import com.alibaba.fastjson.JSON;
import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.MessageDigest5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.AHUnicomChannelSender
 *         Desc: -- 2015-11-6 14:17:04 發送短信結果返回異常,只能發送電信手機號碼
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 15:08
 *   LastChange: 2015-11-05 15:08
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "AH_UNICOM_CHANNEL001" )
public class AHUnicomChannelSender extends HttpDataManipulationImpl
{
	private final static Logger logger = LoggerFactory.getLogger( AHUnicomChannelSender.class );

	@Value( "${AH.DX.USERID}" ) private       String userName;
	@Value( "${AH.DX.MERCHANT.KEY}" ) private String userPassword;
	@Value( "${AH.DX.TEMPLATEID}" ) private   String tplid;
	@Value( "${AH.DX.URL}" ) private          String httpUrl;

	private static final String CHANNEL_NAME = "【安徽电信】通道";

	@Async( value = "AH_UNICOM_CHANNEL_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

	@Override
	public EsDataInfo parseResult( SmsSendDataInfo smsSendDataInfo ) throws Exception
	{
		try
		{
			//TODO -- 2015-11-5 17:35:11 處理短信發送結果，組裝數據，準備存入ES。
			logger.info( "{}短信發送結果：{}.", CHANNEL_NAME, result );
			EsDataInfo esDataInfo = new EsDataInfo();
			esDataInfo.setMobile( "15098648522" );

			return esDataInfo;
		}
		catch ( SenderException e )
		{
			throw new SenderException( e.getMessage(), e );
		}
		catch ( Exception e )
		{
			logger.error( "{}短信發送完畢，組裝發送結果出現異常：{}.", e.getMessage(), e );
		}
		return null;
	}

	public AHUnicomChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	/**
	 * {
	 * "SingleSMSRequest": {
	 * "UserID": "10000002",
	 * "TPLID": "1000",
	 * "MSMSID": "100000022015061212310697",
	 * "StampTime": "2015-06-12 12:31:06",
	 * "SMSText": "7650",
	 * "Sign": "1a7dbaa9f9a2e1166f2d62cf87b6bb3b",
	 * "ObjMobile": "18012988555" }
	 * }
	 * return
	 */
	@Override
	public void httpParam( SmsSendDataInfo vo )
	{
		try
		{
			logger.info( "{}開始構造HTTP請求參數....", channelName );

			param = new ConcurrentHashMap<String, String>();

			Map<String, Map<String, String>> map = constructParam( vo );

			String json = JSON.toJSON( map ).toString();

			logger.info( "{}短信內容：{}.", channelName, json );
			param.put( "json", json );
//			param.put( "json", URLEncoder.encode( json, "utf-8" ) );
		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", CHANNEL_NAME, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
	}

	private Map<String, Map<String, String>> constructParam( SmsSendDataInfo vo )
	{
		String dateTime = DateFormatUtils
				.format( new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern() );

		// userId + stampTime + templateId + mobile + merchantKey
		String str  = userName + dateTime + tplid + vo.getMobile() + userPassword;
		String sign = MessageDigest5Util.md5Hex( str );

		Map<String, String> singleSMSRequest = new HashMap<String, String>();
		singleSMSRequest.put( "UserID", userName );
		singleSMSRequest.put( "TPLID", tplid );
		singleSMSRequest.put( "MSMSID", StringUtils.replace( UUID.randomUUID().toString(), "-", "" ) );
		singleSMSRequest.put( "StampTime", dateTime );
		singleSMSRequest.put( "SMSText", vo.getContent() );
		singleSMSRequest.put( "Sign", sign );
		singleSMSRequest.put( "ObjMobile", vo.getMobile() );

		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map.put( "SingleSMSRequest", singleSMSRequest );
		return map;
	}

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}
}
