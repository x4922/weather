package com.xw.weathe.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.http.client.fluent.Content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.xw.weathe.db.WeatherOpenHelper;

public class WeatherDB {
	//数据库名称
	public static final String DB_NAME="Weathe";
	//数据库版本
	public static final int VERSION=1;
	
	private static WeatherDB weatherDB;
	private SQLiteDatabase db;
	// 构造WeatherDB私有方法
	private WeatherDB(Context context) {
		// TODO 自动生成的构造函数存根
		WeatherOpenHelper dbHelper=new WeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getReadableDatabase();
	}
	//获取实例
	public synchronized static WeatherDB getInstance(Context context) {
		if(weatherDB==null){
			weatherDB= new WeatherDB(context);
		}
		return weatherDB;
	}
	//将实例储存
	//省 Province 
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Provunce", null, values);
		}
	}
	//市 city
	public void saveCity(City city){
		if(city != null){
			ContentValues values=new ContentValues();
			values.put("province_name", city.getCityName());
			values.put("province_code", city.getCityCode());
			values.put("provunce_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	//县区 county 
	public void saveCounty(County county){
		if(county != null){
			ContentValues values=new ContentValues();
			values.put("province_name", county.getCountyName());
			values.put("province_code", county.getCountyCode());
			values.put("provunce_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
    //从数据库读取信息
	//省 Province
	public List<Province> loadProvince(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getColumnIndex("id"));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			} while(cursor.moveToNext());
		}
		return list;
	}
	//市 City
	public List<City> loadCity(){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city=new City();
				city.setId(cursor.getColumnIndex("id"));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getColumnIndex("provunce_id"));
			} while (cursor.moveToNext());
		}
		return list;
	}
	//县区 county
	public List<County> loadCounty(){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county=new County();
				county.setId(cursor.getColumnIndex("id"));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("province_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("province_code")));
				county.setCityId(cursor.getColumnIndex("city_id"));
			} while (cursor.moveToNext());
		}
		return list;
	}
}
