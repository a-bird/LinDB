package com.zhulin.llhibernet;

import android.database.sqlite.SQLiteDatabase;

import com.zhulin.llhibernet.common.TableFieldUnit;
import com.zhulin.llhibernet.common.TableReflect;
import com.zhulin.llhibernet.common.TableUnit;
import com.zhulin.llhibernet.module.User;

import java.util.List;

public class Session<T> implements ISession<T> {

	private ISqliteDataBase dataBase;
	private ISession<T> session;
	private SQLiteDatabase writableDatabase;
	private ITransaction transaction;

	/**
	 * ��ȡ����
	 * 
	 * @param dataBase
	 * @return
	 */
	public ISession<T> initDabaBase(ISqliteDataBase dataBase) {
		if (session == null) {
			session = new Session<T>();
		}

		this.dataBase = dataBase;
		if (this.dataBase != null) {
			writableDatabase = this.dataBase.GetWritableDatabase();
		}
		transaction = new Transaction(dataBase);
		return session;
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public ITransaction getTransaction() {
		return transaction;
	}

	/**
	 * �ر����ݿ�
	 */
	@Override
	public void close() {
		if (writableDatabase != null && writableDatabase.isOpen()) {
			writableDatabase.close();
		}
	}

	/**
	 * ��ʼ����
	 */
	@Override
	public void beginTransaction() {
		if (transaction != null) {
			transaction.begin();
		}
	}

	/**
	 * ���ݿ��Ƿ��
	 */
	@Override
	public Boolean isOpen() {
		if (writableDatabase != null) {
			return writableDatabase.isOpen();
		}
		return false;
	}

	/**
	 * ��������
	 */
	@Override
	public void save(T t) {
        TableReflect reflect = new TableReflect(t);
        TableUnit tableUnit = reflect.getTableUnit();

        String execSql = "insert into " + tableUnit.getTableName() + " " + getTableUnitKey(tableUnit) + " values " + getTableUnitValue(tableUnit);
        execSQL(execSql);
    }

	/**
	 * ɾ������
	 */
	@Override
	public void delete(T t) {

	}

	/**
	 * ������ѯ
	 */
	@Override
	public void createQuery() {

	}

	/**
	 * ��������
	 */
	@Override
	public void update(T t) {

	}

    /**
     * ִ��sql
     * @param sql
     */
    private void execSQL(String sql) {
        if (writableDatabase != null) {
            writableDatabase.execSQL(sql);
        }
    }

    /**
     * ��ȡtable��key
     * @return
     */
    private String getTableUnitKey(TableUnit unit) {
        String retStr = "";

        List<TableFieldUnit> lit = unit.getLitField();
        for (TableFieldUnit fieldUnit : lit){
            retStr += fieldUnit.getFieldName() + ",";
        }

        return "(" + retStr.substring(0,retStr.length() - 1) + ")";
    }

    /**
     *��ȡtable��ֵ
     * @return
     */
    private String getTableUnitValue(TableUnit unit) {
        String retStr = "";

        List<TableFieldUnit> lit = unit.getLitField();
        for (TableFieldUnit fieldUnit : lit){
            fieldUnit.getFieldType();
            retStr += changeType(fieldUnit.getFieldValue(),fieldUnit.getFieldType()) + ",";
        }

        return "(" + retStr.substring(0,retStr.length() - 1) + ")";
    }

    /**
     *
     * @return
     */
    private String changeType(Object obj,String type) {
        String retStr = "";

        switch (type) {
            case "text":
                retStr = "'" + obj + "'";
            break;

            case "int":
                retStr = String.valueOf(obj);
                break;
        }

        return retStr;
    }

}
