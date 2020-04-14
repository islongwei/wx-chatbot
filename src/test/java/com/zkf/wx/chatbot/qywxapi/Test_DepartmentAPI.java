package com.zkf.wx.chatbot.qywxapi;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.BaseTest;
import com.zkf.wx.chatbot.qywxentity.DepartmentEntity;
import com.zkf.wx.chatbot.service.IQywxBaseService;

/**
 * @Description 企业部门api测试类
 * @author longwei
 * @date 2020年1月19日 上午10:42:07
 */
public class Test_DepartmentAPI extends BaseTest{

	DepartmentAPI api = new DepartmentAPI();
	String token = "x9DalEZWMEAuZsSpDqdNPkmQWpzh1DayV5v98wIF5rWCYpF1uhbqWk9L-s9joXIPJVSEn14PrzQ5rpP1-33GaHoxmH9Ph2epJfymZbc-CJScNARgVD8ZJWjrO5TUUwOuHIXUd4h9FCvnUMSV_MUzNXWNk_y_fP68bhf4yKq4y-RlrdNrTY-5ZZS0YSDdcnnk1QBQb7xCLHFEJFtA5CrKLg";
	
	@Autowired
	IQywxBaseService qywxBaseService;
	
	// 根据部门id获取部门信息
	@Test
	public void getDepartmentById() {
		List<DepartmentEntity> departmentById = api.getDepartmentById("1471", token);
		for (DepartmentEntity departmentEntity : departmentById) {
			System.out.println(JSONObject.toJSONString(departmentEntity));
		}
	}

	// 获取所有部门
	@Test
	public void getAllDepartment() {
		List<DepartmentEntity> departmentById = api.getAllDepartment(token);
		for (DepartmentEntity departmentEntity : departmentById) {
			System.out.println(JSONObject.toJSONString(departmentEntity));
		}
	}

}
