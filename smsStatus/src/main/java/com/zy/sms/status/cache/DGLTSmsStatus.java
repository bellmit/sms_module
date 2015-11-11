/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.cache;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.cache.DGLTSmsStatus
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 12:26
 *   LastChange: 2015-10-12 12:26
 *      History:
 * </pre>
 *********************************************************************************************/
public enum DGLTSmsStatus
{
	//	STATUS_UNSTATUS ( "未收到网关状态报告", -1 ),
	STATUS_0( "短信发送成功", 0 ),
	STATUS_1( "提交参数不能为空", 1 ),
	STATUS_2( "账号无效", 2 ),
	STATUS_3( "账号密码错误", 3 ),
	STATUS_20( "系统错误", 20 );

	private int    status;
	private String name;

	DGLTSmsStatus( String name, int status )
	{
		this.status = status;
		this.name = name;
	}

	public static String getStatus( int status )
	{
		for ( DGLTSmsStatus c : DGLTSmsStatus.values() )
		{
			if ( c.status == status )
			{
				return c.name;
			}
		}
		return null;
	}
}
