package com.example.delivery_second;





import java.util.HashMap;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

import com.android.identification;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register_c extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/user_Register";
	ImageButton backBT;
	EditText et_uName;
	EditText et_pwd;
	EditText et_cfmpwd;
	Button registerBT;
	private Map<String,String> params = new HashMap<String, String>();
	protected boolean await=true;
	protected String gsonstring="fail";
	protected String pwd="";
	protected String uName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_c);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_uName = (EditText) findViewById(R.id.et_uName);
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		et_cfmpwd=(EditText) findViewById(R.id.et_cfmpwd);
		registerBT = (Button) findViewById(R.id.bt_register);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Register_c.this, Navigation.class);
						startActivity(intent);					
			}				
			
		});
		registerBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 pwd = et_pwd.getText().toString();
				 uName = et_uName.getText().toString();
				String cfmpwd = et_cfmpwd.getText().toString();
				if(uName.equals("")||uName==null){
				 	Toast.makeText(Register_c.this, "�������ǳ�",
							Toast.LENGTH_SHORT).show();
				}
				else if (pwd.equals("")&&pwd == null||cfmpwd.equals("")&&cfmpwd==null) {
					// �����Ի������ݣ���������Email���ֻ��š���
		    	Toast.makeText(Register_c.this, "����������",
					Toast.LENGTH_SHORT).show();
				}
				else if(!pwd.equals(cfmpwd)) {
					Toast.makeText(Register_c.this, "�����������벻һ��",
							Toast.LENGTH_SHORT).show();
				}
				else if(!identification.isPwdNO(pwd)) {
					Toast.makeText(Register_c.this, "����ӦΪ6-16λ�ַ���ֻ������ĸ����",
							Toast.LENGTH_SHORT).show();
				} else {
					RespCode();
					if (gsonstring.equals("disconnection")) {
						Toast.makeText(Register_c.this, "����������ʧ��",
								Toast.LENGTH_SHORT).show();
					} else if (gsonstring.equals("success")) {// ��¼�ɹ�,��ת��
						Toast.makeText(Register_c.this, "ע��ɹ���ǰ����½ҳ��",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(Register_c.this,Login.class);
						startActivity(intent);
					} else {
						Toast.makeText(Register_c.this, "������æ�����Ժ�",
								Toast.LENGTH_SHORT).show();
					}
					
				}					
			}				
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void RespCode() {
		params.put("user.Name", uName);
		params.put("user.phone", App.Phone);
		params.put("user.Password", pwd);
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
}