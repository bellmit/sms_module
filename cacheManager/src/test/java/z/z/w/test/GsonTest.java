/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package z.z.w.test;

import java.util.List;

//@com.fasterxml.jackson.annotation.JsonIgnoreProperties( ignoreUnknown = true )

/*********************************************************************************************
 * <pre>
 *     FileName: z.z.w.returnBrokenResource.GsonTest
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-13 10:21
 *   LastChange: 2015-10-13 10:21
 *      History:
 * </pre>
 *********************************************************************************************/
public class GsonTest
{

	/**
	 * email : john@smith.com
	 * first_name : John
	 * last_name : Smith
	 * info : {"bio":"Eco-warrior and defender of the weak","age":25,"interests":["dolphins","whales"]}
	 * join_date : 2014/05/01
	 */

	private String     email;
	private String     first_name;
	private String     last_name;
	private InfoEntity info;
	private String     join_date;

	public void setEmail( String email )
	{
		this.email = email;
	}

	public void setFirst_name( String first_name )
	{
		this.first_name = first_name;
	}

	public void setLast_name( String last_name )
	{
		this.last_name = last_name;
	}

	public void setInfo( InfoEntity info )
	{
		this.info = info;
	}

	public void setJoin_date( String join_date )
	{
		this.join_date = join_date;
	}

	public String getEmail()
	{
		return email;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public InfoEntity getInfo()
	{
		return info;
	}

	public String getJoin_date()
	{
		return join_date;
	}

	public static class InfoEntity
	{
		/**
		 * bio : Eco-warrior and defender of the weak
		 * age : 25
		 * interests : ["dolphins","whales"]
		 */

		private String       bio;
		private int          age;
		private List<String> interests;

		public void setBio( String bio )
		{
			this.bio = bio;
		}

		public void setAge( int age )
		{
			this.age = age;
		}

		public void setInterests( List<String> interests )
		{
			this.interests = interests;
		}

		public String getBio()
		{
			return bio;
		}

		public int getAge()
		{
			return age;
		}

		public List<String> getInterests()
		{
			return interests;
		}
	}
}
