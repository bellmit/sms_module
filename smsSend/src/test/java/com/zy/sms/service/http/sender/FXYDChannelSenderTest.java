/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender;

import com.zy.redis.RedisConstant;
import com.zy.redis.SentinelRedisOperator;
import com.zy.sms.service.data.IDataManipulation;
import com.zy.sms.vo.SmsSendDataInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.FXYDChannelSenderTest
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 15:31
 *   LastChange: 2015-11-06 15:31
 *      History:
 * </pre>
 *********************************************************************************************/
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/spring.xml" } )
public class FXYDChannelSenderTest
{
	private IDataManipulation<SmsSendDataInfo> iDataManipulation;
	private SentinelRedisOperator              sentinelRedisOperator;

	@Test
	public void testOperating() throws Exception
	{
		try
		{
			String[] arr = { "18820238065"  /*, "15098648522" , "17001930700" , "17001930700" , "18998561327" , "13686862157" , "13686862157" */ };

//			for ( int i = 0 ; i < 1000 ; i++ )
			{
				for ( String mobile : arr )
				{
					SmsSendDataInfo smsSendDataInfo = new SmsSendDataInfo();
					smsSendDataInfo.setContent( "您好：您的注册码是" + ( ( long ) ( Math.random() * 10000 ) ) + "【技FXYD】" );
					smsSendDataInfo.setMobile( mobile );
					smsSendDataInfo.setAccount( "飛信移動" );
					smsSendDataInfo.setSmsid( UUID.randomUUID().toString().replaceAll( "-", "" ) );
					iDataManipulation.operating( smsSendDataInfo );
				}
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

	@Test
	public void testRedisValue()
	{
		try
		{
			String smsid   = "df9cdae24f9443b7b329c960a6acc612";
			String account = "chuzhong";
			System.out.println( sentinelRedisOperator.hset( RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_KEY, smsid, account ) );

			String uuid = StringUtils.replace( UUID.randomUUID().toString(), "-", "" );
			System.out.println( sentinelRedisOperator.hset( RedisConstant.ZHIYAN_SMS_STATUS_UUID_KEY, smsid, uuid ) );

			ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
			data.put( account, "http://113.31.89.135:19999/zyccreport.do" );
			System.out.println( sentinelRedisOperator.hmset( RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL, data ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public SentinelRedisOperator getSentinelRedisOperator()
	{
		return sentinelRedisOperator;
	}

	@Resource
	public void setSentinelRedisOperator( SentinelRedisOperator sentinelRedisOperator )
	{
		this.sentinelRedisOperator = sentinelRedisOperator;
	}

	public IDataManipulation<SmsSendDataInfo> getiDataManipulation()
	{
		return iDataManipulation;
	}

	@Resource( name = "FX_YD_CHANNEL_001" )
	public void setiDataManipulation( IDataManipulation<SmsSendDataInfo> iDataManipulation )
	{
		this.iDataManipulation = iDataManipulation;
	}
}
