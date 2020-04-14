package com.zkf.wx.chatbot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zkf.wx.chatbot.qywxentity.AccessTokenEntity;
import com.zkf.wx.chatbot.service.IEachCenterPunchClockErrorJobService;
import com.zkf.wx.chatbot.service.IQywxBaseService;
import com.zkf.wx.chatbot.utils.DateUtils;
import com.zkf.wx.chatbot.utils.RedisUtil;

/**
 * @Description 各中心打卡异常人员消息Job
 * @author longwei
 * @date 2020年1月19日 下午2:34:22
 * @remark 推送上一个工作日打卡异常人员给管理组， 要求上一个工作日未打卡的补卡 ,
 *         上一个工作日下午推送打卡消息的时候将日期存入redis，方便取上一个工作日的打卡数据
 */
@Component
public class EachCenterPunchClockErrorJob {

	private static final Logger logger = LoggerFactory.getLogger(EachCenterPunchClockErrorJob.class);

	@Autowired
	IQywxBaseService qywxBaseService;

	@Autowired
	IEachCenterPunchClockErrorJobService punClockErrorService;

	@Autowired
	RedisUtil redisUtil;

	/**
	  * 信息中心打卡异常人员消息，推送上一个工作日打卡异常人员给管理组， 要求上一个工作日未打卡的补卡
	  * 早上10点推送消息
	 * @return void
	 */
	@Scheduled(cron = "0 0 10 * * ?")
	public void infoCenterPunchClockError() {

		logger.info("----------推送信息中心上一工作日异常打卡信息start!!!");

		// 1、判断当日是否是工作日
		String today = DateUtils.getToday();
		// 从redis 取出当日是否是工作日 0 上班 1周末 2节假日 3 接口异常
		Object obj = redisUtil.get(today);
		if (obj == null) {
			logger.error("--------redis获取当日" + today + "是否是工作日失败,请检查redis!!!");
			return;
		}
		if (!DateUtils.isWorkDay(obj.toString())) {
			logger.info("---------当日不是工作日end!!!");
			return;
		}

		// 2、获取真功夫通讯录token
		AccessTokenEntity mailListToken = qywxBaseService.getMailListToken();
		if (!"0".equals(mailListToken.getErrcode())) {
			logger.error("----------获取通讯录token错误码为:" + mailListToken.getErrcode());
			return;
		}

		// 3、信息中心打卡异常人员信息
		int errCount = punClockErrorService.infoCenterPunchClockError(mailListToken.getAccesstoken());

		logger.info("----------推送信息中心上一工作日异常打卡信息end,总条数:" + errCount);
	}

}
