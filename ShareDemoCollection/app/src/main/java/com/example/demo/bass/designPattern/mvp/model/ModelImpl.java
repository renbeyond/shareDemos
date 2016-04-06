package com.example.demo.bass.designPattern.mvp.model;

/**
 * Created by bass on 16/3/30.
 */
public class ModelImpl implements ModelInterface{
    @Override
    public void insert(String name) {
        //添加的放啊
    }

    @Override
    public boolean del(int id) {
        return true;
    }

    @Override
    public boolean update(int id, String name) {
        return true;
    }

    @Override
    public int query(int name) {
        return 1234567;
    }
}
