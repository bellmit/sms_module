/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.service.http.sender;

import com.alibaba.fastjson.JSON;
import com.zy.sms.service.data.HttpDataManipulationImpl;
import com.zy.sms.service.exception.SenderException;
import com.zy.sms.service.http.sender.vo.FXYdResult;
import com.zy.sms.vo.EsDataInfo;
import com.zy.sms.vo.SmsSendDataInfo;
import com.zy.util.MessageDigest5Util;
import com.zy.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.service.http.sender.FXYDChannelSender
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-11-06 15:01
 *   LastChange: 2015-11-06 15:01
 *      History:
 * </pre>
 *********************************************************************************************/
@Service( value = "FX_YD_CHANNEL_001" )
public class FXYDChannelSender extends HttpDataManipulationImpl
{
	private static final Logger logger = LoggerFactory.getLogger( FXYDChannelSender.class );

	@Value( "${FX.YD.USERID}" ) private    String userId;
	@Value( "${FX.YD.TplId}" ) private     String tplId;
	@Value( "${FX.YD.SECRETKEY}" ) private String secretKey;
	@Value( "${FX.YD.Captcha}" ) private   String captcha;
	@Value( "${FX.YD.Product}" ) private   String product;
	@Value( "${FX.YD.URL}" ) private       String httpUrl;

	private static final String CHANNEL_NAME = "【飛信移動】通道";

	public FXYDChannelSender()
	{
		super();
		super.channelName = CHANNEL_NAME;
	}

	@Async( value = "FX_YD__CHANNEL_Executor" )
	@Override
	public void operating( SmsSendDataInfo smsSendDataInfo )
	{
		operate( smsSendDataInfo );
	}

	@Override
	public String httpUrl()
	{
		return httpUrl;
	}

	/**
	 * TplId=1&Product=asdf&Captcha=1234&Content=短信内容&Sendto=15875547740&Serialno=12562565&UserID=4&Sign=AB03ABF42FFF59B3B3C1DA6D1FD76085
	 * 其中sign参数值 AB03ABF42FFF59B3B3C1DA6D1FD76085 = MD5('Captcha=1234&Content=短信内容&Product=asdf
	 * &Sendto=15875547740&Serialno=12562565&TplId=1&UserID=4^02b0be4c513dd325d990417d5db2a059');
	 * TplId=短信模板ID&UserID=用户唯一标识ID&Serialno=贵方自定义的短信流水号&Sendto=发送到那个号码
	 * &Content=短信内容&Product=产品名称/网站名称/公司名称&Captcha=验证码内容&Sign=前面所有的参数签名，用于验证参数合法性
	 * TplId		短信模板ID(template)Id，id为1：短信验证码（传Product和Captcha参数）;
	 * id为0时，短信内容自定义（传Content参数）
	 * UserID		(必传)用户ID，官网用户页面可获得(测试使用可以使用：32,加密用的SecretKey可以使用：7f9747265610f511202efcfbb7f81445)
	 * Serialno	(必传)验证码序列号，可用于查询验证码状态
	 * Sendto		(必传)发送号码
	 * Content		短信内容，TplId不传或者为0时，此参数表示要发送的短信内容
	 * Product		产品名称---产品名称需为2-8个字符
	 * Captcha		验证码-----验证码为4-6位数字
	 * (发送短信内容将由产品名称和验证码合成，如【新浪微博】您申请的验证码是：123456)
	 * Sign		(必传)签名验证，组成方式：由上面的参数根据key排序，组成key=value&key=value方式，在最后加上签名SecretKey（由^分开）)，其中SecretKey在官网用户页面可获得
	 *
	 * @param smsSendDataInfo
	 */
	@Override
	public void httpParam( SmsSendDataInfo smsSendDataInfo )
	{
		try
		{

			logger.info( "{}開始構造HTTP請求參數....", channelName );

			param = new LinkedHashMap<String, String>();
			param = Collections.synchronizedMap( param );

			param.put( "Captcha", captcha );
			param.put( "Content", URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			param.put( "Product", product );
			param.put( "Sendto", smsSendDataInfo.getMobile() );
			param.put( "Serialno", smsSendDataInfo.getSmsid() );
			param.put( "TplId", tplId );
			param.put( "UserID", userId );

			StringBuilder sb = new StringBuilder( "Captcha=" );
			sb.append( captcha ).append( "&Content=" ).append( URLEncoder.encode( smsSendDataInfo.getContent(), "utf-8" ) );
			sb.append( "&Product=" ).append( product );
			sb.append( "&Sendto=" ).append( smsSendDataInfo.getMobile() );
			sb.append( "&Serialno=" ).append( smsSendDataInfo.getSmsid() );
			sb.append( "&TplId=" ).append( tplId );
			sb.append( "&UserID=" ).append( userId );
			sb.append( "^" ).append( secretKey );

			param.put( "Sign", MessageDigest5Util.md5Hex( sb.toString() ) );

		}
		catch ( Exception e )
		{
			logger.error( "{}組裝http請求參數時出現異常：{}.", CHANNEL_NAME, e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
	}

	/**
	 * {"ret":0,"msg":"","data":"950f3a8c67ca4797a3f804fa684b30dc"}.
	 * {"ret":100001,"msg":"签名验证失败","data":{"Str":"Captcha=2628&Content=您好：您的注册码是7773【技FXYD】
	 * &Product=智验&Sendto=15098648522&Serialno=64ece3a5e33644a7a1096f0255bd4d46
	 * &TplId=0&UserID=32^7f9747265610f511202efcfbb7f81445",
	 * "Sign":"17FCD1987D749D63F328787283A91704","checkSign":"3B8F87CF392C7403D8943E1904F91DB4"}}.
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	@Override
	public EsDataInfo parseResult( SmsSendDataInfo smsSendDataInfo ) throws Exception
	{
		try
		{
			logger.info( "{}短信發送結果：{}.", CHANNEL_NAME, ObjectUtil.convertUnicode( result ) );

//			if ( StringUtils.isBlank( result ) || !StringUtils.containsIgnoreCase( "\"ret\":0", result ) ) throw new SenderException( "短信發送失敗，進入重發階段." );

			FXYdResult fxYdResult = JSON.parseObject( result, FXYdResult.class );
			if ( null == fxYdResult ) throw new RuntimeException( "短信發送結果解析失敗，無法進入數據處理模塊." );

			if ( fxYdResult.getRet() != 0 ) throw new SenderException( "短信發送失敗，進入重發階段." );

			//TODO -- 2015-11-6 16:09:51 處理短信發送結果，組裝數據，準備存入ES。
			EsDataInfo esDataInfo = new EsDataInfo();
			esDataInfo.setMobile( "15098648522" );
			esDataInfo.setSmsid( fxYdResult.getData() );
			esDataInfo.setAccount( smsSendDataInfo.getAccount() );

			return esDataInfo;
		}
		catch ( SenderException e )
		{
			logger.error( "{}短信發送失敗：{}.", e.getMessage(), e );
			throw new SenderException( e.getMessage(), e );
		}
		catch ( Exception e )
		{
			logger.error( "{}短信發送完畢，組裝發送結果出現異常：{}.", e.getMessage(), e );
		}
		return null;
	}
}
