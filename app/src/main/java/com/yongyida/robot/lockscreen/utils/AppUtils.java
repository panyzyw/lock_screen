package com.yongyida.robot.lockscreen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.yongyida.robot.lockscreen.R;
import com.zccl.ruiqianqi.tools.LogUtils;
import com.zccl.ruiqianqi.tools.MyAppUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by ruiqianqi on 2017/5/9 0009.
 */

public class AppUtils {

    private static String TAG = AppUtils.class.getSimpleName();

    // 循环监听
    public static final String ACT_RECYCLE_LISTEN = "com.yydrobot.RECYCLE";
    // 停止监听
    public static final String ACT_STOP_LISTEN = "com.yydrobot.STOPLISTEN";

    /**
     * 开始监听【广播】
     *
     * @param context            全局上下文
     * @param from               循环监听来自哪里
     * @param isUseFloatVoice  是否显示悬浮表情
     * @param isUseExpression  是否显示大表情
     */
    public static void startListen(Context context, String from, boolean isUseFloatVoice, boolean isUseExpression){
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.recycle_from_key), from);
        bundle.putBoolean(context.getString(R.string.recycle_voice_float_key), isUseFloatVoice);
        bundle.putBoolean(context.getString(R.string.recycle_expression_key), isUseExpression);
        MyAppUtils.sendBroadcast(context, ACT_RECYCLE_LISTEN, bundle);
    }

    /**
     * 结束监听【广播】
     *
     * @param context  全局上下文
     * @param from     结束监听来自哪里
     */
    public static void stopListen(Context context, String from){
        LogUtils.e(TAG, "stopListen");
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.stop_from_key), from);
        MyAppUtils.sendBroadcast(context, ACT_STOP_LISTEN, bundle);
    }



    //从本地sdcard加载文件
    private static final String EMJO_ROOT_PATH = Environment.getExternalStorageDirectory().getPath()+"/expression/" ;

    /**
     *
     * @param type 表情类型
     * */
    public static ArrayList<byte[]> getEmojBitmap(HashMap<String,byte[]>bitmapMap, String type){

        String[] emojNames = getEmojName(type) ;
        if(emojNames == null){

            return null ;
        }
        int length = emojNames.length ;
        if(length == 0){

            return null;
        }

        ArrayList<byte[]> bitmaps = new ArrayList<>() ;

        for (int i = 0 ; i < length ; i++){

            byte[] bytes = getBitmap(bitmapMap,emojNames[i].trim()) ;
            if(bytes == null){

                return null ;
            }

            bitmaps.add(bytes) ;
        }
        return bitmaps ;
    }

    /***/
    private static String[] getEmojName(String type){

        StringBuffer buffer = new StringBuffer() ;

        String filePath = EMJO_ROOT_PATH + type + ".emoj";

        try {

            InputStream is = new FileInputStream(filePath);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString().split(",");
    }


    private static byte[] getBitmap(HashMap<String,byte[]>bitmapMap, String fileName){

        byte[] bitmap = bitmapMap.get(fileName) ;

        if(bitmap == null){

            String path = EMJO_ROOT_PATH + fileName ;

            bitmap = readFile(path) ;
            bitmapMap.put(fileName,bitmap) ;
        }

        return bitmap ;
    }





    private static byte[] readFile(String path){

        try {

            File file = new File(path) ;
            int length = (int) file.length();

            if(length < 0 ||length > 1024*1024){

                Log.e("AppUtils","图片不合适") ;
                return  null ;
            }

            byte[] bs = new byte[length] ;

            FileInputStream fileInputStream = new FileInputStream(path) ;
            fileInputStream.read(bs) ;

            fileInputStream.close();

            return bs ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null ;
    }


}
