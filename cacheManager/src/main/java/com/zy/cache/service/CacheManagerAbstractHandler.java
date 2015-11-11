/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.service.CacheManagerAbstractHandler
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 16:39
 *   LastChange: 2015-10-13 16:39
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class CacheManagerAbstractHandler
{
	protected final static Logger                      logger      = LoggerFactory.getLogger( CacheManagerAbstractHandler.class );
	protected              CacheManagerAbstractHandler nextHandler = null;
	protected              String                      cacheSign   = null;

	public abstract void handlerRequest( String cacheSign ) throws RuntimeException;

	public CacheManagerAbstractHandler getNextHandler()
	{
		return nextHandler;
	}

	public void setNextHandler( CacheManagerAbstractHandler nextHandler )
	{
		this.nextHandler = nextHandler;
	}

	public String getCacheSign()
	{
		return cacheSign;
	}

	public void setCacheSign( String cacheSign )
	{
		this.cacheSign = cacheSign;
	}
}
