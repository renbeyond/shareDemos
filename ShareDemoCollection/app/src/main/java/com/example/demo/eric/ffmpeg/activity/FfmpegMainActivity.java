package com.example.demo.eric.ffmpeg.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;

import com.example.demo.R;
import com.example.demo.eric.ffmpeg.adapter.FfmpegAdapter;
import com.example.demo.eric.ffmpeg.util.ACache;
import com.example.demo.util.PictureAirLog;

import wseemann.media.FFmpegMediaMetadataRetriever;


/**
 * Created by Eric on 16/3/2.
 */
public class FfmpegMainActivity extends Activity {

    private GridView gvFrame;
    private FfmpegAdapter myAdapter;
    private ProgressDialog dialog;

    //视频地址
    String[] videoUrl = {"http://ac-A7cn1GAf.clouddn.com/07fdd773c2068907.mp4",
            "http://ac-A7cn1GAf.clouddn.com/07fdd773c2068907.mp4"};

    //视频键
    String[] videoName = new String[videoUrl.length];

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //刷新视图
                    myAdapter.refresh(videoName);
                    break;
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmepg);

        initView();
    }

    //加载组件
    public void initView() {

        gvFrame = (GridView) findViewById(R.id.gvFrame);
        //获得视频名称
        for (int i = 0; i < videoUrl.length; i++) {
            videoName[i] = videoUrl[i].substring(videoUrl[i].lastIndexOf("/") + 1, videoUrl[i].length()) + i;
        }

        myAdapter = new FfmpegAdapter(FfmpegMainActivity.this, videoName);
        gvFrame.setAdapter(myAdapter);

        for (int i = 0; i < videoName.length; i++) {
            //异步任务(多线程)
            Log.e("main", "for--->" + i);
            new MyThread(i).start();
        }
    }

    //FFmpegMediaMetadataRetriever获取视频缩略图
    private Bitmap getVideoThumbnail(int time, String videoUrl, String videoName) {

        PictureAirLog.d("get video thumbnail---->");

        Bitmap bitmap = null;
        FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
        try {
            //获取网络视频
            fmmr.setDataSource(videoUrl);
            //获取本地视频
//            fmmr.setDataSource(path + "/123.mp4");
            //获取视频的帧
            bitmap = fmmr.getFrameAtTime();
            if (bitmap != null) {
                Log.e("====", " get video thumbnail time: " + time);
                Bitmap b2 = fmmr
                        .getFrameAtTime(
                                time,
                                FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC);//得到最靠近的关键帧
                if (b2 != null) {
                    bitmap = b2;
                }
                if (bitmap.getWidth() > 640) {// 如果图片宽度规格超过640px,则进行压缩
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                            640, 480,
                            ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//回收

                }

                //放入缓存
                ACache.get(this).put(videoName, bitmap);
                Log.e("========", "videoName " + videoName);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            fmmr.release();
        }
        return bitmap;
    }

    //异步任务
    public class MyThread extends Thread {
        private int threadId;

        public MyThread(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            super.run();
            Log.e("main", "in background---> " + threadId);
            if (ACache.get(FfmpegMainActivity.this).getAsBitmap(videoName[threadId]) == null) {
                getVideoThumbnail(1000000, videoUrl[threadId], videoName[threadId]);
            }
            handler.sendEmptyMessage(1);
        }
    }
}
