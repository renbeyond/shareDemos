package com.example.demo.bass.breakpoint;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问接口的实现
 * Created by bass on 16/3/1.
 */
public class ThreadDAOImpl implements ThreadDAO {
    private DBHelp dbHelp = null;
    private Context context;

    public ThreadDAOImpl(Context context) {
        dbHelp = new DBHelp(context);
        this.context = context;
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        db.execSQL("insert into " + dbHelp.DB_TABLE_NAME + "(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int threadId) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        db.execSQL("delete from " + dbHelp.DB_TABLE_NAME + " where url = ? and thread_id = ?",
                new Object[]{url, threadId});
        db.close();
    }

    @Override
    public void updateThread(String url, int threadId, long finished) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        db.execSQL("update " + dbHelp.DB_TABLE_NAME + " set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished,url, threadId});
        db.close();
    }

    @Override
    public List<ThreadInfo> getTreads(String url) {
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelp.DB_TABLE_NAME + " where url = ?", new String[]{url});
        while (cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int threadId) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelp.DB_TABLE_NAME + " where url = ? and thread_id = ?", new String[]{url, threadId+""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
