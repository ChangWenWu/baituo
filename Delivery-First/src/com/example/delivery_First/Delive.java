package com.example.delivery_First;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.delivery_First.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Delive extends Activity {

	protected static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/first/FS_FeedBackList";
	private FileHelper helper;
	private List<String> groupArray;// 组列表
	private List<List<String>> childArray;// 子列表
	private ExpandableListView expandableListView_one;
	private Button checkBT;
	private Button pickBT;
	private Button deliveBT;
	final String FILE_NAME = "/pickup.bin";
	private Map<String, List<MessageGson>> map = new HashMap<String, List<MessageGson>>();
	private List<MessageGson> list = new ArrayList<MessageGson>();
	protected Map<String, String> params = new HashMap<String, String>();
	private List<Long> llist = new ArrayList<Long>();
	Gson gson = new Gson();
	protected String gsonstring;
	protected boolean await = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delive);
		Log.i("Viewpage", "--onCreate--");
		expandableListView_one = (ExpandableListView) findViewById(R.id.expandableListView);
		pickBT = (Button) findViewById(R.id.button1);
		deliveBT = (Button) findViewById(R.id.button2);
		checkBT = (Button) findViewById(R.id.btn_check);
		groupArray = new ArrayList<String>();
		childArray = new ArrayList<List<String>>();

		deliveBT.setClickable(false);
		helper = new FileHelper(getApplicationContext());

		try {

			helper.createSDFile("sdvvcaca.txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("daczdccdc");

		}
		initdate();
		expandableListView_one.setAdapter(new ExpandableListViewaAdapter(
				Delive.this));

		pickBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EmptyData();
				Intent intent = new Intent();
				intent.setClass(Delive.this, Pickup.class);
				startActivity(intent);
			}
		});

		checkBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkBT.setClickable(false);
				new AlertDialog.Builder(Delive.this)
						.setTitle("系统提示")
						// 设置对话框标题

						.setMessage("请确认该区域所有订单已配送完毕！")
						// 设置显示的内容

						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {// 添加确定按钮
									@Override
									public void onClick(DialogInterface dialog,
											int which) {// 确定按钮的响应事件
										// TODO Auto-generated method stub
										String key = (String) checkBT.getText();
										if (map.get(key) != null) {
											llist.clear();
											for (MessageGson mg : map.get(key)) {
												llist.add(mg.getID());
											}
											feedbeak();
											Log.i("geek", gsonstring);
											if (!gsonstring.trim().equals(
													"success")) {
												Toast.makeText(Delive.this,
														"反馈失败，请及时联系我们",
														Toast.LENGTH_SHORT)
														.show();// 设置显示的内容
											} else {
												Toast.makeText(Delive.this,
														"提交成功",
														Toast.LENGTH_SHORT)
														.show();// 设置显示的内容
											}

										}
									}
								})
						.setNegativeButton("返回",
								new DialogInterface.OnClickListener() {// 添加返回按钮
									@Override
									public void onClick(DialogInterface dialog,
											int which) {// 响应事件
										// TODO Auto-generated method stub
									}
								}).show();// 在按键响应事件中显示此对话框
			}
		});

		expandableListView_one
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {

						checkBT.setText(groupArray.get(groupPosition));
						checkBT.setClickable(true);

					}
				});
	}

	// 添加外卖订单
	private void initdate() {
		Intent intent = getIntent();
		gsonstring = intent.getStringExtra("gsonstring");

		/*
		 * addInfo("北苑一", new String[] { "北苑一公寓   15976236482  ",
		 * "北苑一公寓   17626498327  ", "北苑一公寓   16423579546  " });
		 */
		try {
			list = gson.fromJson(gsonstring,
					new TypeToken<List<MessageGson>>() {
					}.getType());
		} catch (Exception e) {
		}
		ClassAstatistics(); // 数据归类
		for (String key : map.keySet()) {

			int l = map.get(key).size();

			String[] strA = new String[l];
			for (int i = 0; i < l; i++) {

				strA[i] = map.get(key).get(i).getAddress() + "\t\t"
						+ map.get(key).get(i).getPhone();
			}
			addInfo(key, strA);
		}

	}

	private void addInfo(String group, String[] child) {

		groupArray.add(group);

		List<String> childItem = new ArrayList<String>();

		for (int index = 0; index < child.length; index++) {
			childItem.add(child[index]);
		}
		childArray.add(childItem);

		String tem = "";
		for (int i = 0; i < child.length; i++) {
			tem = tem + child[i] + "\n";
		}
		// SD卡数据备份
		try {
			helper.createSDFile(group + ".txt");

		} catch (IOException e) {
			e.printStackTrace();
		}

		helper.writeSDFile(tem, group + ".txt");
	}

	class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
		Activity activity;

		public ExpandableListViewaAdapter(Activity a) {
			activity = a;
		}

		/*-----------------Child----- */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childArray.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			String string = childArray.get(groupPosition).get(childPosition);

			return getGenericView(string);
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childArray.get(groupPosition).size();
		}

		/* ----------------------------Group */
		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return getGroup(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupArray.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			String string = groupArray.get(groupPosition);
			return getGenericView(string);
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		@SuppressLint("RtlHardcoded")
		private TextView getGenericView(String string) {
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			TextView textView = new TextView(activity);
			textView.setLayoutParams(layoutParams);

			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

			textView.setPadding(40, 0, 0, 0);
			textView.setText(string);
			return textView;
		}
	}

	private void EmptyData() {
		try {
			String a = "";
			// 如果手机插入了SD卡，而且应用程序具有访问SD的权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// 获取SD卡的目录
				File sdCardDir = Environment.getExternalStorageDirectory();
				File targetFile = new File(sdCardDir.getCanonicalPath()
						+ FILE_NAME);
				// 以指定文件创建 RandomAccessFile对象
				RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
				// 输出文件内容
				raf.write(a.getBytes());
				raf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ClassAstatistics() {
		for (MessageGson mg : list) {
			String key = mg.getArea();
			if (map.get(key) == null) {
				List<MessageGson> value = new ArrayList<MessageGson>();
				value.add(mg);
				map.put(key, value);
			} else {
				List<MessageGson> value = map.get(key);
				value.add(mg);
				map.put(key, value);
			}
		}
	}

	private void feedbeak() {
		String gstr = gson.toJson(llist);
		params.put("gson", gstr);
		Log.i("geek", params.toString());
		Runnable r = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
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