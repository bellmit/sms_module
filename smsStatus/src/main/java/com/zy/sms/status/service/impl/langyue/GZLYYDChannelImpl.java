/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.langyue;

import com.zy.sms.status.service.impl.similar.SimpleChannelFactoryImpl;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.langyue.GZLYYDChannelImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 12:17
 *   LastChange: 2015-10-12 12:17
 *      History:
 * </pre>
 *********************************************************************************************/
public class GZLYYDChannelImpl extends SimpleChannelFactoryImpl
{
	private static final String CHANNEL_NAME = "【广州朗月移动】通道";
	public static final  String CHANNEL_CODE = "GZ_LY_YD_CHANNEL001";

	public GZLYYDChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}
}
