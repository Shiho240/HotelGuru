package com.sgs.hotelguru;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
		room_out.setMovementMethod(new ScrollingMovementMethod());
		room_out.setText(Html.fromHtml("<b> Cruise Line: </b>"+myStateRoom.getCruiseLine()+"<br/><br/>"+"<b> Ship Name: </b>"+GlobalShipName+"<br/><br/>"+"<b> Deck: </b>"+myStateRoom.getRoomDeck()+"<br/><br/>"+"<b>Room Number: </b>"+room_num+"<br/><br/>"+"<b> Room Type: </b>"+ myStateRoom.getRoomType()+"<br/><br/>"+"<b> Room Occupancy: </b>"+myStateRoom.getOccupancy()+"<br/><br/>"+"<b>Room Size: </b>"+myStateRoom.getRoomSize()+" Square Feet<br/><br/>"+"<b> Balcony Size: </b>"+myStateRoom.getBalconySize()+" Square Feet<br/><br/>"+"<b> Room Description: </b>"+myStateRoom.getRoomDesc()+"<br/><br/><b> Special Notes: </b>"+myStateRoom.getRoomSpecial()+"<br/><br/><b>Comments:</b><br/>"));
		//get comments from external database here
				//make scrollable
		
		JSONObj JSONtiem = new JSONObj();
		String myUrl = null;
		try {
			myUrl = "http://hotelguru.no-ip.org/scripts/getComments.php?shipname="+URLEncoder.encode(GlobalShipName, "UTF-8")+"&&roomnum="+URLEncoder.encode(Integer.toString(room_num), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Log.v("stateroom", myData.toString());
		JSONObject myCData;
		myCData = JSONtiem.getJSONFromUrl(myUrl);
		JSONArray myComments;
		try {
			
			if((myCData.getInt("success"))==1)
			{
				myComments = myCData.getJSONArray("posts");
				for(int i=0;i<myComments.length();i++)
				{
					JSONObject workComment = myComments.getJSONObject(i);
					room_out.append(Html.fromHtml(workComment.getString("Comment")+"-<i>"+workComment.getString("Username")+"</i><br/><br/>"));
				}
				
			}
			else
			{
				room_out.append(Html.fromHtml("<b> No one has commented on this stateroom yet, be the first to do so! </b>"));
			}
			
			
		} catch (JSONException e) {
			Log.v("JSONException", e.toString());
			e.printStackTrace();
		}
		final RatingBar minimumRating = (RatingBar)findViewById(R.id.ratingBar1);
	    minimumRating.setOnTouchListener(new OnTouchListener()
	    { 
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				float touchPositionX = event.getX();
	            float width = minimumRating.getWidth();
	            float starsf = (touchPositionX / width) * 5.0f;
	            int stars = (int)starsf + 1;
	            minimumRating.setRating(stars);
	            return true; 
			} 
	    });
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
			JSONObject myData = JSONtiem.getJSONFromUrl(myUrl);
			try {
				if((myData.getInt("success"))==1)
				{
					newComment.setText("");
					newComment.setSelected(false);
					recreate();//kills current activity and restarts it so we can update with new user comment
				}
				else
				{
					Toast.makeText(StateRoom.this,"An error occured while posting a comment. Please Try Again Later. If you already have posted a comment for this room with this username, you may not post again.",
			                Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
