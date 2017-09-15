package com.yongyida.robot.lockscreen.common;

import android.content.Intent;

/**
 * @projectName 项目名称：YYDRobotLockScreen
 * @description 描    述：呼吸灯Action常量类，用于识别接收到的Intent的action。
 * @creator 作    者：SergioPan
 * @createDate 时    间：2016/8/617:26
 * @changeAuthor 修 改 人：SergioPan
 * @changeDate 修改时间：2016/8/6 17:26
 * @changeRemark 修改备注：//TODO
 * @copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public final class BreatheLedAction {

    /**************************************呼吸灯需要处理的广播意图***********************************************/

    /***系统启动完成***/
    public final static String ACTION_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;

    /***电池电量改变***/
    public final static String ACTION_BATTERY_CHANGED = Intent.ACTION_BATTERY_CHANGED;

    /***低电量***/
    public final static String ACTION_BATTERY_LOW = Intent.ACTION_BATTERY_LOW;

    /***关闭系统***/
    public final static String ACTION_SHUTDOWN = Intent.ACTION_SHUTDOWN;
    
    /***关闭屏幕***/
    public final static String ACTION_SCREEN_OFF = Intent.ACTION_SCREEN_OFF;

    /***短信接收***/
    public final static String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    /***连接电源***/
    public final static String ACTION_POWER_CONNECTED = Intent.ACTION_POWER_CONNECTED;

    /***断开电源***/
    public final static String ACTION_POWER_DISCONNECTED = Intent.ACTION_POWER_DISCONNECTED;

    /***打电话出去***/
    public final static String ACTION_OUTGOING_CALL = Intent.ACTION_NEW_OUTGOING_CALL;

    /*** ***/
    public final static String ACTION_STOP = "com.yydrobot.STOP";

    /*** ***/
    public final static String ACTION_COMPLETE = "queue_complete";

    /***电话状态，静止***/
    public final static String ACTION_CALL_STATE_IDLE = "com.yydrobot.CALL_STATE_IDLE";

    /***来电***/
    public final static String ACTION_INSIDEGOING_CALL = "com.yydrobot.INSIDEGOING_CALL";

    /*** ***/
    public final static String ACTION_DIALOG_READ = "com.yydrobot.sms.DIALOG_READ";

    /*** ***/
    public final static String ACTION_ENTERMONITOR = "com.yydrobot.ENTERMONITOR";

    /*** ***/
    public final static String ACTION_EXITMONITOR = "com.yydrobot.EXITMONITOR";

    /***音乐***/
    public final static String ACTION_MUSIC = "com.yydrobot.MUSIC";

    /***音乐播放状态***/
    public final static String ACTION_MUSIC_STATE = "com.yongyida.robot.artmuseum.musicstate";

    /*** ***/
    public final static String ACTION_MISS_CALL = "com.android.phone.NotificationMgr.MissedCall_intent";

    /*** ***/
    public final static String ACTION_VOICE_STATE = "com.yongyida.robot.voice.VOICE_UNDERSTAND";

    /***音乐播放完成***/
    public final static String ACTION_VOICE_MUSIC_END = "com.yydrobot.VOICE_MUSIC_END";

    /*** 电池***/
    public final static String ACTION_ROBOT_BATTERY = "com.yongyida.robot.BATTERY";

    /***呼吸灯设置广播，TODO 设置的东西应保存在ContentProvider，有需要的进程去读取，希望之后有改进***/
    public final static String ACTION_BREATH_LED = "com.yydrobot.ACTION_BREATH_LED";

    /*********************************其他锁屏需要处理的意图***********************************/

    /***安静***/
    public final static String ACTION_QUIET = "com.yongyida.action.lockscreen.ACTION_QUITE";

    /***锁屏***/
    public final static String ACTION_LOCKSCREEN = "com.yongyida.action.lockscreen.ACTION_LOCKSCREEN";

    /***锁屏解锁***/
    public final static String ACTION_UNLOCKSCREEN = "com.yongyida.action.lockscreen.ACTION_UNLOCKSCREEN";

    /***饿***/
    public final static String ACTION_HUNGRY = "com.yongyida.action.lockscreen.ACTION_HUNGRY";

    /***控制机器人***/
    public final static String ACTION_ENTER_MONITOR = "com.yydrobot.ENTERMONITOR";

    /***打开视频***/
    public final static String ACTION_ENTER_VIDEO = "com.yydrobot.ENTERVIDEO";

    /*****************************************需要处理的意图action数组*****************************************/

    /***动态广播电池电量处理的意图action数组***/
    public static String[] BATTERY_ACTIONS = { Intent.ACTION_BATTERY_CHANGED, ACTION_SCREEN_OFF };
    
    /***Service处理的意图action数组***/
    public static String[] BREATH_LED_ACTIONS = { Intent.ACTION_BATTERY_CHANGED, ACTION_BATTERY_LOW };



}
