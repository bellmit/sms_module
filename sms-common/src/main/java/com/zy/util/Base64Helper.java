/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.util.Base64Helper
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-18 15:39
 *   LastChange: 2015-11-18 15:39
 *      History:
 * </pre>
 *********************************************************************************************/
public class Base64Helper
{
	// 加密
	public static String getBase64( String str )
	{
		byte[] b = null;
		String s = null;
		try
		{
			b = str.getBytes( "utf-8" );
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
		if ( b != null )
		{
			s = new BASE64Encoder().encode( b );
		}
		return s;
	}

	// 解密
	public static String getFromBase64( String s )
	{
		byte[] b      = null;
		String result = null;
		if ( s != null )
		{
			BASE64Decoder decoder = new BASE64Decoder();
			try
			{
				b = decoder.decodeBuffer( s );
				result = new String( b, "utf-8" );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
		return result;
	}
}
