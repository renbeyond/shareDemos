package com.android.talon.test;

/**
 * Created by talon on 16/3/16.
 */
public class JniUtils {

    static {
        System.loadLibrary("JniLibName"); //和生成so文件的名字对应。
    }

    public native String getString();
}
