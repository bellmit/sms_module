/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*********************************************************************************************
 * <pre>
 *     FileName: z.z.w.util.comm.MessageDigest5Util
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 14:52
 *   LastChange: 2015-11-06 14:52
 *      History:
 * </pre>
 *********************************************************************************************/
public class MessageDigest5Util
{
	private static final String HEX_CHARS = "0123456789abcdef";

	public static MessageDigest getDigest()
	{
		try
		{
			return MessageDigest.getInstance( "MD5" );
		}
		catch ( NoSuchAlgorithmException e )
		{
			throw new RuntimeException( e );
		}
	}

	public static byte[] md5( byte[] data )
	{
		return getDigest().digest( data );
	}

	public static byte[] md5( String data )
	{
		byte[] b = md5( data.getBytes() );
		return b;
	}

	public static String md5Hex( String data )
	{
		return toHexString( md5( data ) );
	}

	public static String toHexString( byte[] b )
	{
		StringBuffer sb = new StringBuffer();
		for ( int i = 0 ; i < b.length ; i++ )
		{
			sb.append( HEX_CHARS.charAt( ( b[ i ] >>> 4 ) & 0x0F ) );
			sb.append( HEX_CHARS.charAt( b[ i ] & 0x0F ) );
		}
		return sb.toString();
	}
}
