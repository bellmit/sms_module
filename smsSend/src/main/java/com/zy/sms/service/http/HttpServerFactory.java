/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http;

import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
public abstract class HttpServerFactory implements IHttpRequest<SmsSendDataInfo>, IHttpResponse<EsDataInfo, SmsSendDataInfo>
{
	private final static Logger logger = LoggerFactory.getLogger( HttpServerFactory.class );

	protected String              result      = "ZY_ERROR";
	protected String              channelName = null;
	protected Map<String, String> param       = null;
	protected Map<String, String> header      = null;

	@Override
	public String send() throws SenderException
	{
		long startTime = System.currentTimeMillis();
		try
		{
			if ( StringUtils.isBlank( httpUrl() ) ) throw new SenderException( "URL is null!", null );
			logger.info( "{}HTTP请求-->url:{} == param:{}", channelName, httpUrl(), param );

			return ( result = HttpClientUtil.INSTANCE.httpPost( httpUrl(), param, header ) );
		}
		catch ( Exception e )
		{
			logger.error( "{}HTTP請求出錯 : {}=={}--->{}. ", channelName, httpUrl(), param, e.getMessage(), e );
			throw new SenderException( "Http請求出錯", e );
		}
		finally
		{
			logger.info( "{}HTTP请求完成，用时 : {} ms.", channelName, ( System.currentTimeMillis() - startTime ) );
		}
	}

	@Override
	public void httpHeader( Map<String, String> httpHeader )
	{
		this.header = httpHeader;
	}
}

