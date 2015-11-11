/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.controller;

import com.zy.cache.service.CacheManagerAbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.controller.CacheManageController
 *         Desc: 緩存更新控制
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 15:01
 *   LastChange: 2015-10-13 15:01
 *      History:
 * </pre>
 *********************************************************************************************/
@Controller @RequestMapping( value = "/account" ) public class CacheManageController
{
	final static Logger logger = LoggerFactory.getLogger( CacheManageController.class );

	private CacheManagerAbstractHandler cacheManagerHandlerService;

	@RequestMapping( value = "/{cacheSign}", produces = "text/plain;charset=UTF-8" )
	@ResponseBody
	public String cacheManager( @PathVariable String cacheSign, HttpServletRequest request )
	{
		try
		{
			cacheManagerHandlerService.handlerRequest( cacheSign );
		}
		catch ( Exception e )
		{
			logger.error( "本次緩存更新出現異常 : [{}].", e.getMessage(), e );
			return e.getMessage();
		}
		return "緩存更新成功！！！！";
	}

	public CacheManagerAbstractHandler getCacheManagerHandlerService()
	{
		return cacheManagerHandlerService;
	}

	@Resource
	public void setCacheManagerHandlerService( CacheManagerAbstractHandler cacheManagerHandlerService )
	{
		this.cacheManagerHandlerService = cacheManagerHandlerService;
	}
}
