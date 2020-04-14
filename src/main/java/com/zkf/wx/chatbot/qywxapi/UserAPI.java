package com.zkf.wx.chatbot.qywxapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.enums.EnumMethod;
import com.zkf.wx.chatbot.qywxentity.UserEntity;
import com.zkf.wx.chatbot.utils.WeChatRpcService;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description 企业微信-用户管理API
 * @author longwei
 * @date 2020年1月19日 上午11:14:00
 * @by https://work.weixin.qq.com/api/doc/90000/90135/90194
 */
public class UserAPI {

	// 1 获取成员
	private static final String USERBYID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
	// 2 获取部门下的成员
	private static final String USERLIST_BYDPTID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
	// 3 获取部门成员(详情)
	private static final String DETAILED_USERALIST_BYDPTID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";

	/**
	 * 
	 * 1 获取成员信息
	 * 
	 * @param userid      用户id
	 * @param accessToken token
	 * @return UserEntity
	 */
	public UserEntity getUserByUserid(String userid, String accessToken) {
		UserEntity user = new UserEntity();
		// 拼装获取成员的url
		String url = USERBYID_URL.replace("ACCESS_TOKEN", accessToken).replace("USERID", userid);
		// 调用接口获取成员
		JSONObject jsonObject = WeChatRpcService.httpRequest(url, EnumMethod.GET.name(), null);
		// 把对象转换成UserEntity 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			user = JSONObject.toJavaObject(jsonObject, UserEntity.class);
		}
		return user;
	}

	/**
	 * 2 根据部门id获取部门成员信息
	 * 
	 * @param accessToken token
	 * @param department_id 部门id
	 * @param fetch_child   是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
	 * @return List<UserEntity>
	 */
	public List<UserEntity> getUsersByDepartid(String accessToken, String department_id, String fetch_child) {
		List<UserEntity> userList = new ArrayList<UserEntity>();
		// 拼装获取部门成员的列表的url
		String url = USERLIST_BYDPTID_URL.replace("ACCESS_TOKEN", accessToken).replace("DEPARTMENT_ID", department_id)
				.replace("FETCH_CHILD", fetch_child);
		// 调用接口获取部门成员
		JSONObject jsonObject = WeChatRpcService.httpRequest(url, EnumMethod.GET.name(), null);
		// 把对象转换成List<UserEntity> 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			userList = JSON.parseArray(jsonObject.getString("userlist"), UserEntity.class);
		}
		return userList;
	}

	/**
	 * 
	 * 3 根据部门id获取部门成员信息(详情)
	 * 
	 * @param accessToken token
	 * @param department_id 部门id
	 * @param fetch_child   是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
	 * @return List<UserEntity>
	 */
	public List<UserEntity> getDetailUsersByDepartid(String accessToken, String department_id, String fetch_child) {
		List<UserEntity> userList = new ArrayList<UserEntity>();
		// 拼装获取部门成员(详情)的列表的url
		String url = DETAILED_USERALIST_BYDPTID_URL.replace("ACCESS_TOKEN", accessToken)
				.replace("DEPARTMENT_ID", department_id).replace("FETCH_CHILD", fetch_child);
		// 调用接口获取部门成员(详情)
		JSONObject jsonObject = WeChatRpcService.httpRequest(url, EnumMethod.GET.name(), null);
		// 把对象转换成List<UserEntity> 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			userList = JSON.parseArray(jsonObject.getString("userlist"), UserEntity.class);
		}
		return userList;
	}

}
