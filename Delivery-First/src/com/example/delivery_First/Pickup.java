package com.example.delivery_First;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.delivery_First.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class Pickup extends Activity {

	private static final String PATH = "http://101.200.175.158:8080/BaiTuo_M2F/first/FS_getOrder";
	private static final String TAG = "JPush";
	private static final int MSG_SET_TAGS = 1002;
	private List<String> groupArray;// ���б�
	private List<List<String>> childArray;// ���б�
	private ExpandableListView expandableListView_one;
	private Button deliveBT;
	final String FILE_NAME = "/pickup.bin";
	private String phone = App.Fs.getPhone();
	private Map<String, String> params = new HashMap<String, String>();
	private Map<String, List<MessageGson>> cas = new HashMap<String, List<MessageGson>>();
	protected String gsonstring = "";
	protected Boolean await = true;
	private List<MessageGson> list = new ArrayList<MessageGson>();
	private Button pickBT;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pickup);
		Log.i("Viewpage", "--onCreate--");
		expandableListView_one = (ExpandableListView) findViewById(R.id.expandableListView);
		pickBT = (Button) findViewById(R.id.button1);
		deliveBT = (Button) findViewById(R.id.button2);
		groupArray = new ArrayList<String>();
		childArray = new ArrayList<List<String>>();
		//===============================================
				JPushInterface.setDebugMode(true);
				JPushInterface.init(this);
				setTag();

		
		getorder();
		// Ӧ�üӸ��ж�
		try{
		Gson gson = new Gson();
		list = gson.fromJson(gsonstring, new TypeToken<List<MessageGson>>() {
		}.getType());
		ClassAstatistics();
		initdate();
		}catch(Exception e){
			
		}
		expandableListView_one.setAdapter(new ExpandableListViewaAdapter(
				Pickup.this));

		// delivery Button
		deliveBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("gsonstring", gsonstring);
				intent.setClass(Pickup.this, Delive.class);
				startActivity(intent);

			}
		});

	}

	// �����������
	private void initdate() {


		for (String key : cas.keySet()) {

			int l = cas.get(key).size();
			String content = key + "\t" + l + "��\n";
			String[] strA = new String[l+1];
			strA[0] = "���͵�ַ"+"\t\t"+"��ϵ��ʽ";
			for (int i = 0; i < l; i++) {
				content = content +cas.get(key).get(i).getArea()+ cas.get(key).get(i).getAddress() + "\t\t"
						+ cas.get(key).get(i).getPhone() + "\n";
				strA[i+1] = cas.get(key).get(i).getArea()+cas.get(key).get(i).getAddress() + "\t\t"
						+ cas.get(key).get(i).getPhone();
			}
			write(content);
			addInfo(key + "\t" + l + "��", strA);
		}

	}

	private void addInfo(String group, String[] child) {

		groupArray.add(group);

		List<String> childItem = new ArrayList<String>();

		for (int index = 0; index < child.length; index++) {
			childItem.add(child[index]);
		}
		childArray.add(childItem);
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

	private void write(String content) {
		try {
			// ����ֻ�������SD��������Ӧ�ó�����з���SD��Ȩ��
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// ��ȡSD����Ŀ¼
				File sdCardDir = Environment.getExternalStorageDirectory();
				File targetFile = new File(sdCardDir.getCanonicalPath()
						+ FILE_NAME);
				targetFile.createNewFile();
				// ��ָ���ļ����� RandomAccessFile����
				RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
				// ���ļ���¼ָ���ƶ������
				raf.seek(targetFile.length());
				// ����ļ�����
				raf.write(content.getBytes());
				raf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String read() {
		try {
			// ����ֻ�������SD��������Ӧ�ó�����з���SD��Ȩ��
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// ��ȡSD����Ӧ�Ĵ洢Ŀ¼
				File sdCardDir = Environment.getExternalStorageDirectory();
				// ��ȡָ���ļ���Ӧ��������
				FileInputStream fis = new FileInputStream(
						sdCardDir.getCanonicalPath() + FILE_NAME);
				// ��ָ����������װ��BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fis));
				StringBuilder sb = new StringBuilder("");
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// getorder
	private void getorder() {
		params.put("first.Phone", phone);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				gsonstring = HttpUtils.sendPostMessage(params, "utf-8", PATH);
				await = false;
				Looper.loop();
			}
		}).start();
		while (await);
	}

	// Classification and statistics

	private Map<String, List<MessageGson>> ClassAstatistics() {

		for (MessageGson mg : list) {
			String key = mg.getMerchant();
			if (cas.get(key) == null) {
				List<MessageGson> value = new ArrayList<MessageGson>();
				value.add(mg);
				cas.put(key, value);
			} else {
				List<MessageGson> value = cas.get(key);
				value.add(mg);
				cas.put(key, value);
			}
		}
		return cas;
	}
	//===================
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	private void setTag(){
		String tag = "unknown";
		if(App.Fs.getArea()!=""){
			tag = App.Fs.getArea();
		}
	    // ��� tag ����Ч��
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(this,"Tag������Ч������", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","�����Ķ�� ת���� Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(this,"��Ч�ַ�---Tag ֻ��������,Ӣ����ĸ������", Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//����JPush API����Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 

	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler() {
	    @SuppressWarnings("unchecked")
		@Override
	    public void handleMessage(android.os.Message msg) {
	        super.handleMessage(msg);                          
	            Log.d(TAG, "Set tags in handler.");
	            JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
	         
	        }
	   };
	   private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

	       @Override
	       public void gotResult(int code, String alias, Set<String> tags) {
	           String logs ;
	           switch (code) {
	           case 0:
	               logs = "Set tag  success";
	               Log.i(TAG, logs);
	               break;
	               
	           case 6002:
	               logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	               Log.i(TAG, logs);
	               if (ExampleUtil.isConnected(getApplicationContext())) {
	               	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
	               } else {
	               	Log.i(TAG, "No network");
	               }
	               break;
	           
	           default:
	               logs = "Failed with errorCode = " + code;
	               Log.e(TAG, logs);
	           }
	           
	          ExampleUtil.showToast(logs, getApplicationContext());
	       }
	       
	   };  
}
 class SerializableMap implements Serializable {  
   
	private static final long serialVersionUID = 1L;
	private Map<String, List<MessageGson>> map;  
    public Map<String, List<MessageGson>> getMap()  
    {  
        return map;  
    }  
    public void setMap(Map<String, List<MessageGson>> cas)  
    {  
        this.map=cas;  
    }  
}  
