package com.sgs.hotelguru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MapsExample extends Activity {

	View mainView;
    Button QM2_12002;
    Button buttonZoomIn;
    final float Zoom_max = 4f;
    private static final String TAG = "Die Karte";
    myDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_example);
        mainView =findViewById(R.id.DeckLayout);
        db = new myDatabase(getApplicationContext());

        //Button buttonZoomOut = (Button)findViewById(R.id.zoom_out);
        //Button buttonNormal = (Button)findViewById(R.id.reset);
        buttonZoomIn = (Button)findViewById(R.id.zoom_in);
        QM2_12002 = (Button)findViewById(R.id.QM2_12002);

      /*buttonZoomOut.setOnClickListener(new OnClickListener() {

       @Override
       public void onClick(View v) {
        zoom(0.5f,0.5f,new PointF(0,0));
       }
      });
      buttonNormal.setOnClickListener(new OnClickListener() {

       @Override
       public void onClick(View v) {
        zoom(1f,1f,new PointF(0,0));
       }
      });*/
        buttonZoomIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(new PointF(0,0));
            }
        });
        QM2_12002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	float myWorkX = QM2_12002.getX();
            	float myWorkY = QM2_12002.getY();
            	roomDataStruct myRoomData = db.getRoomData("Queen Mary 2", 12002);
                Toast.makeText(MapsExample.this,myRoomData.getRoomNum()+" "+myRoomData.getRoomType()+" "+myRoomData.getRoomDeck(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mainView.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                // The bounds for the delegate view (an ImageButton
                // in this example)
                QM2_12002.setX(300);
                QM2_12002.setY(400);
                QM2_12002.setEnabled(true);
            }
        });
    }


    /** zooming is done from here */
    public void zoom(PointF pivot){
        mainView.setPivotX(pivot.x);
        mainView.setPivotY(pivot.y);
        float workScaleX = mainView.getScaleX();
        float workScaleY = mainView.getScaleY();
        float workButtonX = QM2_12002.getX();
        float workButtonY = QM2_12002.getY();
        if(workScaleX<Zoom_max)
        {
            mainView.setScaleX(workScaleX*2f);
            mainView.setScaleY(workScaleY*2f);
            QM2_12002.setX(workButtonX/2f);
            QM2_12002.setY(workButtonY/2f);
        }
    }
}