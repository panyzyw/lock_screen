/**
 * @filename 文件名：FacesAnimConfig.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-15
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.faces.model;


import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @filename 文件名：FacesAnimConfig.java
 * @description 描    述：帧动画参数类
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-15
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class FacesAnimConfig implements Parcelable {

    /***动画的图片资源id数组***/
    private int[] imgIds;

    /***播放次数,默认为1次，大于1为重复播放***/
    private int playTimes = 1;

    /***循环播放开始位置***/
    private int circleStart;

    /***循环播放结束位置***/
    private int circleEnd;

    /***播放间隔时间***/
    private int duration;

    public FacesAnimConfig() {
    }

    public FacesAnimConfig(int[] imgIds, int playTimes, int circleStart, int circleEnd, int duration) {
        this.imgIds = imgIds;
        this.playTimes = playTimes;
        this.circleStart = circleStart;
        this.circleEnd = circleEnd;
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(imgIds);
        dest.writeInt(playTimes);
        dest.writeInt(circleStart);
        dest.writeInt(circleEnd);
        dest.writeInt(duration);
    }

    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<FacesAnimConfig> CREATOR = new Parcelable.Creator<FacesAnimConfig>() {
        public FacesAnimConfig createFromParcel(Parcel in) {
            return new FacesAnimConfig(in);
        }

        public FacesAnimConfig[] newArray(int size) {
            return new FacesAnimConfig[size];
        }
    };

    public static Parcelable.Creator<FacesAnimConfig> getCreator() {
        return CREATOR;
    }

    // 读数据进行恢复
    private FacesAnimConfig(Parcel in) {
        imgIds = in.createIntArray();
        playTimes = in.readInt();
        circleStart = in.readInt();
        circleEnd = in.readInt();
        duration = in.readInt();

    }

    /**
     * @return Returns the imgIds.
     */
    public int[] getImgIds() {
        return imgIds;
    }

    /**
     * @param imgIds The imgIds to set.
     */
    public void setImgIds(int[] imgIds) {
        this.imgIds = imgIds;
    }

    /**
     * @return Returns the playTimes.
     */
    public int getPlayTimes() {
        return playTimes;
    }

    /**
     * @param playTimes The playTimes to set.
     */
    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    /**
     * @return Returns the circleStart.
     */
    public int getCircleStart() {
        return circleStart;
    }

    /**
     * @param circleStart The circleStart to set.
     */
    public void setCircleStart(int circleStart) {
        this.circleStart = circleStart;
    }

    /**
     * @return Returns the circleEnd.
     */
    public int getCircleEnd() {
        return circleEnd;
    }

    /**
     * @param circleEnd The circleEnd to set.
     */
    public void setCircleEnd(int circleEnd) {
        this.circleEnd = circleEnd;
    }

    /**
     * @return Returns the duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration The duration to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * //TODO 添加override说明
     * @see java.lang.Object#toString()
     **/
    @Override
    public String toString() {
        return "FacesAnimConfig [imgIds=" + Arrays.toString(imgIds) + ", playTimes=" + playTimes + ", circleStart=" + circleStart + ", circleEnd="
                + circleEnd + ", duration=" + duration + "]";
    }

    
    
    
}
