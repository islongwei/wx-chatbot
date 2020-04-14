package com.zkf.wx.chatbot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zkf.wx.chatbot.service.IInfoCenterChatbotService;
import com.zkf.wx.chatbot.utils.RedisUtil;

/**
 * @Description 信息中心群机器人推送打卡消息Job
 * @author longwei
 * @date 2020年1月10日 下午2:23:21
 */
@Component
public class InfoCenterChatbotJob {

	private static final Logger logger = LoggerFactory.getLogger(InfoCenterChatbotJob.class);

	@Autowired
	private IInfoCenterChatbotService chatbotService;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 工作日早上8点55分推送打卡消息
	 * 
	 * @return void
	 */
	@Scheduled(cron = "0 55 8 * * ?")
	public void runInfoCenterAmJob() {
		logger.info("--------信息中心推送打上班卡消息start!!!");
		// 推送打卡消息,因为在当日还要推送上一工作日的异常打卡情况,
		// 推送异常打卡时间是在8:55之后,所以早上不设置redis(lastweekday),不然上一工作日取数逻辑错误
		chatbotService.infoCenterPunchCardMsg(false);
		logger.info("--------信息中心推送上班卡消息end!!!");
	}

	/**
	 * 工作日下午17点59分推送打卡消息
	 * 
	 * @return void
	 */
	@Scheduled(cron = "0 59 17 * * ?")
	public void runInfoCenterPmJob() {
		logger.info("--------信息中心推送打下班卡消息start!!!");
		// 推送打卡消息,因为在当日还要推送上一工作日的异常打卡情况,
		// 所以在下午推送打卡信息的时候记录当日日期作为下一个工作日的上一个工作日
		chatbotService.infoCenterPunchCardMsg(true);
		logger.info("--------redis lastweekday->" + redisUtil.get("lastweekday") + "--信息中心推送下班卡消息end!!!");
	}

}
