package com.example.demo.bass.breakpoint;

import java.util.List;

/**
 * 数据库imp
 * Created by bass on 16/3/1.
 */
public interface ThreadDAO {
    public void insertThread(ThreadInfo threadInfo);//添加线程
    public void deleteThread(String url, int threadId);//删除线程
    public void updateThread(String url, int threadId, long finished);//更新线程

    public List<ThreadInfo> getTreads(String url);//查询文件线程信息
    public boolean isExists(String url, int threadId);//查询线程是否存在

}
