package com.navyliu.eventbus.bus;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-04-25.
 *
 * @auther navyLiu
 * @Email navyliu666666@gmail.com
 */

public class DNBus {

	private Map<Class, List<SubscribeMethod>> METHOD_CACHE = new HashMap<>();
	private Map<String, List<Subscription>> SUBSCRIBES = new HashMap<>();
	private Map<Class, List<String>> REGISTER = new HashMap<>();
	private static volatile DNBus intance;

	private DNBus() {
	}

	public static DNBus getDefault() {
		if (null == intance) {
			synchronized (DNBus.class) {
				if (null == intance) {
					intance = new DNBus();
				}
			}
		}
		return intance;
	}

	public void clear(){
		METHOD_CACHE.clear();
		SUBSCRIBES.clear();
		REGISTER.clear();
	}

	/**
	 * 注册
	 *
	 * @param object
	 */
	public void register(Object object) {
		Class<?> subscribeClass = object.getClass();
		// 找到 对于类中所有被 Subscribe 什么的函数
		// 将其 method、label、执行函数需要的参数类型数组缓存
		List<SubscribeMethod> subscribes = findSubscribe(subscribeClass);

		// 为了方便注销
		List<String> labels = REGISTER.get(subscribeClass);
		if (null == labels){
			labels = new ArrayList<>();
			REGISTER.put(subscribeClass, labels);
		}

		// 执行表的制作
		for (SubscribeMethod subscribeMethod : subscribes) {
			// 拿到标签
			String label = subscribeMethod.getLabel();
			if (!labels.contains(label)){
				labels.add(label);
			}
			// 执行表数据是否存在
			List<Subscription> subscriptions = SUBSCRIBES.get(label);
			if (null == subscriptions) {
				subscriptions = new ArrayList<>();
				SUBSCRIBES.put(label, subscriptions);
			}
			subscriptions.add(new Subscription(subscribeMethod, object));
		}
	}


	private List<SubscribeMethod> findSubscribe(Class<?> subscribeClass) {
		// 先看缓存中是否有
		List<SubscribeMethod> subscribeMethods = METHOD_CACHE.get(subscribeClass);
		if (null == subscribeMethods) {
			subscribeMethods = new ArrayList<>();
			// 获得methos
			Method[] methods = subscribeClass.getDeclaredMethods();
			for (Method method : methods) {
				Subscribe subscribe = method.getAnnotation(Subscribe.class);
				if (null != subscribe) {
					String[] values = subscribe.value();
					// 函数的参数类型
					Class<?>[] parameterTypes = method.getParameterTypes();
					for (String value : values) {
						// 设置权限 方法是private时有用
						method.setAccessible(true);
						subscribeMethods.add(new SubscribeMethod(value, method, parameterTypes));
					}
				}
			}
			METHOD_CACHE.put(subscribeClass, subscribeMethods);
			return subscribeMethods;
		}
		return subscribeMethods;
	}


	/**
	 * 发送时间给订阅者
	 *
	 * @param label
	 * @param params
	 */
	public void post(String label, Object... params) {
		// 获得所有对应的订阅者
		List<Subscription> subscriptions = SUBSCRIBES.get(label);
		if (null == subscriptions) {
			return;
		}
		for (Subscription subscription : subscriptions) {
			SubscribeMethod subscribeMethod = subscription.getSubscribeMethod();
			// 执行函数需要的参数类型数组
			Class[] paramterClass = subscribeMethod.getParamterClass();
			// 真实的参数
			Object[] realParams = new Object[paramterClass.length];
			if (null != params) {
				for (int i = 0; i < paramterClass.length; i++) {
					// 传进来的参数 类型是method需要的类型
					if (i < params.length && paramterClass[i].isInstance(params[i])) {
						realParams[i] = params[i];
					} else {
						realParams[i] = null;
					}
				}
			}
			try {
				subscribeMethod.getMethod().invoke(subscription.getSubscribe(), realParams);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}


	public void unregister(Object object){
		// 拿到对应对象的所有注册的标签
		List<String> labels = REGISTER.get(object.getClass());
		if (null != labels){
			for (String label : labels){
				// 获得执行表中对应label的所有函数
				List<Subscription> subscriptions = SUBSCRIBES.get(label);
				if (null != subscriptions){
					Iterator<Subscription> iterator = subscriptions.iterator(); // 迭代器
					while (iterator.hasNext()){
						Subscription subscription = iterator.next();
						// 对象是同一个才删除
						if (subscription.getSubscribe() == object){
							iterator.remove();
						}
					}
				}
			}
		}
	}

}
