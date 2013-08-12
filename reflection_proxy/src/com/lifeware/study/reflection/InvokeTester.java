package com.lifeware.study.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeTester {
	
	public int add(int a,int b){
		return a+b;
	}
	

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Class<?> classType = InvokeTester.class;
		Object obj = classType.newInstance();
		
		Method addMethod = classType.getMethod("add", new Class[]{int.class,int.class});
		Object result = addMethod.invoke(obj,new Object[]{100,200});
		System.out.println((Integer)result);
		System.out.println(Double[].class.getName());
	}

}
