package com.zhulin.llhibernet.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)// �����ھֲ�����
@Retention(RetentionPolicy.RUNTIME)// ������ʱ��Ч��������ʱ����)
public @interface TableField {
	String key() default "";
	String type() default "";
	int length() default 1;
	boolean primaryKey() default false;
}
