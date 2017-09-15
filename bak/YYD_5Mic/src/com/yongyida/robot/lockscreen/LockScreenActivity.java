package com.yongyida.robot.lockscreen;

import java.util.Date;

import com.yongyida.robot.lockscreen.contant.Contant;
import com.yongyida.robot.lockscreen.utils.LockscreenUtils;
import com.yongyida.robot.lockscreen.utils.LogcatHelper;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class LockScreenActivity extends Activity implements LockscreenUtils.OnLockStatusChangedListener {
	
	ImageView iv_face;
	LockscreenUtils mLockscreenUtils;
	Handler mHandler;
	Context mContext;
	AnimationRunable mRunable;// self defined
	boolean mFlag = true;
	long time = 0L;
	int tCount = 0; 
	Bitmap oneExpression;
	Bitmap defaultExpression;
	private static boolean isFirstComing = true;


	mBroadCastReceiver mBroadCastReceiver = new mBroadCastReceiver();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		this.getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						// | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		super.onAttachedToWindow();
		setContentView(R.layout.lockscreen_activity_lockscreen);
//		animationList();
		
		try {
			if (!LogcatHelper.LOG_RECORDING) {
				LogcatHelper.getInstance(this).start();
			}
			mContext = this;
			
			init();
			showLockScreen();
			
//			initThread();
		} catch (Exception e) {
			Contant.showTips("LockScreenActivity  exception from oncreate():" + e.getMessage());
		}
		
		this.registerReceiver(mBroadCastReceiver, new IntentFilter(Contant.ACTION_UNLOCKSCREEN));
	}

	private void initThread() {
		new Thread(new Runnable() {
			public void run() {
				while (mFlag) {
					if (tCount == 0) {
						tCount += 1;
					}
					if (tCount > 1) {
						break;// if exist break;
					}
					if (time == 0) {
						Contant.showTips(this.getClass().getName() + "_____show motion normal");
						mHandler.sendEmptyMessage(Contant.MOTION_NORMAL);
						try {
							Thread.sleep(1000 * 1 * 10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					if ((new Date().getTime() - time) >= 10 * 1000) {
						mHandler.sendEmptyMessage(Contant.MOTION_NORMAL);
						Contant.showTips(this.getClass().getName() + "_____show motion after dance");
						
						try {
							Thread.sleep(1000 * 1 * 10);
						} catch (InterruptedException e) {
							e.printStackTrace(); 
						}
						continue;
					}
					// if dance sleep 10*1000 then continue
					try {
						Thread.sleep(1000 * 1 * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}) {
		}.start();
	}

	/** 显示锁屏表情，处理不同的广播action */
	public void showLockScreen() {
		
		try {
			if (getIntent() != null && getIntent().hasExtra("kill") && getIntent().getExtras().getInt("kill") == 1) {
				enableKeyguard();
			} else {
				try {
					if (getIntent().getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
						Contant.showTips("LockScreenActivity_____By_____boot__completed");
					}
					disableKeyguard();
					lockScreen();
					// startService(new Intent(this, LockscreenService.class));
					TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					telephonyManager.listen(new StateListener(), PhoneStateListener.LISTEN_CALL_STATE);
				} catch (Exception e) {
				}
			}
			mHandler = new Handler(new Handler.Callback() {

				@Override
				public boolean handleMessage(Message msg) {
					System.gc();
					try {
						Log.i("123", msg.what+"");
						switch (msg.what) {
						case Contant.MOTION_AFRAID:
							playAnim(Contant.MOTION_AFRAID);
							break;
						case Contant.MOTION_ANGRY:
							playAnim(Contant.MOTION_ANGRY);
							break;
						case Contant.MOTION_CRY:
							playAnim(Contant.MOTION_CRY);
							break;
						case Contant.MOTION_HUNGRY:
							playAnim(Contant.MOTION_HUNGRY);
							break;
						case Contant.MOTION_JIAYOU:
							playAnim(Contant.MOTION_JIAYOU);
							break;
						case Contant.MOTION_KU:
							playAnim(Contant.MOTION_KU);
							break;
						case Contant.MOTION_LAUGH:
							playAnim(Contant.MOTION_LAUGH);
							break;
						case Contant.MOTION_LEARN:
							playAnim(Contant.MOTION_LEARN);
							break;
						case Contant.MOTION_MENG:
							playAnim(Contant.MOTION_MENG);
							break;
						case Contant.MOTION_NORMAL:// 9
							playAnim(Contant.MOTION_NORMAL);
							break;
						case Contant.MOTION_QINQIN:
							playAnim(Contant.MOTION_QINQIN);
							break;
						case Contant.MOTION_SLEEP:
							playAnim(Contant.MOTION_SLEEP);
							break;
						case Contant.MOTION_SPEAK:
							playAnim(Contant.MOTION_SPEAK);
							break;
						case Contant.MOTION_YUN:
							playAnim(Contant.MOTION_YUN);
							break;
						case Contant.MOTION_LISTENR:
							playAnim(Contant.MOTION_LISTENR);
							break;
						case Contant.MOTION_STOP:
							playAnim(Contant.MOTION_STOP);
							break;
						
							default:playAnim(msg.what);
							
							break;
						}
						return true;
					} catch (Exception e) {
						Contant.showTips("LockScreen error!");
					}
					return true;
				}
			});
			if (getIntent().getAction() != null) {
				Contant.showTips("LockScreenActivity__getIntent().getAction(): " + getIntent().getAction());
		
				for (int i = 0; i < Contant.MOTIONS.length; i++) {
					if (getIntent().getAction().equals(Contant.ACTIONS[i])) {
						Contant.showTips(Contant.ACTIONS[i] + "___mHandler___send____msg");
						mHandler.removeMessages(i);
						mHandler.sendEmptyMessageDelayed(i, 100);
						break;
					}
				}
			}
		} catch (Exception e) {
			Contant.showTips("LockScreenActivity show exception:" + e.getMessage());
		}
	}

	int lie = 0;
	int hang = 0;

	void playAnim(final int motion) {
		
		for (int i1 = 0; i1 < Contant.MOTIONS.length; i1++) {
			mHandler.removeMessages(i1);
		}
		Contant.showTips("handleMessage____motion:" + Contant.ACTIONS[motion]);
		hang = motion;
		lie = 0;
		
		mRunable = new AnimationRunable();
		iv_face.removeCallbacks(mRunable);
		iv_face.postDelayed(mRunable, 100);
		
	}

	// motion animation
	private class AnimationRunable implements Runnable {

		@Override
		public void run() {
			
			
			String status =getIntent().getStringExtra("status");
			
			if(getIntent().getAction().equals(Contant.ACTION_LISTENER) && status.equals("end")&&status!=null ){
				Log.d("789", "status:"+status);
				 iv_face.removeCallbacks(this);
//	             iv_face.setBackgroundResource(R.raw.wait_00000);
	             iv_face.setImageBitmap(defaultExpression);
				return;
			}
			
			if(getIntent().getAction().equals(Contant.ACTION_LISTENER) && status.equals("start")&&status!=null ){
				
				if(lie == Contant.PICS[hang].length - 1){
					lie = 0;
				}
			}
			
			
			
			
			Log.i("456", "lie:"+lie);
			 if (lie > Contant.PICS[hang].length - 1) {
	                iv_face.removeCallbacks(this);
//	                iv_face.setBackgroundResource(R.raw.wait_00000);
	                iv_face.setImageBitmap(defaultExpression);

	                // Contant.showTips("j==length");
	               return;
	            }
	            // Contant.showTips("handleMessage____motion:hang=" + hang +
	            // "____lie=" + lie + "___PICS[hang] length is:"
	            // + Contant.PICS[hang].length);
//	            iv_face.setBackgroundResource(Contant.PICS[hang][lie]);

	            

	                if (oneExpression != null && !oneExpression.isRecycled()) {
	                    oneExpression.recycle();
	                }
	                oneExpression = BitmapFactory.decodeStream(getResources().openRawResource(
	                        Contant.PICS[hang][lie]));

	                

	                iv_face.setImageBitmap(oneExpression);
	            

	            lie += 1;
	            if(getIntent().getAction().equals(Contant.ACTION_LISTENER)){
	            iv_face.postDelayed(this, 36);
	            }else{
	            iv_face.postDelayed(this, 200);
	            }
	        }}

	

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent); // 关键
		time = new Date().getTime();// get the current time
		showLockScreen();
	}

	private void init() {
		defaultExpression = BitmapFactory.decodeStream(getResources().openRawResource(
	                R.raw.normal10));
		iv_face = (ImageView) findViewById(R.id.iv_face);
		iv_face.requestFocus();
		iv_face.setBackgroundResource(R.raw.normal10);
		mLockscreenUtils = new LockscreenUtils();
	}

	// Handle events of calls and unlock screen if necessary
	private class StateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				unlockScreen();
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			}
		}
	};

	// Handle button clicks
/* 	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		Contant.showTips(this.getLocalClassName() + "____onKeyDown");
		// if ((keyCode == KeyEvent.KEYCODE_HOME)) {
			// Contant.showTips("KeyEvent.KEYCODE_HOME");
			// return true;
		// }
		// if ((keyCode == KeyEvent.KEYCODE_POWER)
				 // || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
			// return true;
		// }
		// if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
			// Contant.showTips("KeyEvent.KEYCODE_BACK");
			// return true;
		// }
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		switch (keyCode) {
		 case KeyEvent.KEYCODE_VOLUME_UP:// 音量增大
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
					AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_SHOW_UI);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_DTMF, AudioManager.ADJUST_RAISE,
					// AudioManager.FLAG_PLAY_SOUND);
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:// 音量减小
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
					AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_SHOW_UI);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_DTMF, AudioManager.ADJUST_LOWER,
					// AudioManager.FLAG_PLAY_SOUND);
			break; 
		// case KeyEvent.KEYCODE_BACK:// 返回键
			// return true;
		default:
			break;
		}
		return false;
	} */

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stubs
		Contant.showTips(this.getLocalClassName() + "__1__dispatchTouchEvent");
		unlockScreen();// unlock screen if user click on the screen
		for (int i = 0; i < Contant.MOTIONS.length; i++) {
			mHandler.removeMessages(i);
		}
		return super.dispatchTouchEvent(ev);
	}

	// handle the key press events here itself
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (Contant.DEBUG)
			Contant.showTips(this.getLocalClassName() + "____dispatchKeyEvent");
		return super.dispatchKeyEvent(event);
	}

	// Lock home button
	public void lockScreen() {
		Contant.showTips(this.getLocalClassName() + "____" + "lockHomeButton");
		mLockscreenUtils.lock(LockScreenActivity.this);
	}

	// Unlock home button and wait for its callback
	public void unlockScreen() {
		Contant.showTips(this.getLocalClassName() + "____" + "unlockHomeButton");
		mLockscreenUtils.unlock();
		this.finish();
		sendBroadcast(new Intent("com.yongyida.robot.action.motion.close"));
	}

	// Simply unlock device when home button is successfully unlocked
	@Override
	public void onLockStatusChanged(boolean isLocked) {
		if (!isLocked) {
			unlockDevice();
		}
	}

	@Override
	protected void onStop() {
		Contant.showTips(this.getLocalClassName() + "____" + "onStop");
		try{
			this.unregisterReceiver(mBroadCastReceiver);
		}catch(Exception e){
			Contant.showTips("LockScreenActivity Exception onStop():"+e.getMessage());
		}
		super.onStop();
		unlockScreen();
	}

	@Override
	protected void onPause() {
		Contant.showTips(this.getLocalClassName() + "____" + "onPause");
		super.onPause();
		for (int i = 0; i < Contant.MOTIONS.length; i++) {
//			mHandler.removeMessages(i);
		}
		iv_face.removeCallbacks(mRunable);
	}

	private void disableKeyguard() {
		
		try {
			KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
			mKeyguardLock.disableKeyguard();
			Contant.showTips(this.getLocalClassName() + "____" + "disableKeyguard");
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		} catch (Exception e) {
			Contant.showTips("LockScreenActivity exception from disableKeyguard:" + e.getMessage());
		}

	}

	private void enableKeyguard() {
		try {
			KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
			mKeyguardLock.reenableKeyguard();
			Contant.showTips(this.getLocalClassName() + "____" + "enableKeyguard");
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		} catch (Exception e) {
			Contant.showTips("LockScreenActivity exception from enableKeyguard:" + e.getMessage());
		}
	}

	// Simply unlock device by finishing the activity
	private void unlockDevice() {
		finish();
	}

	@Override
	protected void onDestroy() {
		Contant.showTips(this.getLocalClassName() + "____" + "onDestroy");
		mFlag = false;// destroy thread
		isFirstComing = true;
		
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		if (Contant.DEBUG)
			Contant.showTips(this.getLocalClassName() + "____" + "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if (Contant.DEBUG)
			Contant.showTips(this.getLocalClassName() + "____" + "onResume");

		if (!LogcatHelper.LOG_RECORDING) {
			LogcatHelper.getInstance(this).start();
		}
		disableKeyguard();
		lockScreen();
		// startService(new Intent(this, LockscreenService.class));
		iv_face.setBackgroundResource(R.raw.normal10);
		super.onResume();
	}

	@Override
	protected void onStart() {
		if (Contant.DEBUG)	
			Contant.showTips(this.getLocalClassName() + "____" + "onStart");
		super.onStart();
	}

	class mBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Contant.ACTION_LOCKSCREEN)) {
				LockScreenActivity.this.lockScreen();
			}
			if (intent.getAction().equals(Contant.ACTION_UNLOCKSCREEN)) {
				unlockDevice();
			}
			if (intent.getAction().equals(Contant.ACTION_QUITE)) {

			}
			if (intent.getAction().equals(Contant.ACTION_LAUGH)) {

			}
			if (intent.getAction().equals(Contant.ACTION_SPEAK)) {

			}
			if (intent.getAction().equals("com.yydrobot.ENTERVIDEO")) {
				mFlag = false;
				LockScreenActivity.this.finish();
			}
		}
	}
	
	private void animationList(){

		iv_face.setBackgroundResource(R.drawable.animation_listener);
		AnimationDrawable an= (AnimationDrawable) iv_face.getBackground();
		an.start();
		Log.i("123", "hhhhhhhhhhhhhhhhh");
	}
}