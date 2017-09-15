/**
 * @filename 文件名：IAction.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.robot;

import android.content.Context;
import android.content.Intent;

/**
 * @filename 文件名：IAction.java
 * @description 描    述：机器人动作接口，用于隔离
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public interface IAction {

    /**
     * @method 方法名：execute
     * @features 功    能：执行机器人功能的方法
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void execute(Context ctx, Intent mIntent);
    
    /**
     * @method 方法名：stop
     * @features 功    能：机器人停止动作的方法
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    public void stop();

}
