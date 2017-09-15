package com.yongyida.robot.lockscreen.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.yongyida.robot.lockscreen.LockScreenActivity;
import com.yongyida.robot.lockscreen.contant.Contant;

public class LockscreenIntentReceiver extends BroadcastReceiver {
	boolean flag_phone = false;
	/* 摸头 */
	public static final String TOUCHHEAD = "touchHead";
	/* 响铃 */
	public static final String RINGUP = "ringup";
	/* 控制 */
	public static final String CONTRALL = "contrall";
	/* 手机文本推送 */
	public static final String APPTEXT = "apptext";
	
	public String status = "";
	
	public static boolean isFactory = true;

	// Handle actions and display Lockscreen
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("789", "action:"+intent.getAction());
		if (!LogcatHelper.LOG_RECORDING) {
			LogcatHelper.getInstance(context).start(); // 启动log记录
		}


		
		try {
			// 如果是正常的表情则正常显示 其他广播继续处理
			if (intent.getAction() != null) {
			    status = intent.getStringExtra("status");
				Log.i("789", "status:"+status);
				
				if(intent.getAction().equals("com.yongyida.robot.FACTORYSTART")){
					
					isFactory = false;
				}
				if(intent.getAction().equals("com.yongyida.robot.FACTORYCLOSE")){
					isFactory = true;
				}
				
				Contant.showTips(
						"LockscreenIntentReceiver-------Receiver_Match intent is:" + "bbbbbbb");
				for (int i = 0; i < Contant.MOTIONS.length; i++) {
					
					if (intent.getAction().equals(Contant.ACTIONS[i])&& status != null) {
						
						
						Contant.showTips(
								"LockscreenIntentReceiver-------Receiver_Match intent is:" + intent.getAction());
						Contant.showTips(
								"LockscreenIntentReceiver-------Receiver_Match intent is:" + status);
						
						Intent mIntent = new Intent(context, LockScreenActivity.class);
						mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mIntent.setAction(intent.getAction());
						mIntent.putExtra("status", status);
						context.startActivity(mIntent);
						context.sendBroadcast(new Intent("com.yongyida.robot.action.motion.open"));
						
//						start_lockscreen(context, intent.getAction());
						return;
					}
					 
					
					if (intent.getAction().equals(Contant.ACTIONS[i]) && isFactory) {
						
						Log.i("123", "intent:"+intent.getStringExtra("android.intent.extra.Touch"));
						Log.i("123", "intent.getAction():"+intent.getAction());
//						if(intent.getAction().equals(Contant.ACTION_STOP) 
//								&& intent.getStringExtra("android.intent.extra.Touch") != null
//								&& intent.getStringExtra("android.intent.extra.Touch").equals("yyd5")
//								||intent.getStringExtra("android.intent.extra.Touch").equals("yyd6")){
//							Log.i("123", "asdasdasdasd");
//							return;
//						}
						Log.i("123","enter");
						
						Contant.showTips(
								"LockscreenIntentReceiver-------Receiver_Match intent is:" + intent.getAction());
						
						start_lockscreen(context, intent.getAction());
						return;
					}
				}
			}
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
					|| intent.getAction().equals(Intent.ACTION_SCREEN_ON)
					|| intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
					|| intent.getAction().equals(Contant.ACTION_LOCKSCREEN)
					|| intent.getAction().equals("com.yydrobot.STOP")
					|| intent.getAction().equals("com.yydrobot.ENTERVIDEO")
					|| intent.getAction().equals("com.yydrobot.EXITVIDEO")
					|| intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
				Contant.showTips("LockscreenIntentReceiver-------Receiver_Match intent is:" + intent.getAction());
				try{	
				if(isVideoing(context)){
					return;
				}}catch(Exception e){
Contant.showTips("LockScreen get video state error:"+e.getMessage());
start_lockscreen(context, intent.getAction());
return;
}
				// 开机不锁屏
				if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
					// start_lockscreen(context, intent.getAction());
					return;
				}

