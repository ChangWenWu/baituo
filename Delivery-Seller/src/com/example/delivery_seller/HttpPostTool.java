package com.example.delivery_seller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class HttpPostTool {

	private final String TAG = "gan";

	/**
	 * �ϴ��ļ���������
	 * 
	 * @author Administrator
	 * 
	 */
	/**
	 * ֱ��ͨ��HTTPЭ���ύ���ݵ�������,ʵ����������ύ����: <FORM METHOD=POST
	 * ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet"
	 * enctype="multipart/form-data"> <INPUT TYPE="text" NAME="name"> <INPUT
	 * TYPE="text" NAME="id"> <input type="file" name="imagefile"/> <input
	 * type="file" name="zip"/> </FORM>
	 * 
	 * @param path
	 *            �ϴ�·��(ע������ʹ��localhost��127.0.0.1������·�����ԣ���Ϊ����ָ���ֻ�ģ�����������ʹ��http://
	 *            www.iteye.cn��http://192.168.1.101:8083������·������)
	 * @param params
	 *            ������� keyΪ������,valueΪ����ֵ
	 * @param file
	 *            �ϴ��ļ�
	 */
	@SuppressWarnings("resource")
	public boolean post(String path, Map<String, String> params,
			FormFile[] files) throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // ���ݷָ���
		final String endline = "--" + BOUNDARY + "--\r\n";// ���ݽ�����־
		int fileDataLength = 0;
		for (FormFile uploadFile : files) {// �õ��ļ��������ݵ��ܳ���
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null) {
				fileDataLength += uploadFile.getFile().length();
			} else {
				fileDataLength += uploadFile.getData().length;
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// �����ı����Ͳ�����ʵ������
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// ���㴫�����������ʵ�������ܳ���
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// �������HTTP����ͷ�ķ���
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		// �������ı����͵�ʵ�����ݷ��ͳ���
		outStream.write(textEntity.toString().getBytes());
		// �������ļ����͵�ʵ�����ݷ��ͳ���
		for (FormFile uploadFile : files) {
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile.getInStream() != null) {
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
					outStream.write(buffer, 0, len);
				}
				uploadFile.getInStream().close();
			} else {
				outStream.write(uploadFile.getData(), 0,
						uploadFile.getData().length);
			}
			outStream.write("\r\n".getBytes());
		}
		// ���淢�����ݽ�����־����ʾ�����Ѿ�����
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			return false;
		}
		outStream.flush();
		outStream.close();
		reader.close();
		socket.close();
		return true;
	}

	/**
	 * �ύ���ݵ�������
	 * 
	 * @param path
	 *            �ϴ�·��(ע������ʹ��localhost��127.0.0.1������·�����ԣ���Ϊ����ָ���ֻ�ģ�����������ʹ��http://
	 *            www.itcast.cn��http://192.168.1.10:8080������·������)
	 * @param params
	 *            ������� keyΪ������,valueΪ����ֵ
	 * @param file
	 *            �ϴ��ļ�
	 */
	/*public boolean post(String path, Map<String, String> params, FormFile file)
			throws Exception {
		return post(path, params, new FormFile[] { file });
	}*/


	/*
	 * public class MainActivity extends Activity { private File file; private
	 * Handler handler; private static final String TAG="MainActivity";
	 * 
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.main);
	 * Log.i(TAG, "onCreate");
	 * 
	 * file = new File(Environment.getExternalStorageDirectory(), "123.rmvb");
	 * Log.i(TAG, "��Ƭ�ļ��Ƿ���ڣ�"+file);
	 *  handler=new Handler();
	 * handler.post(runnable); }
	 * 
	 * Runnable runnable=new Runnable() {
	 * 
	 * public void run() { Log.i(TAG, "runnable run"); uploadFile(file);
	 * handler.postDelayed(runnable, 5000); }
	 * 
	 * };
	 */

	/**
	 * �ϴ�ͼƬ��������
	 * 
	 * @param imageFile
	 *            ����·��
	 */
	public void uploadFile(File imageFile) {
		Log.i(TAG, "upload start");
		try {
			String requestUrl = "http://192.168.1.101:8083/upload/upload/execute.do";
			// ������ͨ��Ϣ
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", "����");
			params.put("pwd", "zhangsan");
			params.put("age", "21");
			params.put("fileName", imageFile.getName());
			// �ϴ��ļ�
			FormFile formfile = new FormFile(imageFile.getName(), imageFile,
					"image", "application/octet-stream");
			post(requestUrl, params, new FormFile[] { formfile });
			Log.i(TAG, "upload success");
		} catch (Exception e) {
			Log.i(TAG, "upload error");
			e.printStackTrace();
		}
		Log.i(TAG, "upload end");
	}
}