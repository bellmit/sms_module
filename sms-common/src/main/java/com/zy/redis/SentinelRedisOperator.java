/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.redis;

import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.redis.SentinelRedisOperator
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-04 16:03
 *   LastChange: 2015-11-04 16:03
 *      History:
 * </pre>
 *********************************************************************************************/
public class SentinelRedisOperator
{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger( SentinelRedisOperator.class );

	private JedisSentinelPool jedisSentinelPool = null;

	public JedisSentinelPool getJedisSentinelPool()
	{
		return jedisSentinelPool;
	}

	@Resource
	public void setJedisSentinelPool( JedisSentinelPool jedisSentinelPool )
	{
		this.jedisSentinelPool = jedisSentinelPool;
	}

	public Long hlen( String key )
	{

		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;

		try
		{
			Pipeline pipelined = jedis.pipelined();
			Response<Long> hlen = pipelined.hlen( key );
			pipelined.syncAndReturnAll();
			return hlen.get();
		}
		catch ( Exception e )
		{
			logger.error( "Redis pipe exception：{}.", e.getMessage(), e );
		}
		finally
		{
			jedis.close();
		}

		return null;

	}

	public String hmset( String key, ConcurrentHashMap<String, String> map )
	{

		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;

		try
		{
			Pipeline pipelined = jedis.pipelined();

			Response<String> hmset = pipelined.hmset( key, map );

			pipelined.syncAndReturnAll();

			return hmset.get();
		}
		catch ( Exception e )
		{
			logger.error( "Redis pipe exception：{}.", e.getMessage(), e );
		}
		finally
		{
			jedis.close();
		}

		return null;
	}

	public List<String> hmget( Collection<String> keySet, String key )
	{
		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;

		try
		{
			Pipeline pipelined = jedis.pipelined();

			String[] array = new String[ keySet.size() ];
			keySet.toArray( array );

			Response<List<String>> hmget = pipelined.hmget( key, array );

			pipelined.syncAndReturnAll();

			return hmget.get();

		}
		catch ( Exception e )
		{
			logger.error( "Redis pipe exception：{}.", e.getMessage(), e );
		}
		finally
		{
			jedis.close();
		}

		return null;
	}

	public String hget( String key, String field )
	{
		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;

		try
		{
			Pipeline pipelined = jedis.pipelined();

			Response<String> hget = pipelined.hget( key, field );

			pipelined.syncAndReturnAll();

			return hget.get();

		}
		catch ( Exception e )
		{
			logger.error( "Redis pipe exception：{}.", e.getMessage(), e );
		}
		finally
		{
			jedis.close();
		}

		return null;
	}

	public Jedis getRedisClient()
	{
		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;
		return jedis;
	}

	public Long hset( String key, String field, String value )
	{
		Jedis jedis = jedisSentinelPool.getResource();
		if ( jedis == null ) return null;

		try
		{
			Pipeline pipelined = jedis.pipelined();

			Response<Long> hset = pipelined.hset( key, field, value );

			pipelined.syncAndReturnAll();

			return hset.get();

		}
		catch ( Exception e )
		{
			logger.error( "Redis pipe exception：{}.", e.getMessage(), e );
		}
		finally
		{
			jedis.close();
		}

		return null;
	}
}
