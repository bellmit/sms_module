/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.data;

import com.zy.sms.service.exception.SenderException;
import com.zy.sms.service.http.HttpServerFactory;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.data.HttpDataManipulationImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 14:11
 *   LastChange: 2015-11-05 14:11
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class HttpDataManipulationImpl extends HttpServerFactory implements IDataManipulation<SmsSendDataInfo>
{
	private final static Logger                        logger                 = LoggerFactory.getLogger( HttpDataManipulationImpl.class );
	protected            IDataManipulation<EsDataInfo> esDataManipulationImpl = null;

	protected void operate( SmsSendDataInfo smsSendDataInfo )
	{
		try
		{
			httpParam( smsSendDataInfo );

			send();

			esDataManipulationImpl.operating( parseResult( smsSendDataInfo ) );
		}
		catch ( SenderException e )
		{
			//TODO -- 2015-11-5 15:29:32 重發機制
			logger.error( "{}發送短信出現異常：{}.", channelName, e.getMessage(), e );
		}
		catch ( Exception e )
		{
			logger.error( "{}發送及處理短信發送記錄出現異常：{}.", channelName, e.getMessage(), e );
		}

	}

	public IDataManipulation<EsDataInfo> getEsDataManipulationImpl()
	{
		return esDataManipulationImpl;
	}

	@Resource( name = "esDataManipulationImpl" )
	public void setEsDataManipulationImpl( IDataManipulation<EsDataInfo> esDataManipulationImpl )
	{
		this.esDataManipulationImpl = esDataManipulationImpl;
	}
}
