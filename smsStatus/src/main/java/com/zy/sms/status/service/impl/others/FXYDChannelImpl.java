/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl.others;

import com.alibaba.fastjson.JSON;
import com.zy.sms.status.service.impl.CatchStatusServerImpl;
import com.zy.util.MessageDigest5Util;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.others.FXYDChannelImpl
 *         Desc: -- 2015-10-12 14:48:13 佛山飛信 未完成，等待通道方返回結果
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-12 14:47
 *   LastChange: 2015-10-12 14:47
 *      History:
 * </pre>
 *********************************************************************************************/
public class FXYDChannelImpl extends CatchStatusServerImpl
{

	private static final int amount = 50000;
	@Value( "${FEIXIN.YD.CHANNEL.STATUS.URL}" ) private String url;
	@Value( "${FEIXIN.YD.CHANNEL.USERID}" ) private     String userid;
	@Value( "${FEIXIN.YD.CHANNEL.SECRETKEY}" ) private  String serialno;
	private static final String CHANNEL_NAME = "【飞信移动短信】通道";
	public static final  String CHANNEL_CODE = "FX_YD_CHANNEL_001";

	public FXYDChannelImpl()
	{
		super.channelName = CHANNEL_NAME;
		super.channelCode = CHANNEL_CODE;
	}

	/**
	 * 示例：
	 * UserID=33&Amount=5&Sign=4EEF764746DC27F30F1341EE584F8F0C
	 * 其中sign参数值 4EEF764746DC27F30F1341EE584F8F0C = MD5('Amount=5&UserID=33^7f9747265610f511202efcfbb7f81445');
	 * UserID=用户唯一标识ID&Amount=查询的数量&Sign=前面所有的参数签名，用于验证参数合法性
	 * UserID		int		(必传)用户ID，官网用户页面可获得
	 * Amount		int		(必传)查询的数量
	 * Sign		string 	(必传)签名验证，组成方式：由上面的参数根据key排序，组成key=value&key=value方式，在最后加上签名SecretKey（由^分开）)，其中SecretKey在官网用户页面可获得
	 *
	 * @return
	 */
	@Override
	public String httpUrl()
	{
		StringBuilder sb = new StringBuilder( url );

		sb.append( "?UserID=" ).append( userid );
		sb.append( "&Amount=" ).append( amount );

		StringBuilder str = new StringBuilder();
		str.append( "Amount=" ).append( amount );
		str.append( "&UserID=" ).append( userid );
		str.append( "^" ).append( serialno );

		String sign = MessageDigest5Util.md5Hex( str.toString() );

		sb.append( "&Sign=" ).append( sign );

		return sb.toString();
	}

	@Override
	public Map<String, String> httpParam()
	{
		return null;
	}

	@Override
	public List<String> parseResult() throws Exception
	{
		try
		{
			//98e7d5d48476476995efa96c90a39ebc
			logger.debug( "FXYDChannelImpl==>>>" + result );

			List<FXStatusReq.DataEntity> entityList = getJsonObj();
			if ( entityList == null ) return null;

			List<String> list = new ArrayList<String>();
			constructData( entityList, list );

			logger.debug( "---->>>{}.", list );

			return list;
		}
		catch ( Exception e )
		{
			logger.error( "解析" + CHANNEL_NAME + "状态报告出错: " + e.getMessage(), e );
			return null;
		}
		finally
		{
			logger.info( "{}狀態報告結果解析完成！", channelName );
		}
	}

	private void constructData( List<FXStatusReq.DataEntity> entityList, List<String> list )
	{
		for ( FXStatusReq.DataEntity entity : entityList )
		{
			StringBuilder sb = getReqData( entity );

			list.add( sb.toString() );
		}
	}

	private List<FXStatusReq.DataEntity> getJsonObj()
	{
		FXStatusReq parseObject = JSON.parseObject( result, FXStatusReq.class );
		if ( parseObject == null ) return null;
		if ( parseObject.getRet() != 0 ) return null;

		List<FXStatusReq.DataEntity> entityList = parseObject.getData();
		if ( entityList == null || entityList.isEmpty() ) return null;

		return entityList;
	}

	private StringBuilder getReqData( FXStatusReq.DataEntity entity )
	{
		// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
		StringBuilder sb = new StringBuilder( entity.getId() );
		sb.append( "|" ).append( entity.getSendto() );
		sb.append( "|" ).append( entity.getStatus() );
		sb.append( "|" ).append( entity.getDesc() );
		sb.append( "|" ).append( ( entity.getStatus() == 0 ) ? 0 : 1 );
		sb.append( "|" ).append( entity.getSettime() );
		return sb;
	}
}

class FXStatusReq
{

	/**
	 * ret : 0
	 * msg :
	 * data : [{"id":"c49071f89a444955a134307fb93","sendto":"15098648522","sms":"您好：您的注册码是22222【验证码】【技FX】","status":10,"desc":"发送成功","addtime":"2015-10-29 09:47:11","settime":"2015-10-29 09:47:14"}]
	 */

	private int              ret;
	private String           msg;
	/**
	 * 消息id
	 * id : c49071f89a444955a134307fb93
	 * sendto : 15098648522
	 * sms : 您好：您的注册码是22222【验证码】【技FX】
	 * status : 10
	 * desc : 发送成功
	 * addtime : 2015-10-29 09:47:11
	 * settime : 2015-10-29 09:47:14
	 */

	private List<DataEntity> data;

	public void setRet( int ret )
	{
		this.ret = ret;
	}

	public void setMsg( String msg )
	{
		this.msg = msg;
	}

	public void setData( List<DataEntity> data )
	{
		this.data = data;
	}

	public int getRet()
	{
		return ret;
	}

	public String getMsg()
	{
		return msg;
	}

	public List<DataEntity> getData()
	{
		return data;
	}

	public static class DataEntity
	{
		private String id;
		private String sendto;
		private String sms;
		private int    status;
		private String desc;
		private String addtime;
		private String settime;

		public void setId( String id )
		{
			this.id = id;
		}

		public void setSendto( String sendto )
		{
			this.sendto = sendto;
		}

		public void setSms( String sms )
		{
			this.sms = sms;
		}

		public void setStatus( int status )
		{
			this.status = status;
		}

		public void setDesc( String desc )
		{
			this.desc = desc;
		}

		public void setAddtime( String addtime )
		{
			this.addtime = addtime;
		}

		public void setSettime( String settime )
		{
			this.settime = settime;
		}

		public String getId()
		{
			return id;
		}

		public String getSendto()
		{
			return sendto;
		}

		public String getSms()
		{
			return sms;
		}

		public int getStatus()
		{
			return status;
		}

		public String getDesc()
		{
			return desc;
		}

		public String getAddtime()
		{
			return addtime;
		}

		public String getSettime()
		{
			return settime;
		}
	}
}
