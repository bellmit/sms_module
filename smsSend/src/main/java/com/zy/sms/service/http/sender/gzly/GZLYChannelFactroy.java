/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.gzly;

import com.zy.sms.service.exception.SenderException;
import com.zy.sms.service.http.sender.SimpleTemplateChannelFactory;
import com.zy.sms.vo.SmsSendDataInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.gzly.GZLYChannelFactroy
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 10:39
 *   LastChange: 2015-11-09 10:39
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class GZLYChannelFactroy extends SimpleTemplateChannelFactory
{
	private static final Logger logger = LoggerFactory.getLogger( GZLYChannelFactroy.class );

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	@Value( "${GZ.LY.URL}" )
	@Override
	public void setHttpUrl( String httpUrl )
	{
		super.setHttpUrl( httpUrl );
	}

	/**
	 * action=send&userid=12&account=账号&password=密码&mobile=15023239810,13527576163&content=内容&sendTime=&extno=
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

			param.put( "action", "send" );
			param.put( "userid", userId );
			param.put( "account", account );
			param.put( "password", password );
			param.put( "mobile", smsSendDataInfo.getMobile() );
			param.put( "content", smsSendDataInfo.getContent() );
//			param.put( "content", URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			param.put( "sendTime", DateFormatUtils
					.format( new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern() ) );
			param.put( "extno", "" );
		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", channelName, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
	}
}
