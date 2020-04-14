package com.zkf.wx.chatbot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zkf.wx.chatbot.utils.DateUtils;
import com.zkf.wx.chatbot.utils.RedisUtil;

/**
 * @Description 因为判断工作日的接口每天限调20次,所以该Job每日设置当日是否是工作日
 * @author longwei
 * @date 2020年2月17日 下午6:27:56
 */
@Component
public class TodayIsWorkDayJob {

	private static final Logger logger = LoggerFactory.getLogger(TodayIsWorkDayJob.class);

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 每天凌晨2点判断当日是否是工作日,写入redis 0 上班 1周末 2节假日 3 接口异常
	 * 
	 * @return void
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	public void setTodayIsWorkDay() {

		logger.info("-----redis写入 当日是否是工作日start!!!");

		// 当日日期 yyyyMMdd
		String today = DateUtils.getToday();
		// 0 上班 1周末 2节假日 3 接口异常
		String workRestDay = DateUtils.getWorkRestDay(today);
		// 三天后过期
		long time = 3 * 24 * 60 * 60;

		boolean flag = redisUtil.set(today, workRestDay, time);

		logger.info("-----redis写入当日:" + today + " 是" + workRestDay + " 存入redis:" + flag);

	}

}
