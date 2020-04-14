package com.zkf.wx.chatbot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zkf.wx.chatbot.service.IGouldWeatherService;
import com.zkf.wx.chatbot.service.IInfoCenterChatbotService;
import com.zkf.wx.chatbot.utils.DateUtils;
import com.zkf.wx.chatbot.utils.RedisUtil;
import com.zkf.wx.chatbot.utils.SendResult;
import com.zkf.wx.chatbot.utils.WxChatbotUtil;

/**
 * @Description 信息中心群机器人推送打卡消息service层实现类
 * @author longwei
 * @date 2020年1月11日 下午4:52:34
 */
@Service
public class InfoCenterChatbotServiceImpl implements IInfoCenterChatbotService {

	private static final Logger logger = LoggerFactory.getLogger(InfoCenterChatbotServiceImpl.class);

	@Autowired
	IGouldWeatherService weatherService;

	@Autowired
	RedisUtil redisUtil;

	/** 信息中心群机器人webHook地址 */
	@Value("${InfoCenterWebhook_Url}")
	String infoCenterWebhookUrl;

	@Override
	public void infoCenterPunchCardMsg(boolean setLastWeekDay) {

		// 发送的内容
		String text = "温馨提示:请大家记得打卡!\n\n";
		String today = DateUtils.getToday();

		// 从redis 取出当日是否是工作日 0 上班 1周末 2节假日 3 接口异常
		Object obj = redisUtil.get(today);
		if (obj == null) {
			logger.error("--------redis获取当日" + today + "是否是工作日失败,请检查redis!!!");
			return;
		}
		// 是否是工作日
		boolean isWorkDay = false;
		try {
			if (DateUtils.isWorkDay(obj.toString())) {
				logger.info("--------当前是0-工作日work!!!");
				isWorkDay = true;

				// 得到天气内容(高德天气) 440115-南沙区
				String weatherText = weatherService.getGouldWeather("440115");

				// 拼接天气内容,发送打卡消息
				text = text + weatherText;

				// 调群机器人接口,推送打卡消息
				SendResult result = WxChatbotUtil.sendMsgTextToAll(infoCenterWebhookUrl, text);
				logger.info("--------调群机器人接口end:" + result);
			}
		} catch (Exception e) {
			logger.error("--------信息中心群机器人接口调用失败,请检查!!!" + e.getMessage());
			e.printStackTrace();
		} finally {
			// 因为下一个工作日推送上一个工作日(当前)的打卡异常情况，所以在当前工作日的下午设置redis
			if (isWorkDay && setLastWeekDay) {
				boolean flag = redisUtil.set("lastweekday", today, 0);
				logger.info("--------redis set(lastweekday)->:" + flag);
			}
		}
	}

}
