package com.yongyida.robot.lockscreen;

import android.app.Application;


public class ApplicationHelper extends Application{
	
	//表情开关: 0为开，1为关
    public static int facesSwitch = 0;
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
