/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.dglt;

import com.zy.sms.vo.SmsSendDataInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.dglt.DGUnicomYDChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 12:12
 *   LastChange: 2015-11-09 12:12
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "DG_UNICOM_CHANNEL003" )
public class DGUnicomLTChannelSender extends DGUnicomChannelFactory
{
	private static final String CHANNEL_NAME = "【東莞聯通(聯通)】通道";

	public DGUnicomLTChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "DG_UNICOM_CHANNEL_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}
}
