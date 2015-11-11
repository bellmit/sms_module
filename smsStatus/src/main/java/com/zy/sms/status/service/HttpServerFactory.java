/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.sms.status.service;

import com.zy.util.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.HttpServerFactory
 *         Desc: 請求發送消息父類
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 12:18
 *   LastChange: 2015-10-08 12:18
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class HttpServerFactory implements IHttpRequest, IHttpResponse
{
	protected final static Logger logger = LoggerFactory.getLogger( HttpServerFactory.class );

	protected String channelName;
	protected String channelCode;
	protected String result;

	public String send()
	{
		long startTime = System.currentTimeMillis();
		try
		{

			if ( StringUtils.isBlank( httpUrl() ) ) return ( result = "{\"result\":\"FAILD\",\"reason\":\"Url is null.\"}" );
			logger.info( channelName + "请求URL---> " + httpUrl() );
			logger.info( channelName + "请求参数--->" + httpParam() );

			return ( result = HttpClientUtil.INSTANCE.httpPost( httpUrl(), httpParam() ) );
		}
		catch ( Exception e )
		{
			logger.error( channelName + "HTTP請求出錯 : " + e.getMessage() + ":::" + httpUrl(), e );
			return ( result = "{\"result\":\"FAILD\",\"reason\":\"" + e.getMessage() + "\"}" );
		}
		finally
		{
			logger.info( channelName + "HTTP请求完成，用时  : " + ( System.currentTimeMillis() - startTime ) );
		}
	}

}

