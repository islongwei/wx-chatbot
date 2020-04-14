package com.zkf.wx.chatbot.entity;

import java.io.Serializable;

/**
 * @Description 公司某中心下的部门
 * @author longwei
 * @date 2020年1月19日 下午6:03:56
 */
public class DepartmentListConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	// 信息中心部门  1-信息中心，2-信息中心办公室，3-数据中心管理部,
	//               4-POS产品部，5-大数据应用部，6-数字化产品部
	private String infoCenterDept="1,2,3,4,5,6";

	public String getInfoCenterDept() {
		return infoCenterDept;
	}

}
