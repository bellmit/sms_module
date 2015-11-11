/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.biz;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.biz.IPushOperate
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 09:58
 *   LastChange: 2015-10-12 09:58
 *      History:
 * </pre>
 *********************************************************************************************/
public interface IPushOperate
{
	void pushSms();

	void setHttpUrl( String pushUrl );

	void setParam( String pushParam );

	void setMerchant( String merchant );

	void setChannelName( String channelName );
}
