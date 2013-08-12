package com.lifeware.study.reflection;
import java.lang.reflect.Method;

public class DumpMethods {
	//从命令行接受一个字符串（某个类的全称），并打印出该类的方法和声明
	public static void main(String[] args){
		try {
			//Class类java反射的入口点
			Class<?> classType = Class.forName(args[0]);
			Method[] methods = classType.getDeclaredMethods();
			for(Method method:methods){
				System.out.println(method);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
