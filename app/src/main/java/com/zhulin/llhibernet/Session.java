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
	 * 获取单例
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
	 * 获取事务
	 * 
	 * @return
	 */
	public ITransaction getTransaction() {
		return transaction;
	}

	/**
	 * 关闭数据库
	 */
	@Override
	public void close() {
		if (writableDatabase != null && writableDatabase.isOpen()) {
			writableDatabase.close();
		}
	}

	/**
	 * 开始事务
	 */
	@Override
	public void beginTransaction() {
		if (transaction != null) {
			transaction.begin();
		}
	}

	/**
	 * 数据库是否打开
	 */
	@Override
	public Boolean isOpen() {
		if (writableDatabase != null) {
			return writableDatabase.isOpen();
		}
		return false;
	}

	/**
	 * 保存数据
	 */
	@Override
	public void save(T t) {
        TableReflect reflect = new TableReflect(t);
        TableUnit tableUnit = reflect.getTableUnit();

        String execSql = "insert into " + tableUnit.getTableName() + " " + getTableUnitKey(tableUnit) + " values " + getTableUnitValue(tableUnit);
        execSQL(execSql);
    }

	/**
	 * 删除数据
	 */
	@Override
	public void delete(T t) {

	}

	/**
	 * 创建查询
	 */
	@Override
	public void createQuery() {

	}

	/**
	 * 更新数据
	 */
	@Override
	public void update(T t) {

	}

    /**
     * 执行sql
     * @param sql
     */
    private void execSQL(String sql) {
        if (writableDatabase != null) {
            writableDatabase.execSQL(sql);
        }
    }

    /**
     * 获取table的key
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
     *获取table的值
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
