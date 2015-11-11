/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.jutongda;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.jutongda.JTDChannelImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 14:35
 *   LastChange: 2015-10-08 14:35
 *      History:
 * </pre>
 *********************************************************************************************/
public class JTDYDChannelImpl extends JTDChannelFactoryImpl
{
	private static final String CHANNEL_NAME = "【北京聚通达三网】通道";
	public static final  String CHANNEL_CODE = "BJ_JTD_DX_SINGLE_CHANNEL";

	public JTDYDChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}

}
