package com.demo.talon.demo;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by talon on 15/11/23.
 * 定义 动画start 需要的属性。
 */
public class Start {

    float x, y;   // x，y轴
    float rotation; // 旋转角度
    float speed;  // 速度
    float rotationSpeed;
    int width, height;
    float alpha;
    Bitmap bitmap;

    static HashMap<Integer, Bitmap> bitmapMap = new HashMap<Integer, Bitmap>();

    /**
     * 创建一个星星的对象。
     * @param xRange
     * @param originalBitmap bitmap
     * @param width   宽度
     * @param height  高度
     * @return
     */
    static Start createStart(float xRange, Bitmap originalBitmap,float width, float height) {
        Start start = new Start();
        // Size each flake with a width between 5 and 55 and a proportional height
        start.width = (int)(20 + (float)Math.random() * 50);
        float hwRatio = originalBitmap.getHeight() / originalBitmap.getWidth();
        start.height = (int)(start.width * hwRatio);

        start.y = (float)Math.random() * height;
        start.x = (float)Math.random() * width;
        // Each flake travels at 50-200 pixels per second
        start.speed = 50 + (float) Math.random() * 250;
        // Flakes start at -90 to 90 degrees rotation, and rotate between -45 and 45
        // degrees per second
        start.rotation = (float) Math.random() * 180 - 90;
        start.rotationSpeed = (float) Math.random() * 90 - 45;
        start.alpha = (float) Math.random() * 250;
        // Get the cached bitmap for this size if it exists, otherwise create and cache one
        start.bitmap = bitmapMap.get(start.width);
        if (start.bitmap == null) {
            start.bitmap = Bitmap.createScaledBitmap(originalBitmap,
                    start.width, start.height, true);
            bitmapMap.put(start.width, start.bitmap);
        }
        return start;
    }
}
