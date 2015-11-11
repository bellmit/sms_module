/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.zsp2p;

import com.zy.sms.service.http.sender.SimpleTemplateChannelFactory;
import org.springframework.beans.factory.annotation.Value;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.zsp2p.ZSp2pChannelFactory
 *         Desc: TODO -- 2015-11-6 16:57:53 內容不經過UNICODE解碼操作，與通道方聯繫解決
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 16:45
 *   LastChange: 2015-11-06 16:45
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class ZSp2pChannelFactory extends SimpleTemplateChannelFactory
{

//	@Value( "${ZS.P2P.USERID}" ) private   String userId;
//	@Value( "${ZS.P2P.ACCOUNT}" ) private  String account;
//	@Value( "${ZS.P2P.PASSWORD}" ) private String password;
//	@Value( "${ZS.P2P.URL}" ) private      String httpUrl;
//	@Value( "${ZS.P2P.TASKNAME}" ) private String taskName;

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	@Value( "${ZS.P2P.ACCOUNT}" )
	public void setAccount( String account )
	{
		this.account = account;
	}

	@Value( "${ZS.P2P.URL}" )
	public void setHttpUrl( String httpUrl )
	{
		this.httpUrl = httpUrl;
	}

	@Value( "${ZS.P2P.PASSWORD}" )
	public void setPassword( String password )
	{
		this.password = password;
	}

	@Value( "${ZS.P2P.TASKNAME}" )
	public void setTaskName( String taskName )
	{
		this.taskName = taskName;
	}

	@Value( "${ZS.P2P.USERID}" )
	public void setUserId( String userId )
	{
		this.userId = userId;
	}
}
