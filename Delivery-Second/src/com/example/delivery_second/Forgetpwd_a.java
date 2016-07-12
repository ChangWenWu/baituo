package com.example.delivery_second;





import com.android.identification;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Forgetpwd_a extends Activity {

	ImageButton backBT;
	EditText et_tel;
	Button getcodeBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpwd_a);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		et_tel=(EditText) findViewById(R.id.et_tel);
		getcodeBT = (Button) findViewById(R.id.bt_getcode);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Forgetpwd_a.this, Login.class);
						startActivity(intent);					
			}				
			
		});
		getcodeBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = et_tel.getText().toString();
				if (tel == null) {
					// �����Ի������ݣ���������Email���ֻ��š���
			Toast.makeText(Forgetpwd_a.this, "�������ֻ�����",
					Toast.LENGTH_SHORT).show();
				}else if(!identification.isMobileNO(tel)) {
					Toast.makeText(Forgetpwd_a.this, "��������ȷ��ʽ���ֻ�����",
							Toast.LENGTH_SHORT).show();
				}
				else {
					if (true) {// ��¼�ɹ�,��ת��
						Intent intent = new Intent();
						intent.setClass(Forgetpwd_a.this, Forgetpwd_b.class);
						startActivity(intent);
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

}