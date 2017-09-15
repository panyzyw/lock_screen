package com.yongyida.robot.lockscreen.faces.model;

/**
 * @projectName 项目名称：YYDRobotLockScreen
 * @description 描    述：机器人的表情类型的枚举
 * @creator 作    者：SergioPan
 * @createDate 时    间：2016/8/615:30
 * @changeAuthor 修 改 人：SergioPan
 * @changeDate 修改时间：2016/8/6 15:30
 * @changeRemark 修改备注：//TODO
 * @copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public enum FacesType {

    /***憋嘴说话***/
    FACE_SAY_ON_ANGRY("face_say_on_angry"),

    /***害怕***/
    FACE_AFRAID("face_afraid"),

    /***害羞***/
    FACE_SHY("face_shy"),

    /***加油***/
    FACE_FIGHTING("face_fighting"),

    /***哭***/
    FACE_CRY("face_cry"),

    /***酷***/
    FACE_COOL("face_cool"),

    /***卖萌***/
    FACE_ACTING_CUTE("face_acting_cute"),

    /***难过***/
    FACE_SAD("face_sad"),

    /***怒***/
    FACE_RAGE("face_rage"),

    /***亲亲***/
    FACE_KISS("face_kiss"),

    /***倾听***/
    FACE_LISTEN("face_listen"),

    /***睡觉***/
    FACE_SLEEP("face_sleep"),

    /***说话***/
    FACE_SPEAK("face_speak"),

    /***笑眯眼***/
    FACE_LAUGH_SQUINT("face_laugh_squint"),

    /***学习***/
    FACE_STUDY("face_study"),

    /***晕***/
    FACE_GIDDY("face_giddy"),

    /***眨眼***/
    FACE_NICTATION("face_nictation"),

    /***摸头、肚子***/
    FACE_TOUCH_HEAD("face_touch_head"),

    /***安静***/
    FACE_QUIET("face_quiet"),

    /***锁屏***/
    FACE_LOCKSCREEN("face_lockscreen"),

    /***锁屏解锁***/
    FACE_UNLOCKSCREEN("face_unlockscreen"),

    /***未知***/
    FACE_NEW_OUTGOING_CALL("face_new_outgoing_call"),

    /***控制机器人***/
    FACE_ENTER_MONITOR("face_enter_monitor"),

    /***打开视频***/
    FACE_ENTER_VIDEO("face_enter_video"),

    STATE_END("/yydCmdEnd");// 虚假的接口名称，表示列表结束。

    private String facesType;

    private FacesType(String facesType) {
        this.facesType = facesType;
    }

    public static FacesType getEnumFromString(String facesType) {
        if (facesType != null) {
            try {
                return Enum.valueOf(FacesType.class, facesType.trim());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }

}
