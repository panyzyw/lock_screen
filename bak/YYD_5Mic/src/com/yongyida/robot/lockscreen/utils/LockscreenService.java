package com.yongyida.robot.lockscreen.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class LockscreenService extends Service {

	private BroadcastReceiver mReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	// Register for Lockscreen event intents
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try{
			if(!LogcatHelper.LOG_RECORDING) {
				LogcatHelper.getInstance(getApplicationContext()).start();   // 启动log记录
			}
			
			LogcatHelper.getInstance(getApplicationContext()).start();
			IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			mReceiver = new LockscreenIntentReceiver();
			registerReceiver(mReceiver, filter);
//			startForeground();
		}catch(Exception e){
		}
		return START_STICKY;
	}

	

	// Unregister receiver
	@Override
	public void onDestroy() {
		
		if(LogcatHelper.LOG_RECORDING) {
			LogcatHelper.getInstance(getApplicationContext()).stop();   // 停止log记录
		}
		
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
}
