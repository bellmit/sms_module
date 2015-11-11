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
 *     FileName: com.zy.sms.vo.EsDataInfo
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 10:39
 *   LastChange: 2015-11-05 10:39
 *      History:
 * </pre>
 *********************************************************************************************/
public class EsDataInfo extends SmsObject
{
	private String channelCode;

	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof EsDataInfo ) ) return false;
		if ( !super.equals( o ) ) return false;
		EsDataInfo that = ( EsDataInfo ) o;
		return Objects.equals( getChannelCode(), that.getChannelCode() );
	}

	@Override
	public int hashCode()
	{
		return Objects.hash( super.hashCode(), getChannelCode() );
	}

	public String getChannelCode()
	{

		return channelCode;
	}

	public void setChannelCode( String channelCode )
	{
		this.channelCode = channelCode;
	}

	public EsDataInfo()
	{
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "EsDataInfo{" );
		sb.append( "channelCode='" ).append( channelCode ).append( '\'' );
		sb.append( ", super=" ).append( super.toString() );
		sb.append( '}' );
		return sb.toString();
	}
}
