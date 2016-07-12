package com.example.delivery_second;





import java.util.Map;


import tool.TimerCount;

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

public class Forgetpwd_b extends Activity {

	ImageButton backBT;
	EditText et_code;
	Button enterBT;
	Button getcodeBT;
	TimerCount timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpwd_b);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_code=(EditText) findViewById(R.id.et_code);
		enterBT = (Button) findViewById(R.id.bt_enter);
		getcodeBT = (Button) findViewById(R.id.bt_getcode);
		getcodeBT.setClickable(false);
		timer=new TimerCount(60000,1000,getcodeBT);  
		timer.start(); 
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass( Forgetpwd_b.this, Login.class);
						startActivity(intent);					
			}				
			
		});
		
		enterBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = et_code.getText().toString();
				if (tel == null) {
					// 弹出对话框（内容：“请输入Email或手机号”）
			Toast.makeText( Forgetpwd_b.this, "请输入验证码",
					Toast.LENGTH_SHORT).show();
				} else {
					if (true) {// 登录成功,跳转至
						Intent intent = new Intent();
						intent.setClass( Forgetpwd_b.this, Forgetpwd_c.class);
						startActivity(intent);
					} else{
						Toast.makeText( Forgetpwd_b.this, "验证码不正确",
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

}