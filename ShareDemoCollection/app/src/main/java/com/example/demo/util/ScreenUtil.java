package com.example.demo.util;

import android.content.Context;
/**屏幕计算*/
public class ScreenUtil {
	
	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5f);
	}
	/**
	 * 把像素转换为密度
	 */
	public static int px2dip(Context context, float pxValue) {  
	    final float scale = getScreenDensity(context); 
	    return (int) (pxValue / scale + 0.5f);  
	} 
	
	/**
	 * 得到url中正确的文件名
	 * @param url 原始url
	 * @return 文件名
	 */
	public static String getReallyFileName(String url) {  
		String filename = url;  
		filename = filename.substring(filename.lastIndexOf("/") + 1);
		return filename;  
	}
	
}
