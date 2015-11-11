/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender.vo;

import java.util.Objects;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.vo.FXYdResult
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 15:51
 *   LastChange: 2015-11-06 15:51
 *      History:
 * </pre>
 *********************************************************************************************/
public class FXYdResult
{

	/**
	 * ret : 0
	 * msg :
	 * data : 950f3a8c67ca4797a3f804fa684b30dc
	 */

	private int    ret;
	private String msg;
	private String data;
	private String occur;
	private String error;

	public String getError()
	{
		return error;
	}

	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( !( o instanceof FXYdResult ) ) return false;
		FXYdResult that = ( FXYdResult ) o;
		return Objects.equals( getRet(), that.getRet() ) &&
			   Objects.equals( getMsg(), that.getMsg() ) &&
			   Objects.equals( getData(), that.getData() ) &&
			   Objects.equals( getOccur(), that.getOccur() ) &&
			   Objects.equals( getError(), that.getError() );
	}

	@Override
	public int hashCode()
	{
		return Objects.hash( getRet(), getMsg(), getData(), getOccur(), getError() );
	}

	public void setError( String error )
	{

		this.error = error;
	}

	public String getOccur()
	{

		return occur;
	}

	public void setOccur( String occur )
	{
		this.occur = occur;
	}

	public void setRet( int ret ) { this.ret = ret;}

	public void setMsg( String msg ) { this.msg = msg;}

	public void setData( String data ) { this.data = data;}

	public int getRet() { return ret;}

	public String getMsg() { return msg;}

	public String getData() { return data;}

	public FXYdResult()
	{

	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder( "FXYdResult{" );
		sb.append( "data='" ).append( data ).append( '\'' );
		sb.append( ", error='" ).append( error ).append( '\'' );
		sb.append( ", msg='" ).append( msg ).append( '\'' );
		sb.append( ", occur='" ).append( occur ).append( '\'' );
		sb.append( ", ret=" ).append( ret );
		sb.append( ", super=" ).append( super.toString() );
		sb.append( '}' );
		return sb.toString();
	}
}
