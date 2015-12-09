/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service;

import com.zy.redis.RedisConstantEx;
import com.zy.redis.SentinelRedisOperator;
import com.zy.sms.status.cache.SmsStatusPushCache;
import com.zy.sms.status.service.biz.IPushOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.RePushSmsStatusServer
 *         Desc: 重推狀態報告
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-09 18:00
 *   LastChange: 2015-10-09 18:00
 *      History:
 * </pre>
 *********************************************************************************************/
@Service
public class RePushSmsStatusServer
{
	final Logger logger = LoggerFactory.getLogger( RePushSmsStatusServer.class );
	private SentinelRedisOperator sentinelRedisOperator;
	private IPushOperate          smsStatusPushServerImpl;

	/*@Scheduled( cron = "0/2 * * * * ?\"" )*/
	public void task()
	{
		logger.info( "开始状态报告重推机制【每5分钟】...." );
		long startTime = System.currentTimeMillis();

		try
		{
			if ( SmsStatusPushCache.size() != 0 )
			{
				Iterator<String> it = SmsStatusPushCache.getIterator();
				while ( it.hasNext() )
				{

					try
					{
						String accout = it.next();
						String pushUrl = sentinelRedisOperator.hget( RedisConstantEx.ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL, accout );
						String pushStatus = SmsStatusPushCache.getSmsStatus( accout );

						logger.info( "REPUSH Merchant account :->" + accout );
						logger.info( "REPUSH Merchant pushUrl :->" + pushUrl );
						logger.info( "REPUSH Merchant pushStatus :->" + pushStatus );

						callPushServer( accout, pushUrl, pushStatus );

					}
					catch ( Exception e )
					{
						logger.error( "REPUSH  sms status :->" + e.getMessage(), e );
					}

				}

			}
		}
		finally
		{
			logger.info( "状态报告重推机制完成，用時 : " + ( System.currentTimeMillis() - startTime ) );
		}
	}

	private void callPushServer( String accout, String pushUrl, String pushStatus ) throws Exception
	{
		smsStatusPushServerImpl.setHttpUrl( pushUrl );
		smsStatusPushServerImpl.setMerchant( accout );
		smsStatusPushServerImpl.setChannelName( "repush" );
		smsStatusPushServerImpl.setParam( pushStatus );
		smsStatusPushServerImpl.pushSms();
	}

	public IPushOperate getSmsStatusPushServerImpl()
	{
		return smsStatusPushServerImpl;
	}

	@Resource
	public void setSmsStatusPushServerImpl( IPushOperate smsStatusPushServerImpl )
	{
		this.smsStatusPushServerImpl = smsStatusPushServerImpl;
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
}
