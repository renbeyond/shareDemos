package com.demo.talon.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by talon on 15/11/23.
 * 星星动画View ： 随机大小，旋转，渐变。
 */
public class StartView extends View{
    long startTime, prevTime;
    int numStarts = 0;
    Bitmap startBitmap;
    ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    ArrayList<Start> starts = new ArrayList<Start>(); // Start 的集合 List
    Matrix m = new Matrix();

    public StartView(Context context) {
        super(context);
        startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                long nowTime = System.currentTimeMillis();
                float secs = (nowTime - prevTime) / 1000f;
                prevTime = nowTime;
                for (int i = 0; i < numStarts; ++i) {
                    Start start = starts.get(i);
                    start.rotation = start.rotation + (start.rotationSpeed * secs);
                    // 渐变。每次经过减掉 1，小于 0 时还原最大值 255
                    start.alpha = start.alpha - 1;
                    if (start.alpha <= 0 )
                        start.alpha = 255;
                }
                // Force a redraw to see the flakes in their new positions and orientations
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Reset list of droidflakes, then restart it with 8 flakes
        starts.clear();
        numStarts = 0;
        addStarts(0);
        // Cancel animator in case it was already running
        animator.cancel();
        // Set up fps tracking and start the animation
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // For each flake: back-translate by half its size (this allows it to rotate around its center),
        // rotate by its current rotation, translate by its location, then draw its bitmap
        for (int i = 0; i < numStarts; ++i) {
            Start flake = starts.get(i);
            Paint paint =new Paint();
            paint.setAlpha((int) flake.alpha); //透明度。 0 － 225
            m.setTranslate(-flake.width / 2, -flake.height/2);
            m.postRotate(flake.rotation);
            m.postTranslate(flake.width / 2 + flake.x, flake.height / 2 + flake.y);
            canvas.drawBitmap(flake.bitmap, m, paint);
        }
    }
    /**
     * Add the specified number of droidflakes.
     */
    public void addStarts(int quantity) {
        if(numStarts <= 75)
        {
            for (int i = 0; i < quantity; ++i) {
                starts.add(Start.createStart(getWidth(), startBitmap, getWidth(), getHeight()));
            }
            numStarts = quantity;
        }
    }

    public void pause() {
        // Make sure the animator's not spinning in the background when the activity is paused.
        animator.cancel();
    }

    public void resume() {
        animator.start();
    }



}
