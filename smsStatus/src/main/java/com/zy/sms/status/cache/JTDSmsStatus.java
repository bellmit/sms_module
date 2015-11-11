/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.sms.status.cache;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.cache.JTDSmsStatus
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 14:33
 *   LastChange: 2015-10-08 14:33
 *      History:
 * </pre>
 *********************************************************************************************/
public enum JTDSmsStatus
{
	//	STATUS_UNSTATUS ( "未收到网关状态报告", -1 ),
	STATUS_0( "下发成功", 0 ),
	STATUS_1( "下发失败", 1 );

	private int    status;
	private String name;

	JTDSmsStatus( String name, int status )
	{
		this.status = status;
		this.name = name;
	}

	public static String getStatus( int status )
	{
		for ( JTDSmsStatus c : JTDSmsStatus.values() )
		{
			if ( c.status == status )
			{
				return c.name;
			}
		}
		return null;
	}
}
