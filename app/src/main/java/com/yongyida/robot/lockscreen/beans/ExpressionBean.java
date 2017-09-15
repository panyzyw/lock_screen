package com.yongyida.robot.lockscreen.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruiqianqi on 2017/3/27 0027.
 */

public class ExpressionBean extends BaseInfo {

    public ExpressionBean(){
        mServiceType = "chat";
    }

    // 语义
    @SerializedName("semantic")
    public Semantic semantic;

    // 定义语义
    public class Semantic {
        // 没有对应的值

        // 有对应的值
        @SerializedName("slots")
        public Slots slots;
    }

    // 定义服务器的返回
    public class Slots {

        // 要执行的表情动作
        @SerializedName("action")
        public String action;

        // 表情对应的回答
        @SerializedName("answer")
        public String answer;
    }
}
