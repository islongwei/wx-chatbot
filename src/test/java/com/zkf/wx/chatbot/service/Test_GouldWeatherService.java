package com.zkf.wx.chatbot.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.zkf.wx.chatbot.BaseTest;

/**
 * @Description 高德天气测试类
 * @author longwei
 * @date 2020年1月11日 下午4:52:34
 */
public class Test_GouldWeatherService extends BaseTest {

	@Autowired
	IGouldWeatherService service;

	@Test
	public void getWeather() {
		// 440115-南沙区
		String gouldWeather = service.getGouldWeather("440115");
		System.out.println(JSON.toJSONString(gouldWeather));
	}

}
