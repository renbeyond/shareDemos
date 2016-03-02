package com.example.demo.activity;

import java.io.File;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;

/**
 * Http
 * 1、初始化FinalHttp finalHttp = new FinalHttp();
 * @author bass
 *
 */
public class FinalHttpTest extends FinalActivity {
	@ViewInject(id=R.id.btnDownLoad,click = "download") Button btnDownlad;
	@ViewInject(id= R.id.tVPB)TextView tVpB;
	String urlString="http://mmgr.myapp.com/myapp/gjbig/packmanage/24/2/3/102027/tencentmobilemanager5.7.0_android_build3146_102027.apk";
	String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bbbbbbbbbbbbbb.apk";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_http);
	}
	
	public void download(View v){
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.download(urlString, apkPath, new AjaxCallBack<File>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Toast.makeText(FinalHttpTest.this, "下载失败", 1000).show();
				super.onFailure(t, strMsg);
			}

			@Override
			public void onLoading(long count, long current) {
				tVpB.setText("下载进度："+ current + "/" + count);
				super.onLoading(count, current);
			}

			@Override
			public void onStart() {
				Toast.makeText(FinalHttpTest.this, "开始下载", 1000).show();
				super.onStart();
			}

			@Override
			public void onSuccess(File t) {
				Toast.makeText(FinalHttpTest.this, "成功下载", 1000).show();
				super.onSuccess(t);
			}

		});
	}
}
