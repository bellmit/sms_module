/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.redis.ShardRedisOperator
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 16:27
 *   LastChange: 2015-10-08 16:27
 *      History:
 * </pre>
 *********************************************************************************************/
public class ShardRedisOperator
{
	protected final static Logger logger = LoggerFactory.getLogger( ShardRedisOperator.class );

	private ShardedJedisPool shardedJedisPool;

	public ShardedJedisPool getShardedJedisPool()
	{
		return shardedJedisPool;
	}

	@Resource
	public void setShardedJedisPool( ShardedJedisPool shardedJedisPool )
	{
		this.shardedJedisPool = shardedJedisPool;
	}

	public ShardedJedis getRedisClient()
	{
		try
		{
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		}
		catch ( Exception e )
		{
			logger.error( "Get Redis Clent error:{}.", e.getMessage(), e );
		}
		return null;
	}

	public void returnBrokenResource( ShardedJedis shardedJedis )
	{
//		shardedJedisPool.returnBrokenResource( shardedJedis );
		shardedJedis.close();
	}

	public void returnResource( ShardedJedis shardedJedis )
	{
//		shardedJedisPool.returnResource( shardedJedis );
		shardedJedis.close();
	}

	public String set( String key, String value )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null )
		{
			return result;
		}
		try
		{
			result = shardedJedis.set( key, value );
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public String set( String key, Map<String, String> data )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null )
		{
			return result;
		}
		try
		{
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			Response<String> response = pipeline.hmset( key, data );
			pipeline.syncAndReturnAll();
			return response.get();
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public String phget( String key )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			result = shardedJedis.get( key );
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public String setex( String key, int seconds, String value )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			result = shardedJedis.setex( key, seconds, value );
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public String hget( String key, String field )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			result = shardedJedis.hget( key, field );
			return result;
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public String phget( String key, String field )
	{
		String result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			Response<String> response = pipeline.hget( key, field );
			pipeline.syncAndReturnAll();
			result = response.get();
			return result;

		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public Long hlen( String key )
	{
		Long result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			Response<Long> response = pipeline.hlen( key );
			pipeline.syncAndReturnAll();
			return response.get();
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public Long hset( String key, String field, String value )
	{
		Long result = null;

		ShardedJedis shardedJedis = getRedisClient();
		if ( shardedJedis == null ) { return result; }
		try
		{
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			Response<Long> response = pipeline.hset( key, field, value );
			pipeline.syncAndReturnAll();
			return response.get();
		}
		catch ( Exception e )
		{
			logger.error( "Set redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( shardedJedis );
		}
		finally
		{
			returnResource( shardedJedis );
		}

		return result;
	}

	public List<String> hmget( Collection<String> keySet, String key )
	{
		ShardedJedis jedis = getRedisClient();
		if ( jedis == null ) { return null; }
		try
		{
			ShardedJedisPipeline pipelined = jedis.pipelined();

			String[] array = new String[ keySet.size() ];
			keySet.toArray( array );

			Response<List<String>> response = pipelined.hmget( key, array );
			pipelined.syncAndReturnAll();
			return response.get();
		}
		catch ( Exception e )
		{
			logger.error( "Get hmget redis cache data error : {}.", e.getMessage(), e );
			returnBrokenResource( jedis );
		}
		finally
		{
			returnResource( jedis );
		}
		return null;
	}

}
