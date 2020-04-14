package com.zkf.wx.chatbot.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.qywxentity.Message;

/**
 * @Description 企业微信请求帮助类
 * @author longwei
 * @date 2020年1月10日 上午11:26:20
 */
public class WxChatbotClient {

	static HttpClient httpclient = HttpClients.createDefault();

	/**
	 * 方法说明 企业微信发送post请求
	 * 
	 * @param webhook 群机器人webhook地址
	 * @param message 具体消息
	 * @return SendResult
	 */
	public static SendResult send(String webhook, Message message) throws IOException {

		if (StringUtils.isBlank(webhook)) {
			return new SendResult();
		}
		HttpPost httppost = new HttpPost(webhook);
		httppost.addHeader("Content-Type", "application/json; charset=utf-8");
		StringEntity se = new StringEntity(message.toJsonString(), "utf-8");
		httppost.setEntity(se);

		SendResult sendResult = new SendResult();
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject obj = JSONObject.parseObject(result);

			Integer errcode = obj.getInteger("errcode");
			sendResult.setErrorCode(errcode);
			sendResult.setErrorMsg(obj.getString("errmsg"));
			sendResult.setIsSuccess(errcode.equals(0));
		}

		return sendResult;
	}

}
