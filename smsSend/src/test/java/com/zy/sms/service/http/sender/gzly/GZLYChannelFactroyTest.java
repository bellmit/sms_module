/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.gzly;

import com.zy.sms.service.data.IDataManipulation;
import com.zy.sms.vo.SmsSendDataInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.gzly.GZLYChannelFactroyTest
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 11:06
 *   LastChange: 2015-11-09 11:06
 *      History:
 * </pre>
 *********************************************************************************************/
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/spring.xml" } )
public class GZLYChannelFactroyTest
{

	private IDataManipulation<SmsSendDataInfo> iDataManipulation;

	@Test
	public void testOperating() throws Exception
	{
		try
		{
//			for ( int i = 0 ; i < 3000 ; i++ )
			{
				SmsSendDataInfo smsSendDataInfo = new SmsSendDataInfo();
				smsSendDataInfo.setContent( "验证码7662，st&o k?+/”  欢迎使用3G产品 .【朗月yd】" );
				smsSendDataInfo.setMobile( "13422300336" );
				iDataManipulation.operating( smsSendDataInfo );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			Thread.sleep( 300000 );
		}
	}

	public IDataManipulation<SmsSendDataInfo> getiDataManipulation()
	{
		return iDataManipulation;
	}

//	@Resource( name = "gzLYydVIPChannelSender" )
	@Resource( name = "gzLYydChannelSender" )
	public void setiDataManipulation( IDataManipulation<SmsSendDataInfo> iDataManipulation )
	{
		this.iDataManipulation = iDataManipulation;
	}
}
