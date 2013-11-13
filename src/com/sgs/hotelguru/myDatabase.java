package com.sgs.hotelguru;

import java.util.ArrayList;

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
public ArrayList<String> getCruiseLines()
{
	ArrayList<String> myCruiseLines = new ArrayList<String>();
	SQLiteDatabase db = getReadableDatabase();
	Cursor myCursor = db.rawQuery("SELECT DISTINCT Cruise_Line from Ships", null);
	myCursor.moveToFirst();
	for(int i = 0; i<myCursor.getCount();i++)
	{
		myCruiseLines.add(myCursor.getString(0));
		myCursor.moveToNext();
	}
	db.close();
	return myCruiseLines;
	
}

public ArrayList<String> getCruiseShips(String mySelectedCL) {
	// TODO Auto-generated method stub
	ArrayList<String> myCruiseShips = new ArrayList<String>();
	SQLiteDatabase db = getReadableDatabase();
	String[] selargs = new String[1];
	selargs[0] = mySelectedCL;
	Log.v("DB QUERY", "getting cruise ship with cruise line = "+selargs[0]);
	Cursor myCursor = db.rawQuery("SELECT Ship_Name FROM Ships WHERE Cruise_Line=?", selargs);
	myCursor.moveToFirst();
			for(int i = 0; i<myCursor.getCount();i++)
			{
				myCruiseShips.add(myCursor.getString(0));
				myCursor.moveToNext();
			}
			db.close();
			return myCruiseShips;
}

public ArrayList<String> getNumDecks(String myShip) {
	// TODO Auto-generated method stub
	ArrayList<String> myDecks = new ArrayList<String>();
	SQLiteDatabase db = getReadableDatabase();
	String[] selargs = new String[1];
	selargs[0] = myShip;
	Cursor myCursor = db.rawQuery("SELECT Ship_Decks FROM Ships WHERE Ship_Name=?", selargs);
	myCursor.moveToFirst();
	int myNumDecks = 0;
			for(int i = 0; i<myCursor.getCount();i++)
			{
				myNumDecks = myCursor.getInt(0);
				myCursor.moveToNext();
			}
			db.close();
			for(int i = 1; i<=myNumDecks;i++)
				myDecks.add("Deck "+i);
			return myDecks;
}
public ArrayList<ButtonStruct> getButtonCoords(String myShip, int myDeck){
	Log.v("GETBUTTONCOORDS", "Starting getButtonCoords");
	ArrayList<ButtonStruct> myButtonData = new ArrayList<ButtonStruct>();
	SQLiteDatabase db = getReadableDatabase();
	String[] selargs = new String[2];
	selargs[0] = myShip;
	selargs[1] = Integer.toString(myDeck);
	Cursor myCursor = db.rawQuery("SELECT Room_Num, ButtonX, ButtonY FROM Rooms WHERE Ship_Name=? AND Room_Deck=?",selargs);
	myCursor.moveToFirst();
	for(int i=0;i<myCursor.getCount();i++)
	{
		
		int myRoomNum = myCursor.getInt(0);
		int myButtonX = myCursor.getInt(1);
		int myButtonY = myCursor.getInt(2);
		ButtonStruct myWorkButton = new ButtonStruct();
		myWorkButton.setShip_Name(myShip);
		myWorkButton.setRoom_Deck(myDeck);
		myWorkButton.setRoom_Num(myRoomNum);
		myWorkButton.setButtonX(myButtonX);
		myWorkButton.setButtonY(myButtonY);
		myButtonData.add(myWorkButton);
		myCursor.moveToNext();
		
	}
	Log.v("GETBUTTONCOORDS", "about to return buttondata");
	return myButtonData;
}
public shipStruct getShipData(String myShip){
	shipStruct myShipData = new shipStruct();
	SQLiteDatabase db = getReadableDatabase();
	String[] selargs = new String[1];
	selargs[0] = myShip;
	Cursor myCursor = db.rawQuery("SELECT Ship_Name, Ship_Year, Cruise_Line, Capacity, Ship_Decks, Ship_Registry, Ship_Speed, Ship_Length FROM Ships WHERE Ship_Name=?",selargs);
	myCursor.moveToFirst();
	String myShipName = myCursor.getString(0);
	myShipData.setShip_Name(myShipName);
	int myShipYear = myCursor.getInt(1);
	myShipData.setShip_Year(myShipYear);
	String myCruiseLine = myCursor.getString(2);
	myShipData.setCruise_Line(myCruiseLine);
	int myCap = myCursor.getInt(3);
	myShipData.setCapacity(myCap);
	int myDecks = myCursor.getInt(4);
	myShipData.setShip_Decks(myDecks);
	String myRegistry = myCursor.getString(5);
	myShipData.setShip_Registry(myRegistry);
	int mySpeed = myCursor.getInt(6);
	myShipData.setShip_Speed(mySpeed);
	int myLength = myCursor.getInt(7);
	myShipData.setShip_Length(myLength);
	return myShipData;
	
}
}
