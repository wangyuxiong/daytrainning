package com.lifeware.study.daytrainning.filesystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "/Users/metaboy/generatefile.txt";
		CreateFile(path);
	}
	
	public static void CreateFile(String path) throws IOException{
		FileWriter fw = new FileWriter(path);
		String line = "";
		for(int i =0;i<10000;i++){
			line = getCharacterAndNumber(100) + "\r\n";
			fw.write(line);
		}
		fw.write(line);
		fw.close();
	}
	
	//生成随机的字母和数字混杂的字符串
	//参数是生成的随机字符串的长度
	public static String getCharacterAndNumber(int length){
		String val = "";
		Random random = new Random();
		for(int i =0;i<length;i++){
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if("char".equals(charOrNum)){
				int choice = random.nextInt(2) % 2 == 0 ?65 :97;
				val += (char)(choice + random.nextInt(26));
			}
			else if("num".equals(charOrNum)){
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

}
