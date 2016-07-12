package com.example.delivery_second;





import com.android.identification;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Forgetpwd_c extends Activity {

	ImageButton backBT;
	EditText et_pwd;
	EditText et_cfmpwd;
	Button registerBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpwd_c);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		et_cfmpwd=(EditText) findViewById(R.id.et_cfmpwd);
		registerBT = (Button) findViewById(R.id.bt_register);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Forgetpwd_c.this, Login.class);
						startActivity(intent);					
			}				
			
		});
		registerBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String pwd = et_pwd.getText().toString();
				String cfmpwd = et_cfmpwd.getText().toString();
				if (pwd == null||cfmpwd==null) {
					// 弹出对话框（内容：“请输入Email或手机号”）
			Toast.makeText(Forgetpwd_c.this, "请输入密码",
					Toast.LENGTH_SHORT).show();
				}
				else if(pwd.equals(cfmpwd)) {
					Toast.makeText(Forgetpwd_c.this, "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
				}
				else if(!identification.isPwdNO(pwd)) {
					Toast.makeText(Forgetpwd_c.this, "密码应为6-16位字符，只包含字母数字",
							Toast.LENGTH_SHORT).show();
				}
				else {
					if (true) {// 登录成功,跳转至
						Intent intent = new Intent();
						intent.setClass(Forgetpwd_c.this,Login.class);
						startActivity(intent);
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

}