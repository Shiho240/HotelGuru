package com.sgs.hotelguru;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class myDatabase extends SQLiteAssetHelper {
	 private static final String DATABASE_NAME = "HotelGuru DB";
	    private static final int DATABASE_VERSION = 1;
		private static final String TAG = "myDatabase";
public myDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);  
}

public void insertSQL(String Username, String Password) {
	// TODO Auto-generated method stub
	Log.v(TAG, "inside db insert func");
	SQLiteDatabase db = getWritableDatabase();
	Log.v(TAG, "database successfully opened for writing");
	ContentValues userInfo = new ContentValues();
	userInfo.put("Username", Username);
	userInfo.put("Password", Password);
	db.insert("User", null, userInfo);
	db.close();
}

public String getUser() {
	SQLiteDatabase db = getReadableDatabase();
	String myUser = db.query("User", new String[]{"Username"}, "uid = 1", null, null, null, null).toString();
	return myUser;
	
}
}