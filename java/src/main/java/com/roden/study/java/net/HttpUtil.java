package com.roden.study.java.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
	public static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	public static String post(String url,String jsonString) throws IOException {
		HttpURLConnection connection = null;
		try {
			// 创建连接			
			connection = (HttpURLConnection) new URL(url).openConnection();
			// 设置http连接属性
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST"); // 可以根据需要 提交 GET、POST、DELETE、INPUT等http提供的功能
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			// 设置http头 消息
			connection.setRequestProperty("Content-Type", "application/json"); // 设定 请求格式json，也可以设定xml格式的
			// connection.setRequestProperty("Content-Type", "text/xml");
			// 请求格式 xml，
			connection.setRequestProperty("Accept", "application/json");// 设定响应的信息的格式为json，也可以设定xml格式的
			// connection.setRequestProperty("X-Auth-Token","xx");
			//特定http服务器需要的信息，根据服务器所需要求添加
			connection.connect();			
			OutputStream out = connection.getOutputStream();
			out.write(jsonString.getBytes());
			out.flush();
			out.close();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			
			reader.close();
			//// 断开连接
			connection.disconnect();
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String httpRequest(String requestUrl , String requestMethod , String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL" , "SunJSSE");
			sslContext.init(null , tm , new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream , "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("ConnectException",ce);
		} catch (Exception e) {
			log.error("error.");
		}
		return null;
	}

	public static String getContent(String url) {
		try {
			URL Url = new URL(url);
			HttpURLConnection httpconn = (HttpURLConnection) Url
					.openConnection();
			httpconn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			httpconn.setUseCaches(true);
			httpconn.setRequestMethod("GET");
			httpconn.setRequestProperty("Content-type", "text/html");
			httpconn.setRequestProperty("Accept-Charset", "utf-8");
			httpconn.setRequestProperty("contentType", "utf-8");

			// 连接 超时时间
			httpconn.setConnectTimeout(300000);

			// Socket 超时时间
			httpconn.setReadTimeout(320000);
			String content = "";

			httpconn.connect();
			InputStream input = httpconn.getInputStream();

			InputStreamReader inputReader = new InputStreamReader(input);

			BufferedReader reader = new BufferedReader(inputReader);

			String inputLine = null;

			StringBuffer sb = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				sb.append(inputLine).append("\n");
			}

			if (sb != null)
				content = sb.toString();
			reader.close();

			inputReader.close();

			input.close();

			httpconn.disconnect();
			return content;
		} catch (Exception e) {
			return "{}";
		}
	}
	public static void main(String[] args) throws IOException {
		
	}

}