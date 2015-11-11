/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.gzly;

import com.zy.sms.vo.SmsSendDataInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.gzly.GZLYYDChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 10:47
 *   LastChange: 2015-11-09 10:47
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "GZ_LY_YD_CHANNEL001" )
public class GZLYYDChannelSender extends GZLYChannelFactroy
{
	private static final String CHANNEL_NAME = "【廣州朗月(移動)】通道";

	public GZLYYDChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "GZ_LY_CHANNE_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

	@Value( "${GZ.LY.YD.ACCOUNT}" )
	@Override
	public void setAccount( String account )
	{
		super.setAccount( account );
	}

	@Value( "${GZ.LY.YD.USERID}" )
	@Override
	public void setUserId( String userId )
	{
		super.setUserId( userId );
	}

	@Value( "${GZ.LY.YD.PASSWORD}" )
	@Override
	public void setPassword( String password )
	{
		super.setPassword( password );
	}
}
