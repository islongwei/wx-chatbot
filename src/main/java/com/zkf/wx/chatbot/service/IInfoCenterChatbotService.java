package com.zkf.wx.chatbot.service;

/**
 * @Description 信息中心群机器人推送打卡消息service层
 * @author longwei
 * @date 2020年1月11日 下午2:57:47
 */
public interface IInfoCenterChatbotService {

	/**
	  *  信息中心群机器人推送打卡消息
	 * @param setLastWeekDay 是否设置redis(lastweekday)
	 * @return void
	 */
	void infoCenterPunchCardMsg(boolean setLastWeekDay);
	
}
