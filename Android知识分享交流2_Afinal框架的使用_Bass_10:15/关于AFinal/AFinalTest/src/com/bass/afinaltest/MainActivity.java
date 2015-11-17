package com.bass.afinaltest;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 1、继承FinalAty
 * 2、onCreate方法的protected改为public
 * 3、以注解形式写
 * 
 * @author bass
 * 
 */
public class MainActivity extends FinalActivity {
	@ViewInject(id = R.id.btnDB, click = "clickDB") Button btn1;
	@ViewInject(id = R.id.btnBitmap, click = "clickBitmap") Button btn2;
	@ViewInject(id = R.id.btnHttp, click = "clickHttp") Button btn3;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void clickDB(View v) {
		startActivity(new Intent(MainActivity.this, FinalDBTest.class));
	}
	public void clickBitmap(View v) {
		startActivity(new Intent(MainActivity.this, FinalBitmapTest.class));
	}
	public void clickHttp(View v) {
		startActivity(new Intent(MainActivity.this, FinalHttpTest.class));
	}

}
