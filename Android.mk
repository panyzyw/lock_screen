LOCAL_PATH:= $(call my-dir)

#ifeq ($(YYD_5MIC_SUPPORT), yes)
#	include $(LOCAL_PATH)/YYD_5Mic/Android.mk
#else
#	include $(LOCAL_PATH)/YYD_1Mic/Android.mk
#endif

include $(LOCAL_PATH)/APK/Android.mk