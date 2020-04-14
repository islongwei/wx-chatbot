package com.zkf.wx.chatbot.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zkf.wx.chatbot.qywxapi.PunchClockAPI;
import com.zkf.wx.chatbot.qywxapi.UserAPI;
import com.zkf.wx.chatbot.qywxentity.PunchClockParmsEntity;
import com.zkf.wx.chatbot.qywxentity.PunchClockResEntity;
import com.zkf.wx.chatbot.qywxentity.UserEntity;

/**
 * @Description 打卡应用帮助类
 * @author longwei
 * @date 2020年2月12日 下午3:21:54
 */
@Component
public class PunchClockUtils {

	private static final Logger logger = LoggerFactory.getLogger(PunchClockUtils.class);

	static UserAPI userApi = new UserAPI();

	static PunchClockAPI clockAPI = new PunchClockAPI();

	/**
	 * 获取中心成员id,每99为一个list
	 * 
	 * @param accesstoken 通讯录token
	 * @param dept        中心部门数组
	 * @return Map<String,List<String>>
	 */
	public static Map<String, List<String>> getCenterUserIdBydept(String accesstoken, String[] dept) {

		// 获取中心成员
		List<String> userIdList = new ArrayList<String>();
		// 根据部门编号获取部门下的成员
		for (String deptId : dept) {
			List<UserEntity> userList = userApi.getUsersByDepartid(accesstoken, deptId, "0");
			for (UserEntity user : userList) {
				userIdList.add(user.getUserid());
			}
		}

		// 因为 调企业微信-打卡应用，用户列表不能超过100个。若用户超过100个，则需要分批获取
		// 用map存起来新的分组后数据,从userIdList0开始
		int maxtoIndex = 99;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (userIdList.size() > maxtoIndex) {
			int listSize = userIdList.size();
			int keyToken = 0;
			for (int i = 0; i < userIdList.size(); i += maxtoIndex) {
				if (i + maxtoIndex > listSize) { // 作用为maxtoIndex最后没有99条数据则剩余几条newList中就装几条
					maxtoIndex = listSize - i;
				}
				List<String> newList = userIdList.subList(i, i + maxtoIndex);
				map.put("userIdList" + keyToken, newList);
				keyToken++;
			}
		}
		if (userIdList.size() == 0) {
			return null;
		}
		if (userIdList.size() != 0 && userIdList.size() <= maxtoIndex) {
			map.put("userIdList" + 0, userIdList);
		}
		return map;
	}

	/**
	 * 获取上一个工作日中心部门成员打卡信息
	 * 
	 * @param dkToken     打卡token
	 * @param userIdMap   中心成员id Map,每99为一个list,key从userIdList0开始
	 * @param lastweekday 上一工作日
	 * @param startHour   打卡规则开始时间
	 * @param startHour   打卡规则截止时间
	 * @return List<PunchClockResEntity>
	 */
	public static List<PunchClockResEntity> getLastdayClockInfo(String dkToken, Map<String, List<String>> userIdMap,
			String lastweekday, int startHour, int endHours) {

		// 打卡记录开始的时间戳
		Long stateTimeStamp = DateUtils.getAppointTimeStamp(lastweekday, startHour);
		// 打卡记录结束的时间戳
		Long endTimeStamp = DateUtils.getAppointTimeStamp(DateUtils.getToday(), endHours);

		PunchClockParmsEntity parmsEntity = new PunchClockParmsEntity();
		parmsEntity.setOpencheckindatatype(3); // 3：全部打卡
		parmsEntity.setStarttime(stateTimeStamp);
		parmsEntity.setEndtime(endTimeStamp);
		List<PunchClockResEntity> punchClockList = new ArrayList<PunchClockResEntity>();
		// 循环读取中心人员打卡信息
		for (int i = 0; i < userIdMap.size(); i++) {
			List<String> userIdList = userIdMap.get("userIdList" + i);
			parmsEntity.setUseridlist(userIdList);
			List<PunchClockResEntity> punchClockInfo = clockAPI.getPunchClockInfo(dkToken, parmsEntity);
			punchClockList.addAll(punchClockInfo);
		}
		return punchClockList;
	}

