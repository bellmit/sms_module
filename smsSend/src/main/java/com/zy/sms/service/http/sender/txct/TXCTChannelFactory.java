/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.txct;

import com.zy.sms.service.http.sender.SimpleTemplateChannelFactory;
import org.springframework.beans.factory.annotation.Value;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.txct.TXCTChannelFactory
 *         Desc: TODO -- 2015-11-6 17:54:38 該通道發送短信返回簽名錯誤
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 17:30
 *   LastChange: 2015-11-06 17:30
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class TXCTChannelFactory extends SimpleTemplateChannelFactory
{
	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	@Value( "${TX.CT.ACCOUNT}" )
	public void setAccount( String account )
	{
		this.account = account;
	}

	@Value( "${TX.CT.URL}" )
	public void setHttpUrl( String httpUrl )
	{
		this.httpUrl = httpUrl;
	}

	@Value( "${TX.CT.PASSWORD}" )
	public void setPassword( String password )
	{
		this.password = password;
	}

	@Value( "${TX.CT.TASKNAME}" )
	public void setTaskName( String taskName )
	{
		this.taskName = taskName;
	}

	@Value( "${TX.CT.USERID}" )
	public void setUserId( String userId )
	{
		this.userId = userId;
	}
}
