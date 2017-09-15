package com.yongyida.robot.lockscreen.presentation.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yongyida.robot.lockscreen.R;
import com.yongyida.robot.lockscreen.presentation.presenter.expression.ExpressionPresenter;
import com.yongyida.robot.lockscreen.utils.AppUtils;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.MYUIUtils;
import com.zccl.ruiqianqi.tools.StringUtils;
import com.zccl.ruiqianqi.tools.SystemUtils;

import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.ACTION_FINISH;

public class ExpressionActivity extends Activity {

    private static String TAG = ExpressionActivity.class.getSimpleName();

    // 表情显示
    private ImageView iv_face;
    // 表情控制中心
    private ExpressionPresenter expressionPresenter;
    // 手势监听
    protected GestureDetectorCompat mDetector;
    // 调用对话框保证表情在最上层
    private OverlayDialog mOverlayDialog;
    // 本地广播
    private LocalBroadcastManager mLocalBroadcastManager;
    // 本地广播
    private BroadcastReceiver mReceiver;
    // 单击退出监听
    private boolean isSingleTapQuit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MYUIUtils.removeStatusTitle(this);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        initData();
        initView();
        expressionPresenter.startExpression(iv_face, getIntent());
    }

    /**
     * 初始化数据
     */
    private void initData(){
        expressionPresenter = new ExpressionPresenter();
        expressionPresenter.setExpressionCallback(new ExpressionPresenter.IExpressionCallback() {
            @Override
            public void OnPlayOver() {
                finish();
            }
        });
        // 手势监控
        mDetector = new GestureDetectorCompat(this, gestureListener);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FINISH);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ACTION_FINISH.equals(intent.getAction())) {
                    finish();
                }
            }
        };
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);

        String isSingleTapQuitKey = getString(R.string.is_single_tap_quit);
        if(!StringUtils.isEmpty(isSingleTapQuitKey)) {
            // 默认单击退出
            isSingleTapQuit = SystemProperties.getBoolean(isSingleTapQuitKey, true);
        }
    }

    /**
     * 初始化UI
     */
    private void initView(){
        iv_face = (ImageView) findViewById(R.id.iv_face);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        expressionPresenter.startExpression(iv_face, intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    /**********************************************************************************************/
    /**
     * 显示表情
     */
    private void show() {
        // 关闭系统锁屏服务
        SystemUtils.disableKeyguard(this);

        if (null == mOverlayDialog) {
            mOverlayDialog = new OverlayDialog(this);
            mOverlayDialog.show();
        }
    }

    /**
     * 隐藏表情
     */
    private void hide() {
        // 打开系统锁屏服务
        SystemUtils.enableKeyguard(this);

        if (null != mOverlayDialog) {
            mOverlayDialog.dismiss();
            mOverlayDialog = null;
        }

        expressionPresenter.stop();

        finish();
    }

    /**********************************************************************************************/
    /**
     * Create overlay dialog for lockedScreen to disable hardware buttons
     * TYPE_SYSTEM_ALERT跟TYPE_SYSTEM_OVERLAY的区别
     * 以上面的代码为例，system_alert窗口可以获得焦点，响应操作
     * system_overlay窗口显示的时候焦点在后面的Activity上，仍旧可以操作后面的Activity
     */
    private static class OverlayDialog extends AlertDialog {

        public OverlayDialog(Activity activity) {
            super(activity, R.style.OverlayDialog);
            try {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;// 解决表情遮挡的问题
                params.dimAmount = 0.0F;
                params.width = 0;
                params.height = 0;
                params.gravity = Gravity.BOTTOM;
                getWindow().setAttributes(params);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN,
                        0xffffff);
                setOwnerActivity(activity);
                setCancelable(false);
            } catch (Exception e) {

            }
        }

        // consume touch events
        public final boolean dispatchTouchEvent(MotionEvent motionevent) {
            return true;
        }

    }

    /**
     * 手势监听
     */
    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        /**
         * 确定了，就是单击
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            LogUtils.e(TAG, "onSingleTapConfirmed");
            if(isSingleTapQuit){
                // 隐藏表情
                hide();
                // 停止监听
                AppUtils.stopListen(ExpressionActivity.this, TAG);
            }
            return super.onSingleTapConfirmed(e);
        }

        /**
         * 双击
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            LogUtils.e(TAG, "onDoubleTap");
            if(!isSingleTapQuit){
                // 隐藏表情
                hide();
                // 停止监听
                AppUtils.stopListen(ExpressionActivity.this, TAG);
            }
            return true;
        }

        /**
         * 长按
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            LogUtils.e(TAG, "onLongPress");
        }

    };

}
