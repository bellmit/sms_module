/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package com.zy.sms.status.service.impl;

import com.zy.redis.RedisConstant;
import com.zy.redis.SentinelRedisOperator;
import com.zy.sms.status.cache.SmsStatusPushCache;
import com.zy.sms.status.service.HttpServerFactory;
import com.zy.sms.status.service.biz.ICatchOperate;
import com.zy.sms.status.service.biz.IPushOperate;
import com.zy.sms.status.vo.StatusVo;
import com.zy.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.sms.status.service.impl.CatchStatusServerImpl
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-10 16:22
 *   LastChange: 2015-10-10 16:22
 *      History:
 * </pre>
 *********************************************************************************************/
public abstract class CatchStatusServerImpl extends HttpServerFactory implements ICatchOperate
{
	@Value( "${multSize}" ) private int                   multSize;
	private                         SentinelRedisOperator sentinelRedisOperator;
	private                         IPushOperate          smsStatusPushServerImpl;

	//	@Scheduled( cron = "0/3 * * * * ?" )
	public void task()
	{
		channelSmsOperate();
	}

	public void channelSmsOperate()
	{
		long startTime = System.currentTimeMillis();
		try
		{

			logger.info( "开始抓取{}状态报告...", channelName );

			send();

			prepareDataToPush();

		}
		catch ( Exception e )
		{
			logger.error( "{}抓取状态报告异常：{}", channelName, e.getMessage(), e );
		}
		finally
		{
			logger.info( "完成抓取{}状态报告，用时 : {}", channelName, ( System.currentTimeMillis() - startTime ) );
		}
	}

	private void prepareDataToPush() throws Exception
	{
		if ( StringUtils.isNotBlank( result ) )
		{
			logger.info( "{}抓取到的狀態報告結果為：{},開始解析....", channelName, ObjectUtil.convertUnicode( result ) );

			List<String> rsList = parseResult();
			if ( null == rsList || rsList.size() == 0 )
			{
				logger.warn( channelName + "解析后沒有可用状态报告." + ObjectUtil.convertUnicode( result ) );
				return;
			}

			// -- 2015-10-8 15:51:46 狀態報告存入Redis緩存
			List<StatusVo> voList = updateDataToCache( rsList );

			// -- 2015-10-8 15:52:34 推送狀態報告,每條記錄350字節
			pushStatusToAccount( voList );

		}
	}

	private List<StatusVo> updateDataToCache( List<String> rsList )
	{
		logger.info( "開始更新{}狀態報告結果至數據緩存中....", channelName );
		long           startTime = System.currentTimeMillis();
		final int      size      = rsList.size();
		String         redisKey  = String.format( RedisConstant.ZHIYAN_SMS_STATUS_REPO_KEY, channelCode );
		List<StatusVo> voList    = new LinkedList<StatusVo>();
		try
		{
			uDtCoper( rsList, size, redisKey, voList );
		}
		finally
		{
			logger.info( "本次{}抓取到[{}]條合規狀態報告并導入系統緩存中，緩存中現有狀態報告數據：{}條,用時：{} ms。", channelName, size, sentinelRedisOperator.hlen( redisKey ),
						 ( System.currentTimeMillis() - startTime ) );
		}
		return voList;
	}

	private void uDtCoper( List<String> rsList, int size, String redisKey, List<StatusVo> voList )
	{
		ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
		for ( int i = 0 ; i < size ; i++ )
		{
			try
			{
				String value = rsList.get( i );
				// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
				StatusVo sv = getStatusVo( value );
				if ( sv == null ) continue;

				voList.add( sv );
				data.put( sv.getSmsId(), value );
			}
			catch ( Exception e )
			{
				logger.error( "解析結果獲取消息ID出現異常：{}", e.getMessage(), e );
			}
		}
		sentinelRedisOperator.hmset( redisKey, data );
	}

