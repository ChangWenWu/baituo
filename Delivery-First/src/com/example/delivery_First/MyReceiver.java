package com.example.delivery_First;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这Receiver，则�? * 1) 默认用户会打�?��界面
 * 2) 接收不到自定义消�? */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private Class<?> cls;
	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " );
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
         
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知信息");   
        	//打开自定义的Activity
            	cls = Pickup.class;  //已登录        
        	Intent i = new Intent(context, cls);
        	i.putExtras(bundle);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}
		

}