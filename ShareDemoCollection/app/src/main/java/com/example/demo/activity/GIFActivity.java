package com.example.demo.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demo.R;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GIFActivity extends Activity{

	private GifImageView gifImageView;
	private GifDrawable gifDrawable;
	private int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gif);
		gifImageView = (GifImageView) findViewById(R.id.giv_demo);
		try {
			gifDrawable = new GifDrawable(getResources(), R.drawable.welcome_gif);
			gifDrawable.addAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationCompleted() {
					// TODO Auto-generated method stub
					i++;
					System.out.println("第"+i+"次播放完成");
					Toast.makeText(GIFActivity.this, "第"+i+"次播放完成", Toast.LENGTH_SHORT).show();
				}
			});
			gifDrawable.setLoopCount(3);
			gifImageView.setBackgroundDrawable(gifDrawable);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (gifDrawable != null) {
			System.out.println("gif recycle");
			gifDrawable.recycle();
		}
	}
}
