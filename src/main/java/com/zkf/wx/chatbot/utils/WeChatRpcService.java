package com.zkf.wx.chatbot.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSONObject;

public class WeChatRpcService {

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) { 
        JSONObject jsonObject = null; 
        StringBuffer buffer = new StringBuffer(); 
        try { 
        	System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            TrustManager[] tm = { new MyX509TrustManager() }; 
            SSLContext sslContext = SSLContext.getInstance("SSL"); 
            
            // 直接通过主机认证֤
            HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}};
            SSLSessionContext sslsc = sslContext.getServerSessionContext();
            sslsc.setSessionTimeout(0);
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            
            sslContext.init(null, tm, new java.security.SecureRandom()); 
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            SSLSocketFactory ssf = sslContext.getSocketFactory();     
            URL url = new URL(requestUrl); 
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection(); 
            httpUrlConn.setSSLSocketFactory(ssf); 
 
            httpUrlConn.setDoOutput(true); 
            httpUrlConn.setDoInput(true); 
            httpUrlConn.setUseCaches(false); 
            httpUrlConn.setRequestMethod(requestMethod); 
          
            if ("GET".equalsIgnoreCase(requestMethod)) 
                httpUrlConn.connect(); 
 
            if (null != outputStr) { 
                OutputStream outputStream = httpUrlConn.getOutputStream(); 
                outputStream.write(outputStr.getBytes("UTF-8")); 
                outputStream.close(); 
            } 
 
            InputStream inputStream = httpUrlConn.getInputStream(); 
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
 
            String str = null; 
            while ((str = bufferedReader.readLine()) != null) { 
                buffer.append(str); 
            } 
            bufferedReader.close(); 
            inputStreamReader.close(); 
            inputStream.close(); 
            inputStream = null; 
            httpUrlConn.disconnect(); 
            jsonObject = JSONObject.parseObject(buffer.toString());
        }  catch (Exception e) { 
        	e.printStackTrace();
        } 
        return jsonObject; 
    } 
}
