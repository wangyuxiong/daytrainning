package com.lifeware.study.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

	/**
	 * 1. 实现InvocationHandler接口创建自己的调用处理器
	 *    InvocationHandler handler = new InvocationHandlerImpl(server);
	 * 2. 通过Proxy指定ClassLoader对象和一组interface创建动态代理类
	 *    Class clazz = Proxy.getProxyClass(classLoader,new class[]{...})
	 * 3. 通过反射机制获取动态代理类的构造函数，其参数类型是调用处理器接口类型:
	 *    Constructor constructor  = clazz.getConstructor(new Class[]{InvocationHandler.class})
	 * 4. 通过构造函数创建动态代理类实例，将调用处理器对象作为参数被传入
	 *    Interface proxy = constructor.newInstance(new Object[]{handler})
	 *    
	 * Proxy中newProxyInstance方法已经封装了步骤2~4，实例如下：
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

//定义一个接口协议
interface CalculatorProtocol{
	public int add(int a,int b);
	public int subtract(int a,int b);
}

//实现接口协议
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
		//可加入预处理
		Object result = method.invoke(this.objOriginal, args);
		return result;
				
	}
	
}

