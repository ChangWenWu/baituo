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

public class Register_a extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_RegiterVeriCode";
	ImageButton backBT;
	EditText et_tel;
	Button getcodeBT;
	private Map<String, String> params = new HashMap<String, String>();
	protected String gsonstring = "fail";
	protected boolean await = true;
	private String phonestr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_a);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_tel = (EditText) findViewById(R.id.et_tel);
		getcodeBT = (Button) findViewById(R.id.bt_getcode);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Register_a.this, Navigation.class);
				startActivity(intent);
			}

		});
		getcodeBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = et_tel.getText().toString();
				if (tel == null) {
					// 弹出对话框（内容：“请输入Email或手机号”）
					Toast.makeText(Register_a.this, "请输入手机号码",
							Toast.LENGTH_SHORT).show();
				} else if (!identification.isMobileNO(tel)) {
					Toast.makeText(Register_a.this, "请输入正确格式的手机号码",
							Toast.LENGTH_SHORT).show();
				} else {
					phonestr = tel;
					App.Phone = tel;
					Register();
					if (gsonstring.equals("disconnection")) {
						Toast.makeText(Register_a.this, "服务器连接失败",
								Toast.LENGTH_SHORT).show();
					} else if (gsonstring.equals("success")) {
						// 登录成功,跳转至
						Intent intent = new Intent();
						intent.setClass(Register_a.this, Register_b.class);
						startActivity(intent);
					} else {
						Toast.makeText(Register_a.this, "服务器忙，请稍候",
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

	private void Register() {
		params.put("Gson", phonestr);
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