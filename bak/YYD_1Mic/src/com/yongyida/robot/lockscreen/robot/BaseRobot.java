/**
 * @filename 文件名：BaseRobot.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-22
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.robot;

import android.content.Context;
import android.content.Intent;

/**
 * @filename 文件名：BaseRobot.java
 * @description 描    述：机器人基类
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-22
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public abstract class BaseRobot implements IAction {

    protected String myName;

    /**
     * //TODO 添加override说明
     * @see com.yongyida.robot.lockscreen.robot.IAction#execute(android.content.Context, android.content.Intent)
     **/
    @Override
    public void execute(Context ctx, Intent mIntent) {
        if (ctx == null || mIntent == null || mIntent.getAction() == null) {
            return;
        }
        //初始化数据
        initData(ctx, mIntent);
        //动作开始
        actionStart(ctx);

    }

    /**
     * @method 方法名：initData
     * @features 功    能：实例化数据，抽象方法，子类必须实现
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    protected abstract void initData(Context ctx, Intent mIntent);

    /**
     * @method 方法名：actionStart
     * @features 功    能：动作开始，抽象方法，子类必须实现
     * @params 参    数：
     * @return 返回值：void
     * @modify 修改者: Sergio Pan
     */
    protected abstract void actionStart(Context ctx);

    /**
     * @return Returns the myName.
     */
    public String getMyName() {
        return myName;
    }

    /**
     * @param myName The myName to set.
     */
    public void setMyName(String myName) {
        this.myName = myName;
    }

}
