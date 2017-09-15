package com.yongyida.robot.lockscreen.faces.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yongyida.robot.lockscreen.R;
import com.yongyida.robot.lockscreen.common.ConstKey;
import com.yongyida.robot.lockscreen.faces.model.FacesAnimConfig;
import com.yongyida.robot.lockscreen.faces.util.LockscreenUtils;
import com.yongyida.robot.lockscreen.faces.util.LogcatHelper;
import com.yongyida.robot.lockscreen.robot.FacesRobot;

/**
 * 
 * @filename 文件名：LockScreenActivity.java
 * @description 描    述：锁屏动画展示的Activity
 * 此Activity由FacesRobot启动，再回调FacesRobot的showFaces方法展示是表情动画
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-16
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
@SuppressWarnings("deprecation")
public class LockScreenActivity extends Activity implements LockscreenUtils.OnLockStatusChangedListener {

    private ImageView ivFace;

    private LockscreenUtils mLockscreenUtils;

    private Bitmap oneExpression;

    private Bitmap defaultExpression;

    public static LockScreenActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        // this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        /*| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON*/ | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onAttachedToWindow();
        setContentView(R.layout.activity_lock_screen);

        if (!LogcatHelper.LOG_RECORDING) {
            LogcatHelper.getInstance().start(this);
        }
        initView();

    }

    @Override
    protected void onResume() {
        if (!LogcatHelper.LOG_RECORDING) {
            LogcatHelper.getInstance().start(this);
        }
        disableKeyguard();
        lockScreen();
        //布局完成，显示后展示表情动画
        Intent mIntent = getIntent();//获取新的Intent
        if (mIntent != null) {
            //解析从LockscreenService发送过来的Intent
            Bundle mBundle = mIntent.getBundleExtra(ConstKey.FACES_BUNDLE);
            if (mBundle != null) {
                FacesAnimConfig faces = mBundle.getParcelable(ConstKey.FACES_CONFIG);
                if (faces != null) {
                    showLockScreen(faces);
                }
            }
        }
        super.onResume();
    }

    /**
     * 初始化
     */
    private void initView() {
        defaultExpression = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.normal10));
        ivFace = (ImageView) findViewById(R.id.iv_face);
        ivFace.requestFocus();
        mLockscreenUtils = new LockscreenUtils();
    }

    /**
     * @method 方法名：showLockScreen
     * @features 功    能：处理不同的广播action，显示锁屏/表情动画
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void showLockScreen(FacesAnimConfig faces) {
        Intent mIntent = getIntent();
        if (mIntent.hasExtra("kill") && mIntent.getExtras() != null && mIntent.getExtras().getInt("kill") == 1) {
            enableKeyguard();
        } else {
            disableKeyguard();
            lockScreen();
            // startService(new Intent(this, LockscreenService.class));
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            telephonyManager.listen(new StateListener(), PhoneStateListener.LISTEN_CALL_STATE);
        }
        //交给机器人处理，展示表情
        FacesRobot.getInstance().showFaces(this, ivFace, faces);

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
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        unlockScreen();// unlock screen if user click on the screen
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    // Lock home button
    public void lockScreen() {
    }

    // Unlock home button and wait for its callback
    public void unlockScreen() {
        mLockscreenUtils.unlock();
        FacesRobot.getInstance().stop();
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
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void disableKeyguard() {

        try {
            KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
            mKeyguardLock.disableKeyguard();
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        } catch (Exception e) {
        }

    }

    private void enableKeyguard() {
        try {
            KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
            mKeyguardLock.reenableKeyguard();
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        } catch (Exception e) {
        }
    }

    // Simply unlock device by finishing the activity
    private void unlockDevice() {
        finish();
    }

    @Override
    protected void onDestroy() {
        //停止动画
        FacesRobot.getInstance().stop();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 监听新Intent的方法
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     **/
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);//设置新Intent为当前的Intent
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public enum Status {
        START, STOP;
    }

}