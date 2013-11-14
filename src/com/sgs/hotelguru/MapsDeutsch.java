package com.sgs.hotelguru;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.sgs.hotelguru.MapsExample.ButtonListener;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapsDeutsch extends Activity {

	RelativeLayout mainView;
    //Button QM2_12002;
    //Button QM2_12081;
    Button buttonZoomIn;
    final float Zoom_max = 8f;
    final float Zoom_min = 1f;
    private static final String TAG = "Die Karte";
    myDatabase db;
	float myWorkX = 0;
	float myWorkY = 0;
	String GlobalCruiseLine;
	String GlobalShipName;
	String GlobalShipDeck;
	ArrayList<ButtonStruct> myButtonData;
	Button [] buttons;
	int counter;
	//int width = 20;
	//int height = 7;

	
    //@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_example);

    	Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    GlobalCruiseLine = extras.getString("GlobalCruiseLine");
    	    GlobalShipName = extras.getString("GlobalShipName");
    	    GlobalShipDeck = extras.getString("GlobalShipDeck");
    	    /*Toast.makeText(MapsDeutsch.this,"MOVED to selected Deck Plans for "+GlobalCruiseLine+" "+GlobalShipName+" "+GlobalShipDeck,
                    Toast.LENGTH_SHORT).show();*/
    	}
    	
       
    	
        mainView = (RelativeLayout) findViewById(R.id.DeckLayout);
        db = new myDatabase(getApplicationContext());
        String delims = "[ ]+";
    	String[] tokens = GlobalShipDeck.split(delims);
    	int myDeck = Integer.parseInt(tokens[1]);
        
    	//old code for loading deck images dynamically from assets folder(bad too much space)
        //ImageView img = (ImageView) findViewById(R.id.imageView1);
        /*ImageView img = new ImageView(this);
        RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
	            RelativeLayout.LayoutParams.MATCH_PARENT,
	            RelativeLayout.LayoutParams.MATCH_PARENT);
        img.setLayoutParams(vp);
        AssetManager assetManager = getResources().getAssets();
        //set image for deckplans
		try {
			InputStream ims = assetManager.open(GlobalCruiseLine+"/"+GlobalShipName+"/"+"deck "+myDeck+".jpg");
			Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            img.setImageDrawable(d);
            img.setScaleType(ScaleType.FIT_CENTER);
            DisplayMetrics dm1 = getResources().getDisplayMetrics();
    		float Simgheight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, dm1);
            vp.height = (int) Simgheight;
            mainView.addView(img,vp);
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	//deck images will load from sdcard, if it doesnt exist, it will download from server and store in sdcard
    	ImageView img = new ImageView(this);
    	RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
	            RelativeLayout.LayoutParams.MATCH_PARENT,
	            RelativeLayout.LayoutParams.MATCH_PARENT);
        img.setLayoutParams(vp);
        //lets see if the image has already been cached in the sdcard
        File extStore = Environment.getExternalStorageDirectory();
        File myFile = new File(extStore.getAbsolutePath() + "/HotelGuru/img/"+GlobalCruiseLine+"/"+GlobalShipName,"deck "+myDeck+".jpg");

        if(!myFile.exists()){
        	try {
        		myFile.createNewFile();
        		Log.v("dynamic_deck", "File Doesnt Exist");

                String workUrl = ("http://hotelguru.no-ip.org/img/"+URLEncoder.encode(GlobalCruiseLine,"UTF-8")+"/"+URLEncoder.encode(GlobalShipName,"UTF-8")+"/"+URLEncoder.encode("deck ","UTF-8")+myDeck+".jpg");
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
            	 Log.e("MapsExample Dynamic Deck Plans Exception", "Something went wrong transferring the image to the sd card with error " + e);
            }
        	
        }
        	//file exists for sure here lets open it and set the img view to it
        	File deckImg = new File(extStore.getAbsolutePath()+"/HotelGuru/img/"+GlobalCruiseLine+"/"+GlobalShipName,"deck "+myDeck+".jpg");  
        	Bitmap bitmap = BitmapFactory.decodeFile(deckImg.getAbsolutePath());
        	img.setImageBitmap(bitmap);
        	 DisplayMetrics dm1 = getResources().getDisplayMetrics();
     		float Simgheight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, dm1);
             vp.height = (int) Simgheight;
             mainView.addView(img,vp);
        
    	

        //dynamically create buttons here
    	
    	
    	Log.v("MapsExample", "about to get coords from database from deck "+myDeck);
    	myButtonData = db.getButtonCoords(GlobalShipName, myDeck);
    	counter = myButtonData.size();
    	Log.v("Button Data", "Size of button struct is: "+counter);
    	buttons = new Button[counter];
    	for(int i = 0; i<counter;i++)
    	{
    		buttons[i] = new Button(this);
    		int mycoordX = myButtonData.get(i).getButtonX();
    		int mycoordY = myButtonData.get(i).getButtonY();
    		Log.v("Dynamic Button", "creating new button at "+mycoordX+" "+mycoordY);
    		buttons[i].setX(mycoordX);
    		buttons[i].setY(mycoordY);
    		buttons[i].setEnabled(true);
    		buttons[i].setTextSize(10.f);
    		//buttons[i].setVisibility(View.INVISIBLE);
    		buttons[i].setText(GlobalShipName+"_"+myButtonData.get(i).getRoom_Num());
    		DisplayMetrics dm = getResources().getDisplayMetrics();
    		float scaledW = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, dm);
    		float scaledH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, dm);
    		//buttons[i].setHeight(scaledH);
    		//buttons[i].setWidth (scaledW);
    		buttons[i].setOnClickListener(new ButtonListener());
    		RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
    	            RelativeLayout.LayoutParams.WRAP_CONTENT,
    	            RelativeLayout.LayoutParams.WRAP_CONTENT);
            buttons[i].setId(myButtonData.get(i).getRoom_Num());
            lprams.setMargins(mycoordX, mycoordY, 0, 0);
            lprams.height = (int) scaledH;
            lprams.width = (int) scaledW;
            lprams.leftMargin = mycoordX;
            lprams.topMargin = mycoordY;
            
            buttons[i].setLayoutParams(lprams);
            mainView.addView(buttons[i],lprams);
    	}


        Button buttonZoomOut = (Button)findViewById(R.id.zoom_out);
        buttonZoomOut.setX(350);
        buttonZoomOut.bringToFront();
        
        //Button buttonNormal = (Button)findViewById(R.id.reset);
        buttonZoomIn = (Button)findViewById(R.id.zoom_in);
        buttonZoomIn.bringToFront();
        
        FrameLayout zoom = (FrameLayout) findViewById(R.id.zoom_Layout);
        zoom.bringToFront();
        zoom.requestFocus();
        //QM2_12002 = (Button)findViewById(R.id.QM2_12002);
        //QM2_12081 = (Button)findViewById(R.id.QM2_12081);
      buttonZoomOut.setOnClickListener(new OnClickListener() {

       @Override
       public void onClick(View v) {
        zoom_out(new PointF(myWorkX,myWorkY));
       }
      });/*
      buttonNormal.setOnClickListener(new OnClickListener() {

       @Override
       public void onClick(View v) {
        zoom(1f,1f,new PointF(0,0));
       }
      });*/
      mainView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				//Toast.makeText(MapsExample.this,"Setting zoom point to here",Toast.LENGTH_SHORT).show();
				myWorkX = arg1.getX();
				myWorkY = arg1.getY();
				return true;
			}
  	});
        buttonZoomIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	

            	
            	
                zoom(new PointF(myWorkX,myWorkY));
            }
        });
     /*   QM2_12002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	//float myWorkX = QM2_12002.getX();
            	//float myWorkY = QM2_12002.getY();
            	roomDataStruct myRoomData = db.getRoomData("Queen Mary 2", 12002);
                Toast.makeText(MapsExample.this,myRoomData.getRoomNum()+" "+myRoomData.getRoomType()+" "+myRoomData.getRoomDeck(),
                        Toast.LENGTH_SHORT).show();
            }
            
        });
        QM2_12081.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	//float myWorkX = QM2_12081.getX();
            	//float myWorkY = QM2_12081.getY();
            	roomDataStruct myRoomData = db.getRoomData("Queen Mary 2", 12081);
                Toast.makeText(MapsExample.this,myRoomData.getRoomNum()+" "+myRoomData.getRoomType()+" "+myRoomData.getRoomDeck(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
       mainView.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                // The bounds for the delegate view (an ImageButton
                // in this example)
            	for(int i = 0; i<counter;i++)
            	{
            		int mycoordX = myButtonData.get(i).getButtonX();
            		int mycoordY = myButtonData.get(i).getButtonY();
            		buttons[i].setX(mycoordX);
            		buttons[i].setY(mycoordY);
            		buttons[i].setEnabled(true);
            	}
               /* QM2_12002.setX(325);
                QM2_12002.setY(92);
                QM2_12002.setEnabled(true);
                QM2_12081.setX(390);
                QM2_12081.setY(370);
                QM2_12081.setEnabled(true);*/
            	
            	//Iterator<ButtonStruct> buttonIterator = myButtonData.iterator();
            	//while(buttonIterator.hasNext())
            	//{
            		
            	//}
            }
        });

    }



	/** zooming is done from here */
    public void zoom(PointF pivot){
        mainView.setPivotX(pivot.x);
        mainView.setPivotY(pivot.y);
        float workScaleX = mainView.getScaleX();
        float workScaleY = mainView.getScaleY();
       /* float workButtonX = QM2_12002.getX();
        float workButtonY = QM2_12002.getY();
        float workButtonX12081 = QM2_12081.getX();
        float workButtonY12081 = QM2_12081.getY();*/
        if(workScaleX<Zoom_max)
        {
            mainView.setScaleX(workScaleX*2f);
            mainView.setScaleY(workScaleY*2f);
            /*QM2_12002.setX(workButtonX/2f);
            QM2_12002.setY(workButtonY/2f);
            QM2_12081.setX(workButtonX12081/2f);
            QM2_12081.setY(workButtonY12081/2.45f);*/
        }
    }
    public void zoom_out(PointF pivot){
    	 mainView.setPivotX(pivot.x);
         mainView.setPivotY(pivot.y);
         float workScaleX = mainView.getScaleX();
         float workScaleY = mainView.getScaleY();
         if(workScaleX>Zoom_min)
         {
             mainView.setScaleX(workScaleX/2f);
             mainView.setScaleY(workScaleY/2f);
         }
    }
    public class ButtonListener implements View.OnClickListener{

    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		int room_num = v.getId();
    		/*Toast.makeText(MapsDeutsch.this,"Debug: Room Button clicked for room number: "+room_num,
                    Toast.LENGTH_SHORT).show();*/
    		Intent i = new Intent(getApplicationContext(), StateRoom.class);
    		//lets get the buttonstruct data for this button here
    		i.putExtra("GlobalShipName", GlobalShipName);
    		i.putExtra("room_num",room_num);
    		startActivity(i);
    		 
    	}

    }

}
