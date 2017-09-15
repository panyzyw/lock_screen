package com.yongyida.robot.lockscreen.presentation.presenter.expression;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yongyida.robot.lockscreen.presentation.presenter.BasePresenter;
import com.yongyida.robot.lockscreen.utils.AppUtils;
import com.zccl.ruiqianqi.brain.system.SystemPresenter;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.MyAppUtils;
import com.zccl.ruiqianqi.tools.StringUtils;
import com.zccl.ruiqianqi.tools.anim.FrameAnimation;

import java.util.ArrayList;
import java.util.HashMap;

import static com.yongyida.robot.lockscreen.FaceConfig.FACE_AFRAID;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_ANGRY;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_COOL;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_CRY;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_FIGHT;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_HUNGRY;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_INIT;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_INNOCENT;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_KISS;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_LAUGH;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_LEARN;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_MENG;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_SLEEP;
import static com.yongyida.robot.lockscreen.FaceConfig.FACE_SPEAK;
import static com.yongyida.robot.lockscreen.FaceConfig.INTERNAL;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_ACTION;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_DURATION_TIME;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_IS_EXIT_APP;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_IS_NEED_RECYC_LELISTEN;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_IS_READ_SDCARD;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_TEXT;
import static com.yongyida.robot.lockscreen.brain.receiver.MainReceiver.KEY_WHILE_PLAY;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_AFRAID;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_ANGRY;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_COOL;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_CRY;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_FIGHT;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_HUNGRY;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_INIT;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_INNOCENT;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_KISS;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_LAUGH;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_LEARN;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_MENG;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_SLEEP;
import static com.yongyida.robot.lockscreen.config.MyConfig.OP_ACTION_SPEAK;

/**
 * Created by ruiqianqi on 2017/5/17 0017.
 */

public class ExpressionPresenter extends BasePresenter{

    private static String TAG = ExpressionPresenter.class.getSimpleName();
    // 应用发音用的
    public static final String ACTION_TTS = "com.yongyida.robot.TTS";
    // 开始发音，还是停止发音
    public static final String TTS_ACTION_KEY = "tts_action";
    // 开始发音
    public static final String TTS_ACTION_VALUE_START = "start";
    // 停止发音
    public static final String TTS_ACTION_VALUE_STOP = "stop";
    // 开始发音的文字
    public static final String TTS_TEXT_KEY = "tts_text";

    public static final String KEY_FROM         = "from";
    public static final String VALUE_EXPRESSION = "expression";

    // 表情动画
    private FrameAnimation frameAnimation;
    // 待机动画
    private FrameAnimation initAnimation;
    // 表情回调接口
    private IExpressionCallback expressionCallback;
    // 初始化表情
    private boolean isInitFace;
    // 非初始化表情之是否循环播放
    private boolean isWhilePlay;

    //表示是否是退出APP， 如果是true 表示退出APP；false表示退出当前表情
    private boolean isExitAPP ;

    // 表示是否需要重新监听
    private boolean isNeedRecycleListen ;

    // 表示是否读取本地资源
    private boolean isReadSdcard ;

    public ExpressionPresenter(){

        SystemPresenter.getInstance(mContext) ;
    }

