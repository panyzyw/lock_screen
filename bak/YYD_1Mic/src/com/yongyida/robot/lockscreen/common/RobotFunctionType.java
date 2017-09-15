/**
 * @filename 文件名：RobotFunctionType.java
 * @description 描    述：TODO
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。。
 */
package com.yongyida.robot.lockscreen.common;

/**
 * @filename 文件名：RobotFunctionType.java
 * @description 描    述：机器人功能的枚举
 * @author 作    者：Sergio Pan
 * @date 时    间：2016-8-18
 * @Copyright 版    权：勇艺达机器人公司源代码，版权归勇艺达机器人公司所有。
 */
public enum RobotFunctionType {
    /***表情功能***/
    FACES("faces"),

    /***呼吸灯功能***/
    BREATHE_LED("breathe_led"),

    TYPE_END("/yydCmdEnd");// 虚假的接口名称，表示列表结束。

    private String functionType;

    private RobotFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public static RobotFunctionType getEnumFromString(String functionType) {
        if (functionType != null) {
            try {
                return Enum.valueOf(RobotFunctionType.class, functionType.trim());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }
}
