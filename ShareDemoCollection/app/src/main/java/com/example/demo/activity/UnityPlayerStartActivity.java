package com.example.demo.activity;

import com.example.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UnityPlayerStartActivity extends Activity implements
		OnClickListener {

	private Button mStartButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_activity);
		mStartButton = (Button) findViewById(R.id.button_start);
		mStartButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, UnityPlayerActivity.class);
		startActivity(intent);
	}

	// // Quit Unity
	// @Override protected void onDestroy ()
	// {
	// mUnityPlayer.quit();
	// super.onDestroy();
	// }
	//
	// // Pause Unity
	// @Override protected void onPause()
	// {
	// super.onPause();
	// mUnityPlayer.pause();
	// }
	//
	// // Resume Unity
	// @Override protected void onResume()
	// {
	// super.onResume();
	// mUnityPlayer.resume();
	// }
}
