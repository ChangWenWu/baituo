package com.yangyu.mycustomtab02;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.DetailAdapter;
import com.android.DetailEntity;
import com.example.delivery_second.Navigation;
import com.example.delivery_second.R;
import com.example.delivery_second.Register_a;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Withdraw_result extends Activity {
	ImageButton backBT;
	Button bt_withdraw;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdrawresult);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		bt_withdraw = (Button) findViewById(R.id.bt_withdraw);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Withdraw_result.this, Withdraw_detail.class);
						startActivity(intent);					
			}				
			
		});
		bt_withdraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Withdraw_result.this, MainTabActivity.class);
						startActivity(intent);					
			}				
		});
		
		
		
	

	
	
	
	}
}