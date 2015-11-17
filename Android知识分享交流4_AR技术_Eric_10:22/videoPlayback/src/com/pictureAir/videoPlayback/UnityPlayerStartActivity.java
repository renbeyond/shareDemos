package com.pictureAir.videoPlayback;

import com.pictureAir.videoPlayback.R;
import com.pictureAir.videoPlayback.R.id;
import com.pictureAir.videoPlayback.R.layout;
import com.unity3d.player.UnityPlayer;

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
		setContentView(R.layout.startactivity);
		mStartButton = (Button) findViewById(R.id.button_start);
		mStartButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this,
				com.pictureAir.videoPlayback.UnityPlayerActivity.class);
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
