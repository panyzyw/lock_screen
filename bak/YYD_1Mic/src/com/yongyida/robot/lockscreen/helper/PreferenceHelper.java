/**
 * 
 */
package com.yongyida.robot.lockscreen.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;


/**
 * @filename 文件名：PreferenceHelper.java
 * @description 描    述：SharePreference操作工具类
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-19
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public class PreferenceHelper {

    public static void write(Context context, String fileName, String k, int v) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write(Context context, String fileName, String k, long v) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putLong(k, v);
        editor.commit();
    }

    public static void write(Context context, String fileName, String k, boolean v) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }

    public static void write(Context context, String fileName, String k, String v) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }

    public static int readInt(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getInt(k, 0);
    }

    public static long readLong(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getLong(k, 0);
    }

    public static int readInt(Context context, String fileName, String k, int defv) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    public static boolean readBoolean(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context, String fileName, String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    public static String readString(Context context, String fileName, String k, String defV) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getString(k, defV);
    }
    
    //  存储List<String>的方法
    public static void writeStringList(Context context, String fileName, String k, List<String> list){
        SharedPreferences poreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor edit = poreferences.edit();
        try {
            edit.putString(k, list2String(list));
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    读取List<String>的方法
    public static List<String> readStringList(Context context, String fileName, String k) {
        SharedPreferences poreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        List<String> data = null;
        try {
            data = string2List(poreferences.getString(k, null));
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    private static List<String> string2List(String list) throws StreamCorruptedException, IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(list.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<String> SceneList = (List<String>) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }

    private static String list2String(List<String> list) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(list);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    public static void remove(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

    /**
     * 清除
     * @param cxt 上下文
     * @param fileName 文件名
     */
    public static void clean(Context cxt, String fileName) {
        SharedPreferences preference = cxt.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }
}
