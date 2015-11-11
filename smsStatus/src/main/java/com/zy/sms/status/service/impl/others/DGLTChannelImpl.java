/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.others;

import com.zy.sms.status.cache.DGLTSmsStatus;
import com.zy.sms.status.service.impl.CatchStatusServerImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.others.DGLTChannelImpl
 *         Desc: 東莞聯通
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 12:24
 *   LastChange: 2015-10-12 12:24
 *      History:
 * </pre>
 *********************************************************************************************/
public class DGLTChannelImpl extends CatchStatusServerImpl
{

	@Value( "${DG.CHANNEL.LOGIN.NAME}" ) private String name;
	@Value( "${DG.CHANNEL.PASSWORD}" ) private   String password;
	@Value( "${DG.CHANNEL.STATUS.URL}" ) private String url;
	@Value( "${DG.CHANNEL.SPCODE}" ) private     String spcode;
	private static final String CHANNEL_NAME = "【东莞联通】通道";
	public static final  String CHANNEL_CODE = "DG_UNICOM_CHANNEL001";

	public DGLTChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}

	@Override
	public String httpUrl()
	{
		StringBuilder sb = new StringBuilder( url );

		sb.append( "?SpCode=" ).append( spcode );
		sb.append( "&LoginName=" ).append( name );
		sb.append( "&Password=" ).append( password );

		return sb.toString();
	}

	@Override
	public Map<String, String> httpParam()
	{
		return null;
	}

	@Override
	public List<String> parseResult() throws Exception
	{
		try
		{
			// result=0&out=436267125669,18535713929,0;436267125670,18535713929,0;436267125671,15626573130,0;
			logger.debug( "DGLTChannel==>>>" + result );
			String[] resultArr = StringUtils.splitPreserveAllTokens( result, "&" );

			// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
			if ( !StringUtils.equalsIgnoreCase( StringUtils.substring( resultArr[ 0 ], "result=".length() ), "0" ) ) return null;

			String rs = StringUtils.substring( resultArr[ 1 ], "out=".length() );
			// 436267125669,18535713929,0;436267125670,18535713929,0;436267125671,15626573130,0;
			if ( StringUtils.containsIgnoreCase( rs, "," ) ) rs = StringUtils.replace( rs, ",", "|" );

			List<String> list = new ArrayList<String>();
			resultArr = StringUtils.splitPreserveAllTokens( rs, ";" );
			for ( String res : resultArr )
			{
				String str = constructRlt( res );
				list.add( str );
			}

			logger.debug( "-->>list : " + list );

			return list;
		}
		catch ( Exception e )
		{
			logger.error( "解析" + CHANNEL_NAME + "状态报告出错: " + e.getMessage(), e );
			return null;
		}
		finally
		{
			logger.info( "{}狀態報告結果解析完成！", channelName );
		}
	}

	private String constructRlt( String res )
	{
		StringBuilder str = new StringBuilder( res );

		int idx2 = res.indexOf( "|", res.indexOf( "|" ) + 1 );
		int ss   = ( StringUtils.isBlank( res.substring( idx2 + 1, res.length() ) ) ? 0 : Integer.parseInt( res.substring( idx2 + 1, res.length() ) ) );

		str.append( "|" ).append( DGLTSmsStatus.getStatus( ss ) );
		str.append( "|" ).append( ( ( ss == 0 ) ? 0 : 1 ) );
		str.append( "|" ).append(
				DateFormatUtils.format( new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern() ) );

//				res += "|" + DGLTSmsStatus.getStatus( ss );
//				res += "|" + ( ( ss == 0 ) ? 0 : 1 );
//				res += "|" + DateFormatUtils.format( new Date(), DateFormatUtils.ISO_DATETIME_FORMAT.getPattern() );

		return str.toString();
	}
}
