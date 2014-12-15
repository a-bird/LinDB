package com.zhulin.llhibernet.common;

/**
 * Created by zhulin on 2014/12/11.
 */
public class TableFieldUnit {
    private String fieldName;
    private String fieldType;
    private int fieldLength;
    private boolean isPrimartKey;

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    private Object fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public boolean isPrimartKey() {
        return isPrimartKey;
    }

    public void setPrimartKey(boolean isPrimartKey) {
        this.isPrimartKey = isPrimartKey;
    }
}
