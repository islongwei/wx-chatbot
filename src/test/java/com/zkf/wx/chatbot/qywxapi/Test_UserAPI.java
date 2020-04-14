package com.zkf.wx.chatbot.qywxapi;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.BaseTest;
import com.zkf.wx.chatbot.qywxentity.UserEntity;
import com.zkf.wx.chatbot.service.IQywxBaseService;

/**
 * @Description 企业部门api测试类
 * @author longwei
 * @date 2020年1月19日 上午10:42:07
 */
public class Test_UserAPI extends BaseTest{

	UserAPI api = new UserAPI();
	String token = "x9DalEZWMEAuZsSpDqdNPkmQWpzh1DayV5v98wIF5rWCYpF1uhbqWk9L-s9joXIPJVSEn14PrzQ5rpP1-33GaHoxmH9Ph2epJfymZbc-CJScNARgVD8ZJWjrO5TUUwOuHIXUd4h9FCvnUMSV_MUzNXWNk_y_fP68bhf4yKq4y-RlrdNrTY-5ZZS0YSDdcnnk1QBQb7xCLHFEJFtA5CrKLg";
	
	@Autowired
	IQywxBaseService qywxBaseService;
	
	// 1 获取成员信息
	@Test
	public void getUserByUserid() {
		System.out.println(JSONObject.toJSONString(api.getUserByUserid("LongWei", token)));
	}

	// 2 根据部门id获取部门成员
	@Test
	public void getUsersByDepartid() {
		List<UserEntity> usersByDepartid = api.getUsersByDepartid(token, "1471", "1");
		for (UserEntity userEntity : usersByDepartid) {
			System.out.println(JSONObject.toJSONString(userEntity));
		}
	}

	// 3 根据部门id获取部门成员信息(详情)
	@Test
	public void getDetailUsersByDepartid() {
		List<UserEntity> usersByDepartid = api.getDetailUsersByDepartid(token, "1471", "1");
		for (UserEntity userEntity : usersByDepartid) {
			System.out.println(JSONObject.toJSONString(userEntity));
		}
	}

}
