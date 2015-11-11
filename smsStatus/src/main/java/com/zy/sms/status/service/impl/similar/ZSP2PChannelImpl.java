/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.similar;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.similar.ZSP2PChannelImpl
 *         Desc: 中山點對點
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 12:07
 *   LastChange: 2015-10-12 12:07
 *      History:
 * </pre>
 *********************************************************************************************/
public class ZSP2PChannelImpl extends SimpleChannelFactoryImpl
{
	private static final String CHANNEL_NAME = "【中山点对点】通道";
	public static final  String CHANNEL_CODE = "ZS_P2P_CHANNEL001";

	public ZSP2PChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}
}
