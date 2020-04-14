package com.zkf.wx.chatbot.qywxapi;

import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.enums.EnumMethod;
import com.zkf.wx.chatbot.qywxentity.AccessTokenEntity;
import com.zkf.wx.chatbot.utils.WeChatRpcService;

/**
 * @Description 企业微信获取 access_token API
 * @author longwei
 * @date 2020年1月17日 下午4:20:43
 * @by https://work.weixin.qq.com/api/doc/90000/90135/91039
 */
public class AccessTokenAPI {

	// 获取 access_token 的接口地址(GET)
	public static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";

	/**
	 * 获取access_token
	 * 
	 * @param corpID 企业Id
	 * @param secret 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	 * @return AccessTokenEntity
	 */
	public static AccessTokenEntity getAccessToken(String corpID, String secret) {
		AccessTokenEntity accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("CorpID", corpID).replace("SECRET", secret);
		JSONObject jsonObject = WeChatRpcService.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessTokenEntity();
				accessToken.setAccesstoken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
				accessToken.setErrcode(jsonObject.getString("errcode"));
				accessToken.setErrmsg(jsonObject.getString("errmsg"));
			} catch (Exception e) {
				accessToken = null;
				e.printStackTrace();
			}
		}
		return accessToken;
	}

}
