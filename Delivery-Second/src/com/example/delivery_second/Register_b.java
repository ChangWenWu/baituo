package com.example.delivery_second;

import java.util.HashMap;
import java.util.Map;

import tool.HttpUtils;
import tool.TimerCount;
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

public class Register_b extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_Verification";
	ImageButton backBT;
	EditText et_code;
	Button enterBT;
	Button getcodeBT;
	TimerCount timer;
	private Map<String, String> params = new HashMap<String, String>();
	protected String gsonstring = "fail";
	protected boolean await = true;
	private String code = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_b);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_code = (EditText) findViewById(R.id.et_code);
		enterBT = (Button) findViewById(R.id.bt_enter);
		getcodeBT = (Button) findViewById(R.id.bt_getcode);
		getcodeBT.setClickable(false);
		timer = new TimerCount(60000, 1000, getcodeBT);
		timer.start();
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Register_b.this, Navigation.class);
				startActivity(intent);
			}

		});

		enterBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				code = et_code.getText().toString();
				if (code == null || code.equals("")) {
					Toast.makeText(Register_b.this, "请输入验证码",
							Toast.LENGTH_SHORT).show();
				} else {
					RespCode();
					if (gsonstring.equals("disconnection")) {
						Toast.makeText(Register_b.this, "服务器连接失败",
								Toast.LENGTH_SHORT).show();
					} else if (gsonstring.equals("success")) {// 登录成功,跳转至
						Intent intent = new Intent();
						intent.setClass(Register_b.this, Register_c.class);
						startActivity(intent);
					} else {
						Toast.makeText(Register_b.this, "服务器忙，请稍候",
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
		params.put("Gson", code);
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