/**
 * @filename 文件名：ConstString.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-19
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.common;

import com.yongyida.robot.lockscreen.faces.activity.TestActivity;
import com.yongyida.robot.lockscreen.receiver.FacesReceiver;

/**
 * @filename 文件名：ConstString.java
 * @description 描    述：key字符串常量类
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-19
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public final class ConstKey {
    
    
    /***receiver接收到的intent***/
    public final static String RECEIVER_INTENT = "receiver_intent";
    
    /***receiver接收到的bundlet***/
    public final static String RECEIVER_BUNDLE = "receiver_bundle";
    
    /***Intent的始发者***/
    public final static String FROM = "from";
    
    /***表情动画参数FacesAnimConfig，由FacesRobot传到Activity***/
    public final static String FACES_CONFIG = "faces_config";
    
    /***表情bundle，由FacesRobot传到Activity***/
    public final static String FACES_BUNDLE = "faces_bundle";
    
    /***intent的功能类型***/
    public final static String INTENT_FUNCTION_TYPE = "intent_function_type";
    
    
    
    /***胸部位置Led，读取系统设置的key***/
    public final static String CHEST_LED = "chest_led";
    
    /***耳朵位置Led，读取系统设置的key***/
    public final static String EAR_LED = "ear_led";
    
    /***呼吸灯的状态，读取系统设置的key***/
    public final static String BREATHE_LED_STATUS = "breathe_led_status";
    
    /***广播接收者TAG数组***/
    public final static String[] RECEIVER_TAGS = {FacesReceiver.TAG};
    
    
    
    
    
    

}
