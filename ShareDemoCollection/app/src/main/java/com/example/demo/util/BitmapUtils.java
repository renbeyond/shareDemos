package com.example.demo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by talon on 16/2/24.
 */
public class BitmapUtils {

    /**
     * 照片电影，根据坐标 增加黑色边缘。
     * @param bitmap
     * @return
     */
    public static Bitmap filmBitmap(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight()+200, bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bitmap, 0, 100, null); // 添加图片
        drawText(canvas);
        return newBitmap;
    }


    /**
     * 根据X，Y坐标添加文字
     * @param canvas
     */
    public static void drawText(Canvas canvas){
        //添加文字
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(40.0f);
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.WHITE);
        canvas.drawText("今天天气真好", (1600 - 40 * 6) / 2, 900 - 180,
                textPaint);
        canvas.drawText("It’s really a nice day today", (1600 - 40 * 12) / 2, 900 - 130,
                textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
    }



    /**
     * 保存Bitmap图片到指定文件
     *
     * @param bm
     * @param filePath
     */
    public static void saveBitmap(Bitmap bm, String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
