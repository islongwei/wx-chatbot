package com.zkf.wx.chatbot.qywxentity;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 群机器人推送文本消息
 * @author longwei
 * @date 2020年1月10日 上午10:56:50
 */
public class TextMessageEntity implements Message {

	private String text; // 文本内容，最长不超过2048个字节，必须是utf8编码

	private List<String> mentionedMobileList; // 指定@手机号对应成员

	private boolean isAtAll; // 是否@所有人 true-@所有人

	public TextMessageEntity(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAtAll() {
		return isAtAll;
	}

	public void setIsAtAll(boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

	public List<String> getMentionedMobileList() {
		return mentionedMobileList;
	}

	public void setMentionedMobileList(List<String> mentionedMobileList) {
		this.mentionedMobileList = mentionedMobileList;
	}

	public String toJsonString() {
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("msgtype", "text");

		Map<String, Object> textContent = new HashMap<String, Object>();
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("text should not be blank");
		}
		textContent.put("content", text);
		if (isAtAll) {
			if (mentionedMobileList == null) {
				mentionedMobileList = new ArrayList<String>();
			}
			mentionedMobileList.add("@all");
		}
		if (mentionedMobileList != null && !mentionedMobileList.isEmpty()) {
			textContent.put("mentioned_mobile_list", mentionedMobileList);
		}
		items.put("text", textContent);
		return JSON.toJSONString(items);
	}

}
