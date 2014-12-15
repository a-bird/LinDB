package com.zhulin.llhibernet.common;

import android.util.*;

import java.util.List;

/**
 * Created by zhulin on 2014/12/11.
 */
public class TableCreateReflect {
    private static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS %s(%s)";

    private TableUnit tableUnit;

    public TableCreateReflect(String tableName) {
        TableReflect tableReflect = new TableReflect(tableName);
        this.tableUnit = tableReflect.getTableUnit();
    }

    /**
     * 反射出sql语句
     *
     * @return
     */
    public String ReflectCreateSql() {
        String ret = "";

        if(tableUnit.getTableName() == null) {
            return ret;
        }

        // 主键sql
        String primaryKeySql = getPrimaryKeySql(tableUnit.getLitField());
        // 字段的sql
        String fieldSql = getFieldSql(tableUnit.getLitField());

        String tableFieldSql = "";
        if (!"".equals(primaryKeySql)) {
            tableFieldSql = fieldSql + primaryKeySql;
        } else {
            fieldSql = fieldSql.substring(0, fieldSql.length() - 1);
            tableFieldSql = fieldSql;
        }

        return String.format(CREATE_TABLE_SQL, tableUnit.getTableName(), tableFieldSql);
    }

    /**
     * 获取主键的sql
     *
     * @param
     * @return
     */
    private String getPrimaryKeySql(List<TableFieldUnit> litUnit) {
        String ret = "";

        // 拼接主键sql
        String keySql = "CONSTRAINT [] PRIMARY KEY (%s)";
        String strKeys = "";
        // 最后一个值 去掉逗号
        for (int i = 0; i < litUnit.size(); i++) {
            if (litUnit.get(i).isPrimartKey()) {
                if (i == litUnit.size() - 1) {
                    strKeys = litUnit.get(i).getFieldName();
                } else {
                    strKeys = litUnit.get(i).getFieldName() + ",";
                }
            }
        }

        if (!"".equals(strKeys)) {
            ret = String.format(keySql, strKeys);
        }

        return ret;
    }

    /**
     * 获取字段的sql
     *
     * @param litUnit
     * @return
     */
    private String getFieldSql(List<TableFieldUnit> litUnit) {
        String ret = "";

        for (int i = 0; i < litUnit.size(); i++) {
            TableFieldUnit unit = litUnit.get(i);
            if (unit != null) {
                ret += unit.getFieldName() + " " + unit.getFieldType() + "("
                        + unit.getFieldLength() + ")" + ",";
            }
        }

        return ret;
    }

}
