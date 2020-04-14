package com.zkf.wx.chatbot.qywxentity;

import java.io.Serializable;

/**
 * @Description 企业微信部门实体类
 * @author longwei
 * @date 2020年1月17日 下午4:56:30
 * @by https://work.weixin.qq.com/api/doc/90000/90135/90208
 */
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id; // 部门id
	
	private String name; // 部门名称
	
	private String parentid; // 父亲部门id
	
	private String order; // 在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "DepartmentEntity [id=" + id + ", name=" + name + ", parentid=" + parentid + ", order=" + order + "]";
	}
	
}
