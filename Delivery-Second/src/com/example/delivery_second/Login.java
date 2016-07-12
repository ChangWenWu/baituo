package com.example.delivery_second;

import java.util.HashMap;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

import com.android.bean.UserBean;
import com.google.gson.reflect.TypeToken;
import com.yangyu.mycustomtab02.MainTabActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {
	protected static String result = "failure";
	protected static Map<String, String> data;
	protected boolean bool = true;
	Button loginBT;
	Button telloginBT;
	ImageButton backBT;
	Button RpBT;
	EditText et_userpwd;
	AutoCompleteTextView cardNumAuto;

	CheckBox savePasswordCB;
	SharedPreferences sp;
	String cardNumStr;
	String passwordStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		loginBT = (Button) findViewById(R.id.btn_login);
		telloginBT = (Button) findViewById(R.id.btn_tellogin);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		RpBT = (Button) findViewById(R.id.bt_rp);
		cardNumAuto = (AutoCompleteTextView) findViewById(R.id.cardNumAuto);
		et_userpwd = (EditText) findViewById(R.id.et_userpwd);
		sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
		savePasswordCB.setChecked(true);// 默认为记住密码
		cardNumAuto.setThreshold(1);// 输入1个字母就开始自动提示
		et_userpwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		// 隐藏密码为InputType.TYPE_TEXT_VARIATION_PASSWORD，也就是0x81
		// 显示密码为InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD，也就是0x91

		cardNumAuto.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String[] allUserName = new String[sp.getAll().size()];// sp.getAll().size()返回的是有多少个键值对
				allUserName = sp.getAll().keySet().toArray(new String[0]);
				// sp.getAll()返回一张hash map
				// keySet()得到的是a set of the keys.
				// hash map是由key-value组成的

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						Login.this,
						android.R.layout.simple_dropdown_item_1line,
						allUserName);

				cardNumAuto.setAdapter(adapter);// 设置数据适配器

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				et_userpwd.setText(sp.getString(cardNumAuto.getText()
						.toString(), ""));// 自动输入密码

			}
		});
		loginBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (true) {
					Log.d("geek", "登录");
					// 得到输入编辑框的数据
					String name = cardNumAuto.getText().toString();
					String pwd = et_userpwd.getText().toString();
					Log.d("geek", "用户名：" + name + "密码：" + pwd);
					App.Phone = name;
					data = new HashMap<String, String>();
					data.put("Gson", name);
					data.put("user.Password", pwd);
					Log.d("geek", "data=" + data.toString());

					if (name == null || (name == null && pwd == null)) {
						// 弹出对话框（内容：“请输入Email或手机号”）
						Toast.makeText(Login.this, "请输入用户名或手机号",
								Toast.LENGTH_SHORT).show();
					} else if (pwd == null) {
						// 弹出对话框(内容："请输入密码")
						Toast.makeText(Login.this, "请输入密码", Toast.LENGTH_SHORT)
								.show();
					} else {
						bool = true;
						new Thread(new Runnable() {
							String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_Login";

							public void run() {

								result = HttpUtils.sendPostMessage(data,
										"UTF-8", PATH);
								Log.d("geek", "返回结果：" + result);
								bool = false;
							}
						}).start();
						while (bool)
							;// 等待线程完成
						try {
							App.ub = App.gson.fromJson(result,
									new TypeToken<UserBean>() {
									}.getType());
							// if(result.trim().equals("success")){
							if (App.ub != null) {// 登录成功,跳转至
								App.Phone = App.ub.getPhone();
								Log.d("geek", "App.Phone:" + App.Phone);
								Intent intent = new Intent();
								intent.setClass(Login.this,
										MainTabActivity.class);
								startActivity(intent);
							} else {
								Toast.makeText(Login.this, "登录失败",
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(Login.this, "登录失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				}

			}

		});

		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Login.this, Navigation.class);
				startActivity(intent);

			}

		});

		telloginBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Login.this, QuickLogin.class);
				startActivity(intent);
			}
		});

		RpBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Login.this, Forgetpwd_a.class);
				startActivity(intent);

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
