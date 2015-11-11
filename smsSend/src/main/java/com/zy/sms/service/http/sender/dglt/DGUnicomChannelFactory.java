/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.dglt;

import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.dglt.DGUnicomChannelFactory
 *         Desc: TODO -- 2015-11-9 14:03:40 賬號被停用
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 11:54
 *   LastChange: 2015-11-09 11:54
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class DGUnicomChannelFactory extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( DGUnicomChannelFactory.class );

	@Value( "${DG.UNICOM.SPCODE}" ) private     String spcode;
	@Value( "${DG.UNICOM.LOGIN.NAME}" ) private String loginName;
	@Value( "${DG.UNICOM.PASSWORD}" ) private   String password;
	@Value( "${DG.UNICOM.URL}" ) private        String httpUrl;

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	/**
	 * LoginName=admin&Password=admin&MessageContent=你有一项编号为123456789的事务需要处理。&UserNumber=18616330318&SerialNumber=&ScheduleTime=&f=1
	 *
	 * @param smsSendDataInfo
	 */
	@Override
	public void httpParam( SmsSendDataInfo smsSendDataInfo )
	{
		try
		{
			param = new ConcurrentHashMap<String, String>();

			param.put( "SpCode", spcode );
			param.put( "LoginName", loginName );
			param.put( "Password", password );
			param.put( "ScheduleTime", "" );
			param.put( "ExtendAccessNum", "" );
			param.put( "f", "1" );
			param.put( "UserNumber", smsSendDataInfo.getMobile() );
			param.put( "MessageContent", URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			param.put( "SerialNumber", smsSendDataInfo.getSmsid() );

		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", channelName, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
	}

	@Override
	public EsDataInfo parseResult( SmsSendDataInfo smsSendDataInfo ) throws Exception
	{
		try
		{
			logger.info( "{}短信發送結果：{}.", channelName, ObjectUtil.convertUnicode( result ) );

			if ( StringUtils.isBlank( result ) || !StringUtils.containsIgnoreCase( "\"ret\":0", result ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

//			FXYdResult fxYdResult = JSON.parseObject( result, FXYdResult.class );
//			if ( null == fxYdResult ) throw new RuntimeException( "短信發送結果解析失敗，無法進入數據處理模塊." );

			//TODO -- 2015-11-6 16:09:51 處理短信發送結果，組裝數據，準備存入ES。
			EsDataInfo esDataInfo = new EsDataInfo();
			esDataInfo.setMobile( "15098648522" );
//			esDataInfo.setSmsid( fxYdResult.getData() );

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
}
