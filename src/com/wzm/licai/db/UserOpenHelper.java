package com.wzm.licai.db;

import com.wzm.licai.bean.UserBean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserOpenHelper extends SQLiteOpenHelper{
    //用户表sql语句
	private String sql_user="create table "+UserBean.TABLE_USER+"("+
	        UserBean.ID+" integer primary key autoincrement,"+
    		UserBean.NAME+" text,"+UserBean.PWD+" text)";
	private String sql_wallet="create table wallet("
			+ "_id integer primary key autoincrement,"
			+ "name,money double,"
			+ "u_id references user(_id))";
	private String sql_zhangben="create table zhangben("
			+ "_id integer primary key autoincrement,"
			+ "name,u_id references user(_id))";
	private String sql_money="create table add_money("
			+ "_id integer primary key autoincrement,"
			+ "money double,"
			+ "date datetime,"
			+ "type,"
			+ "mark,"
			+ "w_id references wallet(_id),"
			+ "u_id references user(_id),"
			+ "z_id references zhangben(_id))";
	public UserOpenHelper(Context context) {
		super(context, "licai.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建用户表
		db.execSQL(sql_user);
		db.execSQL(sql_wallet);
		db.execSQL(sql_money);
		db.execSQL(sql_zhangben);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
