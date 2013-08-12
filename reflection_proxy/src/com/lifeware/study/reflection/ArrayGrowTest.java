package com.lifeware.study.reflection;

import java.lang.reflect.Array;

public class ArrayGrowTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,2,3};
		a = (int[])goodArrayGrow(a);
		arrayPrint(a);
		
		String[] b = {"aaa","bbb","ccc"};
		b = (String[])goodArrayGrow(b);
		arrayPrint(b);
		
		//b = (String[])badArrayGrow(b);
	}
	
	public static Object goodArrayGrow(Object a){
		Class<?> c1 = a.getClass();
		if(!c1.isArray()){
			return null;
		}
		Class<?> componentType = c1.getComponentType();
		int length = Array.getLength(a);
		int newLength = length * 11/10 + 10;
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(a, 0, newArray, 0, length);
		
		return newArray;
	}
	
	public static Object[] badArrayGrow(Object[] a){
		int length = Array.getLength(a);
		int newLength = length * 11/10 + 10;
		Object[] newArray = new Object[newLength];
		System.arraycopy(a, 0, newArray, 0, a.length);
		
		return newArray;
	}
	
	public static void arrayPrint(Object a){
		Class<?> c1 = a.getClass();
		if(!c1.isArray()){
			return;
		}
		Class<?> componentType = c1.getComponentType();
		int length = Array.getLength(a);
		System.out.print(componentType.getName() +"[" + length + "] = {");
		for(int i=0;i<length;i++){
			System.out.print(Array.get(a, i)+" ");
		}
		System.out.print(" }");
		
	}

}
