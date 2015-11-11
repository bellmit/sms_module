/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.bjjtd;

import com.zy.sms.vo.SmsSendDataInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.bjjtd.JTDSWYDChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-09 14:19
 *   LastChange: 2015-11-09 14:19
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "BJ_JTD_DX_SINGLE_CHANNEL" )
public class JTDDXSingleChannelSender extends JTDChannelFactory
{
	private static final String CHANNEL_NAME = "【聚通達電信(單網)】通道";

	public JTDDXSingleChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "BJ_JTD_CHANNEL_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

	@Value( "${BJ.JTD.DX.LT.USERNAME}" )
	@Override
	public void setUsername( String username )
	{
		super.setUsername( username );
	}

	@Value( "${BJ.JTD.DX.LT.PWD}" )
	@Override
	public void setPwd( String pwd )
	{
		super.setPwd( pwd );
	}
}
