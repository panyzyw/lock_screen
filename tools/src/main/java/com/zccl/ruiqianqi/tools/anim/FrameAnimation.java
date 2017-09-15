package com.zccl.ruiqianqi.tools.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ruiqianqi on 2016/7/29 0029.
 */
public class FrameAnimation {

    /** 需要播放动画的控件 */
    private View mImageView;
    /** 播放的序列图片 */
    private int[] mFrameRes;
    /** 每个序列帧的延时时间 */
    private int[] mDurations;
    /** 统一的延时时间 */
    private int mDuration;
    /** 最后的序列帧编号 */
    private int mLastFrameNo;
    /** 一个周期播放完了之后的延时 */
    private long mBreakDelay;
    /** 是否停止了 */
    private boolean isStop = true;

    // 播放完毕的回调
    private IPlayOverCallback playOverCallback;

    private ArrayList<byte[]> mFrameBitmaps ;
    private boolean isBitmap = false ;


    /**
     *
     * @param pImageView 需要播放动画的控件
     * @param pFrameRes  播放的序列图片编号数组
     * @param pDurations 每个序列帧的延时时间
     */
    public FrameAnimation(View pImageView, int[] pFrameRes,
                          int[] pDurations) {
        mImageView = pImageView;
        mFrameRes = pFrameRes;
        mDurations = pDurations;
        mLastFrameNo = pFrameRes.length - 1;
        //mImageView.setBackgroundResource(mFrameRes[0]);
        //play(1);
    }

    /**
     *
     * @param pImageView 需要播放动画的控件
     * @param pFrameRes  播放的序列图片编号数组
     * @param pDuration  统一的延时时间
     */
    public FrameAnimation(View pImageView, int[] pFrameRes, int pDuration) {
        mImageView = pImageView;
        mFrameRes = pFrameRes;
        mDuration = pDuration;
        mLastFrameNo = pFrameRes.length - 1;
        mBreakDelay = 0;
        //mImageView.setBackgroundResource(mFrameRes[0]);
        //playConstant(1);
    }

    /**
     *
     * @param pImageView  需要播放动画的控件
     * @param pFrameBitmaps   播放的序列图片编号数组
     * @param pDuration   统一的延时时间
     * @param pBreakDelay 一个周期播放完了之后的延时
     */
    public FrameAnimation(View pImageView, ArrayList<byte[]> pFrameBitmaps,
                          int pDuration, long pBreakDelay) {

        mImageView = pImageView;
        mFrameBitmaps = pFrameBitmaps;
        mDuration = pDuration;
        mLastFrameNo = pFrameBitmaps.size() - 1;
        mBreakDelay = pBreakDelay;

        isBitmap = true ;

    }

    /**
     *
     * @param pImageView 需要播放动画的控件
     * @param pFrameBitmaps  播放的序列图片编号数组
     * @param pDuration  统一的延时时间
     */
    public FrameAnimation(View pImageView, ArrayList<byte[]> pFrameBitmaps, int pDuration) {

        mImageView = pImageView;
        mFrameBitmaps = pFrameBitmaps;
        mDuration = pDuration;
        mLastFrameNo = pFrameBitmaps.size() - 1;
        mBreakDelay = 0;

        isBitmap = true ;
    }

    /**
     *
     * @param pImageView  需要播放动画的控件
     * @param pFrameRes   播放的序列图片编号数组
     * @param pDuration   统一的延时时间
     * @param pBreakDelay 一个周期播放完了之后的延时
     */
    public FrameAnimation(View pImageView, int[] pFrameRes,
                          int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRes = pFrameRes;
        mDuration = pDuration;
        mLastFrameNo = pFrameRes.length - 1;
        mBreakDelay = pBreakDelay;

        //mImageView.setBackgroundResource(mFrameRes[0]);
        //playConstant(1);
    }

    /**
     * 设置播放完毕后的回调接口
     * @param playOverCallback
     */
    public void setPlayOverCallback(IPlayOverCallback playOverCallback) {
        this.playOverCallback = playOverCallback;
    }

    /**
     * 开始播放序列帧，每个都有自己的延时时间
     * @param pFrameNo
     */
    public void play(final int pFrameNo) {
        isStop = false;
        mImageView.setBackgroundResource(mFrameRes[pFrameNo]);
        mImageView.postDelayed(new Runnable() {
            public void run() {
                if(!isStop) {
                    if (pFrameNo == mLastFrameNo) {
                        if(null != playOverCallback){
                            playOverCallback.OnPlayOver();
                        }else {
                            play(0);
                        }
                    } else {
                        play(pFrameNo + 1);
                    }
                }
            }
        }, mDurations[pFrameNo]);
    }


    private BitmapFactory.Options options ;

    /**
     * 开始播放序列帧，用统一的延时时间
     * @param pFrameNo
     */
    public void playConstant(final int pFrameNo) {
        isStop = false;

        if (isBitmap){

            byte[] bs = mFrameBitmaps.get(pFrameNo) ;

            if(options == null){

                options =  new BitmapFactory.Options() ;

                // true 表示开启inBitmap属性，可以是当前的bitmap复用
                options.inMutable = true;

                options.inBitmap = BitmapFactory.decodeByteArray(bs,0,bs.length, options) ;

            }else{

                BitmapFactory.decodeByteArray(bs,0,bs.length ,options) ;
            }

            BitmapDrawable drawable = new BitmapDrawable(null,options.inBitmap) ;
            mImageView.setBackground(drawable);

        }else{

            mImageView.setBackgroundResource(mFrameRes[pFrameNo]);

        }
        mImageView.postDelayed(mRunnable = new Runnable() {
            public void run() {
                if(!isStop) {
                    if (pFrameNo == mLastFrameNo) {
                        if(null != playOverCallback){
                            playOverCallback.OnPlayOver();
                        }else {
                            playConstant(0);
                        }
                    } else {
                        playConstant(pFrameNo + 1);
                    }
                }
            }
        }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);
    }

    private Runnable mRunnable ;

    /**
     * 停止播放
     */
    public void stop(){

        mImageView.removeCallbacks(mRunnable) ;


        isStop = true;

        if(options != null){

            if(options.inBitmap !=null){

                options.inBitmap.recycle();
                options.inBitmap = null ;
            }
            options = null ;
        }

    }

    /**
     * 播放完毕
     */
    public interface IPlayOverCallback{
        void OnPlayOver();
    }

}

