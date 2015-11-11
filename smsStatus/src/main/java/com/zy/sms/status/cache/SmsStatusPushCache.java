/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.cache;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.cache.SmsStatusPushCache
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-09 17:27
 *   LastChange: 2015-10-09 17:27
 *      History:
 * </pre>
 *********************************************************************************************/
public class SmsStatusPushCache
{
	private static       ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();
	private static final ReadWriteLock                 rwl   = new ReentrantReadWriteLock();

	private SmsStatusPushCache()
	{
		super();
	}

	public static boolean remove( String account, String smsStatus )
	{
		rwl.writeLock().lock();
		try
		{
			return cache.remove( account, smsStatus );
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	public static String getSmsStatus( String account )
	{
		rwl.readLock().lock();
		try
		{
			return cache.get( account );
		}
		finally
		{
			rwl.readLock().unlock();
		}
	}

	public static Iterator<String> getIterator()
	{
		rwl.readLock().lock();
		try
		{

			return cache.keySet().iterator();
		}
		finally
		{
			rwl.readLock().unlock();
		}
	}

	public static int size()
	{
		return cache.size();
	}

	/**
	 * Create by : 2015年8月17日 上午10:28:07
	 */
	public static void put( String key, String value )
	{
		rwl.writeLock().lock();
		try
		{
			cache.put( key, value );
		}
		finally
		{
			rwl.writeLock().unlock();
		}

	}
}
