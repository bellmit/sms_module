/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.service;

import com.zy.cache.exception.CacheManagerException;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.service.CacheManagerHandlerAspect
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 16:49
 *   LastChange: 2015-10-13 16:49
 *      History:
 * </pre>
 *********************************************************************************************/
public class CacheManagerHandlerAspect
{
	final static Logger logger = LoggerFactory.getLogger( CacheManagerAbstractHandler.class );

	private void handleAspect( JoinPoint joinPoint )
	{
		try
		{
			String cacheSign = ( String ) joinPoint.getArgs()[ 0 ];
			Class<CacheManagerAbstractHandler> classType = joinPoint.getSignature().getDeclaringType();

			Method getNextHandler = classType.getMethod( "getNextHandler", new Class[] {} );
			CacheManagerAbstractHandler nextHandler = ( CacheManagerAbstractHandler ) getNextHandler.invoke( joinPoint.getThis(), new Object[] {} );

			if ( nextHandler != null )
			{
				nextHandler.handlerRequest( cacheSign );
			}

		}
		catch ( Exception e )
		{
			logger.error( "緩存管理切面實例出現異常:{}.", e.getMessage(), e );
			throw new CacheManagerException( e.getMessage() );
		}
		finally
		{
		}
	}
}
