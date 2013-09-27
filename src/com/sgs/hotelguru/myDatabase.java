package com.sgs.hotelguru;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class myDatabase extends SQLiteAssetHelper {
	 private static final String DATABASE_NAME = "HotelGuru DB";
	    private static final int DATABASE_VERSION = 1;

public myDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);  
}

public void insertSQL(String mySQLPrompt) {
	// TODO Auto-generated method stub
	SQLiteDatabase db = getWritableDatabase();
	db.execSQL(mySQLPrompt);
}
}