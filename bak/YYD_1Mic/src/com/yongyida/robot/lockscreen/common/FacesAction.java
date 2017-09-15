/**
 * @filename 文件名：FacesAction.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.common;

/**
 * @filename 文件名：FacesAction.java
 * @description 描    述：表情Action的常量类，用于识别接收到的Intent的action。
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public final class FacesAction {
    /*********************************表情意图，共18个***********************************/
    /***1.憋嘴说话***/
    public final static String ACTION_SAY_ON_ANGRY = "com.yongyida.action.lockscreen.ACTION_SAY_ON_ANGRY";

    /***2.害怕***/
    public final static String ACTION_AFRAID = "com.yongyida.action.lockscreen.ACTION_AFRAID";

    /***3.害羞***/
    public final static String ACTION_SHY = "com.yongyida.action.lockscreen.ACTION_SHY";

    /***4.加油***/
    public final static String ACTION_JIAYOU = "com.yongyida.action.lockscreen.ACTION_JIAYOU";

    /***5.哭***/
    public final static String ACTION_CRY = "com.yongyida.action.lockscreen.ACTION_CRY";

    /***6.酷***/
    public final static String ACTION_KU = "com.yongyida.action.lockscreen.ACTION_KU";

    /***7.卖萌***/
    public final static String ACTION_MENG = "com.yongyida.action.lockscreen.ACTION_MENG";

    /***8.难过***/
    public final static String ACTION_SAD = "com.yongyida.action.lockscreen.ACTION_SAD";

    /***9.怒***/
    public final static String ACTION_ANGRY = "com.yongyida.action.lockscreen.ACTION_ANGRY";

    /***10.亲亲***/
    public final static String ACTION_QINQIN = "com.yongyida.action.lockscreen.ACTION_QINQIN";

    /***11.倾听***/
    public final static String ACTION_LISTEN = "com.yongyida.robot.voice.VOICE_UNDERSTAND";

    /***12.睡觉***/
    public final static String ACTION_SLEEP = "com.yongyida.action.lockscreen.ACTION_SLEEP";

    /***13.说话***/
    public final static String ACTION_SPEAK = "com.yongyida.action.lockscreen.ACTION_SPEAK";

    /***14.笑眯眼***/
    public final static String ACTION_LAUGH = "com.yongyida.action.lockscreen.ACTION_LAUGH";

    /***15.学习***/
    public final static String ACTION_LEARN = "com.yongyida.action.lockscreen.ACTION_LEARN";

    /***16.晕***/
    public final static String ACTION_YUN = "com.yongyida.action.lockscreen.ACTION_YUN";

    /***17.眨眼睛***/
    public final static String ACTION_NICTATION = "com.yongyida.action.lockscreen.ACTION_NORMAL";

    /***18.摸头、肚子、肩膀***/
    public final static String ACTION_TOUCH_HEAD = "TouchSensor";
    

    /***Activity处理的意图action数组***/
    public static String[] FACES_ACTIONS = { ACTION_SAY_ON_ANGRY, ACTION_AFRAID, ACTION_SHY, ACTION_JIAYOU, ACTION_CRY, ACTION_KU, ACTION_MENG,
            ACTION_SAD, ACTION_ANGRY, ACTION_QINQIN, ACTION_LISTEN, ACTION_SLEEP, ACTION_SPEAK, ACTION_LAUGH, ACTION_LEARN, ACTION_YUN,
            ACTION_NICTATION, ACTION_TOUCH_HEAD };
}
