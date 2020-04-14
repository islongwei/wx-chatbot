package com.zkf.wx.chatbot.service;

/**
 * @Description 各中心打卡异常人员消息Service层
 * @author longwei
 * @date 2020年1月19日 下午3:49:49
 */
public interface IEachCenterPunchClockErrorJobService {

	/**
	 * 信息中心打卡异常人员信息
	 *
	 * @param accesstoken 通讯录token
	 * @return int
	 */
	int infoCenterPunchClockError(String accesstoken);

}
