package com.xw.weathe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper{
//	创建省级的表
	private static final String CREATE_SHEN="create table Province ("
			+ "id integer primary key autoincrement,"
			+ "province_name text,"
			+ "province_code text)";
	//创建市级表
	private static final String CREATE_CITY="create table city ("
			+ "id integer primary key autoincrement,"
			+ "city_name text,"
			+ "city_code text,"
			+ "province_id integer)";
	//创建县级表
	private static final String CREATE_COUNTY="create table city ("
			+ "id interger primary key autoincrement,"
			+ "county_name text,"
			+ "county_code text,"
			+ "city_id integer)";
	public WeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO 自动生成的构造函数存根
	}
	@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO 自动生成的方法存根
			db.execSQL(CREATE_SHEN);
			db.execSQL(CREATE_CITY);
			db.execSQL(CREATE_COUNTY);
		}
	@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自动生成的方法存根
			
		}

}
