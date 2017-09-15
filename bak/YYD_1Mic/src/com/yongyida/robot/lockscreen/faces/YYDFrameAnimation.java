/**
 * @filename 文件名：YYDFrameAnimation.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-12
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.faces;

import android.widget.ImageView;

import com.yongyida.robot.lockscreen.faces.model.FacesAnimConfig;

/**
 * @filename 文件名：YYDFrameAnimation.java
 * @description 描    述：多图帧动画的类,
 * 使用：
 * 1.带参数构造方法获得实例；
 * 2.添加到任务队列addToAnimationTaskQueue
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-12
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class YYDFrameAnimation {

    private ImageView mImageView;

    private int[] imgIds;

    private int duration;

    /***状态***/
    private PlayState state = PlayState.STOP;

    /***播放次数,默认为1次，大于1为重复播放，等于0为无限循环播放***/
    private int playTimes = 1;

    /***当前播放次数***/
    private int curPlayTimes = 1;

    private int circleStart = 0;

    private int circleEnd = 0;

    /***动画结束监听器***/
    private AnimtationListener listenner;

    public YYDFrameAnimation(ImageView iv, FacesAnimConfig faces) {
        this.mImageView = iv;
        if (faces != null) {
            this.imgIds = faces.getImgIds();
            this.playTimes = faces.getPlayTimes();
            this.circleStart = faces.getCircleStart();
            this.circleEnd = faces.getCircleEnd();
            this.duration = faces.getDuration();
        }
    }

    public void setAnimtationListener(AnimtationListener listenner) {
        this.listenner = listenner;
    }

    /***
     * @method 方法名：start
     * @features 功    能：启动线程开始播放表情动画
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void start() {
        new Thread(runnable).start();
    }

    /***播放动画的线程***/
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (playTimes > 1 && circleStart > circleEnd) {//循环设置错误
                return;
            }
            state = PlayState.START;
            playAnimation(0);

        }
    };

    /**
     * @method 方法名：play
     * @features 功    能：播放动画
     * @params 参    数：@param position
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    private void playAnimation(final int position) {

        if (state == PlayState.STOP) {
            if (listenner != null) {
                listenner.onAnimtationfinished();
            }
            return;
        }

        mImageView.postDelayed(new Runnable() {

            public void run() {
                runAnimation(position);
            }

        }, duration);
    }

    /**
     * @method 方法名：runAnimation
     * @features 功    能：执行动画的方法
     * @params 参    数：@param position
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void runAnimation(int position) {
        if (position < imgIds.length) {//播放动画
            mImageView.setBackgroundResource(imgIds[position]);
            // 无限循环播放
            if (playTimes == 0 && position == circleEnd) {
                position = circleStart;
            }
            // 中间部分的循环播放
            if (position == circleEnd && playTimes > 1 && curPlayTimes < playTimes) {
                position = circleStart;
                curPlayTimes++;
            }

            //切换下一张图
            playAnimation(position + 1);
        } else {// 播放完毕
            state = PlayState.STOP;
            playAnimation(position + 1);
        }
    }

    /**
     * @method 方法名：stop
     * @features 功    能：停止播放
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void stop() {
        state = PlayState.STOP;
    }

    /**
     * @method 方法名：isRunning
     * @features 功    能：判断动画是否在运行
     * @params 参    数：@return
     * @return 返回值：boolean
     * @modify 修改者: Sergio Pan
     */
    public boolean isRunning() {
        return state == PlayState.START;
    }

    /**
     * @filename 文件名：YYDFrameAnimation.java
     * @description 描    述：动画的播放状态类
     * @author 作    者：Sergio Pan
     * @date 时    间：2016-8-13
     * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
     */
    private enum PlayState {
        /***开始**/
        START,
        /***停止**/
        STOP;
    }

    /**
     * @filename 文件名：YYDFrameAnimation.java
     * @description 描    述：动画结束监听器
     * @author 作    者：Sergio Pan
     * @date 时    间：2016-8-15
     * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
     */
    public interface AnimtationListener {
        /**
         * 
         * @method 方法名：onAnimtationfinished
         * @features 功    能：动画结束接口
         * @params 参    数：
         * @return 返回值：void
         * @modify 修改者: Sergio Pan
         */
        public void onAnimtationfinished();
    }

}
