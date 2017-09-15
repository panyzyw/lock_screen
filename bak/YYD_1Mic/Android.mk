# Copyright (C) 2010 iFlytek
# 整个工程

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)


#LOCAL_STATIC_JAVA_LIBRARIES:=jarlib1
LOCAL_MODULE_TAGS := optional

#LOCAL_DEX_PREOPT := false

LOCAL_SRC_FILES := $(call all-subdir-java-files) 
LOCAL_PACKAGE_NAME := YYDRobotLockScreen
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := jarlib1:android-support-v4.jar

include $(BUILD_MULTI_PREBUILT)

