package com.yangyu.mycustomtab02;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.Gson_tool;
import tool.HttpUtils;

import com.android.DetailAdapter;
import com.android.DetailEntity;
import com.android.bean.MessageGson;
import com.example.delivery_second.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Inquiry extends Activity {
	ImageButton backBT;
	private ListView talkView;
	private List<DetailEntity> list = null;
	protected String gson;
	protected boolean bool = true;
	protected String phone = "123";
	protected String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/User_FindMessageByPhone";
	protected Map<String,String> params = new HashMap<String,String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inquiry);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Inquiry.this, MainTabActivity.class);
				startActivity(intent);
			}

		});
		Intent intent = getIntent();
		phone = intent.getStringExtra("pho_num");
		if(phone.trim().equals("")){
			phone = "123";
		}
		Log.i("geek", phone);
		listInit();
	}

	public void listInit() {

		bool=true;
		 params.put("Gson", phone);
	
	try{
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				// 连接服务器
				gson = HttpUtils.sendPostMessage(params, "UTF-8", PATH);
				Log.d("geek", gson);
				bool = false;
				Looper.loop();
			}
		}).start();
	}catch(Exception e){
		bool = false;
		gson = "haveno";
	}
	while (bool);
		
		try{
			talkView = (ListView) findViewById(R.id.list);
			list = new ArrayList<DetailEntity>();
	 	List<MessageGson> lm = new ArrayList<MessageGson>();
		if (!gson.trim().equals("haveno")) {
			Gson gson1 = new Gson();
			lm = gson1.fromJson(gson, new TypeToken<List<MessageGson>>() {
			}.getType());
			for (MessageGson mg : lm) {
				String state = "外卖已经做好，配送员正在飞快前往取外卖中。";
				if (mg.getState() == 1) {
					state = "配送员已取到单子，正在派送";
				} else if (mg.getState() == 2) {
					state = "配送员已送到公寓，待取或代取";
				}
				@SuppressWarnings("deprecation")
				DetailEntity d1 = new DetailEntity(phone,
						mg.getSendTime().toLocaleString(), state, R.layout.list_say_me_item);
				list.add(d1);
				talkView.setAdapter(new DetailAdapter(Inquiry.this, list));
				talkView.setDivider(null);// 去掉分割线
			}
		}else{
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			DetailEntity d1 = new DetailEntity(phone,time," ",R.layout.list_say_me_item);
			list.add(d1);
			Toast.makeText(this, "无,检查手机号是否正确",
					Toast.LENGTH_SHORT).show();
			talkView.setAdapter(new DetailAdapter(Inquiry.this, list));
			talkView.setDivider(null);// 去掉分割线
		
		}
		}catch(Exception e){
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			DetailEntity d1 = new DetailEntity(phone,time," ",R.layout.list_say_me_item);
			list.add(d1);
			Toast.makeText(this, "无,检查手机号是否正确",
					Toast.LENGTH_SHORT).show();
			talkView.setAdapter(new DetailAdapter(Inquiry.this, list));
			talkView.setDivider(null);// 去掉分割线
		}
		
	}
}