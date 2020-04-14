package com.zkf.wx.chatbot.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 日期时间帮助类
 * @author longwei
 * @date 2020年1月10日 下午3:15:12
 */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 判断当前日期是节假日还是工作日
	 * 
	 * @param httpArg :参数
	 * @return 0 上班 1周末 2节假日 3 接口异常
	 * @remark 该接口每日限调20次,后期改造每天定时获取一次,之后存入redis,从redis取当前是否是工作日
	 */
	public static String getWorkRestDay(String httpArg) {
		String httpUrl = "http://tool.bitefu.net/jiari/";
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?d=" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			// 接口异常
			e.printStackTrace();
			return "3";
		}
		return result;
	}

	/**
	 * 判断当日是否是工作日
	 * 
	 * @param redisToday redis中当日日期字符串 yyyyMMdd
	 * @remark 注意，该方法是和getWorkRestDay方法还有TodayIsWorkDayJob(redis)配合使用
	 * @return true-是,false-不是
	 */
	public static boolean isWorkDay(String redisToday) {
		// 0 上班 1周末 2节假日 3 接口异常
		if ("0".equals(redisToday)) {
			return true;
		}
		// 获取工作日工具异常处理
		if ("3".equals(redisToday)) {
			logger.error("--------调用工作日工具异常,请检查处理!!!");
			// 就算工具异常了,那用正常的工作日来推送打卡信息
			if (getOne2Five()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取今天日期 yyyyMMdd
	 * 
	 * @return yyyyMMdd
	 */
	public static String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	/**
	 * 获取日期月份 MM
	 * 
	 * @param str 日期yyyyMMdd,yyyy-MM-dd,yyyy/MM/dd
	 * @return MM
	 */
	public static String getMonth(String str) {
		str = str.replace("-", "").replace("/", "");
		return str.substring(4, 6);
	}

	/**
	 * 获取日期日 dd
	 * 
	 * @param str 日期yyyyMMdd,yyyy-MM-dd,yyyy/MM/dd
	 * @return dd
	 */
	public static String getDay(String str) {
		str = str.replace("-", "").replace("/", "");
		return str.substring(6, 8);
	}

	/**
	 * 获取指定日期时间戳
	 * 
	 * @param date yyyyMMdd,yyyy-MM-dd,yyyy/MM/dd
	 * @param hour 小时
	 * @return Long
	 */
	public static Long getAppointTimeStamp(String date, int hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		date = date.replace("-", "").replace("/", "");
		String dateStr = date + " " + hour + ":00:00";
		Long timeStamp = null;
		try {
			Date date1 = sdf.parse(dateStr);
			timeStamp = date1.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStamp;
	}

	/**
	 * 获取传入日期是周几
	 * 
	 * @param d1 日期
	 * @return String
	 */
	public static String getWeekZhCN(Date d1) {
		String weekDay = "";
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		weekDay = weekDays[w];

		return weekDay;
	}

	/**
	 * 判断当日是否是周一到周五
	 * 
	 * @return 周一到周五 true
	 */
	public static boolean getOne2Five() {
		String weekZhCN = getWeekZhCN(new Date());
		if ("1".equals(weekZhCN) || "2".equals(weekZhCN) || "3".equals(weekZhCN) || "4".equals(weekZhCN)
				|| "5".equals(weekZhCN)) {
			return true;
		}
		return false;
	}

}