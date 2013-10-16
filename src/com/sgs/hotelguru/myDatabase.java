package com.sgs.hotelguru;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	Cursor myCursor = db.query("User", new String[]{"Username"}, "uid = 1", null, null, null, null);
	myCursor.moveToFirst();
	String myUser=myCursor.getString(0);
	db.close();
	return myUser;
	
}
public roomDataStruct getRoomData(String shipName, int roomNum)
{
	SQLiteDatabase db = getReadableDatabase();
	Cursor myCursor = db.query("Rooms", new String[]{"Room_Num", "Room_Type", "Room_Desc", "Room_Rating", "Room_Deck", "Ship_Name", "Cruise_Line", "Room_Interior", "Room_Exterior"}, "Room_Num="+roomNum,null,null,null,null);
	myCursor.moveToFirst();
	roomDataStruct myRoomData = new roomDataStruct();
	int myRoomNum = myCursor.getInt(0);
	myRoomData.setRoomNum(myRoomNum);
	Log.v("Roomdatastruct", "Read Room Number from database");
	String myRoomType = myCursor.getString(1);
	myRoomData.setRoomType(myRoomType);
	String myRoomDesc = myCursor.getString(2);
	myRoomData.setRoomDesc(myRoomDesc);
	int myRoomRating = myCursor.getInt(3);
	myRoomData.setRoomRating(myRoomRating);
	int myRoomDeck = myCursor.getInt(4);
	myRoomData.setRoomDeck(myRoomDeck);
	String myShipName = myCursor.getString(5);
	myRoomData.setShipName(myShipName);
	String myCruiseLine = myCursor.getString(6);
	myRoomData.setCruiseLine(myCruiseLine);
	String myRoomInt = myCursor.getString(7);
	myRoomData.setRoomInt(myRoomInt);
	String myRoomExt = myCursor.getString(8);
	myRoomData.setRoomExt(myRoomExt);
	db.close();
	return myRoomData;
}

}