	private void pushStatusToAccount( List<StatusVo> rsList )
	{
		long startTime = System.currentTimeMillis();
		try
		{
			logger.info( "準備報告數據，開始推送...." );
			int endIdx = rsList.size();
			pushStatusToAccount( null, rsList, endIdx );
		}
		catch ( Exception e )
		{
			logger.error( "組裝狀態報告及推送操作出現異常：{}", e.getMessage(), e );
		}
		finally
		{
			logger.info( "本次抓取的狀態報告完成解析及推送用時：{}", ( System.currentTimeMillis() - startTime ) );
		}
	}

	private void pushStatusToAccount( List<StatusVo> orsList, List<StatusVo> rsList, int endIdx )
	{
		if ( endIdx <= multSize )
		{
			constructDataAndPush( rsList, endIdx );

			if ( null == orsList || orsList.isEmpty() ) return;
			else
			{
				rsList = new ArrayList<StatusVo>( orsList );
				logger.info( "已處理推送數據：{}條，還剩餘:{}條數據未推送。", endIdx, orsList.size() );
			}
		}

		if ( multSize < rsList.size() )
		{
			orsList = new ArrayList<StatusVo>( rsList );
			rsList = new ArrayList<StatusVo>( rsList.subList( 0, multSize ) );
			orsList.removeAll( rsList );
			endIdx = rsList.size();
		}
		else
		{
			endIdx = rsList.size();
			orsList = null;
		}

		pushStatusToAccount( orsList, rsList, endIdx );

	}

	private void constructDataAndPush( List<StatusVo> rsList, int endIdx )
	{
		Map<String, String> data = parsePushData( rsList, endIdx );

		logger.debug( "{}", data );
		if ( data == null || data.isEmpty() ) return;
		// -- 2015-10-8 15:52:34 推送狀態報告
		pushStatus( data );
	}

	private void pushStatus( Map<String, String> data )
	{

		List<String> list = sentinelRedisOperator.hmget( new LinkedList<String>( data.keySet() ), RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL );

		int idx = 0;
		for ( Map.Entry<String, String> entry : data.entrySet() )
		{
			String accout = entry.getKey();
			String pushStatus = entry.getValue();

			try
			{
				String pushUrl = list.get( idx );
//				String pushUrl = sentinelRedisOperator.phget( RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL, accout );
				callPushServer( accout, pushStatus, pushUrl );
			}
			catch ( Exception e )
			{
				logger.error( "推送狀態報告并獲取結果異常：{}", e.getMessage(), e );
				// 5分鐘重推機制
				SmsStatusPushCache.put( accout, pushStatus );
			}
			finally
			{
				idx++;
			}
		}
	}

	private void callPushServer( String accout, String pushStatus, String pushUrl ) throws Exception
	{
		smsStatusPushServerImpl.setHttpUrl( pushUrl );
		smsStatusPushServerImpl.setMerchant( accout );
		smsStatusPushServerImpl.setChannelName( new StringBuilder( "賬戶【" ).append( accout ).append( "】推送" ).toString() );
		smsStatusPushServerImpl.setParam( pushStatus );
		smsStatusPushServerImpl.pushSms();
	}

	private Map<String, String> parsePushData( final List<StatusVo> rsList, final int endIdx )
	{
		long startTime = System.currentTimeMillis();
		try
		{

			final Map<String, String> map = new LinkedHashMap<String, String>();
			final Map<String, String> data = Collections.synchronizedMap( map );

			final CountDownLatch begin = new CountDownLatch( 1 );
			final CountDownLatch end = new CountDownLatch( endIdx );
			ExecutorService exec = new ThreadPoolExecutor( endIdx, endIdx, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>( multSize * 10 ) );

			multxPaseData( rsList, endIdx, data, begin, end, exec );

			begin.countDown();
			end.await();
			exec.shutdown();

			return data;
		}
		catch ( Exception e )
		{
			logger.error( "為商戶組裝狀態報告出現異常:{}.", e.getMessage(), e );
		}
		finally
		{
			logger.info( "組裝{}條數據用時：{}ms.", endIdx, ( System.currentTimeMillis() - startTime ) );
		}
		return null;
	}

