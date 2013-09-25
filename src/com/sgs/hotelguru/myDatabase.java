package com.sgs.hotelguru;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class myDatabase extends SQLiteAssetHelper {
	 private static final String DATABASE_NAME = "HotelGuru DB";
	    private static final int DATABASE_VERSION = 1;

public myDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);  
}
}