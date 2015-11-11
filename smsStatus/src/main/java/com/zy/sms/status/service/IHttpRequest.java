/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service;

import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.IHttpRequest
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 12:00
 *   LastChange: 2015-10-08 12:00
 *      History:
 * </pre>
 *********************************************************************************************/
public interface IHttpRequest
{
	String send();

	String httpUrl();

	Map<String, String> httpParam();
}
