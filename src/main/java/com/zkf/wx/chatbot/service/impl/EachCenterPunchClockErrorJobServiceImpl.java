package com.zkf.wx.chatbot.service.impl;

import com.zkf.wx.chatbot.entity.DepartmentListConfig;
import com.zkf.wx.chatbot.qywxapi.PunchClockAPI;
import com.zkf.wx.chatbot.qywxentity.AccessTokenEntity;
import com.zkf.wx.chatbot.qywxentity.PunchClockResEntity;
import com.zkf.wx.chatbot.service.IEachCenterPunchClockErrorJobService;
import com.zkf.wx.chatbot.service.IQywxBaseService;
import com.zkf.wx.chatbot.utils.DateUtils;
import com.zkf.wx.chatbot.utils.PunchClockUtils;
import com.zkf.wx.chatbot.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 真功夫各中心打卡异常人员消息Service层实现类
 * @author longwei
 * @date 2020年1月19日 下午4:03:02
 */
@Service
public class EachCenterPunchClockErrorJobServiceImpl implements IEachCenterPunchClockErrorJobService {

	private static final Logger logger = LoggerFactory.getLogger(EachCenterPunchClockErrorJobServiceImpl.class);

	@Autowired
	IQywxBaseService qywxBaseService;

	@Autowired
	RedisUtil redisUtil;

	/** 信息中心管理组群机器人webHook地址 */
	@Value("${InfoManageWebhook_Url}")
	String infoManageWebhookUrl;

	PunchClockAPI clockAPI = new PunchClockAPI();

	@Override
	public int infoCenterPunchClockError(String accesstoken) {

		DepartmentListConfig deptList = new DepartmentListConfig();
		// 信息中心部门数组
		String[] dept = deptList.getInfoCenterDept().split(",");
		// 每99人为一个key,key从userIdList0开始
		Map<String, List<String>> userIdMap = PunchClockUtils.getCenterUserIdBydept(accesstoken, dept);
		if (userIdMap == null) {
			logger.info("----------该中心部门无成员: null!!!");
			return -1;
		}

		// redis取出上一工作日日期
		Object lastweekday = redisUtil.get("lastweekday");
		if (lastweekday == null) {
			logger.error("--请检查redis: lastweekday->" + lastweekday);
			return -1;
		}

		// 获取真功夫打卡token
		AccessTokenEntity punchClockToken = qywxBaseService.getPunchClockToken();
		if (!"0".equals(punchClockToken.getErrcode())) {
			logger.error("----------获取打卡token错误码为:" + punchClockToken.getErrcode());
			return -1;
		}
		// 信息中心打卡规则 当天凌晨的4点之后到第二天凌晨4点之前，获取上一个工作日打卡异常人员信息
		Map<String, List<PunchClockResEntity>> errMap = PunchClockUtils.getLastdayClockErrInfo(
				punchClockToken.getAccesstoken(), accesstoken, userIdMap, lastweekday.toString(), 4, 4);
		logger.info("----------获取到信息中心上一个工作日打卡异常信息!!!");

		String month = DateUtils.getMonth(lastweekday.toString());
		String day = DateUtils.getDay(lastweekday.toString());

		// 发送上一工作日上班打卡异常消息
		List<PunchClockResEntity> upper = errMap.get("upper");
		PunchClockUtils.sendPunchClochErrMsg(upper, infoManageWebhookUrl, month, day, "0");
		logger.info("----------发送上一工作日上班打卡异常消息条数:"+upper.size());

		// 发送上一工作日下班打卡异常消息
		List<PunchClockResEntity> lower = errMap.get("lower");
		PunchClockUtils.sendPunchClochErrMsg(lower, infoManageWebhookUrl, month, day, "1");
		logger.info("----------发送上一工作日下班打卡异常消息条数:"+lower.size());

		return upper.size()+lower.size();
	}

}