				for (int i = 0; i < Contant.MOTIONS.length; i++) {
					if (intent.getAction().equals(Contant.ACTIONS[i])) {
						start_lockscreen(context, intent.getAction());
						
						return;
					}
				}
				if (intent.getAction().equals("") && intent.getExtras().getString("result").equals("touchHead")) {
					// 摸头stop
					Log.i("123", "hhh");
					start_lockscreen(context, intent.getAction());
					return;
				} else if (intent.getExtras().getString("result").equals("ringup")) {
					// 响铃stop
					return;
				} else if (intent.getExtras().getString("result").equals("contrall")) {
					// 控制stop
					return;
				} else if (intent.getExtras().getString("result").equals("apptext")) {
					// app推送文本stop

					return;
				}
				// 外拨电话
				if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
					return;
				}
				// 视频通话
				if (intent.getAction().equals("com.yydrobot.ENTERVIDEO")) {
					return;
				}
				// 视频通话
				if (intent.getAction().equals("com.yydrobot.EXITVIDEO")) {
					start_lockscreen(context, intent.getAction());
					return;
				}
				// STOP intent 单独处理 通话中不显示表情
				if (intent.getAction().equals("com.yydrobot.voice.endCall.STOP")) {
					return;
				}
				// 音乐播放启动的时候不显示表情
				if (intent.getAction().equals("com.yydrobot.STOP") && intent.getStringExtra("package") != null
						&& intent.getStringExtra("package").equals("com.yongyida.robot.launcher")) {
					return;
				}
			
				//摸机器人耳朵不弹出表情
				if(intent.getAction().equals("com.yydrobot.STOP") && intent.getStringExtra("from") != null
						
						&& intent.getStringExtra("from").equals("yyd3")||intent.getStringExtra("from").equals("yyd4")){
							Log.i("123", "123");
							return;	
							
						}
				
				// STOP intent 电话接听或者响铃状态不显示表情,摸耳朵时不显示表情  其他情况显示表情
				if (intent.getAction().equals("com.yydrobot.STOP") && !flag_phone) {
					Contant.showTips("LockscreenIntentReceiver__lock screen by intent com.yydrobot.STOP");
					
					start_lockscreen(context, intent.getAction());
					return;
				}
				
				
				
				
				// 以上处理的广播之外 再处理其余广播 通话状态不显示表情
				if (!flag_phone) {
					Contant.showTips("LockscreenIntentReceiver__lock screen by intent:" + intent.getAction());
					start_lockscreen(context, intent.getAction());
					return;
				} else {
					Contant.showTips("LockscreenIntentReceiver__not lock screen if calling");
					return;
				}
			}
		} catch (Exception e) {
			Contant.showTips("LockscreenIntentReceiver-------OnReceiver_Exception_" + e.getMessage());
		}
	}
	/**
	 * 查询机器人是否处于视频状态
	 * @return true, 正在视频中
	 *         false, 非 视频中
	 *         
	 */
	public boolean isVideoing(Context mContext) {
		String value = null;
		Uri uri  = Uri.parse("content://com.yongyida.robot.video.provider/config");
		Cursor cursor = mContext.getContentResolver().query(uri, 
				null, 
                "name = ?", 
                new String[] { "videoing" },
                null);
		try {
			if (cursor.moveToFirst())
				value = cursor.getString(cursor.getColumnIndex("value"));
		}
		catch (Exception e) {
			Log.d("123", "queryVideoing error: " + e);
		}
		finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return (value != null && value.equals("true"));
	}
	// Display lock screen
	private void start_lockscreen(Context context, String action) {
		
		Intent mIntent = new Intent(context, LockScreenActivity.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.setAction(action);
		context.startActivity(mIntent);
		context.sendBroadcast(new Intent("com.yongyida.robot.action.motion.open"));

	}

	/**
	 * 监听手机来电状态
	 */
	PhoneStateListener listener = new PhoneStateListener() {

		@Override
		public void onCallStateChanged(int state, final String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:// 电话挂断状态
				flag_phone = false;
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 电话接听状态
			case TelephonyManager.CALL_STATE_RINGING:// 电话铃响状态
				flag_phone = true;
				break;
			default:
				break;
			}
		}

	};

}
