package com.zkf.wx.chatbot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.entity.GouldWeatherEntity;
import com.zkf.wx.chatbot.service.IGouldWeatherService;
import com.zkf.wx.chatbot.utils.HttpUtil;

/**
 * @Description 高德天气Service层实现类
 * @author longwei
 * @date 2020年2月16日 下午6:52:23
 */
@Service
public class GouldWeatherServiceImpl implements IGouldWeatherService {

	private static final Logger logger = LoggerFactory.getLogger(GouldWeatherServiceImpl.class);

	/** 高德地图api地址 */
	@Value("${Gould_Url}")
	String gouldUrl;

	/** 高德地图key */
	@Value("${Gould_Key}")
	String gouldKey;

	@Override
	public String getGouldWeather(String cityCode) {

		try {
			// 获取天气内容 extensions-all预报天气
			String gouldweatherUrl = gouldUrl + "?key=" + gouldKey + "&city="+cityCode+"&extensions=all";
			JSONObject weatherInfo = HttpUtil.sendGet(gouldweatherUrl);
			// 返回状态 1：成功；0：失败
			if ("1".equals(weatherInfo.get("status"))) {
				logger.info("-------------获取到高德天气信息!!!");
				// 得到预报天气信息数据 forecasts
				JSONArray forecastsArray = (JSONArray) weatherInfo.get("forecasts");
				JSONObject forecastsObj = forecastsArray.getJSONObject(0);

				// 得到预报数据list结构，元素cast,按顺序为当天、第二天、第三天的预报数据
				JSONArray castsArray = (JSONArray) forecastsObj.get("casts");
				// 只需要当天和第二天的天气信息
				GouldWeatherEntity todayWeatherEntity = JSON.toJavaObject(castsArray.getJSONObject(0),GouldWeatherEntity.class);
				String todayTemperature = dealTemperature(todayWeatherEntity);
				GouldWeatherEntity tomorrowWeatherEntity = JSON.toJavaObject(castsArray.getJSONObject(1),GouldWeatherEntity.class);
				String tomorrowTemperature = dealTemperature(tomorrowWeatherEntity);

				// 格式 今天：阴，17℃~25℃，风力3级。
				String city = forecastsObj.getString("city");
				String today = "今天: " + todayWeatherEntity.getDayweather() + "," + todayTemperature + ",风力"
						+ todayWeatherEntity.getDaypower() + "级。";
				String tomorrow = "明天: " + tomorrowWeatherEntity.getDayweather() + "," + tomorrowTemperature + ",风力"
						+ tomorrowWeatherEntity.getDaypower() + "级。";
				String text = city + "天气: \n\n" + today + "\n\n" + tomorrow;
				return text;
			}
		} catch (Exception e) {
			// 异常友好提示，开发员检查接口错误
			logger.error("----------获取到高德天气信息异常,请检查高德接口!!!");
			return "因高德天气缺乏数据导致数据失败\n\n对此给您带来的不便深感抱歉";
		}

		return "因高德天气缺乏数据导致数据失败\n\n对此给您带来的不便深感抱歉";
	}

	/**
	 * 处理温度信息，温度低~温度高，如果温度相同，则取一个温度就好
	 * 
	 * @param gouldWeatherEntity 高德天气实体
	 * @return String
	 */
	public String dealTemperature(GouldWeatherEntity gouldWeatherEntity) {
		String dayTemp = gouldWeatherEntity.getDaytemp();
		String nightTemp = gouldWeatherEntity.getNighttemp();
		float dayTempFloat = Float.parseFloat(dayTemp);
		float nightTempFloat = Float.parseFloat(nightTemp);

		// 这里不用转的float数据，整数转了之后麻烦去小数点
		if (dayTempFloat > nightTempFloat) {
			return nightTemp + "℃~" + dayTemp + "℃";
		} else if (dayTempFloat < nightTempFloat) {
			return dayTemp + "℃~" + nightTemp + "℃";
		} else {
			return dayTemp + "℃";
		}
	}

}
