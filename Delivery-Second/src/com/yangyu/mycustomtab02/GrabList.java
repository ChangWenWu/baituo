package com.yangyu.mycustomtab02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.App;
import tool.HttpUtils;

import com.android.bean.MessageGson;
import com.example.delivery_second.Login;
import com.example.delivery_second.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class GrabList extends Activity implements OnClickListener,
		OnDismissListener {

	protected static String PATH = "http://101.200.175.158/BaiTuo_M2F/user/User_ShowOrder";

	ImageButton backBT;

	private LinearLayout ll_quyu, ll_jiage, ll_huxing, lv1_layout;
	private ListView lv1, lv2;
	private TextView quyu, huxing, jiage;
	private ImageView icon1, icon2, icon3;
	private int screenWidth;
	private int screenHeight;
	private MyAdapter adapter;
	private int idx;
	private SubAdapter subAdapter;
	private String cities[][];
	private String gstr = App.ub.getArea();
	protected String gsonstring = "";

	private Map<String, String> params = new HashMap<String, String>();

	private Gson gson = new Gson();

	private List<MessageGson> listmg = new ArrayList<MessageGson>();

	protected boolean await = true;

	List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grablist);
		cities = new String[][] { null,
				this.getResources().getStringArray(R.array.cuiping), null,
				this.getResources().getStringArray(R.array.nanxi), null, null,
				null, null, null, null };
		initScreenWidth();
		findViews();
		// get order
		getorder();
		try{
		if (gsonstring.equals("haveno")) {
			Toast.makeText(this, "没有空订单", Toast.LENGTH_SHORT).show();

		} else if (gsonstring.equals("disconnection")) {
			Map<String, String> listItem = new HashMap<String, String>();
			listItem.put("s", "");
			listItem.put("p", "");
			listItems.add(listItem);
			Toast.makeText(this, "服务器连接失败", Toast.LENGTH_SHORT).show();
		} else {
			listmg = gson.fromJson(gsonstring,
					new TypeToken<List<MessageGson>>() {
					}.getType());
			Log.i("geek", listmg.toString());
			for (int i = 0; i < listmg.size(); i++) {
				Map<String, String> listItem = new HashMap<String, String>();
				listItem.put("s", SetstateText(listmg.get(i).getState()));
				listItem.put("p", listmg.get(i).getAddress());
				listItems.add(listItem);
			}
		}
		}catch(Exception e){
			Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(this, Login.class);
			startActivity(intent);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.grablist_item, new String[] { "p", "s" }, new int[] {
						R.id.place, R.id.state });
		ListView list = (ListView) findViewById(R.id.lv_category);
		// 为ListView设置Adapter
		list.setAdapter(simpleAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("gan", "你单击" + listmg.get(arg2).getID());
				long id = listmg.get(arg2).getID();
				List<Long> llist = new ArrayList<Long>();
				llist.add(App.ub.getAccount());
				llist.add(id);
				PATH = "http://101.200.175.158:8080/BaiTuo_M2F/user/User_getOrder";
				gstr = gson.toJson(llist);
				Log.i("gan", gstr);
				Log.i("gan",App.ub.toString());
				try {
					getorder();
					if (gsonstring.equals("fail")) {
						Toast.makeText(GrabList.this, "抢单失败",
								Toast.LENGTH_SHORT).show();

					} else if (gsonstring.equals("success")) {
						Toast.makeText(GrabList.this, "抢单成功，前往个人中心查看",
								Toast.LENGTH_SHORT).show();
					} else {

						Toast.makeText(GrabList.this, "服务器连接失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(GrabList.this, "服务器连接失败", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		backBT = (ImageButton) findViewById(R.id.ib_return);
		backBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GrabList.this, MainTabActivity.class);
				startActivity(intent);
			}
		});
	}

	private String SetstateText(int state2) {
		// TODO Auto-generated method stub
		String s = "尚未运送";
		switch (state2) {
		case 2:
			s = "即将到达";
			break;
		case 1:
			s = "已送到";
		default:
			s = "尚未运送";
			break;
		}
		return s;
	}

	private void findViews() {
		ll_quyu = (LinearLayout) findViewById(R.id.ll_quyu);
		ll_jiage = (LinearLayout) findViewById(R.id.ll_jiage);
		ll_huxing = (LinearLayout) findViewById(R.id.ll_huxing);
		quyu = (TextView) findViewById(R.id.quyu);
		huxing = (TextView) findViewById(R.id.huxing);
		jiage = (TextView) findViewById(R.id.jiage);
		icon1 = (ImageView) findViewById(R.id.icon1);
		icon2 = (ImageView) findViewById(R.id.icon2);
		icon3 = (ImageView) findViewById(R.id.icon3);
		ll_quyu.setOnClickListener(this);
		ll_jiage.setOnClickListener(this);
		ll_huxing.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_quyu:
			idx = 1;
			icon1.setImageResource(R.drawable.icon_43343434);
			showPopupWindow(findViewById(R.id.ll_layout), 1);
			break;
		case R.id.ll_jiage:
			idx = 2;
			icon2.setImageResource(R.drawable.icon_43343434);
			showPopupWindow(findViewById(R.id.ll_layout), 2);
			break;
		case R.id.ll_huxing:
			idx = 3;
			icon3.setImageResource(R.drawable.icon_43343434);
			showPopupWindow(findViewById(R.id.ll_layout), 3);
			break;
		}
	}

	public void showPopupWindow(View anchor, int flag) {
		final PopupWindow popupWindow = new PopupWindow(GrabList.this);
		View contentView = LayoutInflater.from(GrabList.this).inflate(
				R.layout.windows_popupwindow, null);
		lv1 = (ListView) contentView.findViewById(R.id.lv1);
		lv2 = (ListView) contentView.findViewById(R.id.lv2);
		lv1_layout = (LinearLayout) contentView.findViewById(R.id.lv_layout);
		switch (flag) {
		case 1:
			adapter = new MyAdapter(GrabList.this, initArrayData(R.array.quyu));
			break;
		case 2:
			adapter = new MyAdapter(GrabList.this,
					initArrayData(R.array.zongjia));
			break;
		case 3:
			adapter = new MyAdapter(GrabList.this,
					initArrayData(R.array.huxing));
			break;
		}
		lv1.setAdapter(adapter);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (parent.getAdapter() instanceof MyAdapter) {
					adapter.setSelectItem(position);
					adapter.notifyDataSetChanged();
					lv2.setVisibility(View.INVISIBLE);
					if (lv2.getVisibility() == View.INVISIBLE) {
						lv2.setVisibility(View.VISIBLE);
						switch (idx) {
						case 1:
							lv1_layout.getLayoutParams().width = 0;
							if (cities[position] != null) {
								subAdapter = new SubAdapter(
										getApplicationContext(),
										cities[position]);
							} else {
								subAdapter = null;
							}
							break;
						case 2:
							lv1_layout.getLayoutParams().width = LayoutParams.MATCH_PARENT;

							break;
						case 3:
							lv1_layout.getLayoutParams().width = LayoutParams.MATCH_PARENT;
							break;
						}
						if (subAdapter != null) {
							lv2.setAdapter(subAdapter);
							subAdapter.notifyDataSetChanged();
							lv2.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									String name = (String) parent.getAdapter()
											.getItem(position);
									setHeadText(idx, name);
									popupWindow.dismiss();
									subAdapter = null;
								}
							});
						} else {
							// 当没有下级时直接将信息设置textview中
							String name = (String) parent.getAdapter().getItem(
									position);
							setHeadText(idx, name);
							popupWindow.dismiss();
						}

					}
				}
			}
		});
		popupWindow.setOnDismissListener(this);
		popupWindow.setWidth(screenWidth);
		popupWindow.setHeight(screenHeight);
		popupWindow.setContentView(contentView);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new PaintDrawable());
		popupWindow.showAsDropDown(anchor);

	}

	/**
	 * @Title: setHeadText
	 * @Description: 点击之后设置在上边的TextView里
	 * @author yimei
	 * @return void 返回类型
	 */
	private void setHeadText(int idx, String text) {
		switch (idx) {
		case 1:
			quyu.setText(text);
			break;
		case 2:
			jiage.setText(text);
			break;
		case 3:
			huxing.setText(text);
			break;
		}

	}

	/**
	 * @Title: initScreenWidth
	 * @Description: 查看自身的宽高
	 * @author yimei
	 * @return void 返回类型
	 */
	private void initScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;
		Log.v("屏幕宽高", "宽度" + screenWidth + "高度" + screenHeight);
	}

	private List<String> initArrayData(int id) {
		List<String> list = new ArrayList<String>();
		String[] array = this.getResources().getStringArray(id);
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	@Override
	public void onDismiss() {
		icon1.setImageResource(R.drawable.icon_435);
		icon2.setImageResource(R.drawable.icon_435);
		icon3.setImageResource(R.drawable.icon_435);
	}

	private void getorder() {
		params.put("Gson", gstr);
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