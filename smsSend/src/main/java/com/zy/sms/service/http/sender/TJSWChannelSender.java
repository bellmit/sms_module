/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender;

import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.Base64Helper;
import com.zy.util.ObjectUtil;
import com.zy.util.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.TJSWChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-18 15:36
 *   LastChange: 2015-11-18 15:36
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "TJ_COMM_CHANNEL001" )
public class TJSWChannelSender extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( TJSWChannelSender.class );

	@Value( "${TJ.SW.USERNAME}" ) private String username;
	@Value( "${TJ.SW.PASSWORD}" ) private String password;
	@Value( "${TJ.SW.URL}" ) private      String httpUrl;

	private static final String CHANNEL_NAME = "【天津三網】通道";

	public TJSWChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "TJ_SW_CHANNEL_Executor" )
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

	@Override
	public void httpParam( SmsSendDataInfo smsRequestVO )
	{
		try
		{
			logger.info( "{0}開始構造HTTP請求參數....", new Object[] { channelName } );

			StringBuilder submit = new StringBuilder( "<submit>" );
			submit.append( "<usermsgid>" );
			submit.append( smsRequestVO.getSmsid() );
			submit.append( "</usermsgid>" );
			submit.append( "<desttermid>" );
			submit.append( smsRequestVO.getMobile() );
			submit.append( "</desttermid>" );
			submit.append( "<srctermid>" );
			submit.append( "</srctermid>" );
			submit.append( "<msgcontent>" );
			submit.append( Base64Helper.getBase64( smsRequestVO.getContent() ) );
			submit.append( "</msgcontent>" );
			submit.append( "<signid>" );
			submit.append( smsRequestVO.getUuid() );
			submit.append( "</signid>" );
			submit.append( "<desttype>" );

			if ( StringUtils.equalsIgnoreCase( smsRequestVO.getCarriers(), "LT" ) ) submit.append( "2" );
			else if ( StringUtils.equalsIgnoreCase( smsRequestVO.getCarriers(), "YD" ) ) submit.append( "1" );
			else if ( StringUtils.equalsIgnoreCase( smsRequestVO.getCarriers(), "DX" ) ) submit.append( "3" );

			submit.append( "</desttype>" );
			submit.append( "<tempid>" );
			submit.append( smsRequestVO.getTemplateId() );
			submit.append( "</tempid>" );
			submit.append( "<needreply>1</needreply>" );
			submit.append( "</submit>" );

			param = new ConcurrentHashMap<String, String>();
			param.put( "submit", submit.toString() );

//			StringBuilder sb = new StringBuilder();
//			sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//					   "<Body>\n" +
//					   "\t<result>0</result>\n" +
//					   "</Body>" );
//
//			result = sb.toString();

		}
		catch ( Exception e )
		{
			logger.error( "{0}組裝http請求參數時出現異常：{1}.", new Object[] { CHANNEL_NAME , e.getMessage() }, e );
			throw new SenderException( e.getMessage(), e );
		}
	}

	@Override
	public String send() throws SenderException
	{
		long startTime = System.currentTimeMillis();
		try
		{
			if ( StringUtils.isBlank( httpUrl ) ) throw new SenderException( "URL is null!" );
			logger.info( "{} HTTP请求-->url:{} == param:{}", new Object[] { channelName , httpUrl , param } );

			StringBuffer sb = new StringBuffer( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
			sb.append( "<Body>" );
			sb.append( "<user>" );
			sb.append( username );
			sb.append( "</user>" );
			sb.append( "<password>" );
			sb.append( password );
			sb.append( "</password>" );
			sb.append( "<version>1.2</version>" );
			sb.append( param.get( "submit" ) );
			sb.append( "</Body>" );

			logger.info( "{}", sb.toString() );

			return ( result = HttpClientUtil.INSTANCE.httpPost( httpUrl, sb.toString(), header, "xml" ) );
		}
		catch ( Exception e )
		{
			logger.error( "{} HTTP請求出錯 : {}=={}--->{}. ", new Object[] { channelName , httpUrl , param , e.getMessage() }, e );
			throw new SenderException( "Http請求出錯", e );
		}
		finally
		{
			logger.info( "{} HTTP请求完成，用时 : {} ms.", channelName, ( System.currentTimeMillis() - startTime ) );
		}
	}

	@Override
	public void httpHeader( Map<String, String> httpHeader )
	{
		this.header = new HashMap<String, String>();
		header.put( "Content-Type", "text/xml; charset=UTF-8" );
		header.put( "Content-Length", "length" );
		header.put( "Action", "\"submitreq\"" );
	}

	/**
	 * HTTP/1.1 200 OK
	 * Content-Type: text/xml; charset=utf-8
	 * Content-Length: length
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <Body>
	 * <result>0成功，其他失败</result>
	 * </Body>
	 *
	 * @param smsSendDataInfo
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
			logger.info( "{}短信發送結果：{}.", new Object[] { CHANNEL_NAME , ObjectUtil.convertUnicode( result ) } );

			Document doc  = DocumentHelper.parseText( result );
			Element  root = doc.getRootElement();

			String result = root.element( "result" ).getTextTrim();
			if ( !StringUtils.equalsIgnoreCase( result, "0" ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

			EsDataInfo esDataInfo = new EsDataInfo();

			return esDataInfo;
		}
		catch ( SenderException e )
		{
			logger.error( "短信發送失敗：" + e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
		catch ( Exception e )
		{
			logger.error( "短信發送完畢，組裝發送結果出現異常：" + e.getMessage(), e );
		}
		return null;
	}
}
