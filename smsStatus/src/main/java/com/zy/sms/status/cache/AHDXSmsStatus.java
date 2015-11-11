/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.cache;

/**************************************************************************
 * <pre>
 *     FileName: com.zy.statistics.biz.vo.AHDXSmsStatus.java
 *         Desc:
 *      @author: Z_Z.W - myhongkongzhen@gmail.com
 *     @version: 2015年9月10日 上午9:32:22
 *   LastChange: 2015年9月10日 上午9:32:22
 *      History:
 * </pre>
 **************************************************************************/
public enum AHDXSmsStatus
{
	STATUS_UNSTATUS( "未收到网关状态报告", -1 ),
	STATUS_0( "请求成功", 0 ),
	STATUS_SUCCESS( "短信发送成功", 10 ),
	STATUS_FAILD( "短信发送失败", 20 ),
	STATUS_8000( "折扣策略未配置，请联系管理员", 8000 ),
	STATUS_8001( "余额不足", 8001 ),
	STATUS_8002( "当日累计发送量达到日限额", 8002 ),
	STATUS_8003( "时间戳格式错误", 8003 ),
	STATUS_8004( "签名校验失败", 8004 ),
	STATUS_8005( "当日累计发送量达到日限额", 8005 ),
	STATUS_8006( "当日累计发送量达到日限额", 8006 ),
	STATUS_8007( "当日累计发送量达到日限额", 8007 ),
	STATUS_8008( "用户无此模板使用权限或模板未审核或失效", 8008 ),
	STATUS_8009( "模板约定参数个数与SMSText格式不符", 8009 ),
	STATUS_8010( "短信内容包含敏感词", 8010 ),
	STATUS_8998( "其他异常错误", 8998 ),
	STATUS_8999( "数据库入库错误", 8999 );

	private int    status;
	private String name;

	AHDXSmsStatus( String name, int status )
	{
		this.status = status;
		this.name = name;
	}

	public static String getStatus( int status )
	{
		for ( AHDXSmsStatus c : AHDXSmsStatus.values() )
		{
			if ( c.status == status )
			{
				return c.name;
			}
		}
		return null;
	}
}
