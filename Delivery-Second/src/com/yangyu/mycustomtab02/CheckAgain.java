package com.yangyu.mycustomtab02;

import java.util.ArrayList;
import java.util.List;

import tool.App;

import com.android.DetailAdapter;
import com.android.DetailEntity;
import com.example.delivery_second.Login;
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
import android.widget.Toast;

public class CheckAgain extends Activity {
	Button checkBT;
	private ListView talkView;
	private List<DetailEntity> list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkagain);
		checkBT = (Button) findViewById(R.id.btn_check);
		
			checkBT.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (App.ub.getArea() == null) {
						checkBT.setClickable(false);
						Toast.makeText(CheckAgain.this, "Éí·Ý´ýÉóºË£¬ÇëÄÍÐÄ", Toast.LENGTH_SHORT).show();
					} else {
					Intent intent = new Intent();
					intent.setClass(CheckAgain.this, GrabList.class);
					startActivity(intent);
					}
				}

			});
		
	}
};
