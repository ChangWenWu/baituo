package com.yangyu.mycustomtab02;

import java.util.HashMap;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

import com.example.delivery_second.R;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainTabActivity extends FragmentActivity {
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	private FragmentManager fragmentManager;
	// 定义一个布局
	private LayoutInflater layoutInflater;
	// 定义数组来存放Fragment界面
	private Class<?> fragmentArray[] = { FragmentPage1.class,
			FragmentPage2.class, FragmentPage3.class };
	// 定义数组来存放按钮图片,tab_home_btn为点击的动画效果
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn, };
	// Tab选项卡的文字
	private String mTextviewArray[] = { "外卖订单动态", "我要抢单", "个人中心" };

	protected Map<String, String> params = new HashMap<String, String>();
	protected boolean bool = true;
	protected Gson gson = new Gson();
	protected String encode = "UTF-8";
	private String gsonstr = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);
		initView();
		fragmentManager = getSupportFragmentManager();
	}

	public void oninquiry(View v) {
		Fragment fragment = fragmentManager.findFragmentByTag("外卖订单动态");
		EditText s = (EditText) fragment.getView().findViewById(R.id.et_tel);
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Inquiry.class);
		intent.putExtra("pho_num", s.getText().toString());
		startActivity(intent);
	}

	public void onmyquiry(View v) {
		
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Inquiry.class);
		TelephonyManager phoneMgr = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (phoneMgr.getLine1Number() != null) {
			intent.putExtra("pho_num", phoneMgr.getLine1Number());
			Log.i("gan", phoneMgr.getLine1Number());
		} else {
			intent.putExtra("pho_num", App.Phone);
		}
		startActivity(intent);
	}

	// 加入我们
	public void oncheck(View v) {
		// Fragment fragment = fragmentManager.findFragmentByTag("我要抢单");
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Check.class);
		startActivity(intent);
	}

	// 服务评价
	public void onevaluate(View v) {
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Evaluate.class);
		startActivity(intent);
	}

	// 我配送的订单
	public void onorder(View v) {
		if (App.ub.getArea() == null) {
			Toast.makeText(this, "很抱歉，你还未加入", Toast.LENGTH_SHORT).show();
		} else {
			Fragment fragment = fragmentManager.findFragmentByTag("个人中心");
			TextView s = (TextView) fragment.getView()
					.findViewById(R.id.et_tel);
			Intent intent = new Intent();
			intent.setClass(MainTabActivity.this, Order.class);
			startActivity(intent);
		}
	}

	// 提现
	public void onwithdraw(View v) {
		if(App.ub.getArea()==null){
			Toast.makeText(
					this,
					"很抱歉，你还未加入",
					Toast.LENGTH_SHORT).show();
		}else{
		Fragment fragment=fragmentManager.findFragmentByTag("个人中心");
		TextView s=(TextView) fragment.getView().findViewById(R.id.et_income);
		Intent intent = new Intent();
		intent.putExtra("income", s.getText().toString());
		intent.setClass(MainTabActivity.this, Withdraw.class);
		startActivity(intent);
		}
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		return view;
	}

}
