package com.example.delivery_seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
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

	AutoCompleteTextView cardNumAuto;
	EditText passwordET;
	Button logBT;

	CheckBox savePasswordCB;
	SharedPreferences sp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		cardNumAuto = (AutoCompleteTextView) findViewById(R.id.cardNumAuto);
		passwordET = (EditText) findViewById(R.id.passwordET);
		logBT = (Button) findViewById(R.id.logBT);

		sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
		savePasswordCB.setChecked(true);
		cardNumAuto.setThreshold(1);
		passwordET.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		cardNumAuto.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String[] allUserName = new String[sp.getAll().size()];
				allUserName = sp.getAll().keySet().toArray(new String[0]);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						Login.this,
						android.R.layout.simple_dropdown_item_1line,
						allUserName);

				cardNumAuto.setAdapter(adapter);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				passwordET.setText(sp.getString(cardNumAuto.getText()
						.toString(), ""));

			}
		});

		logBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String cardNumStr = cardNumAuto.getText().toString();
				final String passwordStr = passwordET.getText().toString();
				if ((cardNumStr == null || cardNumStr.equalsIgnoreCase(""))
						|| (passwordStr == null || passwordStr
								.equalsIgnoreCase(""))) {
					Toast.makeText(Login.this,
							"The user name and password are necessary.",
							Toast.LENGTH_SHORT).show();
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Map<String, String> params = new HashMap<String, String>();
							params.put("merchant.Phone", cardNumStr);
							params.put("merchant.Password", passwordStr);
							Looper.prepare();
							String MSG = HttpUtils
									.sendPostMessage(params, "utf-8",
											"http://101.200.175.158:8080/BaiTuo_M2F/merchant/merchant_Login");// dopost;
							Log.i("agan", "result->>" + MSG);
							try {
								MerchantBean mb = App.Gson.fromJson(MSG, new TypeToken<MerchantBean>(){}.getType());
								App.Mc = mb;
								if (savePasswordCB.isChecked()) {//
									sp.edit()
											.putString(cardNumStr, passwordStr)
											.commit();
								}
								App.phone = cardNumStr;
								Toast.makeText(
										Login.this,
										"欢迎您"+mb.getName()+"请稍等",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(Login.this, Send.class);
								startActivity(intent);
							} catch (Exception e) {
								// TODO: handle exception
								Toast.makeText(
										Login.this,
										"The username or password was wrong, please enter again.",
										Toast.LENGTH_SHORT).show();
							}
							
							Looper.loop();
						}

					}).start();
				}
			}
		});

	}

}