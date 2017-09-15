/**
 * @filename 文件名：FacesTypeUtils.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-16
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.faces.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yongyida.robot.lockscreen.common.FacesAction;
import com.yongyida.robot.lockscreen.faces.activity.LockScreenActivity;
import com.yongyida.robot.lockscreen.faces.activity.LockScreenActivity.Status;
import com.yongyida.robot.lockscreen.faces.common.FacesId;
import com.yongyida.robot.lockscreen.faces.model.FacesAnimConfig;
import com.yongyida.robot.lockscreen.faces.model.FacesType;
import com.yongyida.robot.lockscreen.robot.BaseRobot;
import com.yongyida.robot.lockscreen.robot.FacesRobot;

/**
 * @filename 文件名：FacesTypeUtils.java
 * @description 描    述：表情分类工具类，为FacesRobot分担工作
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-16
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class FacesTypeUtils {

    /**
     * @method 方法名：initFacesAnimConfig
     * @features 功    能：初始化表情动画,包括动画图片资源id数组，循环次数，循环播放开始位置，结束位置和延迟时间 
     * @params 参    数：@param ctx
     * @params 参    数：@param type
     * @params 参    数：@param mIntent
     * @params 参    数：@param duration
     * @params 参    数：@param mRobot
     * @params 参    数：@return
     * @return 返回值：FacesAnimConfig
     * @modify 修改者: Sergio Pan
     */
    public static FacesAnimConfig initFacesAnimConfig(Context ctx, FacesType type, Intent mIntent, int duration, BaseRobot mRobot) {
        FacesAnimConfig faces = null;
        switch (type) {
        case FACE_SAY_ON_ANGRY://憋嘴说话
            faces = new FacesAnimConfig(FacesId.FACE_SAY_ON_ANGRY, 1, 0, 0, duration);
            break;
        case FACE_AFRAID://害怕
            faces = new FacesAnimConfig(FacesId.FACE_AFRAID, 1, 0, 0, duration);
            break;
        case FACE_SHY://害羞
            faces = new FacesAnimConfig(FacesId.FACE_SHY, 1, 0, 0, duration);
            break;
        case FACE_FIGHTING://加油
            faces = new FacesAnimConfig(FacesId.FACE_FIGHTING, 1, 0, 0, duration);
            break;
        case FACE_CRY://哭
            faces = new FacesAnimConfig(FacesId.FACE_CRY, 1, 0, 0, duration);
            break;
        case FACE_COOL://酷
            faces = new FacesAnimConfig(FacesId.FACE_COOL, 1, 0, 0, duration);
            break;
        case FACE_ACTING_CUTE://卖萌
            faces = new FacesAnimConfig(FacesId.FACE_ACTING_CUTE, 1, 0, 0, duration);
            break;
        case FACE_SAD://难过
            faces = new FacesAnimConfig(FacesId.FACE_SAD, 1, 0, 0, duration);
            break;
        case FACE_RAGE://怒
            faces = new FacesAnimConfig(FacesId.FACE_RAGE, 1, 0, 0, duration);
            break;
        case FACE_KISS://亲亲
            faces = new FacesAnimConfig(FacesId.FACE_KISS, 1, 0, 0, duration);
            break;
        case FACE_LISTEN://倾听
            String status = mIntent.getStringExtra("status");
            Log.i("FacesTypeUtils", "initFacesAnimConfig----status = " + status);
            if ("start".equals(status)) {//开始录音
//                faces = new FacesAnimConfig(FacesId.FACE_LISTEN, 0, 0, FacesId.FACE_LISTEN.length - 1, duration);
                return null;
            }
            if ("end".equals(status)) {//录音完毕
 
                if (LockScreenActivity.instance != null) {
                    //原来的Intent置为null，不管是Activity什么状态，都要停止动画
                    LockScreenActivity.instance.setIntent(null);
                }
                if (mRobot != null) {
                    mRobot.stop();
                }
            }

            break;
        case FACE_SLEEP://睡觉
            faces = new FacesAnimConfig(FacesId.FACE_SLEEP, 1, 0, 0, duration);
            break;
        case FACE_SPEAK://说话
            faces = new FacesAnimConfig(FacesId.FACE_SPEAK, 1, 0, 0, duration);
            break;
        case FACE_LAUGH_SQUINT://笑眯眼
            faces = new FacesAnimConfig(FacesId.FACE_LAUGH_SQUINT, 1, 0, 0, duration);
            break;
        case FACE_STUDY://学习
            faces = new FacesAnimConfig(FacesId.FACE_STUDY, 1, 0, 0, duration);
            break;
        case FACE_GIDDY://晕
            faces = new FacesAnimConfig(FacesId.FACE_GIDDY, 1, 0, 0, duration);
            break;
        case FACE_NICTATION://眨眼睛
            faces = new FacesAnimConfig(FacesId.FACE_NICTATION, 1, 0, 0, duration);
            break;
        case FACE_TOUCH_HEAD://摸头、肚子、肩膀
            //            faces = new FacesAnimConfig(FacesId.FACE_NICTATION, 1, 0, 0, duration);
            String touch = mIntent.getStringExtra(FacesRobot.TOUCH_SENSOR);
            Log.i("FacesTypeUtils", "touch = " + touch);
            if ("t_head".equals(touch)) {//摸头
                faces = new FacesAnimConfig(FacesId.FACE_NICTATION, 0, 0, FacesId.FACE_NICTATION.length - 1, duration);
            }
            if ("yyd5".equals(touch)) {//机器人的右肩

            }
            if ("yyd6".equals(touch)) {//机器人的左肩

            }
            break;


        default:
            break;
        }
        if (faces != null && (faces.getImgIds() == null || faces.getImgIds().length == 0)) {
            return null;
        }
        return faces;
    }

    /**
     * 
     * @method 方法名：getFacesStateByAction
     * @features 功    能：获取意图要求展示的表情
     * @params 参    数：@param action
     * @params 参    数：@return
     * @return 返回值：FacesType
     * @modify 修改者: Sergio Pan
     */
    public static FacesType getFacesStateByAction(String action) {
        if (action == null) {
            return null;
        }

        FacesType type = null;
        switch (action) {
        case FacesAction.ACTION_SAY_ON_ANGRY:
            type = FacesType.FACE_SAY_ON_ANGRY;
            break;
        case FacesAction.ACTION_AFRAID:
            type = FacesType.FACE_AFRAID;
            break;
        case FacesAction.ACTION_SHY:
            type = FacesType.FACE_SHY;
            break;
        case FacesAction.ACTION_JIAYOU:
            type = FacesType.FACE_FIGHTING;
            break;
        case FacesAction.ACTION_CRY:
            type = FacesType.FACE_CRY;
            break;
        case FacesAction.ACTION_KU:
            type = FacesType.FACE_COOL;
            break;
        case FacesAction.ACTION_MENG:
            type = FacesType.FACE_ACTING_CUTE;
            break;
        case FacesAction.ACTION_SAD:
            type = FacesType.FACE_SAD;
            break;
        case FacesAction.ACTION_ANGRY:
            type = FacesType.FACE_RAGE;
            break;
        case FacesAction.ACTION_QINQIN:
            type = FacesType.FACE_KISS;
            break;
        case FacesAction.ACTION_LISTEN:
            type = FacesType.FACE_LISTEN;
            break;
        case FacesAction.ACTION_SLEEP:
            type = FacesType.FACE_SLEEP;
            break;
        case FacesAction.ACTION_SPEAK:
            type = FacesType.FACE_SPEAK;
            break;
        case FacesAction.ACTION_LAUGH:
            type = FacesType.FACE_LAUGH_SQUINT;
            break;
        case FacesAction.ACTION_LEARN:
            type = FacesType.FACE_STUDY;
            break;
        case FacesAction.ACTION_YUN:
            type = FacesType.FACE_GIDDY;
            break;
        case FacesAction.ACTION_NICTATION:
            type = FacesType.FACE_NICTATION;
            break;
        case FacesAction.ACTION_TOUCH_HEAD://摸头、肚子
            type = FacesType.FACE_TOUCH_HEAD;
            break;


        default:
            break;
        }

        return type;
    }
}
