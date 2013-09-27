package com.sgs.hotelguru;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class firstUseSetup extends Activity implements OnItemSelectedListener{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.firstuse);
		firstuse();
	}
	public static final String PREFS_NAME = "HotelGuru_Prefs_File";
	public String myLocale = "EN_US"; //default locale is EN_US
	public myDatabase db;
	public void firstuse(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		Spinner spinner = (Spinner) findViewById(R.id.localeSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.locale_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		if(settings.getBoolean("firstBoot", true))
				{
					//FIRST BOOT DETECTED, IMPORT DATABASE AND QUERY FOR LOGIN INFORMATION
					db = new myDatabase(this);
					db.getWritableDatabase();
					db.close();
					//DONE WITH FIRST RUN, SET PREF FLAG SO IT DOESNT RUN AGAIN
					settings.edit().putBoolean("firstBoot", false).commit();
			
				}
		else
		{
			//NORMAL MODE CONTINUE TO MAIN ACTIVITY
			
			//if locale == DE proceed to German side of the app
			//else if locale is english proceed to english side of the app 
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		myLocale = (String) arg0.getItemAtPosition(arg2);
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onSubmit(View view) {
	    // Do something in response to button click
		db.getWritableDatabase();
		String Username = (String) getText(R.id.userName);
		String Password = (String) getText(R.id.userPass);
		String mySQLPrompt = "INSERT INTO User (Username,Password) VALUES ("+Username+","+Password+");";
		db.insertSQL(mySQLPrompt);
	}
		
}