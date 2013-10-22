package com.sgs.hotelguru;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EnglishMain extends Activity {

	myDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_english_main);
		db = new myDatabase(getApplicationContext());
		ArrayList<String> myCruiseLines = new ArrayList<String>();
		myCruiseLines = db.getCruiseLines();
		
		Spinner spinner = (Spinner)findViewById(R.id.CruiseLineSpinner);
		ArrayAdapter<String> spinnerArrayAdapterCLS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myCruiseLines);
		spinner.setAdapter(spinnerArrayAdapterCLS);
		//lets deal with our dynamic spinners here
		
		/*Spinner spinner = (Spinner) findViewById(R.id.localeSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.locale_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.english_main, menu);
		return true;
	}

}
