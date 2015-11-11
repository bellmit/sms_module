/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.service;

import org.springframework.scheduling.annotation.Async;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.service.CacheManagerHandlerService
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 17:01
 *   LastChange: 2015-10-13 17:01
 *      History:
 * </pre>
 *********************************************************************************************/
public class CacheManagerHandlerService extends CacheManagerAbstractHandler
{
	@Async
	@Override
	public void handlerRequest( String cacheSign ) throws RuntimeException
	{
		logger.info( "緩存更新系統查詢到當前操作為：{}. 系統進入到相應模塊進行操作.....", cacheSign );
	}
}
