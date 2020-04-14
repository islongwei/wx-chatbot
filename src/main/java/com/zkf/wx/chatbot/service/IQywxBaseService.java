package com.zkf.wx.chatbot.service;

import com.zkf.wx.chatbot.qywxentity.AccessTokenEntity;

/**
 * @Description 企业微信基础Service层
 * @author longwei
 * @date 2020年1月22日 下午9:28:07
 */
public interface IQywxBaseService {

	/**
	 * 获取企业微信通讯录接口凭证
	 * 
	 * @return AccessTokenEntity
	 */
	AccessTokenEntity getMailListToken();
	
	/**
	  *  获取企业微信打卡接口凭证
	 * @return AccessTokenEntity
	 */
	AccessTokenEntity getPunchClockToken();

}
