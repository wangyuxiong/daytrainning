package com.lifeware.study.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTetser {

	public Object copy(Object obj) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Class<?> classType = obj.getClass();
		System.out.println(classType.getName());
		Object objCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
	
		Field[] fields = classType.getDeclaredFields();
		for(Field field:fields){
			System.out.println(field.getName());
			String firstLetter = field.getName().substring(0,1).toUpperCase();
			String getMethodName = "get" + firstLetter + field.getName().substring(1);
			String setMethodName = "set" + firstLetter + field.getName().substring(1);
			
			Method getMethod = classType.getMethod(getMethodName, new Class[]{});
			Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});
			
			Object value = getMethod.invoke(obj, new Object[]{});
			setMethod.invoke(objCopy, new Object[]{value});
		}
		return objCopy;
	}
	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		Customer cus = new Customer();
		cus.setId(new Long(100));
		cus.setAge(new Long(50));
		cus.setName("zhangsan");
		Customer cuscopy = (Customer) new ReflectTetser().copy(cus);
		System.out.println(cuscopy.getId() + "," + cuscopy.getAge() + "," + cuscopy.getName());
	}

}

class Customer{
	private Long id;
	private Long age;
	private String name;
	
	public Customer(){
		
	}
	
	public Long getId(){
		return id;
	}
	
	public Long getAge(){
		return age;
	}
	
	public String getName(){
		return name;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public void setAge(Long age){
		this.age = age;
	}
	
	public void setName(String name){
		this.name = name;
	}
}