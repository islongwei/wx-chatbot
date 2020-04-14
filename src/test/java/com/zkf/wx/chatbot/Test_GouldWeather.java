package com.zkf.wx.chatbot;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkf.wx.chatbot.entity.GouldWeatherEntity;
import com.zkf.wx.chatbot.utils.HttpUtil;

/**
 * @Description 高德天气测试类
 * @author longwei
 * @date 2020年1月10日 下午3:57:16
 */
public class Test_GouldWeather extends BaseTest{

    @Test
    public void getGouldWeather(){
    	
    	String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=096a28e87615d639bfe6310cbc98e224&city=440115&extensions=all";
    	
    	JSONObject sendGet = HttpUtil.sendGet(url);
    	JSONArray array = (JSONArray) sendGet.get("forecasts");
    	
    	JSONObject jsonObject = array.getJSONObject(0);
    	
    	String province = (String) jsonObject.get("province");
    	String city = (String) jsonObject.get("city");
    	
    	JSONArray weather = (JSONArray) jsonObject.get("casts");
    	int i = 0;
    	for (Object object : weather) {
			System.out.println(i++ +":"+object);
		}
    	JSONObject job = weather.getJSONObject(0);
    	GouldWeatherEntity gouldWeatherEntity = JSON.toJavaObject(job, GouldWeatherEntity.class);
    	gouldWeatherEntity.setProvince(province);
    	gouldWeatherEntity.setCity(city);
    	System.out.println(JSON.toJSONString(gouldWeatherEntity));
    	
    }
}