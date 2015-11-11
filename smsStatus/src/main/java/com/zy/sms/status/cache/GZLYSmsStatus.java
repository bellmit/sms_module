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
 *     FileName: com.zy.sms.status.cache.GZLYSmsStatus
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 11:52
 *   LastChange: 2015-10-12 11:52
 *      History:
 * </pre>
 *********************************************************************************************/
public enum GZLYSmsStatus
{
	//	STATUS_UNSTATUS ( "未收到网关状态报告", -1 ),
	STATUS_1( "用户名或密码不能为空", 1 ),
	STATUS_2( "用户名或密码错误", 2 ),
	STATUS_10( "发送成功", 10 ),
	STATUS_20( "发送失败", 20 ),
	STATUS_3( "该用户不允许查看状态报告", 3 ),
	STATUS_4( "参数不正确", 4 );

	private int    status;
	private String name;

	GZLYSmsStatus( String name, int status )
	{
		this.status = status;
		this.name = name;
	}

	public static String getStatus( int status )
	{
		for ( GZLYSmsStatus c : GZLYSmsStatus.values() )
		{
			if ( c.status == status )
			{
				return c.name;
			}
		}
		return null;
	}
}
