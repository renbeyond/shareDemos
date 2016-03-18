package com.example.demo.eric.ffmpeg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.demo.R;
import com.example.demo.eric.ffmpeg.util.ACache;

/**
 * Created by Eric on 16/2/24.
 */
public class FfmpegAdapter extends BaseAdapter {

    private Context context;
    String[] frameName;


    public void refresh(String[] frameName) {
        this.frameName = frameName;
        notifyDataSetChanged();
    }


    public FfmpegAdapter(Context context, String[] frame) {
        this.context = context;
        this.frameName = frame;
    }

    @Override
    public int getCount() {

        return frameName.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHold viewHold;
        if (convertView == null) {

            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.ffmepg_list_item, null);
            viewHold.ivCache = (ImageView) convertView.findViewById(R.id.ivCache);

            convertView.setTag(viewHold);

        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        //从缓存中获取图片
        Bitmap bitmap = ACache.get(context).getAsBitmap(frameName[position]);
        if (bitmap == null){
            viewHold.ivCache.setImageResource(R.drawable.ic_loading);
        }else{
            viewHold.ivCache.setImageBitmap(bitmap);
        }
        return convertView;
    }


    private final static class ViewHold {
        ImageView ivCache;
    }
}
