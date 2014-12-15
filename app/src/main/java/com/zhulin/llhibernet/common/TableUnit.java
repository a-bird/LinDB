package com.zhulin.llhibernet.common;

import java.util.List;

/**
 * Created by zhulin on 2014/12/11.
 */
public class TableUnit {
    private String tableName;
    private List<TableFieldUnit> litField;

    public String getTableName() {
        return tableName;
    }

    public List<TableFieldUnit> getLitField() {
        return litField;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setLitField(List<TableFieldUnit> litField) {
        this.litField = litField;
    }
}
