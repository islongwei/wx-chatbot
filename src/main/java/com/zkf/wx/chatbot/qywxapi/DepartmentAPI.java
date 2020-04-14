package com.zkf.wx.chatbot.qywxapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.enums.EnumMethod;
import com.zkf.wx.chatbot.qywxentity.DepartmentEntity;
import com.zkf.wx.chatbot.utils.WeChatRpcService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 企业微信-部门管理API
 * @author longwei
 * @date 2020年1月17日 下午4:42:18
 * @by https://work.weixin.qq.com/api/doc/90000/90135/90208
 */
public class DepartmentAPI {

	// 获取部门列表(GET) [获取指定部门]
	private static final String DEPTBYID_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
	// 获取部门列表(GET) [获取全量组织架构]
	private static final String DEPT_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

	/**
	 * 获取所有部门
	 * 
	 * @param accessToken token
	 * @return List<DepartmentEntity>
	 */
	public List<DepartmentEntity> getAllDepartment(String accessToken) {
		List<DepartmentEntity> dptList = new ArrayList<DepartmentEntity>();
		// 拼装获取全部部门的url
		String url = DEPT_LIST_URL.replace("ACCESS_TOKEN", accessToken);
		// 调用接口获取全部部门
		JSONObject jsonObject = WeChatRpcService.httpRequest(url, EnumMethod.GET.name(), null);
		// 接口有值并返回成功 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			dptList = JSON.parseArray(jsonObject.getString("department"), DepartmentEntity.class);
		}
		return dptList;
	}

	/**
	 * 根据部门id获取部门信息
	 * 
	 * @param departId    部门id
	 * @param accessToken token
	 * @return List<DepartmentEntity>
	 */
	public List<DepartmentEntity> getDepartmentById(String departId, String accessToken) {
		List<DepartmentEntity> dptList = new ArrayList<DepartmentEntity>();
		// 拼装获取指定部门的url
		String url = DEPTBYID_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("ID", departId);
		// 调用接口获取指定部门
		JSONObject jsonObject = WeChatRpcService.httpRequest(url, EnumMethod.GET.name(), null);
		// 接口有值并返回成功 0-成功
		if (null != jsonObject && 0 == jsonObject.getIntValue("errcode")) {
			dptList = JSON.parseArray(jsonObject.getString("department"), DepartmentEntity.class);
		}
		return dptList;
	}

}