    /**
     * 设置表情回调接口
     * @param expressionCallback
     */
    public void setExpressionCallback(IExpressionCallback expressionCallback) {
        this.expressionCallback = expressionCallback;
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if(isExitAPP){

                // 时间到了，退出APP
                Activity a = (Activity) imageView.getContext();
                a.finish();

            }else {

                // 时间到了 恢复默认表情
                initAnimation() ;

            }
        }
    };

    private ImageView imageView ;

    /**
     * 开始表情
     * @param intent
     */
    public void startExpression(final ImageView imageView, Intent intent) {
        LogUtils.e(TAG, "startExpression");

        this.imageView = imageView ;
        this.isInitFace = false;

        String action = intent.getStringExtra(KEY_ACTION);

        isExitAPP = intent.getBooleanExtra(KEY_IS_EXIT_APP, false) ;
        isNeedRecycleListen = intent.getBooleanExtra(KEY_IS_NEED_RECYC_LELISTEN ,false) ;


        String words = intent.getStringExtra(KEY_TEXT) ;
        boolean isHasWords = !StringUtils.isEmpty(words) ;

        if(isHasWords){

            // 含有文字 当前表情一直执行直到语音结束
            isWhilePlay = true ;

        }else{

            // 没有文字 就根据传入的时间
            isWhilePlay = intent.getBooleanExtra(KEY_WHILE_PLAY, true);

            // 持续时间（单位为毫秒：如果时间小于等于0，表示无限循环）
            long durationTime = intent.getLongExtra(KEY_DURATION_TIME, 0) ;
            if(durationTime > 0){

                imageView.removeCallbacks(mRunnable) ;
                imageView.postDelayed(mRunnable ,durationTime) ;
            }
        }

        isReadSdcard =intent.getBooleanExtra(KEY_IS_READ_SDCARD, false) ;
        boolean isSuccess = false ;

        if(isReadSdcard){

            isSuccess = startExpressionFromSdCard(imageView, action) ;
        }
        if(!isSuccess){

            isSuccess = startExpression(imageView, action) ;
        }

        // 有该表情并且有文字内容
        if(isHasWords && isSuccess){

            SystemPresenter.getInstance(mContext).startTTS(words, new Runnable() {
                @Override
                public void run() {

                    if(isNeedRecycleListen){

                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //继续监听
                        sendRecycleListenBroadcast(mContext, VALUE_EXPRESSION) ;
                    }
                    imageView.post(mRunnable);
                }
            });

        }

    }


    /**发送继续监听的广播*/
    public void sendRecycleListenBroadcast(Context context, String from){

        Intent intent = new Intent("com.yydrobot.RECYCLE");
        intent.putExtra("from", from);

        context.sendBroadcast(intent);
    }


    /**
     * 初始化表情
     * */
    private void initAnimation(){

        stop() ;
        if( initAnimation != null ){

            return;
        }

        if(isReadSdcard){

            ArrayList<byte[]> emojiBitmaps = AppUtils.getEmojBitmap(bitmapMap,OP_ACTION_INIT) ;
            if(emojiBitmaps != null){

                initAnimation = new FrameAnimation(imageView, emojiBitmaps, INTERNAL, 4000);
            }else{
                initAnimation = new FrameAnimation(imageView, FACE_INIT, INTERNAL, 4000);
            }
        }else{

            initAnimation = new FrameAnimation(imageView, FACE_INIT, INTERNAL, 4000);
        }

        initAnimation.playConstant(0);

    }



    private boolean startExpression(final ImageView imageView,String action) {

        int[] resArray = null;
        if (OP_ACTION_INIT.equals(action)) {
            resArray = FACE_INIT;
            isInitFace = true;

        } else if (OP_ACTION_AFRAID.equals(action)) {
            resArray = FACE_AFRAID;

        } else if (OP_ACTION_ANGRY.equals(action)) {
            resArray = FACE_ANGRY;

        } else if (OP_ACTION_CRY.equals(action)) {
            resArray = FACE_CRY;

        } else if (OP_ACTION_FIGHT.equals(action)) {
            resArray = FACE_FIGHT;

        } else if (OP_ACTION_COOL.equals(action)) {
            resArray = FACE_COOL;

        } else if (OP_ACTION_LAUGH.equals(action)) {
            resArray = FACE_LAUGH;

        } else if (OP_ACTION_LEARN.equals(action)) {
            resArray = FACE_LEARN;

        } else if (OP_ACTION_MENG.equals(action)) {
            resArray = FACE_MENG;

        } else if (OP_ACTION_KISS.equals(action)) {
            resArray = FACE_KISS;

        } else if (OP_ACTION_SLEEP.equals(action)) {
            resArray = FACE_SLEEP;

        } else if (OP_ACTION_HUNGRY.equals(action)) {
            resArray = FACE_HUNGRY;

        } else if (OP_ACTION_INNOCENT.equals(action)) {
            resArray = FACE_INNOCENT;

        } else if (OP_ACTION_SPEAK.equals(action)) {
            resArray = FACE_SPEAK;

        }

        if(resArray == null)
            return false ;

        // 有语义，没表情
        if(0 == resArray.length)
            return false;


        if(null != initAnimation){
            initAnimation.stop();
            initAnimation = null ;
        }

        if(null != frameAnimation){
            frameAnimation.stop();
        }

        // 非初始化表情才需要结束
        if(!isInitFace) {
            if(isWhilePlay){
                frameAnimation = new FrameAnimation(imageView, resArray, INTERNAL, 500);
                frameAnimation.playConstant(0);

            }else {
                frameAnimation = new FrameAnimation(imageView, resArray, INTERNAL);
                frameAnimation.setPlayOverCallback(new FrameAnimation.IPlayOverCallback() {
                    @Override
                    public void OnPlayOver() {
                        if (null != expressionCallback) {
                            //expressionCallback.OnPlayOver();

                            imageView.post(mRunnable) ;
//                            initAnimation = new FrameAnimation(imageView, FACE_INIT, INTERNAL, 4000);
//                            initAnimation.playConstant(0);
                        }
                    }
                });
                frameAnimation.playConstant(0);

            }
        }else {
            initAnimation = new FrameAnimation(imageView, resArray, INTERNAL, 4000);
            initAnimation.playConstant(0);
        }

        return true ;
    }


    private final HashMap<String,byte[]> bitmapMap = new HashMap<>() ;
    private boolean startExpressionFromSdCard(final ImageView imageView,String action) {

        ArrayList<byte[]> emjosBitmap = AppUtils.getEmojBitmap(bitmapMap,action) ;
        if(emjosBitmap == null)
            return false ;


        if (OP_ACTION_INIT.equals(action))
            isInitFace = true;

        if(null != initAnimation){
            initAnimation.stop();
            initAnimation = null ;
        }

        if(null != frameAnimation){
            frameAnimation.stop();
        }

        // 非初始化表情才需要结束
        if(!isInitFace) {
            if(isWhilePlay){
                frameAnimation = new FrameAnimation(imageView, emjosBitmap, INTERNAL, 500);
                frameAnimation.playConstant(0);

            }else {
                frameAnimation = new FrameAnimation(imageView, emjosBitmap, INTERNAL);
                frameAnimation.setPlayOverCallback(new FrameAnimation.IPlayOverCallback() {
                    @Override
                    public void OnPlayOver() {
                        if (null != expressionCallback) {
                            //expressionCallback.OnPlayOver();


                            imageView.post(mRunnable) ;
//                            ArrayList<byte[]> emjosBitmap = AppUtils.getEmojBitmap(bitmapMap,OP_ACTION_INIT) ;
//                            if(emjosBitmap != null){
//
//                                initAnimation = new FrameAnimation(imageView, emjosBitmap, INTERNAL, 4000);
//                            }else{
//                                initAnimation = new FrameAnimation(imageView, FACE_INIT, INTERNAL, 4000);
//                            }
//                            initAnimation.playConstant(0);
                        }
                    }
                });
                frameAnimation.playConstant(0);
            }
        }else {

            initAnimation = new FrameAnimation(imageView, emjosBitmap, INTERNAL, 4000);
            initAnimation.playConstant(0);
        }

        return true ;
    }


    /**
     * 停止动画
     */
    public void stop(){
        if(null != frameAnimation){
            frameAnimation.stop();
        }
    }

    /**
     * 表情回调接口
     */
    public interface IExpressionCallback{
        void OnPlayOver();
    }

}
