package com.zkf.wx.chatbot.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkf.wx.chatbot.BaseTest;

/**
 * @Description 每天凌晨2点判断当日是否是工作日,写入redis 0 上班 1周末 2节假日 3 接口异常
 * @author longwei
 * @date 2020年2月18日 上午11:23:39
 */
public class Test_TodayIsWorkDayJob extends BaseTest {

	@Autowired
	TodayIsWorkDayJob job;

	@Test
	public void setTodayIsWorkDay() {
		job.setTodayIsWorkDay();
	}
	
}
