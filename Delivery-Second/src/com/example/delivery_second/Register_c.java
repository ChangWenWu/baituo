package com.example.delivery_second;





import java.util.HashMap;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

import com.android.identification;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register_c extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_Register";
	ImageButton backBT;
	EditText et_uName;
	EditText et_pwd;
	EditText et_cfmpwd;
	Button registerBT;
	private Map<String,String> params = new HashMap<String, String>();
	protected boolean await=true;
	protected String gsonstring="fail";
	protected String pwd="";
	protected String uName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_c);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_uName = (EditText) findViewById(R.id.et_uName);
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		et_cfmpwd=(EditText) findViewById(R.id.et_cfmpwd);
		registerBT = (Button) findViewById(R.id.bt_register);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Register_c.this, Navigation.class);
						startActivity(intent);					
			}				
			
		});
		registerBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 pwd = et_pwd.getText().toString();
				 uName = et_uName.getText().toString();
				String cfmpwd = et_cfmpwd.getText().toString();
				if(uName.equals("")||uName==null){
				 	Toast.makeText(Register_c.this, "请输入昵称",
							Toast.LENGTH_SHORT).show();
				}
				else if (pwd.equals("")&&pwd == null||cfmpwd.equals("")&&cfmpwd==null) {
					// 弹出对话框（内容：“请输入Email或手机号”）
		    	Toast.makeText(Register_c.this, "请输入密码",
					Toast.LENGTH_SHORT).show();
				}
				else if(!pwd.equals(cfmpwd)) {
					Toast.makeText(Register_c.this, "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
				}
				else if(!identification.isPwdNO(pwd)) {
					Toast.makeText(Register_c.this, "密码应为6-16位字符，只包含字母数字",
							Toast.LENGTH_SHORT).show();
				} else {
					RespCode();
					if (gsonstring.equals("disconnection")) {
						Toast.makeText(Register_c.this, "服务器连接失败",
								Toast.LENGTH_SHORT).show();
					} else if (gsonstring.equals("success")) {// 登录成功,跳转至
						Toast.makeText(Register_c.this, "注册成功，前往登陆页面",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(Register_c.this,Login.class);
						startActivity(intent);
					} else {
						Toast.makeText(Register_c.this, "服务器忙，请稍候",
								Toast.LENGTH_SHORT).show();
					}
					
				}					
			}				
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void RespCode() {
		params.put("user.Name", uName);
		params.put("user.phone", App.Phone);
		params.put("user.Password", pwd);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("geek", params.toString());
				Looper.prepare();
				gsonstring = HttpUtils.sendPostMessage(params, "utf-8", PATH);
				await = false;
				Looper.loop();

			}
		};
		Thread t = new Thread(r);
		t.start();
		while (await) {
			try {
				Thread.sleep(200L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}