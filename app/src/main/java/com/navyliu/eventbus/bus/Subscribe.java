package com.navyliu.eventbus.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018-04-25.
 *
 * @auther navyLiu
 * @Email navyliu666666@gmail.com
 */

@Target(ElementType.METHOD)  // 可以在方法中使用
@Retention(RetentionPolicy.RUNTIME) // 发射
public @interface Subscribe {
	String[] value();
}
