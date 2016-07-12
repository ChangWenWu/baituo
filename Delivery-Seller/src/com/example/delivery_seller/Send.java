package com.example.delivery_seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class Send extends Activity {
	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	MyPagerAdapter adapter;
	LayoutInflater mInflater;

	RelativeLayout rel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.send);
		Log.i("Viewpage", "--onCreate--");
		initImageView();
		initTextView();
		initPageView();

	}

	private void initPageView() {
		mInflater = getLayoutInflater();
		listViews = new ArrayList<View>();
		listViews.add(mInflater.inflate(R.layout.fillform, null));
		listViews.add(mInflater.inflate(R.layout.check, null));
		listViews.add(mInflater.inflate(R.layout.aboutus, null));
		adapter = new MyPagerAdapter(listViews);
		mPager = (ViewPager) findViewById(R.id.page);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initTextView() {
		t1 = (TextView) findViewById(R.id.tab1);
		t2 = (TextView) findViewById(R.id.tab2);
		t3 = (TextView) findViewById(R.id.tab3);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		rel = (RelativeLayout) findViewById(R.id.layout);

		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 3 - bmpW) / 2;
		cursor.setBackgroundResource(R.drawable.a);
		rel.setPadding(offset, 0, 0, 0);

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	}

	public class MyPagerAdapter extends PagerAdapter implements OnClickListener {
		public List<View> mListViews;
		public View v1;
		public View v2;
		public View v3;

		// fillform
		private TextView send;
		private Spinner spinner1, spinner2;
		private ArrayAdapter<CharSequence> adapter1;
		private ArrayAdapter<CharSequence> adapter2;
		private String username = App.Mc.getName();
		EditText etad, ettel;

		private final String[] add = { "北苑", "南苑", "经信", "文苑", "经信教学楼",
				"第三教学楼", "逸夫教学楼", "图书馆" };

		private final String[][] ad = { { "北苑", "北苑一", "北苑二" },
				{ "南苑", "南一", "南二", "南三", "南四" },
				{ "经信", "经一", "经二", "经三", "经四", "经五", "经六" },
				{ "文苑", "文一", "文二", "文三", "文四", "文五", "文六" }, { "经信教学楼", "无" },
				{ "第三教学楼", "无" }, { "逸夫教学楼", "无" }, { "图书馆", "无" } };

		// check

		private List<String> groupArray;// 组列表
		private List<List<String>> childArray;// 子列表
		private ExpandableListView expandableListView_one;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
			getViewClickListener(mListViews);
		}

		public void getViewClickListener(List<View> listview) {
			v1 = listview.get(0);
			v2 = listview.get(1);
			v3 = listview.get(2);

			// 发单界面
			// mButton = (Button) v1.findViewById(R.id.a);
			// mButton.setOnClickListener(this);
			etad = (EditText) v1.findViewById(R.id.roominput);
			ettel = (EditText) v1.findViewById(R.id.telinput);

			spinner1 = (Spinner) v1.findViewById(R.id.spinner1);
			spinner2 = (Spinner) v1.findViewById(R.id.spinner2);
			send = (Button) v1.findViewById(R.id.sendbtn);
		
			Log.i("agan", "result->>" + username);
			// 将可选内容与ArrayAdapter连接起来
			adapter1 = new ArrayAdapter<CharSequence>(Send.this,
					android.R.layout.simple_spinner_item, add);

			// 设置下拉列表的风格
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// 将adapter 添加到spinner中
			spinner1.setAdapter(adapter1);
			// 添加事件Spinner事件监听
			spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// 为二级下拉列表适配器赋值
					adapter2 = new ArrayAdapter<CharSequence>(Send.this,
							android.R.layout.simple_spinner_item, ad[arg2]);
					// 设置列表项显示风格为完全显示
					adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					// 使用适配器
					spinner2.setAdapter(adapter2);
					// 设置提示信息
					spinner2.setPrompt("请选择分区");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
			// 设置默认值
			spinner1.setVisibility(View.VISIBLE);

			// 为send按钮设置监听器
			send.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					final String Username = username;
					// 获取文本框里的信息
					TextView text_sel1 = (TextView) spinner1.getSelectedView();
					TextView text_sel2 = (TextView) spinner2.getSelectedView();

					final String temaddress = etad.getText().toString().trim();
					final String temtel = ettel.getText().toString().trim();
					final String Tag = text_sel2.getText().toString();
					final String Region = text_sel1.getText().toString();
					new Thread(new Runnable() {
						public void run() {
							Map<String, String> params = new HashMap<String, String>();
							Log.i("agan", "result->>" + Username);
							Log.i("agan", "result->>" + temtel);
							Log.i("agan", "result->>" + temaddress);
							Log.i("agan", "result->>" + Tag);
							params.put("message.Region", Region);
							params.put("message.Address", temaddress);
							params.put("message.phone", temtel);
							params.put("message.Area", Tag);
							params.put("merchant.Phone", App.phone);
							Looper.prepare();
							String MSG = HttpUtils
									.sendPostMessage(params, "utf-8",
											"http://101.200.175.158:8080/BaiTuo_M2F/merchant/Merchant");// dopost;
							Log.i("agan", "result->>" + MSG);
							if (MSG.trim().equals("success")) {
								Toast.makeText(Send.this, "成功",
										Toast.LENGTH_SHORT).show();
							} else if (MSG.trim().equals("未连接服务器")) {
								Toast.makeText(Send.this, "未连接服务器",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(Send.this, "失败！",
										Toast.LENGTH_SHORT).show();
							}
							Looper.loop();
						}
					}).start();
				}
			});

			// 已发单界面
			expandableListView_one = (ExpandableListView) v2
					.findViewById(R.id.expandableListView);
			groupArray = new ArrayList<String>();
			childArray = new ArrayList<List<String>>();

			initdate();
			expandableListView_one.setAdapter(new ExpandableListViewaAdapter(
					Send.this));

		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void onClick(View v) {
			AlertDialog dialog = new AlertDialog.Builder(Send.this)
					.setIcon(null)
					.setTitle("dialog")
					.setMessage("nihao")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Send.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
								}
							}).create();
			// 显示对话框也可以使用showDialog（int id）方法显示对话框
			dialog.show();
		}

		class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
			Activity activity;

			public ExpandableListViewaAdapter(Activity a) {
				activity = a;
			}

			/*-----------------Child */
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
				String string = childArray.get(groupPosition)
						.get(childPosition);
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
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

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

		// 添加外卖订单
		private void initdate() {
			addInfo("16516165150   12:05", new String[] { "姓名： 小明",
					"电话： 16516165150", "地址： 经信2公寓403" });
			addInfo("18626491598   16:45", new String[] { "姓名： 小红",
					"电话： 18626491598", "地址： 南苑4公寓108" });
		}

		private void addInfo(String group, String[] child) {

			groupArray.add(group);

			List<String> childItem = new ArrayList<String>();

			for (int index = 0; index < child.length; index++) {
				childItem.add(child[index]);
			}
			childArray.add(childItem);
		}

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			rel.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}
}