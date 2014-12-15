package com.zhulin.llhibernet.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ݿ��ķ���
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
     * ��ʼ��class
     *
     * @return
     */
    private void InitClass() {
        //new and reflect
        tableUnit = new TableUnit();
        reflectFieldUnit();
    }

    /**
     * �����ʵ��
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
     * ת�����ֶε�ʵ��
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

        int length = 0;// �ֶγ���
        String fieldName = field.getName();// �ֶ�����
        Type type = field.getGenericType();
        String typeName = type.toString().replace(" ", "").replace("class", "")
                .replace("java.lang.", "");// �ֶ����� ��Ҫת��
        boolean primaryKey = tableField.primaryKey();

        //��ȡ�ֶε�Get����
        String getMethodName = "get" + toFirstLetterUpperCase(fieldName);

        // ��ע���ֶ���Ϊ�� ���ȡ������
        if (!"".equals(tableField.key())) {
            fieldName = tableField.key();
        }
        // ����ֵ
        length = tableField.length();
        if (tableField.length() == 0) {
            length = 20;
        }
        // ������������
        if (!"".equals(tableField.type())) {
            typeName = tableField.type();
        }
        String fieldType = getSqliteType(typeName);

        //��ȡ�ֶε�ֵ
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

        //��ֵ�ɶ���
        unit = new TableFieldUnit();
        unit.setFieldName(fieldName);
        unit.setFieldType(fieldType);
        unit.setFieldLength(length);
        unit.setPrimartKey(primaryKey);
        unit.setFieldValue(obj);

        return unit;
    }


    /**
     * ת�������ݿ��ֶ���������
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
     * ��һ����ĸ��д
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