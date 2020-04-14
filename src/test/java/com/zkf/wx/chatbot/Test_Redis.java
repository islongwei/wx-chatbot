package com.zkf.wx.chatbot;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkf.wx.chatbot.utils.RedisUtil;

/**
 * @Description Redis测试类
 * @author longwei
 * @date 2020年2月16日 下午8:37:32
 */
public class Test_Redis extends BaseTest{

	@Autowired
	RedisUtil redisUtil;

	@Test
	public void setStringRedis() {

		System.out.println(redisUtil.set("lastweekday", "20200413", 0));

		System.out.println(redisUtil.set("20200414", "0", 0));

		Object object = redisUtil.get("lastweekday");
		System.out.println(object.toString());
	}

}
