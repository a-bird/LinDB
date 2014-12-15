package com.zhulin.llhibernet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zhulin.llhibernet.common.TableCreateReflect;
import com.zhulin.llhibernet.config.SqliteConfigureInfo;

import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final int DATABASEVERSION = 3;// 数据库版本

	private static SqliteConfigureInfo configureInfo;
	private static SqliteHelper DB = null;

	/**
	 * 初始化 创建数据库
	 * 
	 * @param context
	 */
	private SqliteHelper(Context context) {
		super(context, configureInfo.dbName, null, DATABASEVERSION);

        Log.v("SqliteHelper","new");
	}

	/**
	 * 获取单例
	 * 
	 * @param context
	 * @param info
	 * @return
	 */
	public static SqliteHelper GetInstance(Context context,
			SqliteConfigureInfo info) {
		configureInfo = info;
		if (DB == null) {
			DB = new SqliteHelper(context);
		}
		return DB;
	}

	/**
	 * 获取可读取数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase GetReadableSQLiteDatabase() {
		return this.getReadableDatabase();
	}

	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.i("SqliteDB", "onCreate!");
		
		List<String> litClazz = configureInfo.tableClass;
		if (!litClazz.isEmpty()) {
			for (int j = 0; j < litClazz.size(); j++) {
                TableCreateReflect reflect = new TableCreateReflect(litClazz.get(j));
                String createSql = reflect.ReflectCreateSql();
                Log.v("create sql",createSql);
				arg0.execSQL(createSql);
			}
		}
	}

	/**
	 * 更新数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("SqliteDB", "onUpgrade!");

	}

	/**
	 * 打开数据库
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("SqliteDB", "onOpen!");
	}

}
