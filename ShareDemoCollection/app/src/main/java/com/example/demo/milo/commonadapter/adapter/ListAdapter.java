package com.example.demo.milo.commonadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.milo.commonadapter.entity.Mode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by milo on 16/3/23.
 * 普通的adapter
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Mode> list;
    private ViewHolder viewHolder;
    private doOnClickListener listener;


    public ListAdapter(Context context, List<Mode> list, doOnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void refresh(List<Mode> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.noTv.setText(list.get(position).getNo());
        viewHolder.contentTv.setText(list.get(position).getContent());
        if (list.get(position).getStatus().equals("1")) {
            viewHolder.submitBtn.setBackgroundResource(R.color.green);
        } else {
            viewHolder.submitBtn.setBackgroundResource(R.color.app_main_color);
        }

        viewHolder.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "position: " + position, Toast.LENGTH_LONG).show();
                if (list.get(position).getStatus().equals("1")) {
                    list.get(position).setStatus("0");
                    v.setBackgroundResource(R.color.app_main_color);
                } else {
                    list.get(position).setStatus("1");
                    v.setBackgroundResource(R.color.green);
                }
            }
        });

//        viewHolder.submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.Select(position, v);
//            }
//        });

        return convertView;
    }

    public interface doOnClickListener {
        void Select(int position, View view);
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_list.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.no_tv)
        TextView noTv;
        @Bind(R.id.content_tv)
        TextView contentTv;
        @Bind(R.id.submit_btn)
        Button submitBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
