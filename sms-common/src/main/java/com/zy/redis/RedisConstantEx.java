/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit. *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu. *
 **********************************************************************************************************************/

package com.zy.redis;

/*********************************************************************************************
 * <pre>
 *     FileName: com.zy.redis.RedisConstantEx
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-08 16:59
 *   LastChange: 2015-10-08 16:59
 *      History:
 * </pre>
 *********************************************************************************************/
public class RedisConstantEx
{
	public static final String REDIS_SPLIT_STRING = "::";

	public static final String ZHIYAN_STATUS_KEY_PREFIX = "ZHIYAN" + REDIS_SPLIT_STRING + "STATUS" + REDIS_SPLIT_STRING;

	/**
	 * 消息ID對應狀態報告，存儲于Redis
	 */
	public static final String
			ZHIYAN_SMS_STATUS_REPO_KEY =
			ZHIYAN_STATUS_KEY_PREFIX + "REPO" + REDIS_SPLIT_STRING + "CHANNEL" + REDIS_SPLIT_STRING + "SMSID" + REDIS_SPLIT_STRING + "%s";

	/**
	 * 消息ID對應發送賬戶，存儲于Redis
	 */
	public static final String ZHIYAN_SMS_STATUS_ACCOUNT_KEY = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "SMSID";
	/**
	 * 消息ID對應客戶的UUID，存儲于Redis
	 */
	public static final String ZHIYAN_SMS_STATUS_UUID_KEY    = ZHIYAN_STATUS_KEY_PREFIX + "UUID" + REDIS_SPLIT_STRING + "SMSID";

	/**
	 * 商戶賬戶綁定推送狀態報告URL
	 */
	public static final String ZHIYAN_SMS_STATUS_ACCOUNT_PUSH_URL = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "URL";

	/**
	 * 商戶賬戶綁定發送短信IP
	 */
	public static final String ZHIYAN_SMS_STATUS_ACCOUNT_BAND_IP = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "IP";

	/**
	 * 商戶賬戶綁定發送短信IP，是否生效
	 */
	public static final String ZHIYAN_SMS_STATUS_ACCOUNT_BAND_IP_VALID = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "IP_VALID";

	public static final String ZHIYAN_SMS_STATUS_ACCOUNT_BAND = ZHIYAN_STATUS_KEY_PREFIX + "ACCOUNT" + REDIS_SPLIT_STRING + "BAND";

	private RedisConstantEx()
	{
	}
}
