/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.langyue;

import com.zy.sms.status.service.impl.similar.SimpleChannelFactoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.langyue.GZLYVIPChannelImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-28 10:10
 *   LastChange: 2015-10-28 10:10
 *      History:
 * </pre>
 *********************************************************************************************/
public class GZLYVIPChannelImpl extends SimpleChannelFactoryImpl
{
	private static final String CHANNEL_NAME = "【广州朗月VIP移动】通道";
	public static final  String CHANNEL_CODE = "GZ_LY_YD_VIP_SINGLE_CHANNEL";

	public GZLYVIPChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}

	@Override public String httpUrl()
	{
		StringBuilder sb = new StringBuilder( url );

		sb.append( "?userid=" ).append( userid );
		sb.append( "&password=" ).append( password );

		return sb.toString();
	}

	@Override protected StringBuffer constructRlt( Element statusbox )
	{
		String mobile = statusbox.element( "recvnumber" ).getTextTrim();

		if ( !StringUtils.isNotBlank( mobile ) )
		{
			logger.warn( channelName + "解析状态报告目标号码为空返回。" );
			return null;
		}

		String taskid = statusbox.element( "msgid" ).getTextTrim();
		String status = statusbox.element( "status" ).getTextTrim();
		String receivetime = statusbox.element( "recvtime" ).getTextTrim();

//		String error = "";
//		String remark = "";
//		if ( StringUtils.isBlank( status ) )
//		{
//			error = statusbox.element( "error" ).getTextTrim();
//			remark = statusbox.element( "remark" ).getTextTrim();
//		}

		// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
		StringBuffer result = new StringBuffer( taskid );

		result.append( "|" ).append( mobile );
//		result.append( "|" ).append( ( StringUtils.isBlank( status ) ? error : status ) );
		result.append( "|" ).append( status );
//		result.append( "|" ).append( StringUtils.isBlank( status ) ? remark : GZLYSmsStatus.getStatus( Integer.parseInt( status ) ) );
		result.append( "|" ).append( statusbox.element( "info" ).getTextTrim() );
		result.append( "|" ).append( ( StringUtils.equalsIgnoreCase( status, "0" ) ? "0" : "1" ) );
		result.append( "|" ).append( receivetime );

		return result;
	}
}
