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

public class Evaluate extends Activity {
	ImageButton backBT;

	private String[] place = new String[] { "һ�Ź�Ԣ101", "һ�Ź�Ԣ102", "һ�Ź�Ԣ103",
			"һ�Ź�Ԣ104" };

	private String[] state = new String[] { "���ʹ�", "��������", "��������", "���ʹ�" };

	private String[] eva = new String[] { "������", "δ����", "������", "������" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluate);
		backBT = (ImageButton) findViewById(R.id.ib_return);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Evaluate.this, MainTabActivity.class);
				startActivity(intent);
			}

		});

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < place.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("s", state[i]);
			listItem.put("p", place[i]);
			listItem.put("e", eva[i]);
			listItems.add(listItem);
		}
		// ����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.evaluateitem, new String[] { "p", "s", "e" },
				new int[] { R.id.place, R.id.state, R.id.eva });
		ListView list = (ListView) findViewById(R.id.lv_category);
		// ΪListView����Adapter
		list.setAdapter(simpleAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Evaluate.this, Evaluate_detail.class);
				startActivity(intent);
			}
		});

	}

}