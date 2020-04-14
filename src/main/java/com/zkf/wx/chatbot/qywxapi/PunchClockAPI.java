package com.zkf.wx.chatbot.qywxapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.qywxentity.PunchClockParmsEntity;
import com.zkf.wx.chatbot.qywxentity.PunchClockResEntity;
import com.zkf.wx.chatbot.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 企业微信-打卡应用API
 * @author longwei
 * @date 2020年1月19日 下午5:39:39
 * @by https://work.weixin.qq.com/api/doc/11196
 */
public class PunchClockAPI {

	// 获取打卡数据接口地址(POST)
	public static final String PUNCH_CLOCK_URL = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=ACCESS_TOKEN";

	/**
	 * 获取打卡数据
	 * 
	 * @param accessToken token
	 * @return List<PunchClockResEntity>
	 */
	public List<PunchClockResEntity> getPunchClockInfo(String accessToken, PunchClockParmsEntity parmsEntity) {
		List<PunchClockResEntity> clockList = new ArrayList<PunchClockResEntity>();
		// 拼装获取指定部门的url
		String url = PUNCH_CLOCK_URL.replace("ACCESS_TOKEN", accessToken);
		String parmsJson = JSONObject.toJSONString(parmsEntity);
		// 调用接口获取指定部门
		JSONObject jsonObject = HttpUtil.sendPost(url, parmsJson);
		// 接口有值并返回成功 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			clockList = JSON.parseArray(jsonObject.getString("checkindata"), PunchClockResEntity.class);
		}
		return clockList;
	}

}
