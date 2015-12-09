/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.service.impl;

import com.zy.cache.dao.MerchantUrlIpMapper;
import com.zy.cache.entity.MerchantUrlIpEntity;
import com.zy.cache.exception.CacheManagerException;
import com.zy.cache.service.CacheManagerAbstractHandler;
import com.zy.redis.RedisConstantEx;
import com.zy.redis.SentinelRedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.service.impl.MerchantPushUrlCacheUpdateService
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 17:00
 *   LastChange: 2015-10-13 17:00
 *      History:
 * </pre>
 *********************************************************************************************/
public class MerchantPushUrlCacheUpdateService extends CacheManagerAbstractHandler
{
	private MerchantUrlIpMapper   merchantUrlIpMapper;
	private SentinelRedisOperator sentinelRedisOperator;

	@Async
	@Override
	public void handlerRequest( String cacheSign ) throws RuntimeException
	{
		if ( StringUtils.equalsIgnoreCase( this.cacheSign, cacheSign ) )
		{
			long startTime = System.currentTimeMillis();
			try
			{
				logger.info( "開始更新 賬戶 綁定的 推送 URL 緩存信息。。。。。。" );

				// -- 2015-10-13 18:03:34 數據庫中查詢 賬戶與推送url信息
				List<MerchantUrlIpEntity> entityList = merchantUrlIpMapper.selectAll();
				if ( entityList != null && !entityList.isEmpty() ) updateDataToCache( entityList );

			}
			catch ( Exception e )
			{
				logger.error( "更新 賬戶 綁定推送 URL 緩存信息出現異常：{}.", e.getMessage(), e );
				throw new CacheManagerException( "更新賬戶綁定推送URL緩存信息異常:" + e.getMessage(), e );
			}
			finally
			{
				logger.info( "完成更新 賬戶 綁定的 推送 URL 緩存信息,用時：{} ms。。。。。。", ( System.currentTimeMillis() - startTime ) );
			}
		}
	}

	private void updateDataToCache( List<MerchantUrlIpEntity> entityList )
	{
		Jedis redisClient = null;
		try
		{
//{"bandIp":"127.0.0.1,118.195.138.97,14.152.94.42","id":7,"isValideIp":0,"merchantAccount":"thomas","pushUrl":""}
			// -- 2015-10-13 18:03:58 根據查詢出來的數據，更新至緩存中
			//pushUrl,bandIp,isValideIp
			redisClient = sentinelRedisOperator.getRedisClient();
			if ( redisClient == null ) return;
			Pipeline pipelined = redisClient.pipelined();

			updateCache( entityList, pipelined );

			pipelined.syncAndReturnAll();
		}
		catch ( Exception e )
		{
			logger.error( "批量更新redis緩存出現錯誤：{}", e.getMessage(), e );
		}
		finally
		{
		}

	}

	private void updateCache( List<MerchantUrlIpEntity> entityList, Pipeline pipelined )
	{
//		Map<String, String> data = new HashMap<String, String>();
//		for ( MerchantUrlIpEntity entity : entityList )
//		{
//			String jsonString = JSON.toJSONString( entity );
//			logger.info( "==={}===", jsonString );
//			data.put( entity.getMerchantAccount(),jsonString );
//		}
//		pipelined.hmset( RedisConstantEx.ZHIYAN_SMS_STATUS_ACCOUNT_BAND, data );

		updateDataToCache( entityList, pipelined, "ip" );
		updateDataToCache( entityList, pipelined, "url" );
		updateDataToCache( entityList, pipelined, "ipValid" );
	}

	private void updateDataToCache( List<MerchantUrlIpEntity> entityList, Pipeline pipelined, String dataCache )
	{
		Map<String, String> data = new HashMap<String, String>();
		for ( MerchantUrlIpEntity entity : entityList )
		{
			if ( dataCache.equals( "ip" ) ) data.put( entity.getMerchantAccount(), entity.getBandIp() );
			else if ( dataCache.equals( "url" ) ) data.put( entity.getMerchantAccount(), entity.getPushUrl() );
			else data.put( entity.getMerchantAccount(), String.valueOf( entity.getIsValideIp() ) );
		}

		if ( dataCache.equals( "ip" ) ) pipelined.hmset( RedisConstantEx.ZHIYAN_SMS_STATUS_ACCOUNT_BAND_IP, data );
		else if ( dataCache.equals( "url" ) ) pipelined.hmset( RedisConstantEx.ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL, data );
		else pipelined.hmset( RedisConstantEx.ZHIYAN_SMS_STATUS_ACCOUNT_BAND_IP_VALID, data );
	}

	public MerchantUrlIpMapper getMerchantUrlIpMapper()
	{
		return merchantUrlIpMapper;
	}

	@Resource
	public void setMerchantUrlIpMapper( MerchantUrlIpMapper merchantUrlIpMapper )
	{
		this.merchantUrlIpMapper = merchantUrlIpMapper;
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
