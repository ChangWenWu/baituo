package com.example.delivery_First;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;




public class HttpUtils {
	// private static String PATH ;
	private static URL url;

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String sendPostMessage(Map<String, String> params,
			String encode, String PATH) {
		try {
			url = new URL(PATH);

		} catch (Exception e) {
			// TODO: handle exception
		//	Log.i("agan", "result->>" + "url链接异常");
		}
		StringBuffer buffer = new StringBuffer();
		try {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					buffer.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(), encode))
							.append("&");
				}
			}
			buffer.deleteCharAt(buffer.length() - 1);

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			
			if (null != App.sessionid || !"".equals(App.sessionid)) {
				urlConnection.setRequestProperty("Cookie", App.sessionid);
			}
			
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);// 从服务端取数据
			urlConnection.setDoOutput(true); // 向服务端写数据
			// 数据上传的数组
			byte[] mydata = buffer.toString().getBytes();
			// 表示设置请求体的类型和长度
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					String.valueOf(mydata.length));
			// 获得输出流，向服务器输出数据
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata);
			outputStream.flush();
			outputStream.close();
			createSession(urlConnection);
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				return changeInputStream(urlConnection.getInputStream(), encode);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		//	Log.i("agan", "result->>" + "链接异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	Log.i("agan", "result->>" + "IO链接异常");
		}
		return "未连接服务器";
	}

	/**
	 * @将的字符流转换为指定的字符串
	 * @param inputStream
	 * @param encode
	 * @return
	 */

	private static String changeInputStream(InputStream inputStream,
			String encode) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				outputStream.flush();
				outputStream.close();
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			//	Log.i("agan", "result->>" + "字异常");
				e.printStackTrace();
			}
		}
		return result;
	}
	public static  void createSession(HttpURLConnection urlConn) {
        // System.err.println(App.sessionid);
		if (null == App.sessionid || "".equals(App.sessionid)) {
			String cookieval = urlConn.getHeaderField("Set-Cookie");
			if (cookieval != null) {
				App.sessionid = cookieval.substring(0, cookieval.indexOf(";"));
			}
			App.sessionCreateTime = new Date().getTime();
			
		}
		// session 20分钟失效
		long nowTime = new Date().getTime();
		int min = (int) ((nowTime - App.sessionCreateTime) / (1000 * 60));
		if (min >= 15) {
			String cookieval = urlConn.getHeaderField("Set-Cookie");
			if (cookieval != null) {
				App.sessionid = cookieval.substring(0, cookieval.indexOf(";"));
			}
			App.sessionCreateTime = new Date().getTime();
		} else {
			App.sessionCreateTime = new Date().getTime();
		}

	}
}
