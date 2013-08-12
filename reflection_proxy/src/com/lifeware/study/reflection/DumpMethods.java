package com.lifeware.study.reflection;
import java.lang.reflect.Method;

public class DumpMethods {
	//�������н���һ���ַ�����ĳ�����ȫ�ƣ�������ӡ������ķ���������
	public static void main(String[] args){
		try {
			//Class��java�������ڵ�
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
