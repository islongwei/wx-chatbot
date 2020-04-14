package com.zkf.wx.chatbot;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zkf.wx.chatbot.qywxentity.TextMessageEntity;
import com.zkf.wx.chatbot.utils.SendResult;
import com.zkf.wx.chatbot.utils.WxChatbotClient;

/**
 * @Description 机器人发送消息测试类
 * @author longwei
 * @date 2020年1月10日 下午3:57:31
 */
public class Test_TextMessage extends BaseTest{

    @Test
    public void testSendTextMessageWithAtAndAtAll() throws Exception {
    	
    	TextMessageEntity message = new TextMessageEntity("哈哈哈\n\n"+"xixiix");
        List<String> mentionedMobileList=new ArrayList<String>();
        mentionedMobileList.add("15675865922");//@群内成员  手机号
        message.setMentionedMobileList(mentionedMobileList);
        message.setIsAtAll(true);//@所有人
        SendResult result = WxChatbotClient.send(TestConfig.CHATBOT_WEBHOOK, message);
        System.out.println(result);
    }
}