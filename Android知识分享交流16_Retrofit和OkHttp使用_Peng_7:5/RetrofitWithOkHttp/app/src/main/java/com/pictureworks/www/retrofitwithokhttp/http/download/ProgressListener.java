package com.pictureworks.www.retrofitwithokhttp.http.download;

/**
 * Created by pengwu on 16/7/5.
 */
public interface ProgressListener {

    void onProgress(long progress,long total,boolean done);
}
