package com.example.demo.milo.commonadapter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by milo on 15/12/30.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> datas;
    private int layoutId;

    public CommonAdapter(Context mContent, List<T> datas, int layoutId) {
        this.context = mContent;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, layoutId);
        convert(holder, getItem(position),position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t, int position);



    public void refresh(List<T> t) {
        this.datas = t;
        notifyDataSetChanged();
    }
}
