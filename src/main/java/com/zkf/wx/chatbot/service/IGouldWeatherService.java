package com.zkf.wx.chatbot.service;

/**
 * @Description 高德天气Service层
 * @author longwei
 * @date 2020年2月16日 下午6:49:59
 */
public interface IGouldWeatherService {

	/**
	 * 获取地区天气预报信息
	 * 
	 * @param cityCode 城市编码
	 * @return 天气信息
	 */
	String getGouldWeather(String cityCode);

}
