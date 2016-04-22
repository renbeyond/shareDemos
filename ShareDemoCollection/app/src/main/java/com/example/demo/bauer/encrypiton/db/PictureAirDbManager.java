package com.example.demo.bauer.encrypiton.db;

import android.content.Context;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * 数据库操作管理封装类，以后所有的数据库操作，都在这里进行
 *
 * @author bauer_bao
 */
public class PictureAirDbManager {
    private static final String TAG = "PictureAirDbManager";
    private SQLiteOpenHelper photoInfoDBHelper;
    private SQLiteDatabase database;

    public PictureAirDbManager(Context context) {
        if (photoInfoDBHelper == null) {
//            photoInfoDBHelper = PictureAirDBHelper.getInstance(context);
            photoInfoDBHelper = SQLiteHelperFactory.create(context);
            DBManager.initializeInstance(photoInfoDBHelper);//初始化数据库操作类
        }
    }

    /**
     * 插入测试数据
     * @param recordStr
     */
    public void insertRecord(String recordStr){
        database = DBManager.getInstance().writData();
        database.beginTransaction();
        try {
            database.execSQL("insert into " + PictureAirDBHelper.SHARE_DB_TABLE + " values(null,?)",
                    new String[]{recordStr});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            DBManager.getInstance().closeDatabase();
        }
    }

    /**
     * 获取记录
     *
     */
    public String getLastRecord() {
        String result = "无任何数据";
        Cursor cursor = null;
        try {
            database = DBManager.getInstance().readData();
            cursor = database.rawQuery("select * from " + PictureAirDBHelper.SHARE_DB_TABLE + " order by _id desc limit 1", null);//
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex("record"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            DBManager.getInstance().closeDatabase();
        }
        return result;
    }

}
