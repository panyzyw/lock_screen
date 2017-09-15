package com.yongyida.robot.lockscreen.contant;

import com.yongyida.robot.lockscreen.R;

import android.util.Log;

public class Contant {
	public static boolean DEBUG = true;
	public static String TAG = "xzh";

	public static void showTips(String msg) {
		if (DEBUG)
			Log.d(TAG, msg);
	}
	//1.定义表情和表情图片序列
	//2.注册图片序列和表情
	//3.新增调用方式
	//4.注册表情调用方式
	public final static int MOTION_AFRAID = 0;
	public final static int MOTION_ANGRY = 1;
	public final static int MOTION_CRY = 2;
	public final static int MOTION_HUNGRY = 3;
	public final static int MOTION_JIAYOU = 4;
	public final static int MOTION_KU = 5;
	public final static int MOTION_LAUGH = 6;
	public final static int MOTION_LEARN = 7;
	public final static int MOTION_MENG = 8;
	public final static int MOTION_NORMAL = 9;
	public final static int MOTION_QINQIN = 10;
	public final static int MOTION_SLEEP = 11;
	public final static int MOTION_SPEAK = 12;
	public final static int MOTION_YUN = 13;
	public final static int MOTION_LISTENR = 14;
	public final static int MOTION_STOP = 15;
	
	public final static int MOTION_TEST=16; //1
	
	public final static int[] PIC_AFRAID={R.raw.afraid0,R.raw.afraid1,R.raw.afraid0,R.raw.afraid1,R.raw.afraid0,R.raw.afraid1,R.raw.afraid0,R.raw.afraid1,R.raw.afraid0,R.raw.afraid1,R.raw.normal10};
	public final static int[] PIC_ANGRY={R.raw.angry1,R.raw.angry2,R.raw.angry3,R.raw.angry4,R.raw.angry5,R.raw.angry6,R.raw.angry1,R.raw.angry2,R.raw.angry3,R.raw.angry4,R.raw.angry5,R.raw.angry6,R.raw.normal10};
	public final static int[] PIC_CRY={R.raw.cry1,R.raw.cry2,R.raw.cry3,R.raw.cry1,R.raw.cry2,R.raw.cry3,R.raw.cry1,R.raw.cry2,R.raw.cry3,R.raw.cry1,R.raw.cry2,R.raw.cry3,R.raw.normal10};
	public final static int[] PIC_HUNGRY={R.raw.hungry0,R.raw.hungry1,R.raw.hungry2,R.raw.hungry4,R.raw.hungry5,R.raw.hungry6,R.raw.hungry7,R.raw.hungry8,R.raw.hungry9,R.raw.hungry10,R.raw.hungry11,R.raw.hungry12,R.raw.normal10};
	public final static int[] PIC_JIAYOU={R.raw.jiayou0,R.raw.jiayou1,R.raw.jiayou2,R.raw.jiayou3,R.raw.jiayou4,R.raw.jiayou5,R.raw.jiayou6,R.raw.jiayou7,R.raw.normal10};
	public final static int[] PIC_KU={R.raw.ku0,R.raw.ku1,R.raw.ku3,R.raw.ku4,R.raw.ku5,R.raw.ku6,R.raw.ku7,R.raw.ku8,R.raw.ku9,R.raw.ku10,R.raw.ku11,R.raw.ku12,R.raw.normal10};
	public final static int[] PIC_LAUGH={R.raw.laugh1,R.raw.laugh2,R.raw.laugh3,R.raw.laugh4,R.raw.laugh5,R.raw.laugh6,R.raw.laugh7,R.raw.normal10};
	public final static int[] PIC_LEARN={R.raw.learn0,R.raw.learn1,R.raw.learn2,R.raw.learn3,R.raw.learn4,R.raw.learn0,R.raw.learn1,R.raw.learn2,R.raw.learn3,R.raw.learn4,R.raw.normal10};
	public final static int[] PIC_MENG={R.raw.meng1,R.raw.meng2,R.raw.meng3,R.raw.meng4,R.raw.meng1,R.raw.meng2,R.raw.meng3,R.raw.meng4,R.raw.normal10};
	public final static int[] PIC_NORMAL={R.raw.normal1,R.raw.normal2,R.raw.normal3,R.raw.normal4,R.raw.normal5,R.raw.normal6,R.raw.normal7,R.raw.normal8,R.raw.normal9,R.raw.normal10,R.raw.normal10};
	public final static int[] PIC_QINQIN={R.raw.qinqin0,R.raw.qinqin1,R.raw.qinqin2,R.raw.qinqin3,R.raw.qinqin4,R.raw.qinqin5,R.raw.qinqin6,R.raw.qinqin7,R.raw.qinqin8,R.raw.qinqin9,R.raw.qinqin10,R.raw.qinqin11,R.raw.qinqin12,R.raw.qinqin13,R.raw.normal10};
	public final static int[] PIC_SLEEP={R.raw.sleep0,R.raw.sleep1,R.raw.sleep2,R.raw.sleep3,R.raw.sleep4,R.raw.sleep5,R.raw.sleep6,R.raw.sleep7,R.raw.sleep8,R.raw.sleep9,R.raw.sleep10,R.raw.sleep11,R.raw.sleep12,R.raw.sleep13,R.raw.normal10};
	public final static int[] PIC_SPEAK={R.raw.speak0,R.raw.speak1,R.raw.speak2,R.raw.speak3,R.raw.speak4,R.raw.speak5,R.raw.speak6,R.raw.speak7,R.raw.speak8,R.raw.speak9,R.raw.speak10,R.raw.speak11,R.raw.normal10};
	public final static int[] PIC_YUN={R.raw.yun1,R.raw.yun2,R.raw.yun3,R.raw.yun4,R.raw.yun5,R.raw.yun6,R.raw.yun7,R.raw.yun8,R.raw.yun9,R.raw.yun10,R.raw.yun11,R.raw.yun12,R.raw.normal10};
	public final static int[] PIC_LISTENER = {R.raw.listen0,R.raw.listen1,R.raw.listen3,R.raw.listen4,R.raw.listen5,R.raw.listen6,R.raw.listen7,R.raw.listen8,R.raw.listen9,R.raw.listen10,R.raw.listen11,R.raw.listen12,R.raw.listen13,R.raw.listen14,R.raw.listen15,R.raw.listen16,R.raw.listen17,R.raw.listen18,R.raw.listen19,R.raw.listen20,R.raw.listen21,R.raw.listen22,R.raw.listen23,R.raw.listen24,R.raw.listen25,R.raw.listen26,R.raw.listen27,R.raw.normal10};
	public final static int[] PIC_STOP = {R.raw.normal10};
	public final static int[][] PICS={PIC_AFRAID,PIC_ANGRY,PIC_CRY,PIC_HUNGRY,PIC_JIAYOU,PIC_KU,PIC_LAUGH,PIC_LEARN,PIC_MENG,PIC_NORMAL,PIC_QINQIN,PIC_SLEEP,PIC_SPEAK,PIC_YUN,PIC_LISTENER,PIC_STOP};//2
	
