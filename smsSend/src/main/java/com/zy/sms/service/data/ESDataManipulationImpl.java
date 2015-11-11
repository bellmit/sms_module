/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.data;

import com.zy.sms.vo.EsDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.data.ESDataManipulationImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 14:15
 *   LastChange: 2015-11-05 14:15
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "esDataManipulationImpl" )
public class ESDataManipulationImpl implements IDataManipulation<EsDataInfo>
{
	private static final Logger logger = LoggerFactory.getLogger( ESDataManipulationImpl.class );

	@Async( value = "asyncDefaultExecutor" )
	@Override
	public void operating( EsDataInfo esDataInfo )
	{
		long startTime = System.currentTimeMillis();
		try
		{
			logger.info( "開始準備ES存儲發送記錄-->>>>{}.", esDataInfo.toString() );
		}
		catch ( Exception e )
		{
			logger.error( "將發送結果存入ES出現異常：{}.", e.getMessage(), e );
		}
		finally
		{
			logger.info( "將發送結果存入ES完成，用時：{} ms.", ( System.currentTimeMillis() - startTime ) );
		}
	}
}
