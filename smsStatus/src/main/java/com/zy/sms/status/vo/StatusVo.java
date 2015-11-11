/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.vo.StatusVo
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-10 16:27
 *   LastChange: 2015-10-10 16:27
 *      History:
 * </pre>
 *********************************************************************************************/
public class StatusVo
{
	private String smsId;
	private String receiveChannelStatus;
	private String receiveChannelStatusDesc;
	private String smsTime;

	// 接受手机号, 用户接收时间, 接收状态, 接收失败原因，对应的发送短信的UID
	private String mobile;
	private String status;
	private String uid;
	private String merchantAccount;

	public String pushStatus()
	{
		/**
		 * {
		 * "mobile":"13333333333",
		 * "uid":”fdsfds48789fdslkljff8”
		 * "datetime":"2014-01-12 09:12:12",
		 * "status":"0",
		 * "desc":""
		 * }
		 */

		StringBuffer sb = new StringBuffer( "{" );
		sb.append( "\"mobile\":\"" ).append( this.getMobile() ).append( "\"," );
		sb.append( "\"uid\":\"" ).append( this.getUid() ).append( "\"," );
		sb.append( "\"datetime\":\"" ).append( this.getSmsTime() ).append( "\"," );
		sb.append( "\"status\":\"" ).append( this.getStatus() ).append( "\"," );
		sb.append( "\"desc\":\"" ).append( StringUtils.stripToEmpty( this.getReceiveChannelStatusDesc() ) ).append( "\"}" );
		return sb.toString();
	}

	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof StatusVo ) ) return false;
		StatusVo statusVo = ( StatusVo ) o;
		return Objects.equals( getSmsId(), statusVo.getSmsId() ) &&
			   Objects.equals( getReceiveChannelStatus(), statusVo.getReceiveChannelStatus() ) &&
			   Objects.equals( getReceiveChannelStatusDesc(), statusVo.getReceiveChannelStatusDesc() ) &&
			   Objects.equals( getSmsTime(), statusVo.getSmsTime() ) &&
			   Objects.equals( getMobile(), statusVo.getMobile() ) &&
			   Objects.equals( getStatus(), statusVo.getStatus() ) &&
			   Objects.equals( getUid(), statusVo.getUid() ) &&
			   Objects.equals( getMerchantAccount(), statusVo.getMerchantAccount() );
	}

	@Override
	public int hashCode()
	{
		return Objects.hash( getSmsId(), getReceiveChannelStatus(), getReceiveChannelStatusDesc(), getSmsTime(), getMobile(), getStatus(), getUid(),
							 getMerchantAccount() );
	}

	public String getMerchantAccount()
	{
		return merchantAccount;
	}

	public void setMerchantAccount( String merchantAccount )
	{
		this.merchantAccount = merchantAccount;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile( String mobile )
	{
		this.mobile = mobile;
	}

	public String getReceiveChannelStatus()
	{
		return receiveChannelStatus;
	}

	public void setReceiveChannelStatus( String receiveChannelStatus )
	{
		this.receiveChannelStatus = receiveChannelStatus;
	}

	public String getReceiveChannelStatusDesc()
	{
		return receiveChannelStatusDesc;
	}

	public void setReceiveChannelStatusDesc( String receiveChannelStatusDesc )
	{
		this.receiveChannelStatusDesc = receiveChannelStatusDesc;
	}

	public String getSmsId()
	{
		return smsId;
	}

	public void setSmsId( String smsId )
	{
		this.smsId = smsId;
	}

	public String getSmsTime()
	{
		return smsTime;
	}

	public void setSmsTime( String smsTime )
	{
		this.smsTime = smsTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid( String uid )
	{
		this.uid = uid;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "StatusVo{" );
		sb.append( "merchantAccount='" ).append( merchantAccount ).append( '\'' );
		sb.append( ", mobile='" ).append( mobile ).append( '\'' );
		sb.append( ", receiveChannelStatus='" ).append( receiveChannelStatus ).append( '\'' );
		sb.append( ", receiveChannelStatusDesc='" ).append( receiveChannelStatusDesc ).append( '\'' );
		sb.append( ", smsId='" ).append( smsId ).append( '\'' );
		sb.append( ", smsTime='" ).append( smsTime ).append( '\'' );
		sb.append( ", status='" ).append( status ).append( '\'' );
		sb.append( ", uid='" ).append( uid ).append( '\'' );
		sb.append( ", super=" ).append( super.toString() );
		sb.append( '}' );
		return sb.toString();
	}
}
