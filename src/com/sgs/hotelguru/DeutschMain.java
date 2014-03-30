package com.sgs.hotelguru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DeutschMain extends Activity {

	
	myDatabase db;
	String mySelectedCL;
	String GlobalCruiseLine;
	String GlobalShipName;
	String GlobalShipDeck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deutsch_main);
		db = new myDatabase(getApplicationContext());
		this.setTitle("Willkommen Zur�ck "+db.getUser()+"!");
		this.SetCruiseLine();
		
	}
	@Override
	public void onBackPressed() {
	}
	public void onSpinnerSubmit(View v){
	
		Intent i = new Intent(getApplicationContext(), MapsDeutsch.class);
		i.putExtra("GlobalCruiseLine",GlobalCruiseLine);
		i.putExtra("GlobalShipName", GlobalShipName);
		i.putExtra("GlobalShipDeck", GlobalShipDeck);
		startActivity(i);
		/*Toast.makeText(DeutschMain.this,"Preparing to move to selected Deck Plans for "+GlobalCruiseLine+" "+GlobalShipName+" "+GlobalShipDeck,
                Toast.LENGTH_SHORT).show();*/
	}
	
public void SetCruiseLine()
{
	ArrayList<String> myCruiseLines = new ArrayList<String>();
	myCruiseLines = db.getCruiseLines();
	
	Spinner spinner = (Spinner)findViewById(R.id.CruiseLineSpinner);
	ArrayAdapter<String> spinnerArrayAdapterCLS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myCruiseLines);
	spinner.setAdapter(spinnerArrayAdapterCLS);
	spinner.setOnItemSelectedListener(new planOnClickListener());
	
}
public void SetCruiseShip(String myCruiseLine)
{
	ArrayList<String> myCruiseShips = new ArrayList<String>();
	myCruiseShips = db.getCruiseShips(myCruiseLine);
	Spinner shipSpinner = (Spinner) findViewById(R.id.ShipSelectSpinner);
	ArrayAdapter<String> spinnerArrayAdapterCLS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myCruiseShips);
	shipSpinner.setAdapter(spinnerArrayAdapterCLS);
	shipSpinner.setOnItemSelectedListener(new cruiseShipOnClickListener());
}
public void SetShipDeck(String myShip){
	ArrayList<String> myShipDecks = new ArrayList<String>();
	myShipDecks = db.getNumDecks(myShip);
	Spinner DecksSpinner = (Spinner) findViewById(R.id.DeckSelectSpinner);
	ArrayAdapter<String> spinnerArrayAdapterDCS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myShipDecks);
	DecksSpinner.setAdapter(spinnerArrayAdapterDCS);
	DecksSpinner.setOnItemSelectedListener(new DeckOnClickListener());
}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.english_main, menu);
		return true;
	}

	public class planOnClickListener implements OnItemSelectedListener {

	    @Override
	   public void onItemSelected(AdapterView<?> parent, View v, int pos,
	           long id) {

	       String mySelectedCL = parent.getItemAtPosition(pos).toString();
	       GlobalCruiseLine = mySelectedCL;
	       Log.v("DEUTSCHMAIN","Selected Cruise line is "+mySelectedCL);
	       SetCruiseShip(mySelectedCL);

	      
	}
	   @Override
	   public void onNothingSelected(AdapterView<?> arg0) {
	       // TODO Auto-generated method stub

	   }
	}
	public class cruiseShipOnClickListener implements OnItemSelectedListener {

	    @Override
	   public void onItemSelected(AdapterView<?> parent, View v, int pos,
	           long id) {

	      String myShip = parent.getItemAtPosition(pos).toString();
	      GlobalShipName = myShip;
	      Log.v("Deutschmain", "global ship name is "+GlobalShipName);
			shipStruct shipInfo = db.getShipData(GlobalShipName);
			TextView shipStats = (TextView)findViewById(R.id.ShipData);
			shipStats.append("Kreuzfahrtschiff Name: "+shipInfo.getShip_Name()+'\n'+"Reederei: "+shipInfo.getCruise_Line()+'\n'+"Land der Registrierung: "+shipInfo.getShip_Registry()+'\n'+ "Gebaut/Umger�stet: "+shipInfo.getShip_Year()+'\n'+"Deckzahl: "+shipInfo.getShip_Decks()+'\n'+"Passagierkapazit�t: "+shipInfo.getCapacity()+" Reisenden"+'\n'+"Geschwindigkeit: "+shipInfo.getShip_Speed()+" Knoten"+'\n'+"L�nge: "+Math.round(shipInfo.getShip_Length()*.3048)+" m");
			ImageView shipIcon = (ImageView) findViewById(R.id.ShipIcon);
			 //lets see if the image has already been cached in the sdcard
	        File extStore = Environment.getExternalStorageDirectory();
	        File myDurr = new File(extStore.getAbsolutePath()+"/HotelGuru/img/"+GlobalCruiseLine+"/"+GlobalShipName);
	        File myFile = new File(extStore.getAbsolutePath() + "/HotelGuru/img/"+GlobalCruiseLine+"/"+GlobalShipName,GlobalShipName+".png");
	       
	        if(!myFile.exists()){
	        	try {
	        		myDurr.mkdirs();
	        		myFile.createNewFile();
	        		Log.v("dynamic_ship_icon", "File Doesnt Exist");

	                String workUrl = ("http://hotelguru.no-ip.org/img/"+URLEncoder.encode(GlobalCruiseLine,"UTF-8")+"/"+URLEncoder.encode(GlobalShipName,"UTF-8")+"/"+URLEncoder.encode(GlobalShipName,"UTF-8")+".png");
	                workUrl= workUrl.replaceAll("\\+", "%20");
	                URL url = new URL(workUrl);
	                InputStream input = url.openStream();
	                    //The sdcard directory e.g. '/sdcard' can be used directly, or 
	                    //more safely abstracted with getExternalStorageDirectory()
	                	Log.v("mapsexample", "url opened and creating outputstream");
	                    File storagePath = Environment.getExternalStorageDirectory();
	                    OutputStream output = new FileOutputStream (myFile);

	                    	Log.v("beginning read from url", "inside nested try before buffer set");
	                        byte[] buffer = new byte[1028];
	                        int bytesRead = 0;
	                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
	                            output.write(buffer, 0, bytesRead);
	                            Log.v("deck image buffer", buffer.toString());
	                        }
	                        output.close();

	                    input.close();
	                }
	             catch (Exception e) {
	            	 Log.e("ship icon download", "Something went wrong transferring the image to the sd card with error " + e);
	            }
	        	
	        }
	        	//file exists for sure here lets open it and set the img view to it
	        	File deckImg = new File(extStore.getAbsolutePath()+"/HotelGuru/img/"+GlobalCruiseLine+"/"+GlobalShipName,GlobalShipName+".png");  
	        	Bitmap bitmap = BitmapFactory.decodeFile(deckImg.getAbsolutePath());
	        	shipIcon.setImageBitmap(bitmap);
			SetShipDeck(myShip);

	      
	}
	   @Override
	   public void onNothingSelected(AdapterView<?> arg0) {
	       // TODO Auto-generated method stub

	   }
	}
	public class DeckOnClickListener implements OnItemSelectedListener {

	    @Override
	   public void onItemSelected(AdapterView<?> parent, View v, int pos,
	           long id) {

	       String myDeck = parent.getItemAtPosition(pos).toString();
	       GlobalShipDeck = myDeck;

	      
	}
	   @Override
	   public void onNothingSelected(AdapterView<?> arg0) {
	       // TODO Auto-generated method stub

	   }
	}
}