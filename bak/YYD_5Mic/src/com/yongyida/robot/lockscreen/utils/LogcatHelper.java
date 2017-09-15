package com.yongyida.robot.lockscreen.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yongyida.robot.lockscreen.contant.Contant;

import android.content.Context;

/**
 * log日志统计保存
 */
public class LogcatHelper {

	private static LogcatHelper INSTANCE = null;
	private static String PATH_LOGCAT;
	private LogDumper mLogDumper = null;
	private int mPId;
	public static boolean LOG_RECORDING = false;
	/**
	 * 
	 * 初始化目录
	 * 
	 */
	public void init(Context context) {
		// 路径： /data/data/包名/files/
		PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "logs"; // 文件夹
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static LogcatHelper getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LogcatHelper(context);
		}
		return INSTANCE;
	}

	private LogcatHelper(Context context) {
		init(context);
		mPId = android.os.Process.myPid();
	}

	public void start() {
		if (mLogDumper == null)
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
		mLogDumper.start();
		LOG_RECORDING = true;
	}

	public void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
		LOG_RECORDING = false;
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private String mDir;
		private FileOutputStream out = null;
		private File file;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			mDir = pid;
			file = new File(dir, "lockScreen_debug.log");   // 文件名
			
			try {
				out = new FileOutputStream(file, true);
			} catch (IOException e) {
			}

			if (isFileOver2M(file.getAbsolutePath())) {
				if (file.exists()) {
					if (file.isFile()) {
						file.delete();
					}
				}
			}

			/**
			 * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
			 * 显示当前mPID程序的 E和W等级的日志.
			 */
			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat | grep \"(" + mPID + ")\"";//打印所有日志信息
			// cmds = "logcat -s way";//打印标签过滤信息
			cmds = "logcat *:e " + Contant.TAG +":d | grep \"(" + mPID + ")\""; // 记录级别E和D，D过滤TAG

		}

		/** 把超过2M的日志文件删除  */
		boolean isFileOver2M(String filePath) {
			File f = new File(filePath);
			// 大于2m
			if (f.exists() && f.length() > 1024*1024*2)
				return true;
			return false;
		}

		/** 停止记录Log */
		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((MyDate.getDateEN() + "  " + line + "\n").getBytes());
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}
	}
}

 
