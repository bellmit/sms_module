/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl;

import com.zy.sms.status.cache.SmsStatusPushCache;
import com.zy.sms.status.service.HttpServerFactory;
import com.zy.sms.status.service.biz.IPushOperate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.SmsStatusPushServerImpl
 *         Desc: 推送線程
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-09 17:03
 *   LastChange: 2015-10-09 17:03
 *      History:
 * </pre>
 *********************************************************************************************/
@Service public class SmsStatusPushServerImpl extends HttpServerFactory implements IPushOperate
{
	private String httpUrl;
	private String param;
	private String merchant;

	@Override public String httpUrl()
	{
		return httpUrl;
	}

	@Override public Map<String, String> httpParam()
	{
		try
		{
			logger.debug( "----httpParam-->>>" + param );
			ConcurrentHashMap<String, String> params = new ConcurrentHashMap<String, String>();
			params.put( "data", URLEncoder.encode( new StringBuffer( "{" ).append( param ).append( "}" ).toString(), "utf-8" ) );
			logger.debug( "-----httpParam....data-->>>" + param );
			return params;
		}
		catch ( Exception e )
		{
			logger.error( "URLEncoder編碼化傳送內容出錯：{}", e.getMessage(), e );
		}

		return null;
	}

	@Override public List<String> parseResult() throws Exception
	{
		return null;
	}

	private void pushResultCheck()
	{
		boolean error = StringUtils.containsIgnoreCase( result, "FAILD" ) || StringUtils.containsIgnoreCase( result, "ERROR" );
		if ( StringUtils.equalsIgnoreCase( channelName, "repush" ) ) logger.info( "重推結果為：{}.", error ? "失敗" : "成功" );
		if ( error )
		{
			if ( !StringUtils.equalsIgnoreCase( channelName, "repush" ) ) SmsStatusPushCache.put( merchant, param );
		}
		else
		{
			if ( StringUtils.equalsIgnoreCase( channelName, "repush" ) ) SmsStatusPushCache.remove( merchant, param );
		}

	}

	@Async @Override public void pushSms()
	{
		long startTime = System.currentTimeMillis();
		try
		{
			logger.info( "为账户【" + merchant + "】推送报告開始....." );
			send();
			logger.info( "为账户【" + merchant + "】推送报告结束....." + result );

			pushResultCheck();
		}
		catch ( Exception e )
		{
			logger.error( "为账户【" + merchant + "】推送报告发生异常 : " + e.getMessage(), e );
			// 5分鐘重推機制
			SmsStatusPushCache.put( merchant, param );
		}
		finally
		{
			logger.info( "为账户【" + merchant + "】完成推送报告，用时 : " + ( System.currentTimeMillis() - startTime ) );
		}
	}

	@Override public void setHttpUrl( String pushUrl )
	{
		this.httpUrl = pushUrl;
	}

	@Override public void setParam( String pushParam )
	{
		this.param = pushParam;
	}

	@Override public void setMerchant( String merchant )
	{
		this.merchant = merchant;
	}

	@Override public void setChannelName( String channelName )
	{
		this.channelName = channelName;
	}
}
