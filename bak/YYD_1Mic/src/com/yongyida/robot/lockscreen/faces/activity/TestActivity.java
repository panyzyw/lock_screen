package com.yongyida.robot.lockscreen.faces.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yongyida.robot.lockscreen.R;
import com.yongyida.robot.lockscreen.common.FacesAction;
import com.yongyida.robot.lockscreen.receiver.FacesReceiver;

/**
 * 
 * @filename 文件名：TestActivity.java
 * @description 描    述：测试表情的TestActivity,模拟语音发广播，而触发表情，用于测试，烧写系统时屏蔽
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-10
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class TestActivity extends Activity implements OnClickListener {
    
    public static final String TAG = "TestActivity";

    private boolean isContinue = true;

    public static boolean isTesting = false;

    private Button btnAutoTest;

    /***表情时间间隔，单位ms***/
    private static int FACES_INTERVAL_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        findViewById(R.id.btn_face_say_on_angry).setOnClickListener(this);
        findViewById(R.id.btn_face_afraid).setOnClickListener(this);
        findViewById(R.id.btn_face_shy).setOnClickListener(this);
        findViewById(R.id.btn_face_fighting).setOnClickListener(this);
        findViewById(R.id.btn_face_cry).setOnClickListener(this);
        findViewById(R.id.btn_face_cool).setOnClickListener(this);
        findViewById(R.id.btn_face_acting_cute).setOnClickListener(this);
        findViewById(R.id.btn_face_sad).setOnClickListener(this);
        findViewById(R.id.btn_face_rage).setOnClickListener(this);
        findViewById(R.id.btn_face_kiss).setOnClickListener(this);
        findViewById(R.id.btn_face_give_ear_to).setOnClickListener(this);
        findViewById(R.id.btn_face_sleep).setOnClickListener(this);
        findViewById(R.id.btn_face_saying).setOnClickListener(this);
        findViewById(R.id.btn_face_laugh_squint).setOnClickListener(this);
        findViewById(R.id.btn_face_study).setOnClickListener(this);
        findViewById(R.id.btn_face_giddy).setOnClickListener(this);

        findViewById(R.id.btn_face_touch).setOnClickListener(this);
        findViewById(R.id.btn_face_quiet).setOnClickListener(this);
        findViewById(R.id.btn_face_lock).setOnClickListener(this);
        findViewById(R.id.btn_face_unlock).setOnClickListener(this);
        findViewById(R.id.btn_face_unknow).setOnClickListener(this);
        findViewById(R.id.btn_face_monitor).setOnClickListener(this);

        findViewById(R.id.btn_face_nictation).setOnClickListener(this);

        btnAutoTest = (Button) findViewById(R.id.btn_face_auto_test);
        btnAutoTest.setOnClickListener(this);

    }

    /**
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     **/
    @Override
    public void onClick(View v) {

        Intent mIntent = new Intent(this, FacesReceiver.class);
        String action = null;

        switch (v.getId()) {
        case R.id.btn_dialog:
            action = "video.test.dialog";
            finish();
            break;
        case R.id.btn_face_say_on_angry:
            action = FacesAction.ACTION_SAY_ON_ANGRY;
            break;
        case R.id.btn_face_afraid:
            action = FacesAction.ACTION_AFRAID;
            break;
        case R.id.btn_face_shy:
            action = FacesAction.ACTION_SHY;
            break;
        case R.id.btn_face_fighting:
            action = FacesAction.ACTION_JIAYOU;
            break;
        case R.id.btn_face_cry:
            action = FacesAction.ACTION_CRY;
            break;
        case R.id.btn_face_cool:
            action = FacesAction.ACTION_KU;
            break;
        case R.id.btn_face_acting_cute:
            action = FacesAction.ACTION_MENG;
            break;
        case R.id.btn_face_sad:
            action = FacesAction.ACTION_SAD;
            break;
        case R.id.btn_face_rage:
            action = FacesAction.ACTION_ANGRY;
            break;
        case R.id.btn_face_kiss:
            action = FacesAction.ACTION_QINQIN;
            break;
        case R.id.btn_face_give_ear_to:
            action = FacesAction.ACTION_LISTEN;
            break;
        case R.id.btn_face_sleep:
            action = FacesAction.ACTION_SLEEP;
            break;
        case R.id.btn_face_saying:
            action = FacesAction.ACTION_SPEAK;
            break;
        case R.id.btn_face_laugh_squint:
            action = FacesAction.ACTION_LAUGH;
            break;
        case R.id.btn_face_study:
            action = FacesAction.ACTION_LEARN;
            break;
        case R.id.btn_face_giddy:
            action = FacesAction.ACTION_YUN;
            break;
        case R.id.btn_face_nictation:
            action = FacesAction.ACTION_NICTATION;
            break;
        case R.id.btn_face_touch:
            action = FacesAction.ACTION_TOUCH_HEAD;
            break;
//        case R.id.btn_face_quiet:
//            action = FacesAction.ACTION_QUIET;
//            break;
//        case R.id.btn_face_lock:
//            action = FacesAction.ACTION_LOCKSCREEN;
//            break;
//        case R.id.btn_face_unlock:
//            action = FacesAction.ACTION_UNLOCKSCREEN;
//            break;
//        case R.id.btn_face_unknow:
//            action = FacesAction.ACTION_NEW_OUTGOING_CALL;
//            break;
//        case R.id.btn_face_monitor:
//            action = FacesAction.ACTION_ENTER_MONITOR;
//            break;
        case R.id.btn_face_auto_test:
            if (!isTesting) {
                autoTest();
                isTesting = true;
                btnAutoTest.setText(getResources().getString(R.string.face_stop_test));
            } else {
                btnAutoTest.setText(getResources().getString(R.string.face_auto_test));
                isTesting = false;
            }

            break;

        default:
            action = null;
            break;
        }

        if (action != null) {
            mIntent.putExtra("from", TAG);
            mIntent.setAction(action);
            sendBroadcast(mIntent);
        }
    }

    /**
     * @method 方法名：autoTest
     * @features 功    能：//TODO 添加方法功能描述 
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void autoTest() {
        new Thread(new Runnable() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                Intent mIntent = new Intent(TestActivity.this, FacesReceiver.class);
                String action = null;
                int execute = 0;
                while (isContinue) {
                    execute++;
                    switch (execute) {
                    case 1:
                        action = FacesAction.ACTION_ANGRY;
                        break;
                    case 2:
                        action = FacesAction.ACTION_AFRAID;
                        break;
                    case 3:
                        //            action = FacesAction.ACTION_ANGRY;
                        break;
                    case 4:
                        action = FacesAction.ACTION_JIAYOU;
                        break;
                    case 5:
                        action = FacesAction.ACTION_CRY;
                        break;
                    case 6:
                        action = FacesAction.ACTION_KU;
                        break;
                    case 7:
                        //            action = FacesAction.ACTION_ANGRY;
                        break;
                    case 8:
                        //            action = FacesAction.ACTION_ANGRY;
                        break;
                    case 9:
                        action = FacesAction.ACTION_ANGRY;
                        break;
                    case 10:
                        action = FacesAction.ACTION_QINQIN;
                        break;
                    case 11:
                        action = FacesAction.ACTION_LISTEN;
                        break;
                    case 12:
                        action = FacesAction.ACTION_SLEEP;
                        break;
                    case 13:
                        //            action = FacesAction.ACTION_ANGRY;
                        break;
                    case 14:
                        action = FacesAction.ACTION_LAUGH;
                        break;
                    case 15:
                        action = FacesAction.ACTION_LEARN;
                        break;
                    case 16:
                        action = FacesAction.ACTION_YUN;
                        break;
                    case 17:
                        //            action = FacesAction.ACTION_ANGRY;
                        break;

                    default:
                        break;
                    }
                    if (action != null) {
                        mIntent.putExtra("from", "TestActivity");
                        mIntent.setAction(action);
                        sendBroadcast(mIntent);
                    }

                    try {
                        Thread.currentThread().sleep(FACES_INTERVAL_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (execute >= 17) {
                        execute = 0;
                    }
                }
            }
        }).start();
    }

    /**
     * @see android.app.Activity#onDestroy()
     **/
    @Override
    protected void onDestroy() {
        isContinue = false;
        super.onDestroy();
    }

}
