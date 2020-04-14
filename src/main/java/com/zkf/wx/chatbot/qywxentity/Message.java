package com.zkf.wx.chatbot.qywxentity;

/**
 * @Description 消息的Json格式字符串封装接口
 * @author longwei
 * @date 2020年1月10日 下午2:32:20
 */
public interface Message {

	/**
	 * 返回消息的Json格式字符串
	 * 
	 * @return 消息的Json格式字符串
	 */
	String toJsonString();

}
