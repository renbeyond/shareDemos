package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.constant.Constants;
import com.example.demo.entity.DemoInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 16/2/24.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<DemoInfo> listInfo = new ArrayList<DemoInfo>();

    public MyAdapter(Context context, List<DemoInfo> listInfo) {
        this.context = context;
        this.listInfo = listInfo;
    }


    @Override
    public int getCount() {

        return listInfo.size();
    }

    @Override
    public Object getItem(int position) {

        return listInfo.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHold.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHold.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            viewHold.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(viewHold);

        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        viewHold.tvTitle.setText(listInfo.get(position).getTitle());
        viewHold.tvAuthor.setText(listInfo.get(position).getAuthor());
        viewHold.tvDate.setText(listInfo.get(position).getDate());

        return convertView;
    }


    private final static class ViewHold {
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;
    }
}
