package com.yongyida.robot.lockscreen.robot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.yongyida.robot.lockscreen.R;
import com.yongyida.robot.lockscreen.common.ConstKey;
import com.yongyida.robot.lockscreen.faces.YYDFrameAnimation;
import com.yongyida.robot.lockscreen.faces.YYDFrameAnimation.AnimtationListener;
import com.yongyida.robot.lockscreen.faces.activity.LockScreenActivity;
import com.yongyida.robot.lockscreen.faces.model.FacesAnimConfig;
import com.yongyida.robot.lockscreen.faces.model.FacesType;
import com.yongyida.robot.lockscreen.faces.util.FacesTypeUtils;

/**
 * @description 描    述：表情机器人
 * @creator 作    者：SergioPan
 * @createDate 时    间：2016/8/615:28
 * @changeAuthor 修 改 人：SergioPan
 * @changeDate 修改时间：2016/8/6 15:28
 * @changeRemark 修改备注：//TODO
 * @copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class FacesRobot extends BaseRobot {

    public final static String TOUCH_SENSOR = "android.intent.extra.Touch";

    private static FacesRobot myself = null;

    /***表情动画***/
    private YYDFrameAnimation animFaces;

    private FacesAnimConfig faces;

    private FacesRobot() {
        setMyName(FacesRobot.class.toString());
    }

    public static FacesRobot getInstance() {
        if (myself == null) {
            synchronized (FacesRobot.class) {
                if (myself == null) {
                    myself = new FacesRobot();
                }
            }
        }
        return myself;
    }

    /**
     * 初始化数据
     * @see com.yongyida.robot.lockscreen.robot.BaseRobot#initData()
     **/
    @Override
    protected void initData(Context ctx, Intent mIntent) {
        Log.i("FacesRobot", "mIntent.getAction() = " + mIntent.getAction());
        String mAction = mIntent.getAction();
        FacesType type = FacesTypeUtils.getFacesStateByAction(mAction);
        if (type == null) {
            faces = null;
            return;
        }
        faces = FacesTypeUtils.initFacesAnimConfig(ctx, type, mIntent, 36, this);
    }

    /**
     * 动作开始
     * @see com.yongyida.robot.lockscreen.robot.BaseRobot#actionStart()
     **/
    @Override
    protected void actionStart(Context ctx) {
        if (faces == null) {
            return;
        }
        Log.i("FacesRobot", "faces = " + faces.toString());
        //启动Activity展示表情动画
        startActivityToShow(ctx, faces);

    }

    /**
     * @method 方法名：showFaces
     * @features 功    能：展示机器人表情动画的方法
     * @params 参    数：@param ctx
     * @params 参    数：@param iv
     * @params 参    数：@param faces
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void showFaces(Context ctx, final ImageView iv, FacesAnimConfig faces) {
        if (ctx == null || faces == null || iv == null) {
            return;
        }

        //先结束上一个动画，再开始下一个动画
        if (animFaces != null && animFaces.isRunning()) {
            animFaces.stop();
        }
        animFaces = new YYDFrameAnimation(iv, faces);
        animFaces.setAnimtationListener(new AnimtationListener() {

            @Override
            public void onAnimtationfinished() {
                iv.setBackgroundResource(R.drawable.unknow_00000);
            }
        });
        // 开始播放表情动画
        animFaces.start();

    }

    /**
     * @function    功    能：启动锁屏Activity展示表情动画
     * @param  ：context
     * @return  返回值：
     * @change_author 修改者: SergioPan
     * @change_date 修改时间: 2016/8/9 18:43
     */
    private void startActivityToShow(Context context, FacesAnimConfig faces) {
        //TODO 黑屏状态下，Activity不可能被启动，所以新来的表情广播会丢失
        Intent mIntent = new Intent(context, LockScreenActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(ConstKey.FACES_CONFIG, faces);
        mIntent.putExtra(ConstKey.FACES_BUNDLE, mBundle);
        context.startActivity(mIntent);
    }

    /**
     * /停止动作
     * @see com.yongyida.robot.robotaction.robot.IRobot#stop()
     **/
    @Override
    public void stop() {
        if (animFaces != null) {
            animFaces.stop();
        }

    }

}
