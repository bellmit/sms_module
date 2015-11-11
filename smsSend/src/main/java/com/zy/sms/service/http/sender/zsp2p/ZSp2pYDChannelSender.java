/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.zsp2p;

import com.zy.sms.vo.SmsSendDataInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.zsp2p.ZSp2pYDChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 16:39
 *   LastChange: 2015-11-06 16:39
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "ZS_P2P_CHANNEL001" )
public class ZSp2pYDChannelSender extends ZSp2pChannelFactory
{
	private static final String CHANNEL_NAME = "【中山點對點(移动)】通道";

	public ZSp2pYDChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "ZS_P2P_CHANNEL_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

}
