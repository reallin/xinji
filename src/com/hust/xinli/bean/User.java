package com.hust.xinli.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;

public class User implements Serializable{
	
	public static String username="";
	public static String password="";
	public static String cookie="";       //登录后的cookie
	public static String nickname;
	public static String signature;
	public static String birthday;
	public static boolean isBoy;
	public static boolean isLogin=false;
	
	public static List<String> scores7=new ArrayList<String>();                   //7分数
	public static int category;

	

}
