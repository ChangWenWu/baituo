package com.example.delivery_First;



import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.delivery_First.R;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/first/fs_Login";
	AutoCompleteTextView cardNumAuto;
	EditText passwordET;
	Button logBT;

	CheckBox savePasswordCB;
	SharedPreferences sp;
	String cardNumStr;
	String passwordStr;
	boolean await = true;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		cardNumAuto = (AutoCompleteTextView) findViewById(R.id.cardNumAuto);
		passwordET = (EditText) findViewById(R.id.passwordET);
		logBT = (Button) findViewById(R.id.logBT);

		sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
		savePasswordCB.setChecked(true);// Ĭ��Ϊ��ס����
		cardNumAuto.setThreshold(1);// ����1����ĸ�Ϳ�ʼ�Զ���ʾ
		passwordET.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		// ��������ΪInputType.TYPE_TEXT_VARIATION_PASSWORD��Ҳ����0x81
		// ��ʾ����ΪInputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD��Ҳ����0x91

		cardNumAuto.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String[] allUserName = new String[sp.getAll().size()];// sp.getAll().size()���ص����ж��ٸ���ֵ��
				allUserName = sp.getAll().keySet().toArray(new String[0]);
				// sp.getAll()����һ��hash map
				// keySet()�õ�����a set of the keys.
				// hash map����key-value��ɵ�

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						Login.this,
						android.R.layout.simple_dropdown_item_1line,
						allUserName);

				cardNumAuto.setAdapter(adapter);// ��������������

			}


			@Override
			public void afterTextChanged(Editable s) {
				passwordET.setText(sp.getString(cardNumAuto.getText()
						.toString(), ""));// �Զ���������

			}


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
		});

		// ��½
		logBT.setOnClickListener(new OnClickListener() {

			private String retstr ="";
			private HashMap<String, String> data;

			@Override
			public void onClick(View v) {
				cardNumStr = cardNumAuto.getText().toString();
				passwordStr = passwordET.getText().toString();
				if((cardNumStr == null||cardNumStr.equalsIgnoreCase("")) || (passwordStr == null||passwordStr.equalsIgnoreCase(""))){
					Toast.makeText(Login.this, "The user name and password are necessary.",
							Toast.LENGTH_SHORT).show();
				}
				else{
					   data = new HashMap<String, String>();
						data.put("fs.Phone",cardNumStr);
						data.put("fs.Password",passwordStr);
						await = true;
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("geek",data.toString());
						Looper.prepare();
						 retstr = HttpUtils.sendPostMessage(data, "utf-8", PATH);
						 await = false;
						 Looper.loop();
					}
				}).start();
				 while(await);
				 Log.i("geek",retstr);
				 try {
						First_SendBean mb = App.Gson.fromJson(retstr, new TypeToken<First_SendBean>(){}.getType());
						App.Fs = mb;
						if (savePasswordCB.isChecked()) {//
							sp.edit()
									.putString(cardNumStr, passwordStr)
									.commit();
						}
						App.phone = cardNumStr;
						Toast.makeText(
								Login.this,
								"��ӭ����������",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(Login.this, Pickup.class);
						startActivity(intent);
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(
								Login.this,
								"The username or password was wrong, please enter again.",
								Toast.LENGTH_SHORT).show();
					}
				}				
			}
		});

	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
}