/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.vo;

import java.util.Objects;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.vo.SmsObject
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 10:27
 *   LastChange: 2015-11-05 10:27
 *      History:
 * </pre>
 *********************************************************************************************/
public class SmsObject
{
	private String mobile;
	private String content;
	private String account;
	private String carriers;
	private String templateId;

	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof SmsObject ) ) return false;
		SmsObject smsObject = ( SmsObject ) o;
		return Objects.equals( getMobile(), smsObject.getMobile() ) &&
			   Objects.equals( getContent(), smsObject.getContent() ) &&
			   Objects.equals( getAccount(), smsObject.getAccount() ) &&
			   Objects.equals( getCarriers(), smsObject.getCarriers() ) &&
			   Objects.equals( getTemplateId(), smsObject.getTemplateId() ) &&
			   Objects.equals( getUuid(), smsObject.getUuid() ) &&
			   Objects.equals( getSmsid(), smsObject.getSmsid() );
	}

	@Override
	public int hashCode()
	{
		return Objects.hash( getMobile(), getContent(), getAccount(), getCarriers(), getTemplateId(), getUuid(), getSmsid() );
	}

	public String getCarriers()
	{

		return carriers;
	}

	public void setCarriers( String carriers )
	{
		this.carriers = carriers;
	}

	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId( String templateId )
	{
		this.templateId = templateId;
	}

	private String uuid;

	public String getSmsid()
	{
		return smsid;
	}

	public void setSmsid( String smsid )
	{
		this.smsid = smsid;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid( String uuid )
	{
		this.uuid = uuid;
	}

	private String smsid;

	public String getAccount()
	{
		return account;
	}

	public void setAccount( String account )
	{
		this.account = account;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent( String content )
	{
		this.content = content;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile( String mobile )
	{
		this.mobile = mobile;
	}

	public SmsObject()
	{
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "SmsObject{" );
		sb.append( "account='" ).append( account ).append( '\'' );
		sb.append( ", carriers='" ).append( carriers ).append( '\'' );
		sb.append( ", content='" ).append( content ).append( '\'' );
		sb.append( ", mobile='" ).append( mobile ).append( '\'' );
		sb.append( ", smsid='" ).append( smsid ).append( '\'' );
		sb.append( ", templateId='" ).append( templateId ).append( '\'' );
		sb.append( ", uuid='" ).append( uuid ).append( '\'' );
		sb.append( ", super=" ).append( super.toString() );
		sb.append( '}' );
		return sb.toString();
	}
}
