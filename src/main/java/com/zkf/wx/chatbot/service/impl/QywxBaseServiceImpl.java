package com.zkf.wx.chatbot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zkf.wx.chatbot.qywxapi.AccessTokenAPI;
import com.zkf.wx.chatbot.qywxentity.AccessTokenEntity;
import com.zkf.wx.chatbot.service.IQywxBaseService;

/**
 * @Description 企业微信基础Service层实现类
 * @author longwei
 * @date 2020年1月22日 下午9:30:26
 */
@Service
public class QywxBaseServiceImpl implements IQywxBaseService {

	/** 企业Id */
	@Value("${ZKF_corpid}")
	String ZKF_corpid;

	/** 通讯录的凭证密钥 */
	@Value("${ZKF_txl_secret}")
	String ZKF_txl_secret;

	/** 打卡的凭证密钥 */
	@Value("${ZKF_dk_secret}")
	String ZKF_dk_secret;

	@Override
	public AccessTokenEntity getMailListToken() {
		// 1、获取通讯录token
		return AccessTokenAPI.getAccessToken(ZKF_corpid, ZKF_txl_secret);
	}

	@Override
	public AccessTokenEntity getPunchClockToken() {
		// 1、获取打卡token
		return AccessTokenAPI.getAccessToken(ZKF_corpid, ZKF_dk_secret);
	}

}
