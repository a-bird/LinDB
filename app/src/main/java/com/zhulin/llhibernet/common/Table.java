package com.zhulin.llhibernet.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//����������
@Retention(RetentionPolicy.RUNTIME)//������ʱ��Ч��������ʱ����)
public @interface Table {
	String tableName() default "";
}
