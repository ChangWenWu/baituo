package com.yangyu.mycustomtab02;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Order extends Activity {
	protected static final String PATH ="http://101.200.175.158:8080/BaiTuo_M2F/user/User_PersonOrder_sended";
	ImageButton backBT;
	
	private String gsonstring="";
	private  String phonestr="1234567";
	protected List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
	private Gson gson =new Gson();
	private List<MessageGson> listmg = new ArrayList<MessageGson>();
	private boolean await=true;
	private Map<String, String> params= new HashMap<String, String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		backBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Order.this, MainTabActivity.class);
						startActivity(intent);					
			}				
			
		});
		phonestr = App.Phone;
		// get order
		getorder();
		if(gsonstring.equals("haveno")){
			Toast.makeText(this, "没有空订单",
					Toast.LENGTH_SHORT).show();
		
		}else if(gsonstring.equals("disconnection")){
				Map<String, String> listItem = new HashMap<String, String>();
				listItem.put("s","");
				listItem.put("p","");
				listItems.add(listItem);
				Toast.makeText(this, "服务器连接失败",
						Toast.LENGTH_SHORT).show();
		}else{
			listmg  = gson.fromJson(gsonstring, new TypeToken<List<MessageGson>>() {
			}.getType());
			Log.i("geek", listmg.toString());
			for (int i = 0; i < listmg.size(); i++) {
				Map<String, String> listItem = new HashMap<String, String>();
				listItem.put("s", SetstateText(listmg.get(i).getState()));
				listItem.put("p", listmg.get(i).getAddress());
				listItems.add(listItem);
			}
		}
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this
			, listItems 
			, R.layout.grablist_item
			, new String[]{ "p", "s" }
			, new int[]{R.id.place, R.id.state});
		ListView list = (ListView)findViewById(R.id.lv_category);
		//为ListView设置Adapter
		list.setAdapter(simpleAdapter);
		
	    list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Order.this,Order_detail.class);
				startActivity(intent);
			}});
	
	}
	private void getorder() {
		params.put("Gson",phonestr);
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
		private String SetstateText(int state2) {
		// TODO Auto-generated method stub
		String s = "尚未运送";
		switch (state2) {
		case 2:
			s = "即将到达";
			break;
		case 1:
			s = "已送到";
		default:
			s = "尚未运送";
			break;
		}
		return s;
	}

}