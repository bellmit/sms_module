/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.service.test;

import com.zy.cache.entity.MerchantAccountEntity;
import com.zy.redis.RedisConstant;
import com.zy.redis.SentinelRedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.service.returnBrokenResource.SmsSendMockService
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-14 16:00
 *   LastChange: 2015-10-14 16:00
 *      History:
 * </pre>
 *********************************************************************************************/
@Service
public class SmsSendMockService
{
	final static Logger logger = LoggerFactory.getLogger( SmsSendMockService.class );

	private SentinelRedisOperator sentinelRedisOperator;

	final static private CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();

	@Async
	public void mockSendSms( MerchantAccountEntity entity )
	{
		/**
		 消息ID對應發送賬戶，存儲于Redis
		 public static final String ZHIYAN_SMS_STATUS_ACCOUNT_KEY = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "SMSID";
		 消息ID對應客戶的UUID，存儲于Redis
		 public static final String ZHIYAN_SMS_STATUS_UUID_KEY = ZHIYAN_STATUS_KEY_PREFIX + "UUID" + REDIS_SPLIT_STRING + "SMSID";
		 int max=20;
		 int min=10;
		 random.nextInt(max)%(max-min+1) + min;
		 以生成[10,20]随机数为例，首先生成0-20的随机数，然后对(20-10+1)取模得到[0-10]之间的随机数，然后加上min=10，最后生成的是10-20的随机数
		 */

		long startTime = System.currentTimeMillis();

		try
		{
			String smsid = StringUtils.substring( UUID.randomUUID().toString(), 0, 8 );
			String uuid = StringUtils.replace( UUID.randomUUID().toString(), "-", "" );

			logger.debug( "賬戶:{} 發送消息成功，返回消息ID：{} ，用戶UUID：{}.", entity.getMerchantAccount(), smsid, uuid );

			Long uuidR = sentinelRedisOperator.hset( RedisConstant.ZHIYAN_SMS_STATUS_UUID_KEY, smsid, uuid );
			if ( uuidR.intValue() == 0 )
			{
				logger.info( "插入uuid失敗.=key={}=smsid={}=uuid={}.", RedisConstant.ZHIYAN_SMS_STATUS_UUID_KEY, smsid, uuid );
				return;
			}
			Long accountR = sentinelRedisOperator.hset( RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_KEY, smsid, entity.getMerchantAccount() );
			if ( accountR.intValue() == 0 )
			{
				logger.info( "插入賬戶失敗。==key={}==smsid=={}==account=={}==", RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_KEY, smsid, entity.getMerchantAccount() );
				return;
			}
			copyOnWriteArrayList.add( smsid );

			if ( copyOnWriteArrayList.size() % 500 == 0 )
				logger.info( "緩存中的短信條數：{}==smsid:{}==account:{}.", copyOnWriteArrayList.size(), smsid, entity.getMerchantAccount() );

		}
		catch ( Exception e )
		{
			logger.error( "發送短信出現錯誤:{}", e.getMessage(), e );
		}
		finally
		{
			logger.debug( "發送短信成功，用時：{} ms" + ".", ( System.currentTimeMillis() - startTime ) );
		}

	}

	public String mockCatchStatus( String username )
	{

		StringBuilder sb  = new StringBuilder();
		final int     idx = copyOnWriteArrayList.size();
		logger.info( "===========本次請求消息ID記錄數:{}=================.", idx );

//		final int size = ThreadLocalRandom.current().nextInt( idx - 100 ) % ( idx - 100 - 14 + 1 ) + 14;
		for ( String s : copyOnWriteArrayList )
		{
			int max = copyOnWriteArrayList.size();
			int index = ThreadLocalRandom.current().nextInt( max ) % ( max - 0 + 0 ) + 0;
			String smsid = copyOnWriteArrayList.remove( index );

			long mobile = Math.round( ( ThreadLocalRandom.current().nextDouble() * ( 9999999999l - 100000000l ) ) + 100000000l );
			int sendresult = ThreadLocalRandom.current().nextInt( 1 ) % 2;
			String datetime = DateFormatUtils
					.format( new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern() );

			sb.append( smsid );
			sb.append( "|" ).append( "1" ).append( mobile );
			sb.append( "|" ).append( sendresult );
			sb.append( "|" ).append( datetime ).append( "^" );

		}

//				"74386746486091|13718249651|1|2011-03-19 24:50:00^143867464287031|13839399808|0|2015-03-19 05:50:00^14386746487031|13839399808|0|2015-03-19 05:50:00^743867464486091|13839399808|0|2015-03-19 05:50:00";
		if ( sb.length() == 0 )
			return "74386746486091|13718249651|1|2011-03-19 24:50:00^143867464287031|13839399808|0|2015-03-19 05:50:00^14386746487031|13839399808|0|2015-03-19 05:50:00^743867464486091|13839399808|0|2015-03-19 05:50:00";

		String status = sb.toString().substring( 0, sb.toString().length() - 1 );

		return status;
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
