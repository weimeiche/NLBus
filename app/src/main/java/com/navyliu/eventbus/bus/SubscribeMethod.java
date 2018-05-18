package com.navyliu.eventbus.bus;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018-04-25.
 *
 * @auther navyLiu
 * @Email navyliu666666@gmail.com
 */

public class SubscribeMethod {

	// 标签
	private String label;
	// 方法
	private Method method;
	// 参数的类型
	private Class[] paramterClass;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class[] getParamterClass() {
		return paramterClass;
	}

	public void setParamterClass(Class[] paramterClass) {
		this.paramterClass = paramterClass;
	}

	public SubscribeMethod(String label, Method method, Class[] paramterClass) {
		this.label = label;
		this.method = method;
		this.paramterClass = paramterClass;
	}
}
