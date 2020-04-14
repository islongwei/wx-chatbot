package com.zkf.wx.chatbot.utils;

import java.io.IOException;

import com.zkf.wx.chatbot.qywxentity.TextMessageEntity;

/**
 * @Description 类的注释,说明类说明
 * @author longwei
 * @date 2020年2月20日 下午4:42:54
 */
public class WxChatbotUtil {

	/**
	 * 群机器人发送文本内容给所有人@all
	 * 
	 * @param webhook 群机器人webhook地址
	 * @param text    文本内容
	 * @return SendResult
	 * @throws IOException 
	 */
	public static SendResult sendMsgTextToAll(String webhook, String text) throws IOException {
		
		TextMessageEntity message = new TextMessageEntity(text);
		message.setIsAtAll(true);// @所有人
		return WxChatbotClient.send(webhook, message);
	}

}
