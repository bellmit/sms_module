/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.gzly;

import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.gzly.GZLYYDVIPChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 10:55
 *   LastChange: 2015-11-09 10:55
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "GZ_LY_YD_VIP_SINGLE_CHANNEL" )
public class GZLYYDVIPChannelSender extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( GZLYYDVIPChannelSender.class );

	@Value( "${GZ.LY.YD.VIP.USERID}" ) private   String userid;
	@Value( "${GZ.LY.YD.VIP.PASSWORD}" ) private String password;
	@Value( "${GZ.LY.YD.VIP.URL}" ) private      String httpUrl;

	private static final String CHANNEL_NAME = "【廣州朗月(移動VIP)】通道";

	public GZLYYDVIPChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "GZ_LY_CHANNE_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	/**
	 * userid:用户名 password:密码 destnumbers 发送号码 msg 发送内容 sendtime发送时间
	 *
	 * @param smsSendDataInfo
	 */
	@Override
	public void httpParam( SmsSendDataInfo smsSendDataInfo )
	{
		try
		{

			logger.info( "{}開始構造HTTP請求參數....", channelName );

			param = new ConcurrentHashMap<String, String>();

			param.put( "userid", userid );
			param.put( "password", password );
			param.put( "destnumbers", smsSendDataInfo.getMobile() );
			param.put( "msg", smsSendDataInfo.getContent() );
//			param.put( "msg", URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			param.put( "sendtime", DateFormatUtils
					.format( new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern() ) );

		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", CHANNEL_NAME, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
	}

	/**
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <root return="0" info="成功" msgid="04A77FDFA7023820" numbers="1" messages="1"/>.
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	@Override
	public EsDataInfo parseResult( SmsSendDataInfo smsSendDataInfo ) throws Exception
	{
		try
		{
			//TODO -- 2015-11-5 17:35:11 處理短信發送結果，組裝數據，準備存入ES。
			logger.info( "{}短信發送結果：{}.", CHANNEL_NAME, result );

			String taskIdVal = parseXmlRequest();

			EsDataInfo esDataInfo = new EsDataInfo();
			esDataInfo.setMobile( "15098648522" );
			esDataInfo.setSmsid( taskIdVal );

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

	private String parseXmlRequest() throws DocumentException
	{
		Document doc       = DocumentHelper.parseText( result );
		Element  root      = doc.getRootElement();
		String   statusVal = root.attributeValue( "return" );
		if ( !StringUtils.equalsIgnoreCase( statusVal, "0" ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

		return root.attributeValue( "msgid" );
	}
}
