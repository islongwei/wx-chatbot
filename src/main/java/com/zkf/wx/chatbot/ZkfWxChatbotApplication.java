package com.zkf.wx.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description 企业微信群机器人主函数
 * @author longwei
 * @date 2020年1月10日 下午2:03:11
 */
@SpringBootApplication
@EnableScheduling
public class ZkfWxChatbotApplication {
	private final static Logger logger = LoggerFactory.getLogger(ZkfWxChatbotApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZkfWxChatbotApplication.class, args);
		logger.info("Service [zkf-wx-chatbot] is already started!!!");
	}

}
