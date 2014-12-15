package com.zhulin.llhibernet.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表的放射
 *
 * @param <T>
 * @author zhulin
 */
public class TableReflect<T> {
    private static String PACK_NAME = "com.zhulin.llhibernet.module";

    private String className;
    private TableUnit tableUnit;
    private Class<?> clazz;
    private T t;

    public TableReflect(String className) {
        this.className = className;

        try {
            clazz = Class.forName(PACK_NAME + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        InitClass();
    }

    public TableReflect(T t) {
        this.t = t;
        clazz = t.getClass();
        InitClass();
    }

    public String getClassName() {
        return className;
    }

    public TableUnit getTableUnit() {
        return tableUnit;
    }

    /**
     * 初始化class
     *
     * @return
     */
    private void InitClass() {
        //new and reflect
        tableUnit = new TableUnit();
        reflectFieldUnit();
    }

    /**
     * 反射出实体
     */
    private void reflectFieldUnit() {
        List<TableFieldUnit> litField = new ArrayList<TableFieldUnit>();

        if (clazz == null) {
            return;
        }

        Table table = clazz.getAnnotation(Table.class);
        tableUnit.setTableName(table.tableName());
        if ("".equals(tableUnit.getTableName())) {
            return;
        }

        Field[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            TableFieldUnit unit = getTableFieldUnit(field);
            if (unit != null) {
                litField.add(unit);
            }
        }

        tableUnit.setLitField(litField);
    }


    /**
     * 转换成字段的实体
     *
     * @param
     * @return
     */
    private TableFieldUnit getTableFieldUnit(Field field) {
        TableFieldUnit unit = null;

        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField == null) {
            return unit;
        }

        int length = 0;// 字段长度
        String fieldName = field.getName();// 字段名称
        Type type = field.getGenericType();
        String typeName = type.toString().replace(" ", "").replace("class", "")
                .replace("java.lang.", "");// 字段类型 需要转换
        boolean primaryKey = tableField.primaryKey();

        //获取字段的Get方法
        String getMethodName = "get" + toFirstLetterUpperCase(fieldName);

        // 若注解字段名为空 则获取属性名
        if (!"".equals(tableField.key())) {
            fieldName = tableField.key();
        }
        // 设置值
        length = tableField.length();
        if (tableField.length() == 0) {
            length = 20;
        }
        // 设置数据类型
        if (!"".equals(tableField.type())) {
            typeName = tableField.type();
        }
        String fieldType = getSqliteType(typeName);

        //获取字段的值
        Object obj = null;
        if (t != null)
        {
            try {
                obj = clazz.getMethod(getMethodName).invoke(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //赋值成对象
        unit = new TableFieldUnit();
        unit.setFieldName(fieldName);
        unit.setFieldType(fieldType);
        unit.setFieldLength(length);
        unit.setPrimartKey(primaryKey);
        unit.setFieldValue(obj);

        return unit;
    }


    /**
     * 转换成数据库字段数据类型
     *
     * @param type
     * @return
     */
    private String getSqliteType(String type) {
        String ret = "";

        switch (type) {

            case "String":
                ret = "text";
                break;

            case "int":
                ret = "int";
                break;

            case "boolean":
                ret = "int";
                break;

            case "Date":
                ret = "datetime";
                break;

            default:
                ret = "text";
                break;
        }

        return ret;
    }

    /**
     * 第一个字母大写
     * @param str
     * @return
     */
    public static String toFirstLetterUpperCase(String str) {
        if(str == null || str.length() < 2){
            return str;
        }
        String firstLetter = str.substring(0, 1).toUpperCase();
        return firstLetter + str.substring(1, str.length());
    }

}