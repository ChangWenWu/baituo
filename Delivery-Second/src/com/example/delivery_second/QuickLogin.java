package com.example.delivery_second;



import java.util.HashMap;
import java.util.Map;

import com.yangyu.mycustomtab02.MainTabActivity;

import tool.TimerCount;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuickLogin extends Activity {
	Button loginBT;
	Button getcodeBT;
	ImageButton backBT;
	EditText et_code;
	EditText et_tel;
	TimerCount timer;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quicklogin);
		loginBT = (Button) findViewById(R.id.btn_login);
		getcodeBT = (Button) findViewById(R.id.btn_getcode);
		getcodeBT.setClickable(false);
		timer=new TimerCount(60000,1000,getcodeBT);  
		timer.start(); 
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_code = (EditText) findViewById(R.id.et_code);
		et_tel = (EditText) findViewById(R.id.et_tel);
		this.changeBack();
		
		loginBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (true) {
					Log.d("geek", "登录");
					// 得到输入编辑框的数据
					String name = et_tel.getText().toString();
					String pwd = et_code.getText().toString();
					Log.d("geek", "用户名：" + name + "密码：" + pwd);
					Map<String, String> data = new HashMap<String, String>();
					data.put("user.username", name);
					data.put("user.userpwd", pwd);
					Log.d("geek", "data=" + data);
					String result = "failure";
					//result = UserUtils.loginResult(path, data);
					Log.d("geek", "返回结果：" + result);
					if (name == null || (name == null && pwd == null)) {
						// 弹出对话框（内容：“请输入Email或手机号”）
						Toast.makeText(QuickLogin.this, "请输入用户名或手机号",
								Toast.LENGTH_SHORT).show();
					} else if (pwd == null) {
						// 弹出对话框(内容："请输入密码")
						Toast.makeText(QuickLogin.this, "请输入密码",
								Toast.LENGTH_SHORT).show();
					} else {
						if (true) {// 登录成功,跳转至
							Intent intent = new Intent();
							intent.setClass(QuickLogin.this, MainTabActivity.class);
							startActivity(intent);
						} else {
							Toast.makeText(QuickLogin.this, "登录失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
				

			}
							
		
		});
		
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(QuickLogin.this,Navigation.class);
						startActivity(intent);

			}
							
		
		});
		
		
		
	}

	
	
	
	public void changeBack() {
		et_tel.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					et_tel
							.setBackgroundResource(R.drawable.bg_edit_selected);
				} else {
					et_tel
							.setBackgroundResource(R.drawable.bg_edit_unselected);
				}

			}
		});
		et_code.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					et_code
							.setBackgroundResource(R.drawable.bg_edit_selected);
				} else {
					et_code
							.setBackgroundResource(R.drawable.bg_edit_unselected);
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

}