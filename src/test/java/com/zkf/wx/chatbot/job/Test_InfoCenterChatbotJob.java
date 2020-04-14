package com.zkf.wx.chatbot.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkf.wx.chatbot.BaseTest;

/**
 * @Description 信息中心群机器人推送打卡消息测试类
 * @author longwei
 * @date 2020年1月19日 下午2:46:31
 */
public class Test_InfoCenterChatbotJob extends BaseTest {

	@Autowired
	InfoCenterChatbotJob job;

	// 早上
	@Test
	public void runInfoCenterAmJob() {
		job.runInfoCenterAmJob();
	}
	
	// 下午
	@Test
	public void runInfoCenterPmJob() {
		job.runInfoCenterPmJob();
	}
	
	
}
