package com.mifa.cloud.voice.server.component;

import java.util.Random;

public class RandomSort {
	public static StringBuffer GeneratePassword(int length) {
		String randStr = "0123456789abcdefghijklmnopqrstuvwxyz"; // 写入你所希望的所有的字母A-Z,a-z,0-9
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = length; // 接收需要生成随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(36);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return generateRandStr; // 返回生成的随机数
	}
	
	
	public static String getToken() {
		String randStr = "0123456789abcdefghijklmnopqrstuvwxyz"; // 写入你所希望的所有的字母A-Z,a-z,0-9
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = 32; // 接收需要生成随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(36);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return generateRandStr.toString().toUpperCase(); // 返回生成的随机数
	}

	public static Long generateRandomNum(int length) {
		String randStr = "123456789";
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = length; // 接收需要生成随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(9);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return Long.valueOf(generateRandStr.toString()); // 返回生成的随机数
	}
	
	/*
	 *生成指定位数的数字字符串 
	 */
	public static String generateConfirmStr(String str) {
		int a = str.length();
		int b= 6 - a;
		for(int i=0;i<=b-1;i++) {
			str = "0" + str;
		}
		return str;
		
	}

	public static String genereateFourRandomNum() {
		String string = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			string += random.nextInt(10);
		}
		return string;
	}

}
