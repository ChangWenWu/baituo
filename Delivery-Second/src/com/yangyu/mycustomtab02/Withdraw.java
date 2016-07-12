package com.yangyu.mycustomtab02;





import java.util.ArrayList;
import java.util.List;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Withdraw extends Activity {
	ImageButton backBT;
	Button bt_withdraw;
	EditText et_price;
	EditText et_tbao;
	EditText et_name;
	TextView et_income;
    TextView ev_income;
	private  String income;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdraw);
		ev_income = (TextView) findViewById(R.id.ev_income);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		bt_withdraw = (Button) findViewById(R.id.bt_withdraw);
		et_price= (EditText) findViewById(R.id.et_price);
		et_tbao= (EditText) findViewById(R.id.et_tbao);
		et_name= (EditText) findViewById(R.id.et_name);
		
		Intent intent = getIntent();
		income = intent.getStringExtra("income");
		ev_income.setText("可提现金额："+income);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Withdraw.this, MainTabActivity.class);
						startActivity(intent);					
			}				
		});
		bt_withdraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("income", income);
						intent.putExtra("price",et_price.getText().toString());
						intent.putExtra("tbao",et_tbao.getText().toString());
						intent.putExtra("name",et_name.getText().toString());
						intent.setClass(Withdraw.this, Withdraw_detail.class);
						startActivity(intent);					
			}				
		});
	}

	
	
	
	
}