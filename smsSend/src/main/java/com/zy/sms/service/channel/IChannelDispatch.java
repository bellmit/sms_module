/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.channel;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.channel.IChannelDispatch
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 11:05
 *   LastChange: 2015-11-05 11:05
 *      History:
 * </pre>
 *********************************************************************************************/
public interface IChannelDispatch<T>
{
	void operating( T t ) throws Exception;
}
