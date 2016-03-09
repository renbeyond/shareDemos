package com.example.demo.bass.breakpoint;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.demo.R;


/**
 * 下载链接可能失效
 * 断点续传下载
 */
public class BreakpointDownloadActivity extends Activity implements View.OnClickListener{
    private final String TAG = "BreakpointDownloadActivity";
    private final String MP3_URL = "http://dd.myapp.com/16891/0A81099808E86539B02F50CB5A3B5F86.apk?fsname=com.pictureAir_2.0_3.apk";
    private FileInfo fileInfo = null;

    private ProgressBar progressBar = null;
    private Button btnStop,btnDownload;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakpoint_download);
        initView();
    }

    private void initView() {
        context = this;
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnDownload = (Button)findViewById(R.id.btnDownload);
        btnStop = (Button)findViewById(R.id.btnStop);

        btnDownload.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        progressBar.setMax(100);
        //创建文件信息
        fileInfo = new FileInfo();
        fileInfo.setId(0);
        fileInfo.setUrl(MP3_URL);
        fileInfo.setFileName("popping.mp3");
        fileInfo.setFinished(0);
        fileInfo.setLength(0);

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(receiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload:
                setDownloadServiceAction(DownloadService.ACTION_STRAT);
                Toast.makeText(context,"下载",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnStop:
                setDownloadServiceAction(DownloadService.ACTION_STOP);
                Toast.makeText(context,"暂停",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void setDownloadServiceAction(String action) {
        Intent intent = new Intent(context,DownloadService.class);
        intent.setAction(action);
        intent.putExtra("fileInfo",fileInfo);
        startService(intent);
    }

    /**
     *
     */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())){
                long finished = intent.getLongExtra("finished", 0);
                progressBar.setProgress((int)finished);
                if (finished == 100){
                    Toast.makeText(context,"finish",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}
