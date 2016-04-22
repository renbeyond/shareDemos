package com.example.demo.bauer.encrypiton.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Photo页的图片信息数据库的databasehelper
 * photopass图片表，记录所有的photopass的照片数据
 * favorite表，记录所有的喜爱的地点
 * photopass条码表，记录所有的photopass信息表
 *
 * @author bauer_bao
 */
public class PictureAirDBHelper extends SQLiteOpenHelper {
    public static final String SHARE_DB_TABLE = "share_test";

    public PictureAirDBHelper(Context context) {
        this(context, "share_demo.db3");
    }

    public PictureAirDBHelper(Context context, String name, CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }

    public PictureAirDBHelper(Context context, String name) {
        this(context, name, 1);
    }

    public PictureAirDBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 创建firststart表，记录所有页面第一次进入的记录
         * _id
         * activity
         * userId
         */
        db.execSQL("create table if not exists " + SHARE_DB_TABLE +
                "(_id integer primary key autoincrement, record text)");

    }

    //如果数据库的版本号不一致，会执行onUpgrade函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
