/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.similar;

import com.zy.sms.status.cache.GZLYSmsStatus;
import com.zy.sms.status.service.impl.CatchStatusServerImpl;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.similar.SimpleChannelFactoryImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 11:43
 *   LastChange: 2015-10-12 11:43
 *      History:
 * </pre>
 *********************************************************************************************/
public class SimpleChannelFactoryImpl extends CatchStatusServerImpl
{
	protected String account;
	protected String password;
	protected String url;
	protected String userid;

	private final static String ACTION = "query";

	@Override public String httpUrl()
	{
		// action=query&userid=12&account=账号&password=密码
		StringBuilder sb = new StringBuilder( url );

		sb.append( "?action=" ).append( ACTION );
		sb.append( "&userid=" ).append( userid );
		sb.append( "&account=" ).append( account );
		sb.append( "&password=" ).append( password );

		return sb.toString();
	}

	@Override public Map<String, String> httpParam()
	{
		return null;
	}

	@Override public List<String> parseResult() throws Exception
	{
		Document doc = null;
		try
		{
			doc = DocumentHelper.parseText( result );
			Element root = doc.getRootElement();

			List<String> list = parstRlt( root );

			return list;

		}
		catch ( DocumentException e )
		{
			logger.error( "解析" + channelName + "状态报告出错: " + e.getMessage(), e );
			return null;
		}
		finally
		{
			logger.info( "{}狀態報告結果解析完成！", channelName );
		}
	}

	private List<String> parstRlt( Element root )
	{
		List<String> list = new ArrayList<String>();
		List<Element> eList = root.elements();

		for ( Element statusbox : eList )
		{
			try
			{
				StringBuffer result = constructRlt( statusbox );
				if ( result == null ) continue;

				list.add( result.toString() );
			}
			catch ( Exception e )
			{
				logger.error( "解析" + channelName + "状态报告出错: " + e.getMessage(), e );
			}

		}
		return list;
	}

	protected StringBuffer constructRlt( Element statusbox )
	{
		String mobile = statusbox.element( "mobile" ).getTextTrim();

		if ( !StringUtils.isNotBlank( mobile ) )
		{
			logger.warn( channelName + "解析状态报告目标号码为空返回。" );
			return null;
		}

		String taskid = statusbox.element( "taskid" ).getTextTrim();
		String status = statusbox.element( "status" ).getTextTrim();
		String receivetime = statusbox.element( "receivetime" ).getTextTrim();

		String error = "";
		String remark = "";
		if ( StringUtils.isBlank( status ) )
		{
			error = statusbox.element( "error" ).getTextTrim();
			remark = statusbox.element( "remark" ).getTextTrim();
		}

		// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
		StringBuffer result = new StringBuffer( taskid );
		result.append( "|" ).append( mobile );

		result.append( "|" ).append( ( StringUtils.isBlank( status ) ? error : status ) );

		result.append( "|" ).append( StringUtils.isBlank( status ) ? remark : GZLYSmsStatus.getStatus( Integer.parseInt( status ) ) );
		result.append( "|" ).append( ( StringUtils.equalsIgnoreCase( status, "10" ) ? "0" : "1" ) );

		result.append( "|" ).append( receivetime );

		return result;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount( String account )
	{
		this.account = account;
	}

	public static String getACTION()
	{
		return ACTION;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid( String userid )
	{
		this.userid = userid;
	}
}
