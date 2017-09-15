package com.yongyida.robot.lockscreen.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yongyida.robot.lockscreen.ApplicationHelper;
import com.yongyida.robot.lockscreen.common.FacesAction;
import com.yongyida.robot.lockscreen.common.FacesSwitchAction;
import com.yongyida.robot.lockscreen.common.RobotFunctionType;
import com.yongyida.robot.lockscreen.faces.activity.TestActivity;
import com.yongyida.robot.lockscreen.faces.util.LogcatHelper;

/**
 * @projectName 项目名称：FacesReceiver
 * @description 描    述：接收表情广播的广播接收者
 * @creator 作    者：SergioPan
 * @createDate 时    间：2016/8/615:30
 * @changeAuthor 修 改 人：SergioPan
 * @changeDate 修改时间：2016/8/6 15:30
 * @changeRemark 修改备注：//TODO
 * @copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class FacesReceiver extends BaseReceiver {

    public final static String TAG = "FacesReceiver";

    private RobotFunctionType mFunctionType = RobotFunctionType.getEnumFromString(RobotFunctionType.FACES.toString());

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
		
        //判断是否是表情机器人要处理的广播
        String mAction = intent.getAction();
        Log.d(TAG, "广播 ： " + mAction);
        boolean isFaces = false;
        for (String action : FacesAction.FACES_ACTIONS) {
            if (mAction.equals(action)) {
                isFaces = true;
                break;
            }
        }
        for (String action : FacesSwitchAction.CLOSE_SWITCH) {
            if (mAction.equals(action)) {

                Log.d(TAG, "表情关");
                ApplicationHelper.facesSwitch = 1;
            }
        }
        for (String action : FacesSwitchAction.OPEN_SWITCH) {
            if (mAction.equals(action)) {

                Log.d(TAG, "表情开");
                ApplicationHelper.facesSwitch = 0;
            }
        }
        if (!isFaces || ApplicationHelper.facesSwitch == 1) {
            return;
        }

        String from = intent.getStringExtra("from");
        if (!TestActivity.TAG.equals(from)) {//自动测试时，收到主服务的广播时，停止自动测试
            TestActivity.isTesting = false;
        }

        if (!LogcatHelper.LOG_RECORDING) {
            LogcatHelper.getInstance().start(context); // 启动log记录
        }
        //将接收到的需要处理的广播Intent交给Service处理
        doIntentByService(context, intent, mFunctionType, TAG);
    }

}
