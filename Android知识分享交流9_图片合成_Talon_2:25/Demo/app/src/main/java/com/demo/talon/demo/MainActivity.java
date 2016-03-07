package com.demo.talon.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity{


    private StartView startView;
    private LinearLayout lyContent;
    private TextView tvPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext);


//        tvPlay = (TextView) findViewById(R.id.tv_play);
//        lyContent = (LinearLayout) findViewById(R.id.ly_content);
//        startView = new StartView(this);
//        lyContent.addView(startView);
//        tvPlay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                lyContent.setVisibility(View.VISIBLE);
//                handlerRain.postDelayed(runnableRain, 0);
//            }
//        });
    }



//    Handler handlerRain = new Handler();
//    Runnable runnableRain = new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            startView.addStarts(300);
////            handlerRain.postDelayed(runnableRain, 2000);
////            if(startView.getNumFlakes() > 70)
////            {
////                handlerRain.removeCallbacks(runnableRain);
////            }
//        }
//    };


}
