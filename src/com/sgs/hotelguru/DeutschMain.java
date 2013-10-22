package com.sgs.hotelguru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DeutschMain extends Activity {

	
	myDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deutsch_main);
		db = new myDatabase(getApplicationContext());
		TextView welcomeDE = (TextView) findViewById(R.id.welcomeDE);
		welcomeDE.setText("Herzlich Wilkommen "+db.getUser());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deutsch_main, menu);
		return true;
	}
	public void onToMap(View view)
	{
		Intent intent = new Intent(this, MapsExample.class);
		startActivity(intent);
	}
}
