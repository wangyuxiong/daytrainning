package com.lifeware.study.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

	/**
	 * 1. ʵ��InvocationHandler�ӿڴ����Լ��ĵ��ô�����
	 *    InvocationHandler handler = new InvocationHandlerImpl(server);
	 * 2. ͨ��Proxyָ��ClassLoader�����һ��interface������̬������
	 *    Class clazz = Proxy.getProxyClass(classLoader,new class[]{...})
	 * 3. ͨ��������ƻ�ȡ��̬������Ĺ��캯��������������ǵ��ô������ӿ�����:
	 *    Constructor constructor  = clazz.getConstructor(new Class[]{InvocationHandler.class})
	 * 4. ͨ�����캯��������̬������ʵ���������ô�����������Ϊ����������
	 *    Interface proxy = constructor.newInstance(new Object[]{handler})
	 *    
	 * Proxy��newProxyInstance�����Ѿ���װ�˲���2~4��ʵ�����£�
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalculatorProtocol server = new Server();
		InvocationHandler handler = new CalculatorHandler(server);
		CalculatorProtocol client = (CalculatorProtocol)Proxy.newProxyInstance(server.getClass().getClassLoader(), 
				server.getClass().getInterfaces(), handler);
		int result = client.add(3, 2);
		System.out.println("3+2=" + result);
		result = client.subtract(5, 2);
		System.out.println("5-2=" + result);
	}
}

//����һ���ӿ�Э��
interface CalculatorProtocol{
	public int add(int a,int b);
	public int subtract(int a,int b);
}

//ʵ�ֽӿ�Э��
class Server implements CalculatorProtocol{
	public int add(int a,int b){
		return a+b;
	}
	
	public int subtract(int a,int b){
		return a-b;
	}
}

class CalculatorHandler implements InvocationHandler{
	private Object objOriginal;
	public CalculatorHandler(Object obj){
		this.objOriginal = obj;
	}
	
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		//�ɼ���Ԥ����
		Object result = method.invoke(this.objOriginal, args);
		return result;
				
	}
	
}

