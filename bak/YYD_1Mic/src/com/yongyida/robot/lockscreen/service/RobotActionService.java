package com.yongyida.robot.lockscreen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.yongyida.robot.lockscreen.common.ConstKey;
import com.yongyida.robot.lockscreen.common.RobotFunctionType;
import com.yongyida.robot.lockscreen.faces.util.LogcatHelper;
import com.yongyida.robot.lockscreen.robot.BaseRobot;
import com.yongyida.robot.lockscreen.robot.FacesRobot;

/**
 * @projectName 项目名称：RobotActionService
 * @description 描    述：机器人动作执行服务
 * 1.收到的广播接收者传过来的Intent，包括Intent和功能类型；
 * 2.根据功能类型实例化对应的机器人；
 * 3.通过IRobot回调execute方法执行对应功能，如展示表情动画、呼吸灯闪亮等功能，具体功能由具体的Robot实现。
 * @creator 作    者：SergioPan
 * @createDate 时    间：2016/8/615:30
 * @changeAuthor 修 改 人：SergioPan
 * @changeDate 修改时间：2016/8/6 15:30
 * @changeRemark 修改备注：//TODO
 * @copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class RobotActionService extends Service {

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
        Log.i("LockscreenService", "-----onStartCommand---");
        if (!LogcatHelper.LOG_RECORDING) {
            LogcatHelper.getInstance().start(getApplicationContext()); // 启动log记录
        }
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String from = intent.getStringExtra(ConstKey.FROM);
        Log.i("LockscreenService", "-----from = " + from);
        boolean isContain = false;
        for (String tag : ConstKey.RECEIVER_TAGS) {
            if (tag.equals(from)) {
                isContain = true;
                break;
            }
        }
        if (!isContain) {
            return super.onStartCommand(intent, flags, startId);
        }
        //解析从锁屏广播接收者发送过来的Intent
        Bundle mBundle = intent.getBundleExtra(ConstKey.RECEIVER_BUNDLE);
        if (mBundle != null) {
            //解析要执行的广播Intent
            Intent receiverIntent = mBundle.getParcelable(ConstKey.RECEIVER_INTENT);
            //解析要执行的功能类型
            String function = intent.getStringExtra(ConstKey.INTENT_FUNCTION_TYPE);
            Log.i("LockscreenService", "-----function = " + function);
            RobotFunctionType robotFunction = RobotFunctionType.getEnumFromString(function);
            Log.i("LockscreenService", "-----robotFunction = " + robotFunction);
            if (function != null) {
                robotFunction = RobotFunctionType.getEnumFromString(function);
            }

            if (receiverIntent != null && robotFunction != null) {
                //按功能类型处理广播Intent
                doIntent(getApplicationContext(), receiverIntent, robotFunction);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Unregister receiver
    @Override
    public void onDestroy() {
        if (LogcatHelper.LOG_RECORDING) {
            LogcatHelper.getInstance().stop(); // 停止log记录
        }

        super.onDestroy();

    }

    /**
     * @method 方法名：doIntent
     * @features 功    能：按功能类型处理广播Intent
     * @params 参    数：@param context
     * @params 参    数：@param intent
     * @params 参    数：@param robotFunction
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    private void doIntent(Context context, Intent intent, RobotFunctionType robotFunction) {
        if (intent == null || intent.getAction() == null || robotFunction == null) {
            return;
        }

        //让IRobot执行
        BaseRobot mRobot = initRobotInstance(robotFunction);
        if (mRobot != null) {
            mRobot.execute(context, intent);
        }

    }

    /**
     * @method 方法名：initRobotInstance
     * @features 功    能：初始化IRobot实例
     * @params 参    数：@param robotFunction
     * @params 参    数：@return
     * @return 返回值：IRobot
     * @modify 修改者: Sergio Pan
     */
    public BaseRobot initRobotInstance(RobotFunctionType robotFunction) {
        BaseRobot mRrobot = null;
        switch (robotFunction) {
        case FACES://表情
            mRrobot = FacesRobot.getInstance();
            break;
        case BREATHE_LED://呼吸灯
            break;

        default:
            break;
        }
        return mRrobot;
    }

}
