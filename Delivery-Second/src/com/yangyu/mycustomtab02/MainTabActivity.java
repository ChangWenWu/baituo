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
	// ����FragmentTabHost����
	private FragmentTabHost mTabHost;
	private FragmentManager fragmentManager;
	// ����һ������
	private LayoutInflater layoutInflater;
	// �������������Fragment����
	private Class<?> fragmentArray[] = { FragmentPage1.class,
			FragmentPage2.class, FragmentPage3.class };
	// ������������Ű�ťͼƬ,tab_home_btnΪ����Ķ���Ч��
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn, };
	// Tabѡ�������
	private String mTextviewArray[] = { "����������̬", "��Ҫ����", "��������" };

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
		Fragment fragment = fragmentManager.findFragmentByTag("����������̬");
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

	// ��������
	public void oncheck(View v) {
		// Fragment fragment = fragmentManager.findFragmentByTag("��Ҫ����");
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Check.class);
		startActivity(intent);
	}

	// ��������
	public void onevaluate(View v) {
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, Evaluate.class);
		startActivity(intent);
	}

	// �����͵Ķ���
	public void onorder(View v) {
		if (App.ub.getArea() == null) {
			Toast.makeText(this, "�ܱ�Ǹ���㻹δ����", Toast.LENGTH_SHORT).show();
		} else {
			Fragment fragment = fragmentManager.findFragmentByTag("��������");
			TextView s = (TextView) fragment.getView()
					.findViewById(R.id.et_tel);
			Intent intent = new Intent();
			intent.setClass(MainTabActivity.this, Order.class);
			startActivity(intent);
		}
	}

	// ����
	public void onwithdraw(View v) {
		if(App.ub.getArea()==null){
			Toast.makeText(
					this,
					"�ܱ�Ǹ���㻹δ����",
					Toast.LENGTH_SHORT).show();
		}else{
		Fragment fragment=fragmentManager.findFragmentByTag("��������");
		TextView s=(TextView) fragment.getView().findViewById(R.id.et_income);
		Intent intent = new Intent();
		intent.putExtra("income", s.getText().toString());
		intent.setClass(MainTabActivity.this, Withdraw.class);
		startActivity(intent);
		}
	}

	/**
	 * ��ʼ�����
	 */
	private void initView() {
		// ʵ�������ֶ���
		layoutInflater = LayoutInflater.from(this);

		// ʵ����TabHost���󣬵õ�TabHost
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// �õ�fragment�ĸ���
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ���
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// ����Tab��ť�ı���
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	/**
	 * ��Tab��ť����ͼ�������
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
