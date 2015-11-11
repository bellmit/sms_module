/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.jutongda;

import com.zy.sms.status.service.impl.CatchStatusServerImpl;
import com.zy.sms.status.cache.JTDSmsStatus;
import com.zy.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.jutongda.JTDChannelFactoryImpl
 *         Desc: 聚通達抓取狀態報告父類
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 14:30
 *   LastChange: 2015-10-08 14:30
 *      History:
 * </pre>
 *********************************************************************************************/
public class JTDChannelFactoryImpl extends CatchStatusServerImpl
{

	protected String userName;
	protected String userPassword;

	@Value( "${BJ.JTD.STATUS.URL}" ) private String httpUrl;

	@Override public String httpUrl()
	{
		StringBuffer sb = new StringBuffer();
		sb.append( httpUrl );
		sb.append( "?username=" ).append( userName );// 用户名
		sb.append( "&pwd=" ).append( userPassword );// 密码

		return sb.toString();
	}

	@Override public Map<String, String> httpParam()
	{
		return null;
	}

	@Override public List<String> parseResult() throws Exception
	{
		// result = "74386746486091|13718249651|0|2011-03-19 24:50:00^14386746487031|13839399808|0|2015-03-19 05:50:00";
		// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
		List<String> list = null;
		try
		{
			if ( !StringUtils.contains( result, '^' ) && !StringUtils.contains( result, '|' ) ) return null;
			String array[] = StringUtils.splitPreserveAllTokens( result, '^' );
			List<String> oList = Arrays.asList( array );
			list = new ArrayList<String>();

			for ( String str : oList )
			{
				StringBuffer sb = parseStrResult( str );

				list.add( sb.toString() );
			}

			if ( list.isEmpty() )
			{
				throw new RuntimeException( channelName + "解析后沒有可用狀態報告," + ObjectUtil.convertUnicode( result ) );
			}

			return list;
		}
		catch ( Exception e )
		{
			logger.error( "解析" + channelName + "状态报告出错: " + e.getMessage(), e );
			throw new RuntimeException( e );
		}
		finally
		{
			logger.info( "{}狀態報告結果解析完成！", channelName );
		}
	}

	private StringBuffer parseStrResult( String str )
	{
		int idx1 = str.indexOf( "|" );
		int idx2 = str.indexOf( "|", idx1 + 1 );
		int idx3 = str.indexOf( "|", idx2 + 1 );
		String ss = str.substring( idx2 + 1, idx3 );
		int ssi = ( StringUtils.isBlank( ss ) ? 0 : Integer.parseInt( ss ) );
		StringBuffer sb = new StringBuffer( str.substring( 0, idx1 ) );
		sb.append( "|" ).append( str.substring( idx1 + 1, idx2 ) );
		sb.append( "|" ).append( ss );
		sb.append( "|" ).append( JTDSmsStatus.getStatus( ssi ) );
		sb.append( "|" ).append( ( ( ssi == 0 ) ? 0 : 1 ) );
		sb.append( "|" ).append( str.substring( idx3 + 1, str.length() ) );
		return sb;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword( String userPassword )
	{
		this.userPassword = userPassword;
	}
}
