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
     * �����sql���
     *
     * @return
     */
    public String ReflectCreateSql() {
        String ret = "";

        if(tableUnit.getTableName() == null) {
            return ret;
        }

        // ����sql
        String primaryKeySql = getPrimaryKeySql(tableUnit.getLitField());
        // �ֶε�sql
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
     * ��ȡ������sql
     *
     * @param
     * @return
     */
    private String getPrimaryKeySql(List<TableFieldUnit> litUnit) {
        String ret = "";

        // ƴ������sql
        String keySql = "CONSTRAINT [] PRIMARY KEY (%s)";
        String strKeys = "";
        // ���һ��ֵ ȥ������
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
     * ��ȡ�ֶε�sql
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
