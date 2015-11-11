/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.util;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.util.ObjectUtil
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-05 17:41
 *   LastChange: 2015-11-05 17:41
 *      History:
 * </pre>
 *********************************************************************************************/
public class ObjectUtil
{
	public static String convertUnicode( String ori )
	{
		char         aChar;
		int          len       = ori.length();
		StringBuffer outBuffer = new StringBuffer( len );
		for ( int x = 0 ; x < len ; )
		{
			aChar = ori.charAt( x++ );
			if ( aChar == '\\' )
			{
				aChar = ori.charAt( x++ );
				if ( aChar == 'u' )
				{
					// Read the xxxx
					int value = 0;
					for ( int i = 0 ; i < 4 ; i++ )
					{
						aChar = ori.charAt( x++ );
						switch ( aChar )
						{
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = ( value << 4 ) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = ( value << 4 ) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = ( value << 4 ) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException( "Malformed   \\uxxxx   encoding." );
						}
					}
					outBuffer.append( ( char ) value );
				}
				else
				{
					if ( aChar == 't' ) aChar = '\t';
					else if ( aChar == 'r' ) aChar = '\r';
					else if ( aChar == 'n' ) aChar = '\n';
					else if ( aChar == 'f' ) aChar = '\f';
					outBuffer.append( aChar );
				}
			}
			else outBuffer.append( aChar );

		}
		return outBuffer.toString();
	}
}