	//表情定义数组
	public static int[] MOTIONS = { MOTION_AFRAID, MOTION_ANGRY, MOTION_CRY, MOTION_HUNGRY, MOTION_JIAYOU, MOTION_KU,
			MOTION_LAUGH, MOTION_LEARN, MOTION_MENG, MOTION_NORMAL, MOTION_QINQIN, MOTION_SLEEP, MOTION_SPEAK,
			MOTION_YUN,MOTION_LISTENR,MOTION_STOP};//2
	public static String[] ACTIONS = { Contant.ACTION_AFRAID, Contant.ACTION_ANGRY, Contant.ACTION_CRY,
			Contant.ACTION_HUNGRY, Contant.ACTION_JIAYOU, Contant.ACTION_KU, Contant.ACTION_LAUGH, Contant.ACTION_LEARN,
			Contant.ACTION_MENG, Contant.ACTION_NORMAL, Contant.ACTION_QINQIN, Contant.ACTION_SLEEP,
			Contant.ACTION_SPEAK, Contant.ACTION_YUN,Contant.ACTION_LISTENER,Contant.ACTION_STOP};//4

	public final static String ACTION_LOCKSCREEN = "com.yongyida.action.lockscreen.ACTION_LOCKSCREEN";
	public final static String ACTION_UNLOCKSCREEN = "com.yongyida.action.lockscreen.ACTION_UNLOCKSCREEN";

	public final static String ACTION_AFRAID = "com.yongyida.action.lockscreen.ACTION_AFRAID";
	public final static String ACTION_LAUGH = "com.yongyida.action.lockscreen.ACTION_LAUGH";
	public final static String ACTION_ANGRY = "com.yongyida.action.lockscreen.ACTION_ANGRY";
	public final static String ACTION_CRY = "com.yongyida.action.lockscreen.ACTION_CRY";
	public final static String ACTION_HUNGRY = "com.yongyida.action.lockscreen.ACTION_HUNGRY";
	public final static String ACTION_JIAYOU = "com.yongyida.action.lockscreen.ACTION_JIAYOU";
	public final static String ACTION_KU = "com.yongyida.action.lockscreen.ACTION_KU";
	public final static String ACTION_LEARN = "com.yongyida.action.lockscreen.ACTION_LEARN";
	public final static String ACTION_MENG = "com.yongyida.action.lockscreen.ACTION_MENG";
	public final static String ACTION_NORMAL = "com.yongyida.action.lockscreen.ACTION_NORMAL";
	public final static String ACTION_QINQIN = "com.yongyida.action.lockscreen.ACTION_QINQIN";
	public final static String ACTION_SLEEP = "com.yongyida.action.lockscreen.ACTION_SLEEP";
	public final static String ACTION_QUITE = "com.yongyida.action.lockscreen.ACTION_QUITE";
	public final static String ACTION_SPEAK = "com.yongyida.action.lockscreen.ACTION_SPEAK";
	public final static String ACTION_YUN = "com.yongyida.action.lockscreen.ACTION_YUN";
	public final static String ACTION_LISTENER = "com.yongyida.robot.voice.VOICE_UNDERSTAND";
	public final static String ACTION_STOP = "TouchSensor";
}
