package com.yongyida.robot.lockscreen.brain.app;

import android.app.Application;
import android.content.Context;

import com.zccl.ruiqianqi.tools.config.MyConfigure;

/**
 * Created by ruiqianqi on 2017/5/17 0017.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        MyConfigure.init(this);
    }

    /**
     * 返回应用上下文
     * @return
     */
    public static Context getContext(){
        return instance.getApplicationContext();
    }

}
