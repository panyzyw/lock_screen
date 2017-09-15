package com.yongyida.robot.lockscreen.brain.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.yongyida.robot.lockscreen.beans.ExpressionBean;
import com.yongyida.robot.lockscreen.presentation.view.ExpressionActivity;
import com.zccl.ruiqianqi.tools.JsonUtils;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.StringUtils;

/**
 * Created by ruiqianqi on 2017/5/17 0017.
 */
public class MainReceiver extends BroadcastReceiver {

    private static String TAG = MainReceiver.class.getSimpleName();
    // 系统启动广播（这个静态注册了）
    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    // 表情聊天
    public static final String INTENT_EMOTION_CHAT = "com.yydrobot.emotion.CHAT";

    // 广播给子服务时，携带的结果的KEY
    public static final String KEY_RESULT = "result";
    // 表情动作
    public static final String KEY_ACTION= "action";
    // 表情文字
    public static final String KEY_TEXT = "text";
    // 循环播放表情
    public static final String KEY_WHILE_PLAY = "while_play";
    public static final String KEY_EMOJI                        = "emoji" ;
    public static final String KEY_IS_READ_SDCARD               = "isReadSdcard" ;
    public static final String KEY_DURATION_TIME                = "durationTime" ;

    public static final String KEY_IS_LOOP                      = "isLoop" ;
    // 是否需要退出APP true 到了时间退出APP；false 恢复成初始表情
    public static final String KEY_IS_EXIT_APP                  = "isExitApp" ;
    //是否需要循环监听
    public static final String KEY_IS_NEED_RECYC_LELISTEN       = "isNeedRecycleListen" ;


    // 结束表情
    public static final String ACTION_FINISH = "finish";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(null == intent)
            return;
        String action = intent.getAction();

        LogUtils.e(TAG, action + "");
        // 表情
        if(INTENT_EMOTION_CHAT.equals(action)){

            String emoji = intent.getStringExtra(KEY_EMOJI);
            if(!StringUtils.isEmpty(emoji)){

                Intent faceIntent = new Intent(context, ExpressionActivity.class);
                faceIntent.putExtra(KEY_ACTION,         emoji);
                faceIntent.putExtra(KEY_IS_READ_SDCARD, intent.getBooleanExtra(KEY_IS_READ_SDCARD, false)) ;
                faceIntent.putExtra(KEY_WHILE_PLAY,     intent.getBooleanExtra(KEY_IS_LOOP, true)) ;
                faceIntent.putExtra(KEY_DURATION_TIME,  intent.getLongExtra(KEY_DURATION_TIME, 0)) ;
                faceIntent.putExtra(KEY_IS_EXIT_APP,    intent.getBooleanExtra(KEY_IS_EXIT_APP, false)) ;
                faceIntent.putExtra(KEY_TEXT,           intent.getStringExtra(KEY_TEXT));

                faceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(faceIntent);

                return;
            }


            String result = intent.getStringExtra(KEY_RESULT);
            if(StringUtils.isEmpty(result))
                return;

            if(ACTION_FINISH.equals(result)){

                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_FINISH));

            }else {
                ExpressionBean expressionBean = JsonUtils.parseJson(result, ExpressionBean.class);
                if (null != expressionBean) {
                    Intent faceIntent = new Intent(context, ExpressionActivity.class);
                    if (null != expressionBean.semantic && null != expressionBean.semantic.slots) {
                        faceIntent.putExtra(KEY_ACTION, expressionBean.semantic.slots.action);
                        faceIntent.putExtra(KEY_TEXT, expressionBean.semantic.slots.answer);
                    }
                    faceIntent.putExtra(KEY_WHILE_PLAY, intent.getBooleanExtra(KEY_WHILE_PLAY, true));
                    faceIntent.putExtra(KEY_IS_NEED_RECYC_LELISTEN , true);

                    faceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(faceIntent);
                }
            }

        }
        // 开机初始化表情
        else if(ACTION_BOOT.equals(action)){
            Intent faceIntent = new Intent(context, ExpressionActivity.class);
            faceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            faceIntent.putExtra(KEY_ACTION, "init");
            context.startActivity(faceIntent);
        }
    }
}
