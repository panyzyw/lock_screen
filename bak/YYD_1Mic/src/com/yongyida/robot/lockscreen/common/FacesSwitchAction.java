package com.yongyida.robot.lockscreen.common;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public final class FacesSwitchAction {

    public final static String ENTER_VIDEO = "com.yydrobot.ENTERVIDEO";
    public final static String EXIT_VIDEO = "com.yydrobot.EXITVIDEO";
    public final static String ENTER_MONITOR = "com.yydrobot.ENTERMONITOR";
    public final static String EXIT_MONITOR = "com.yydrobot.EXITMONITOR";
    public final static String FACTORY_START = "com.yongyida.robot.FACTORYSTART";
    public final static String FACTORY_CLOSE = "com.yongyida.robot.FACTORYCLOSE";

    public static String[] OPEN_SWITCH = {EXIT_VIDEO, EXIT_MONITOR, FACTORY_CLOSE};
    public static String[] CLOSE_SWITCH = {ENTER_VIDEO, ENTER_MONITOR, FACTORY_START};
}
