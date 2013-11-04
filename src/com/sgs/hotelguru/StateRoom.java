package com.sgs.hotelguru;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StateRoom extends Activity {

	int room_num;
	String GlobalShipName;
	roomDataStruct myStateRoom;
	myDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_state_room);
        db = new myDatabase(getApplicationContext());
		Bundle extras = getIntent().getExtras();
    	if (extras != null) {
		room_num = extras.getInt("room_num");
		GlobalShipName = extras.getString("GlobalShipName");
    	}
		setTitle("Stateroom "+room_num);
		myStateRoom = db.getRoomData(GlobalShipName, room_num);
		TextView room_out = (TextView) findViewById(R.id.stateroominfo);
		room_out.setText("CruiseLine: "+myStateRoom.getCruiseLine()+'\n'+"Ship Name: "+GlobalShipName+'\n'+"Deck: "+myStateRoom.getRoomDeck()+'\n'+"Room Number: "+room_num+'\n'+"Room Type: "+ myStateRoom.getRoomType()+'\n'+ "Room Description: "+myStateRoom.getRoomDesc());
		TextView commentsIn = (TextView) findViewById(R.id.stateroomComments);
		//get comments from external database here
		//make scrollable
		commentsIn.setMovementMethod(new ScrollingMovementMethod());
		JSONObj JSONtiem = new JSONObj();
		String myUrl = null;
		try {
			myUrl = "http://hotelguru.no-ip.org/scripts/getComments.php?shipname="+URLEncoder.encode(GlobalShipName, "UTF-8")+"&&roomnum="+URLEncoder.encode(Integer.toString(room_num), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject myData;
		myData = JSONtiem.getJSONFromUrl(myUrl);
		Log.v("stateroom", myData.toString());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.state_room, menu);
		return true;
	}
	
	public void newComment(View view){
	
		
		 // Do something in response to button click
		Log.v("Stateroom Submit Comment", "Submit button pressed");
		EditText newComment = (EditText) findViewById(R.id.newComment);
		String Comment = newComment.getText().toString();
		JSONObj JSONtiem = new JSONObj();
		String myUrl = null;
		try {
			myUrl = "http://hotelguru.no-ip.org/scripts/setComments.php?shipname="+URLEncoder.encode(GlobalShipName, "UTF-8")+"&&roomnum="+URLEncoder.encode(Integer.toString(room_num), "UTF-8")+"&&message="+URLEncoder.encode(Comment, "UTF-8")+"&&username="+URLEncoder.encode(db.getUser(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject myData;
		myData = JSONtiem.getJSONFromUrl(myUrl);
		Log.v("stateroom", myData.toString());
		
		
	}

}
