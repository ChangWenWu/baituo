package com.yangyu.mycustomtab02;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.App;
import tool.Gson_tool;
import tool.HttpUtils;

import com.android.bean.UserBean;
import com.example.delivery_second.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPage3 extends Fragment {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/User_FindUserByPhone";
	Button btn_inquiry;
	TextView et_tel;
	Button btn_otherinquiry;
	TextView et_name;
	TextView et_reg;
	TextView et_integral;
	TextView et_income;
	 

	protected String phone = "1xxxxxxx";
	protected String gson = "yonghu";
	protected boolean bool = true;
	private String user = "user";
	private Integer integral=0;
	private Integer income=0;
	private String date=dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_3, container, false);
		et_name = (TextView) v.findViewById(R.id.et_fname);
		et_tel = (TextView) v.findViewById(R.id.et_tel);
		et_reg = (TextView) v.findViewById(R.id.et_reg);
		et_integral = (TextView) v.findViewById(R.id.et_integral);
		et_income = (TextView) v.findViewById(R.id.et_income);
		if(App.ub!=null){
		  phone = App.ub.getPhone();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("phone", phone);
				gson = Gson_tool.CreateGsonString(map);
				Map<String, String> params = new HashMap<String, String>();
				Log.d("geek", gson);
				params.put("Gson", gson);
				Looper.prepare();
				gson = HttpUtils.sendPostMessage(params, "UTF-8", PATH);
				bool  = false;
				Looper.loop();
			}
		}).start();
		while(bool);
		List<UserBean> lu = new ArrayList<UserBean>();
		if (!gson.trim().equals("haveno")&&(!gson.trim().equals("disconnection"))) {
			Gson gson1 = new Gson();
			lu = gson1.fromJson(gson, new TypeToken<List<UserBean>>(){}.getType());
			UserBean mb = lu.get(0);
			user =mb.getName();
			integral = mb.getIntegral();
			income = mb.getIncome();
			phone = mb.getPhone();
			date = dateToStr(mb.getRegdate(), "yyyy-MM-dd HH:mm:ss");
		}
		App.Phone=phone;
		et_name.setText("用户名：" +user);
		et_tel.setText("手机：" + phone);
		et_reg.setText("注册日期：" + date);
		et_integral.setText(integral+"");
		et_income.setText(income+"");
		return v;
	}
	//转换时间格式
	public static String dateToStr(Date date,String pattern) {
	       if (date == null || date.equals(""))
	    	 return null;
	       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	       return formatter.format(date);
 } 
}