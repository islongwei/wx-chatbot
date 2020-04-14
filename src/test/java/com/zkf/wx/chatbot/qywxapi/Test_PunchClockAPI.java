package com.zkf.wx.chatbot.qywxapi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkf.wx.chatbot.BaseTest;
import com.zkf.wx.chatbot.qywxentity.PunchClockParmsEntity;
import com.zkf.wx.chatbot.service.IQywxBaseService;

/**
 * @Description 企业微信-打卡应用API测试类
 * @author longwei
 * @date 2020年1月19日 下午5:51:53
 */
public class Test_PunchClockAPI extends BaseTest{
	
	PunchClockAPI api = new PunchClockAPI();
	String token = "x9DalEZWMEAuZsSpDqdNPkmQWpzh1DayV5v98wIF5rWCYpF1uhbqWk9L-s9joXIPJVSEn14PrzQ5rpP1-33GaHoxmH9Ph2epJfymZbc-CJScNARgVD8ZJWjrO5TUUwOuHIXUd4h9FCvnUMSV_MUzNXWNk_y_fP68bhf4yKq4y-RlrdNrTY-5ZZS0YSDdcnnk1QBQb7xCLHFEJFtA5CrKLg";
	
	@Autowired
	IQywxBaseService qywxBaseService;
	
	// 获取打卡数据
	@Test
	public void getPunchClockInfo() {
		
		PunchClockParmsEntity parmsEntity = new PunchClockParmsEntity();
		parmsEntity.setOpencheckindatatype(3);
		parmsEntity.setStarttime(new Long(1579464000));
		parmsEntity.setEndtime(new Long(1579550400));
		
		List<String> list = new ArrayList<String>();
		list.add("ZKF19081503010");
		list.add("ZKF15082400265");
		list.add("ZKF2018070000281");
		list.add("ZKF1312197111");
		
		parmsEntity.setUseridlist(list);
		
		api.getPunchClockInfo(token, parmsEntity);
	}

}
