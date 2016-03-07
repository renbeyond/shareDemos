package com.example.demo.bass.breakpoint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bass on 16/3/1.
 */
public class DBHelp extends SQLiteOpenHelper {

    private static final String DB_NAME = "download db";
    public static final String DB_TABLE_NAME = "thread_info";

    private static final int VERSION =1;
    //创建表
    private static final String SQL_CREATE = "create table "+DB_TABLE_NAME+"(_id integer primary key autoincrement," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    //删除表
    private static final String SQL_DROP = "drop table if exists "+DB_TABLE_NAME;

    public DBHelp(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
