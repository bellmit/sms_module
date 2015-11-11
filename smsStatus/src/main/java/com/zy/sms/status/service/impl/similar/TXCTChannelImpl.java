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
 *     FileName: com.zy.sms.status.service.impl.similar.TXCTChannelImpl
 *         Desc: 天下暢通
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 12:06
 *   LastChange: 2015-10-12 12:06
 *      History:
 * </pre>
 *********************************************************************************************/
public class TXCTChannelImpl extends SimpleChannelFactoryImpl
{
	private static final String CHANNEL_NAME = "【天下暢通】通道";
	public static final  String CHANNEL_CODE = "BJ_CT_CHANNEL001";

	public TXCTChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}
}
