package com.example.demo.bass.breakpoint;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bass on 16/3/1.
 */
public class DownloadService extends Service {
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/bassDownload/";
    public static final String ACTION_STRAT = "action strat";
    public static final String ACTION_STOP = "action stop";
    public static final String ACTION_UPDATE = "action update";

    public static final int MSG_INIT = 0;

    private DownloadTask mTask = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_STRAT.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            //启动初始化线程
            if (null != mTask){
                mTask.isPause = false;
            }
            new InitThread(fileInfo).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            if (null != mTask){
                mTask.isPause = true;
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    //启动下载任务
                    mTask = new DownloadTask(DownloadService.this,fileInfo);
                    mTask.download();
                    break;
            }

        }
    };

    /**
     * 初始化线程
     */
    class InitThread extends Thread {
        private FileInfo fileInfo;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
//            super.run();
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(fileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");

                int length = -1;

                if (conn.getResponseCode() == 200){
                    //获得文件长度
                    length = conn.getContentLength();
                }

                if (length <= 0) {
                    return;
                }
                //在本地创建文件
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");//随即访问文件，可在文件任意一个位置访问,//"rwd"表示可读可写可删
                //设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    //关闭流
                    if (null != raf){
                        raf.close();
                    }
                    if (null != conn){
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
