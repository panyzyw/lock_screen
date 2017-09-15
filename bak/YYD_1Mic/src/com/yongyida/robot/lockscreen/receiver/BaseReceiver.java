/**
 * @filename 文件名：BaseReceiver.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-19
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yongyida.robot.lockscreen.common.ConstKey;
import com.yongyida.robot.lockscreen.common.RobotFunctionType;
import com.yongyida.robot.lockscreen.service.RobotActionService;

/**
 * @filename 文件名：BaseReceiver.java
 * @description 描    述：本应用的广播接收者基类
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-19
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public abstract class BaseReceiver extends BroadcastReceiver {

    /**
     * @method 方法名：doIntentByService
     * @features 功    能：启动Service处理接收到的广播Inetent
     * @params 参    数：@param context
     * @params 参    数：@param intent
     * @params 参    数：@param functionType 机器人功能类型
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    protected void doIntentByService(Context context, Intent intent, RobotFunctionType functionType, String from) {
        Intent mIntent = new Intent(context, RobotActionService.class);
        Bundle receiverBundle = new Bundle();
        receiverBundle.putParcelable(ConstKey.RECEIVER_INTENT, intent);
        mIntent.putExtra(ConstKey.INTENT_FUNCTION_TYPE, functionType.toString());
        mIntent.putExtra(ConstKey.RECEIVER_BUNDLE, receiverBundle);
        mIntent.putExtra(ConstKey.FROM, from);
        context.startService(mIntent);
    }
}
