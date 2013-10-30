package com.sgs.hotelguru;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StateRoom extends Activity {

	int room_num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_state_room);
		
		Bundle extras = getIntent().getExtras();
    	if (extras != null) {
		room_num = extras.getInt("room_num");
    	}
		setTitle("Stateroom "+room_num);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.state_room, menu);
		return true;
	}

}
