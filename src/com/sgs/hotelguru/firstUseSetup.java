package com.sgs.hotelguru;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class firstUseSetup extends Activity implements OnItemSelectedListener{
	public myDatabase db;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.firstuse);
		Spinner spinner = (Spinner) findViewById(R.id.localeSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.locale_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		db = new myDatabase(this);
		Log.v(TAG, "Database and spinner created, and about to call firstrun?");
		firstuse();
	}
	public static final String PREFS_NAME = "HotelGuru_Prefs_File";
	public String myLocale = "EN_US"; //default locale is EN_US
	private static final String TAG = "FirstRun";
	public void firstuse(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		Log.v(TAG,"Inside first use");
		if(settings.getBoolean("firstBoot", true))
				{
			Log.v(TAG, "First use detected");
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
		Log.v(TAG, "Submit button pressed");
		EditText uidField = (EditText) findViewById(R.id.userName);
		EditText passwordField = (EditText) findViewById(R.id.userPass);
		String Username = uidField.getText().toString();
		String Password = passwordField.getText().toString();
		Log.v(TAG, "Calling a database insert with Username and password = "+Username+" "+Password);
		db.insertSQL(Username, Password);
	}
		
}