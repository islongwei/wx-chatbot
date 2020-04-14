package com.zkf.wx.chatbot.entity;

import java.io.Serializable;

/**
 * @Description 高德天气实体
 * @author longwei
 * @date 2020年1月11日 下午2:36:55
 */
public class GouldWeatherEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String province; // 省份

	private String city; // 区

	private String date; // 日期

	private String dayweather; // 白天天气现象

	private String daywind; // 白天风向

	private String week; // 星期几

	private String daypower; // 白天风力

	private String daytemp; // 白天温度

	private String nightwind; // 晚上风向

	private String nighttemp; // 晚上温度

	private String nightweather; // 晚上天气现象

	private String nightpower; // 晚上风力

	public GouldWeatherEntity() {
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDayweather() {
		return dayweather;
	}

	public void setDayweather(String dayweather) {
		this.dayweather = dayweather;
	}

	public String getDaywind() {
		return daywind;
	}

	public void setDaywind(String daywind) {
		this.daywind = daywind;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDaypower() {
		return daypower;
	}

	public void setDaypower(String daypower) {
		this.daypower = daypower;
	}

	public String getDaytemp() {
		return daytemp;
	}

	public void setDaytemp(String daytemp) {
		this.daytemp = daytemp;
	}

	public String getNightwind() {
		return nightwind;
	}

	public void setNightwind(String nightwind) {
		this.nightwind = nightwind;
	}

	public String getNighttemp() {
		return nighttemp;
	}

	public void setNighttemp(String nighttemp) {
		this.nighttemp = nighttemp;
	}

	public String getNightweather() {
		return nightweather;
	}

	public void setNightweather(String nightweather) {
		this.nightweather = nightweather;
	}

	public String getNightpower() {
		return nightpower;
	}

	public void setNightpower(String nightpower) {
		this.nightpower = nightpower;
	}

}
