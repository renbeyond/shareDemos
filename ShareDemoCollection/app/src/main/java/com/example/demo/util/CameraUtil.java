package com.example.demo.util;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talon on 15/11/9.
 */
public class CameraUtil {

    /**
     * 设置焦点和测光区域
     *
     * @param event
     */
    public static void focusOnTouch(MotionEvent event,View relativeLayout,View view_focus,Camera mycamera) {
        //		if (!taking_flag) {
        int[] location = new int[2];
        relativeLayout.getLocationOnScreen(location);
        Rect focusRect = calculateTapArea(view_focus.getWidth(),
                view_focus.getHeight(), 1f, event.getRawX(),
                event.getRawY(), location[0],
                location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());
        Rect meteringRect = calculateTapArea(view_focus.getWidth(),
                view_focus.getHeight(), 1.5f, event.getRawX(),
                event.getRawY(), location[0],
                location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());
       //切换  聚焦模式。
        Camera.Parameters parameters = mycamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(focusRect, 1000));
            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));

            parameters.setMeteringAreas(meteringAreas);
        }
        try {
            mycamera.setParameters(parameters);
        } catch (Exception e) {
        }
    }
    /**
     * 聚焦 的具体方法
     */
    public static Rect calculateTapArea(int focusWidth, int focusHeight,
                                        float areaMultiple, float x, float y, int previewleft,
                                        int previewRight, int previewTop, int previewBottom) {
        int areaWidth = (int) (focusWidth * areaMultiple);
        int areaHeight = (int) (focusHeight * areaMultiple);
        int centerX = (previewleft + previewRight) / 2;
        int centerY = (previewTop + previewBottom) / 2;
        double unitx = ((double) previewRight - (double) previewleft) / 2000;
        double unity = ((double) previewBottom - (double) previewTop) / 2000;
        int left = clamp((int) (((x - areaWidth / 2) - centerX) / unitx), -1000, 1000);
        int top = clamp((int) (((y - areaHeight / 2) - centerY) / unity), -1000, 1000);
        int right = clamp((int) (left + areaWidth / unitx), -1000, 1000);
        int bottom = clamp((int) (top + areaHeight / unity), -1000, 1000);

        return new Rect(left, top, right, bottom);
    }

    public static int clamp(int x, int min, int max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
    }

    /**
     * 保存 二进制文件到本地。
     * @param photoFile
     * @param picData
     */
    public static void outputPhotoForStream(File photoFile,byte[] picData){
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(photoFile);
            outputStream.write(picData); // 写入sd卡中
            outputStream.close(); // 关闭输出流
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * //主动让媒体库去更新最新文件
     *
     * @param file
     *            需要扫描的文件
     */
    public static void scan(String file,Context context) {
        MediaScannerConnection.scanFile(context, new String[]{file},
                new String[]{"image/*"},
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        //刷新成功。
                    }
                });
    }
}
