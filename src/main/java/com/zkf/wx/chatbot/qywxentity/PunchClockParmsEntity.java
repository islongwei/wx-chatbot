package com.zkf.wx.chatbot.qywxentity;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 获取企业微信打卡数据参数实体类
 * @author longwei
 * @date 2020年1月19日 下午2:20:49
 * @by https://work.weixin.qq.com/api/doc/11196
 */
public class PunchClockParmsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int opencheckindatatype; // 打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡

	private Long starttime; // 获取打卡记录的开始时间。Unix时间戳

	private Long endtime; // 获取打卡记录的结束时间。Unix时间戳

	private List<String> useridlist; // 需要获取打卡记录的用户列表

	public int getOpencheckindatatype() {
		return opencheckindatatype;
	}

	public void setOpencheckindatatype(int opencheckindatatype) {
		this.opencheckindatatype = opencheckindatatype;
	}

	public Long getStarttime() {
		return starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public List<String> getUseridlist() {
		return useridlist;
	}

	public void setUseridlist(List<String> useridlist) {
		this.useridlist = useridlist;
	}

}
