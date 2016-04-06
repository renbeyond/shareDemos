package com.example.demo.bass.designPattern.mvp.model;

/**
 * Created by bass on 16/3/30.
 */
public interface ModelInterface {
    /**
     * 增删改查
     */
    void insert(String name);
    boolean del(int id);
    boolean update(int id,String name);
    int query(int name);

}
