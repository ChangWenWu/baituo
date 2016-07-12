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
		savePasswordCB.setChecked(true);// Ĭ��Ϊ��ס����
		cardNumAuto.setThreshold(1);// ����1����ĸ�Ϳ�ʼ�Զ���ʾ
		et_userpwd.setInputType(InputType.TYPE_CLASS_TEXT
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
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				et_userpwd.setText(sp.getString(cardNumAuto.getText()
						.toString(), ""));// �Զ���������

			}
		});
		loginBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (true) {
					Log.d("geek", "��¼");
					// �õ�����༭�������
					String name = cardNumAuto.getText().toString();
					String pwd = et_userpwd.getText().toString();
					Log.d("geek", "�û�����" + name + "���룺" + pwd);
					App.Phone = name;
					data = new HashMap<String, String>();
					data.put("Gson", name);
					data.put("user.Password", pwd);
					Log.d("geek", "data=" + data.toString());

					if (name == null || (name == null && pwd == null)) {
						// �����Ի������ݣ���������Email���ֻ��š���
						Toast.makeText(Login.this, "�������û������ֻ���",
								Toast.LENGTH_SHORT).show();
					} else if (pwd == null) {
						// �����Ի���(���ݣ�"����������")
						Toast.makeText(Login.this, "����������", Toast.LENGTH_SHORT)
								.show();
					} else {
						bool = true;
						new Thread(new Runnable() {
							String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_Login";

							public void run() {

								result = HttpUtils.sendPostMessage(data,
										"UTF-8", PATH);
								Log.d("geek", "���ؽ����" + result);
								bool = false;
							}
						}).start();
						while (bool)
							;// �ȴ��߳����
						try {
							App.ub = App.gson.fromJson(result,
									new TypeToken<UserBean>() {
									}.getType());
							// if(result.trim().equals("success")){
							if (App.ub != null) {// ��¼�ɹ�,��ת��
								App.Phone = App.ub.getPhone();
								Log.d("geek", "App.Phone:" + App.Phone);
								Intent intent = new Intent();
								intent.setClass(Login.this,
										MainTabActivity.class);
								startActivity(intent);
							} else {
								Toast.makeText(Login.this, "��¼ʧ��",
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(Login.this, "��¼ʧ��",
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
