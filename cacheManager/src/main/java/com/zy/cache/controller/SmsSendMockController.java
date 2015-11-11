/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.cache.controller;

import com.zy.cache.dao.MerchantAccountEntityMapper;
import com.zy.cache.entity.MerchantAccountEntity;
import com.zy.cache.service.test.SmsSendMockService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.cache.controller.SmsSendMockController
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-14 15:02
 *   LastChange: 2015-10-14 15:02
 *      History:
 * </pre>
 *********************************************************************************************/
@Controller @RequestMapping( value = "/sms" ) public class SmsSendMockController
{
	final static Logger logger = LoggerFactory.getLogger( SmsSendMockController.class );

	private SmsSendMockService          smsSendMockService;
	private MerchantAccountEntityMapper merchantAccountEntityMapper;

	@RequestMapping( value = "/send", produces = "text/plain;charset=UTF-8" ) @ResponseBody public String sendsms( HttpServletRequest request )
	{
		try
		{
			final String[] accountArr = getAccountArr();
			final List<MerchantAccountEntity> entityList = merchantAccountEntityMapper.selectAllAccount();

			final int size = entityList.size() - 1;

			boolean sign = true;
			while ( sign )
			{
				int idx = ThreadLocalRandom.current().nextInt( size ) % ( size - 1 + 1 ) + 1;
				MerchantAccountEntity entity = entityList.get( idx );
				if ( entity.getMerchantAccount() == null || !ArrayUtils.contains( accountArr, entity.getMerchantAccount() ) ) continue;
				smsSendMockService.mockSendSms( entity );
			}

		}
		catch ( Exception e )
		{
			logger.error( "本次測試發送短信出現異常 : [{}].", e.getMessage(), e );
			return e.getMessage();
		}
		return "短信測試發送成功！！！！";
	}

	private String[] getAccountArr()
	{
		return new String[] { "chuzhong" ,
							  "gjy123" ,
							  "" ,
							  "TouchpalSMS" ,
							  "" ,
							  "TEST" ,
							  "ZHIYAN" ,
							  "thomas" ,
							  "meilele" ,
							  "yudaowo1314" ,
							  "xavier" ,
							  "szhezuo" ,
							  "yijiwang" ,
							  "houmingjun" };
	}

	@RequestMapping( value = "/status/jtd", produces = "text/plain;charset=UTF-8" ) @ResponseBody public String catchstatus( HttpServletRequest request )
	{
		String status;
		try
		{
			status = smsSendMockService.mockCatchStatus( request.getParameter( "username" ) );
		}
		catch ( Exception e )
		{
			logger.error( "本次測試發送短信出現異常 : [{}].", e.getMessage(), e );
			return e.getMessage();
		}
		return status;

	}

	public SmsSendMockService getSmsSendMockService()
	{
		return smsSendMockService;
	}

	@Resource public void setSmsSendMockService( SmsSendMockService smsSendMockService )
	{
		this.smsSendMockService = smsSendMockService;
	}

	public MerchantAccountEntityMapper getMerchantAccountEntityMapper()
	{
		return merchantAccountEntityMapper;
	}

	@Resource public void setMerchantAccountEntityMapper( MerchantAccountEntityMapper merchantAccountEntityMapper )
	{
		this.merchantAccountEntityMapper = merchantAccountEntityMapper;
	}
}
