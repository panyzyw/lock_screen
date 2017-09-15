package com.yongyida.robot.lockscreen.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.yongyida.robot.lockscreen.contant.Contant;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputMethodUtils {
	// 隐藏虚拟键盘
	public static void HideKeyboard(Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//		if (imm.isActive()) {
			Contant.showTips("close InputMethod ");
			imm.hideSoftInputFromWindow(null, 0);
//		}
	}
	// 切换虚拟键盘的状态
	public static void switchKeyBoard(Context mContext) {
		// 1.得到InputMethodManager对象
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 2.调用toggleSoftInput方法，实现切换显示软键盘的功能。
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	// 判断输入法是否是激活状态
	public static boolean isActive(Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();// isOpen若返回true，则表示输入法打开
	}

	// 显示虚拟键盘
	public static void ShowKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

	}

	// 强制显示或者关闭系统键盘
	public static void KeyBoard(final EditText txtSearchKey, final String status) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager m = (InputMethodManager) txtSearchKey.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (status.equals("open")) {
					m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
				} else {
					m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
				}
			}
		}, 300);
	}

	// 通过定时器强制隐藏虚拟键盘
	public static void TimerHideKeyboard(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}
			}
		}, 10);
	}

	// 判断某一个界面上输入法是否是激活状态
	public static boolean KeyBoard(EditText edittext) {
		boolean bool = false;
		InputMethodManager imm = (InputMethodManager) edittext.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			bool = true;
		}
		return bool;

	}

}
