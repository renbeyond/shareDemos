package com.example.demo.peng.function.androidseven.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by pengwu on 16/11/2.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ConnectivityService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("ConnectivityService", "onStartJob");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("ConnectivityService", "onStopJob");
        return false;
    }
}
