package com.navyliu.eventbus.bus;

/**
 * Created by Administrator on 2018-04-25.
 *
 * @auther navyLiu
 * @Email navyliu666666@gmail.com
 */

public class Subscription {

	SubscribeMethod subscribeMethod;
	Object subscribe;

	public SubscribeMethod getSubscribeMethod() {
		return subscribeMethod;
	}

	public void setSubscribeMethod(SubscribeMethod subscribeMethod) {
		this.subscribeMethod = subscribeMethod;
	}

	public Object getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Object subscribe) {
		this.subscribe = subscribe;
	}

	public Subscription(SubscribeMethod subscribeMethod, Object subscribe) {
		this.subscribeMethod = subscribeMethod;
		this.subscribe = subscribe;
	}
}
