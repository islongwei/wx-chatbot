package com.zkf.wx.chatbot.qywxentity;

import java.io.Serializable;

/**
 * @Description 企业微信-打卡数据实体类
 * @author longwei
 * @date 2020年1月19日 下午2:24:30
 * @by https://work.weixin.qq.com/api/doc/11196
 */
public class PunchClockResEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid; // 用户id

	private String username; // 用户名

	private String groupname; // 打卡规则名称

	private String checkin_type; // 打卡类型。字符串，目前有：上班打卡，下班打卡，外出打卡

	private String exception_type; // 异常类型，字符串，包括：时间异常，地点异常，未打卡，wifi异常，非常用设备。如果有多个异常，以分号间隔

	private String checkin_time; // 打卡时间。Unix时间戳

	private String location_title; // 打卡地点title

	private String location_detail; // 打卡地点详情

	private String wifiname; // 打卡wifi名称

	private String notes; // 打卡备注

	private String wifimac; // 打卡的MAC地址/bssid

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getCheckin_type() {
		return checkin_type;
	}

	public void setCheckin_type(String checkin_type) {
		this.checkin_type = checkin_type;
	}

	public String getException_type() {
		return exception_type;
	}

	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}

	public String getCheckin_time() {
		return checkin_time;
	}

	public void setCheckin_time(String checkin_time) {
		this.checkin_time = checkin_time;
	}

	public String getLocation_title() {
		return location_title;
	}

	public void setLocation_title(String location_title) {
		this.location_title = location_title;
	}

	public String getLocation_detail() {
		return location_detail;
	}

	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}

	public String getWifiname() {
		return wifiname;
	}

	public void setWifiname(String wifiname) {
		this.wifiname = wifiname;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWifimac() {
		return wifimac;
	}

	public void setWifimac(String wifimac) {
		this.wifimac = wifimac;
	}

}
