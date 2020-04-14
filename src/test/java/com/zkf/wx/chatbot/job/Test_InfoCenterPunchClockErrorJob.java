package com.zkf.wx.chatbot.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkf.wx.chatbot.BaseTest;

/**
 * @Description 信息中心打卡异常人员消息测试类
 * @author longwei
 * @date 2020年1月19日 下午2:46:31
 */
public class Test_InfoCenterPunchClockErrorJob extends BaseTest {

	@Autowired
	EachCenterPunchClockErrorJob job;

	@Test
	public void infoCenterPunchClockErrorJob() {
		job.infoCenterPunchClockError();
	}

}
