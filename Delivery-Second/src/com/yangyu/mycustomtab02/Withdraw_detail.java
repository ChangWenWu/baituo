package com.yangyu.mycustomtab02;

import java.util.HashMap;
import java.util.Map;





import tool.HttpUtils;

import com.example.delivery_second.R;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Withdraw_detail extends Activity {
	ImageButton backBT;
	Button bt_withdraw;
	private String income="";
	private String tbao="";
	private String price="";
	private String name="";
	private TextView tv_price;
	private TextView tv_name;
	private TextView tv_tbao;
	private String PATH ="http://101.200.175.158:8080/BaiTuo_M2F/user/User_FindByPhone";
	protected String s="" ;
	protected  Map<String,String> params = new HashMap<String, String>();
	protected boolean bool;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdrawdetail);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_tbao = (TextView) findViewById(R.id.tv_tbao);
		tv_name = (TextView) findViewById(R.id.tv_name);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		bt_withdraw = (Button) findViewById(R.id.bt_withdraw);

		Intent intent = getIntent();
		income = intent.getStringExtra("income");
		price = intent.getStringExtra("price");
		tbao = intent.getStringExtra("tbao");
		name = intent.getStringExtra("name");
		
		tv_price.setText("½ð¶î£º"+price);
		tv_tbao.setText("ÌÔ±¦ºÅ£º"+tbao);
		tv_name.setText("Ãû×Ö"+name);
		
		params.put("price", price);
		params.put("tbao",tbao);
		params.put("name",name);
		
		backBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.putExtra("income", income);
				intent.setClass(Withdraw_detail.this, Withdraw.class);
				startActivity(intent);
			}

		});
		bt_withdraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Looper.prepare();
						s = HttpUtils.sendPostMessage(params, "UTF-8", PATH);
						bool = false;
						Looper.loop();
					}
				}).start();
				while(bool);
				Log.i("geek",s);
				
				Intent intent = new Intent();
				intent.setClass(Withdraw_detail.this, Withdraw_result.class);
				startActivity(intent);
			}
		});

	}
}