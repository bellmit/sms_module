/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.bjjtd;

import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.bjjtd.JTDChannelFactory
 *         Desc: \
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 14:12
 *   LastChange: 2015-11-09 14:12
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class JTDChannelFactory extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( JTDChannelFactory.class );

	private                            String username;
	private                            String pwd;
	@Value( "${BJ.JTD.URL}" ) private  String httpUrl;
	@Value( "${BJ.JTD.GWID}" ) private String gwid;

	public String getUsername()
	{
		return username;
	}

	public void setUsername( String username )
	{
		this.username = username;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd( String pwd )
	{
		this.pwd = pwd;
	}

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	/**
	 * username=xxx&pwd=yyy&mobile=1392055xxxx&msg=验证码:xxx【阿里云】
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

			param.put( "username", username );
			param.put( "pwd", pwd );
			param.put( "mobile", smsSendDataInfo.getMobile() );
			param.put( "msg", smsSendDataInfo.getContent() );
//			param.put( "msg", URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			param.put( "gwid", gwid );
			param.put( "key", smsSendDataInfo.getSmsid() );

		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", channelName, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}

	}

	/**
	 * success:15098648522:fe2b4a0c08414142a43a56e0ae63a26c1.
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
			logger.info( "{}短信發送結果：{}.", channelName, ObjectUtil.convertUnicode( result ) );

			if ( StringUtils.isBlank( result ) || !StringUtils.containsIgnoreCase( "success", result ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

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
