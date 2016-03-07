package com.example.demo.bass.breakpoint;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 下载任务类
 * Created by bass on 16/3/1.
 */
public class DownloadTask {
    private Context context;
    private FileInfo fileInfo;
    private ThreadDAO threadDAO = null;
    private long mFinish = 0;
    public boolean isPause = false;//是否暂停

    public DownloadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        threadDAO = new ThreadDAOImpl(context);
    }

    public void download(){
        //读取数据库的线程信息
        List<ThreadInfo> list = threadDAO.getTreads(fileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (null != list && list.size() == 0){
            //无线程，初始化线程对象，重新添加
            threadInfo = new ThreadInfo(0,fileInfo.getUrl(),0,fileInfo.getLength(),0);
        }else{
            //有线程存在
            threadInfo = list.get(0);
        }
        //创建子线程进行下载
        new DownloadThread(threadInfo).start();
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread{
        private ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo){
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            super.run();
            //向数据库插入线程信息
            if (!threadDAO.isExists(threadInfo.getUrl(),threadInfo.getId())){
                threadDAO.insertThread(threadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;
            try {
                //连接网络文件
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置文件写入位置
                int start = threadInfo.getStart() + threadInfo.getFinished();
                //Range 表示下载的位置设置 ，bytes ＝ 开始位置 到 结束位置 ，用“－”表示
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");//随即访问文件，可在文件任意一个位置访问,//"rwd"表示可读可写可删
                raf.seek(start);//例如：seek(100），则跳过100个自己，从第101个自己开始读写

                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                mFinish += threadInfo.getFinished();//完成的进度保存一下
                //开始下载
                //读取数据
                if (conn.getResponseCode() == 206){//部分下载

                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    //相隔500毫秒发送一次
//                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        //把下载的进度发送广播给 Activity
                        mFinish += len;

//                        if (System.currentTimeMillis() - time > 500) {
//                            time = System.currentTimeMillis();
                            intent.putExtra("finished", mFinish * 100 / fileInfo.getLength());
                            context.sendBroadcast(intent);
//                        }
                        //在下载暂停时，保存下载进度
                        if (isPause) {
                            threadDAO.updateThread(threadInfo.getUrl(), threadInfo.getId(), mFinish);
                            return;
                        }
                    }

                    //下载完后，删除线程信息
                    threadDAO.deleteThread(threadInfo.getUrl(), threadInfo.getId());
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    //关闭流
                    conn.disconnect();
                    raf.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
