/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.vo;

import java.util.List;
import java.util.Objects;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.vo.SmsSendDataInfo
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 10:28
 *   LastChange: 2015-11-05 10:28
 *      History:
 * </pre>
 *********************************************************************************************/
public class SmsSendDataInfo extends SmsObject
{
	//LinkedList
	private List<String> channelCodeList;

	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof SmsSendDataInfo ) ) return false;
		if ( !super.equals( o ) ) return false;
		SmsSendDataInfo that = ( SmsSendDataInfo ) o;
		return Objects.equals( getChannelCodeList(), that.getChannelCodeList() );
	}

	@Override
	public int hashCode()
	{
		return Objects.hash( super.hashCode(), getChannelCodeList() );
	}

	public List<String> getChannelCodeList()
	{

		return channelCodeList;
	}

	public void setChannelCodeList( List<String> channelCodeList )
	{
		this.channelCodeList = channelCodeList;
	}

	public SmsSendDataInfo()
	{

	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "SmsSendDataInfo{" );
		sb.append( "channelCodeList=" ).append( channelCodeList );
		sb.append( ", super=" ).append( super.toString() );
		sb.append( '}' );
		return sb.toString();
	}
}
