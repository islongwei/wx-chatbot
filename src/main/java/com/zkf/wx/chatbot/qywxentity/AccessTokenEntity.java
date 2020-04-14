package com.zkf.wx.chatbot.qywxentity;

import java.io.Serializable;

/**
 * @Description 企业微信通用接口凭证
 * @author longwei
 * @date 2020年1月17日 下午4:18:46
 * @by https://work.weixin.qq.com/api/doc/90000/90135/91039
 */
public class AccessTokenEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errcode; // 出错返回码，为0表示成功，非0表示调用失败

	private String errmsg; // 返回码提示语

	private String accesstoken; // 获取到的凭证

	private int expiresIn; // 凭证有效时间, 单位：秒

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}