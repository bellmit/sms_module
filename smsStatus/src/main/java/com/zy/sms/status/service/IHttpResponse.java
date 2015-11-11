/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.sms.status.service;

import java.util.List;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.IHttpResponse
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 12:18
 *   LastChange: 2015-10-08 12:18
 *      History:
 * </pre>
 *********************************************************************************************/
public interface IHttpResponse
{
	List<String> parseResult() throws Exception;
}