	private void multxPaseData( final List<StatusVo> rsList, int endIdx, final Map<String, String> data, final CountDownLatch begin, final CountDownLatch end,
								ExecutorService exec )
	{

		final List<String> keyList = getKeyList( rsList );

		final List<String> listAccount = getListAccount( keyList );
		if ( listAccount == null ) return;

		final List<String> listUUID = sentinelRedisOperator.hmget( keyList, RedisConstant.ZHIYAN_SMS_STATUS_UUID_KEY );

		for ( int i = 0 ; i < endIdx ; i++ )
		{

			final int idx = i;
			exec.submit( new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						begin.await();
						parseSingleData( rsList, idx, listAccount, listUUID, data );
					}
					catch ( Exception e )
					{
						logger.error( "為商戶組裝狀態報告粗線異常：{}", e.getMessage(), e );
					}
					finally
					{
						end.countDown();
					}
				}
			} );

		}
	}

	private List<String> getListAccount( List<String> keyList )
	{
		final List<String> listAccount = sentinelRedisOperator.hmget( keyList, RedisConstant.ZHIYAN_SMS_STATUS_ACCOUNT_KEY );
		if ( listAccount.isEmpty() )
		{
			logger.warn( "緩存系統沒有查到相關消息ID對應的賬戶信息." );
			return null;
		}
		return listAccount;
	}

	private List<String> getKeyList( List<StatusVo> rsList )
	{
		final List<String> keyList = new LinkedList<String>();
		for ( int i = 0 ; i < rsList.size() ; i++ )
		{
			keyList.add( i, rsList.get( i ).getSmsId() );
		}
		return keyList;
	}

	private void parseSingleData( List<StatusVo> rsList, int idx, List<String> listAccount, List<String> listUUID, Map<String, String> data )
	{
		// 74386746486091|13718249651|0:狀態報告status|描述|0：本系統status success|2011-03-19 24:50:00
		StatusVo sv     = rsList.get( idx );
		String   accout = listAccount.get( idx );

		if ( StringUtils.isBlank( accout ) )
		{
			logger.warn( "狀態報告記錄：{} 無對應發送賬戶信息，返回此次更新。。", sv.getSmsId() );
			return;
		}
		sv.setMerchantAccount( accout );
		sv.setUid( StringUtils.trimToEmpty( listUUID.get( idx ) ) );

		String pushStatus = sv.pushStatus();

		if ( data.containsKey( sv.getMerchantAccount() ) )
			pushStatus = new StringBuilder( data.get( sv.getMerchantAccount() ) ).append( "," ).append( pushStatus ).toString();

		data.put( sv.getMerchantAccount(), pushStatus );
	}

	private StatusVo getStatusVo( String value )
	{
		StatusVo sv = new StatusVo();

		logger.debug( "sms status result : " + value );
		if ( !StringUtils.contains( value, '|' ) ) return null;

		String aya[] = StringUtils.splitPreserveAllTokens( value, '|' );
		parseData( sv, aya );

		return sv;
	}

	private void parseData( StatusVo sv, String[] aya )
	{
		// 74386746486091|13718249651|0:狀態報告status|描述|1：本系統status|2011-03-19 24:50:00
		try
		{
			sv.setSmsId( aya[ 0 ] );
			sv.setMobile( aya[ 1 ] );
			sv.setReceiveChannelStatus( aya[ 2 ] );
			sv.setReceiveChannelStatusDesc( aya[ 3 ] );
			sv.setStatus( aya[ 4 ] );
			sv.setSmsTime( aya[ 5 ] );
		}
		catch ( Exception e )
		{
			logger.error( "組裝解析報告結果出現異常：{}.", e.getMessage(), e );
			sv = null;
		}
	}

	public IPushOperate getSmsStatusPushServerImpl()
	{
		return smsStatusPushServerImpl;
	}

	@Resource
	public void setSmsStatusPushServerImpl( IPushOperate smsStatusPushServerImpl )
	{
		this.smsStatusPushServerImpl = smsStatusPushServerImpl;
	}

	public SentinelRedisOperator getSentinelRedisOperator()
	{
		return sentinelRedisOperator;
	}

	@Resource
	public void setSentinelRedisOperator( SentinelRedisOperator sentinelRedisOperator )
	{
		this.sentinelRedisOperator = sentinelRedisOperator;
	}
}