	/**
	 * 获取上一个工作日中心部门成员异常打卡信息
	 * 
	 * @param dkToken     打卡token
	 * @param txlToken    通讯录token
	 * @param userIdMap   中心成员id Map,每99为一个list,key从userIdList0开始
	 * @param lastweekday 上一工作日
	 * @param startHour   打卡规则开始时间
	 * @param startHour   打卡规则截止时间
	 * @return Map<String, List<PunchClockResEntity>>
	 */
	public static Map<String, List<PunchClockResEntity>> getLastdayClockErrInfo(String dkToken, String txlToken,
			Map<String, List<String>> userIdMap, String lastweekday, int startHour, int endHours) {
		// 获取中心部门成员打卡信息
		List<PunchClockResEntity> lastdayClockInfo = getLastdayClockInfo(dkToken, userIdMap, lastweekday, startHour,
				endHours);
		if (lastdayClockInfo.isEmpty()) {
			logger.debug("--该中心部门在上一个工作日无打卡记录!!!");
		}

		// 异常打卡list
		List<PunchClockResEntity> errAll = new ArrayList<PunchClockResEntity>();
		// 正常打卡list
		List<PunchClockResEntity> normalAll = new ArrayList<PunchClockResEntity>();

		// 中心人员异常打卡信息
		// 1、先筛选出上下班异常打卡的全部数据和正常打卡数据
		for (PunchClockResEntity resEntity : lastdayClockInfo) {
			// 异常打卡类型不是空
			if (!"".equals(resEntity.getException_type())) {
				errAll.add(resEntity);
			} else {
				normalAll.add(resEntity);
			}
		}

		// 2、剔除上下班异常打卡之后补卡的正常数据 暂不支持请假、外出审批类数据
		List<PunchClockResEntity> removeList = new ArrayList<PunchClockResEntity>();
		for (PunchClockResEntity errEnt : errAll) {
			for (PunchClockResEntity normalEnt : normalAll) {
				// 剔除上下班异常打卡之后补卡的正常数据
				if (errEnt.getUserid().equals(normalEnt.getUserid())
						&& errEnt.getCheckin_type().equals(normalEnt.getCheckin_type())) {
					removeList.add(errEnt);
				}
			}
		}

		// 3、异常打卡list移除补卡的list
		errAll.removeAll(removeList);

		// 4、处理成员姓名,将异常打卡list拆分为上下班打卡两个类型的list
		List<PunchClockResEntity> upper = new ArrayList<PunchClockResEntity>();
		List<PunchClockResEntity> lower = new ArrayList<PunchClockResEntity>();
		for (PunchClockResEntity errEnt : errAll) {
			UserEntity user = userApi.getUserByUserid(errEnt.getUserid(), txlToken);
			errEnt.setUsername(user.getName());
			if ("上班打卡".equals(errEnt.getCheckin_type())) {
				upper.add(errEnt);
			}
			if ("下班打卡".equals(errEnt.getCheckin_type())) {
				lower.add(errEnt);
			}
		}

		// 返回上下班异常打卡数据
		Map<String, List<PunchClockResEntity>> errMap = new HashMap<String, List<PunchClockResEntity>>();
		errMap.put("upper", upper);
		errMap.put("lower", lower);

		return errMap;
	}

	/**
	 * 发送异常打卡消息
	 * 
	 * @param list       异常list
	 * @param webhookUrl 群机器人webhookUrl
	 * @param month      月
	 * @param day        日
	 * @param type       打卡类型 0-上班打卡,1-下班打卡
	 * @return List<PunchClockResEntity>
	 */
	public static void sendPunchClochErrMsg(List<PunchClockResEntity> list, String webhookUrl, String month, String day,
			String type) {

		// 发送的内容,不能超过2048字节，分批发送
		StringBuffer text = new StringBuffer();
		text.append(month).append("月").append(day).append("日");
		if ("0".equals(type)) {
			text.append("上班");
		}
		if ("1".equals(type)) {
			text.append("下班");
		}
		text.append("打卡异常数据如下:\n");
		String originalText = text.toString(); // 原始内容
		try {
			if (list.isEmpty()) {
				text.append("无异常打卡数据。");
				WxChatbotUtil.sendMsgTextToAll(webhookUrl, text.toString());
			} else {
				int originalByt = text.toString().getBytes().length; // 原始字节数
				int bytCount = originalByt; // 总字节数
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					// 清除上次存的内容
					sb.setLength(0);
					// 此次内容及字节
					String str = sb.append(list.get(i).getUsername()).append("/").append(list.get(i).getException_type()).append("\n").toString();
					int curByt = str.getBytes().length;

					bytCount += curByt;

					if (bytCount >= 2048) {
						// 群机器人发送上一工作日异常打卡消息
						WxChatbotUtil.sendMsgTextToAll(webhookUrl, text.toString());

						// 内容、集合下标、字节处理
						text.setLength(0);
						text.append(originalText);
						i--;
						bytCount = originalByt;
					} else {
						// 拼接此次内容
						text.append(str);
					}

					// 如果循环到最后一个下标内容还没超过2048字节,也发送消息
					if (list.size()-1 == i) {
						WxChatbotUtil.sendMsgTextToAll(webhookUrl, text.toString());
					}

				}
			}
		} catch (IOException e) {
			logger.error("--推送信息中心上一工作日异常打卡信息群机器人接口调用失败,请检查!!!" + e.getMessage());
			e.printStackTrace();
		}
	}

}
