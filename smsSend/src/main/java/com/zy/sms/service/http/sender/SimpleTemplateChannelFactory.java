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
import com.zy.sms.service.http.sender.zsp2p.ZSp2pChannelFactory;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.SimpleTemplateChannelFactory
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 17:15
 *   LastChange: 2015-11-06 17:15
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class SimpleTemplateChannelFactory extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( ZSp2pChannelFactory.class );

	protected String userId;
	protected String account;
	protected String password;
	protected String httpUrl;
	protected String taskName;

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
			param.put( "sendTime", "" );
			param.put( "taskName", taskName );
			param.put( "checkcontent", "0" );
			param.put( "mobilenumber", "1" );
			param.put( "countnumber", "0" );
			param.put( "telephonenumber", "0" );
		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", channelName, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}

	}

	/**
	 * <?xml version="1.0" encoding="utf-8" ?><returnsms>
	 * <returnstatus>Success</returnstatus>
	 * <message>ok</message>
	 * <remainpoint>96801</remainpoint>
	 * <taskID>30749029</taskID>
	 * <successCounts>1</successCounts></returnsms>.
	 * <p>
	 * <?xml version="1.0" encoding="utf-8" ?><returnsms>
	 * <returnstatus>Faild</returnstatus>
	 * <message>参数错误</message>
	 * <remainpoint>0</remainpoint>
	 * <taskID>0</taskID>
	 * <successCounts>0</successCounts></returnsms>.
	 * </p>
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

			String taskIdVal = parseXmlResult();

			//TODO -- 2015-11-6 16:09:51 處理短信發送結果，組裝數據，準備存入ES。
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

	private String parseXmlResult() throws DocumentException
	{
		Document doc       = DocumentHelper.parseText( result );
		Element  root      = doc.getRootElement();
		Element  status    = root.element( "returnstatus" );
		String   statusVal = status.getText();
		Element  taskId    = root.element( "taskID" );
		String   taskIdVal = taskId.getText();

		statusVal = StringUtils.equalsIgnoreCase( statusVal, "Success" ) ? "success" : statusVal;
		if ( StringUtils.isBlank( statusVal ) || !StringUtils.equalsIgnoreCase( "success", statusVal ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

		return taskIdVal;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount( String account )
	{
		this.account = account;
	}

	public String getHttpUrl()
	{
		return httpUrl;
	}

	public void setHttpUrl( String httpUrl )
	{
		this.httpUrl = httpUrl;
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName( String taskName )
	{
		this.taskName = taskName;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId( String userId )
	{
		this.userId = userId;
	}
}
