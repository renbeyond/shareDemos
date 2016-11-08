package com.example.demo.milo.commonadapter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.demo.R;
import com.example.demo.milo.commonadapter.adapter.CommonAdapter;
import com.example.demo.milo.commonadapter.adapter.ListAdapter;
import com.example.demo.milo.commonadapter.adapter.ViewHolder;
import com.example.demo.milo.commonadapter.entity.Mode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by milo on 16/3/23.
 * 列表activity
 */
public class ListActivity extends Activity {
    private static final String TAG = "imeibi";
    private List<Mode> modeList;
    private ListAdapter adapter;
    private CommonAdapter commonAdapter;


    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        ButterKnife.bind(this);
        initData();//初始化数据
        initView();//普通adapter
//        initView2();//通用adapter

    }

    private void initData() {
        modeList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Mode mode = new Mode();
            mode.setNo(i + "");
            mode.setContent("第 " + i + "个内容...");
            mode.setStatus("0");
            modeList.add(mode);
        }
    }

    private void initView() {
        adapter = new ListAdapter(this, modeList, new ListAdapter.doOnClickListener() {
            @Override
            public void Select(int position, View view) {
                //更新数据
                if (modeList.get(position).getStatus().equals("1")) {
                    modeList.get(position).setStatus("0");
                    view.setBackgroundResource(R.color.app_main_color);
                } else {
                    modeList.get(position).setStatus("1");
                    view.setBackgroundResource(R.color.green);
                }
                //刷新界面
                adapter.refresh(modeList);
            }
        });
        listView.setAdapter(adapter);
    }

    private void initData2() {
        Log.v(TAG, "initData2");
        if (modeList != null) {
            modeList.removeAll(modeList);
        }
        for (int i = 51; i < 100; i++) {
            Mode mode = new Mode();
            mode.setNo(i + "");
            mode.setContent("第 " + i + "个内容...");
            mode.setStatus("0");
            modeList.add(mode);
        }
    }

    private void initView2() {

        commonAdapter = new CommonAdapter<Mode>(this, modeList, R.layout.item_list) {
            @Override
            public void convert(ViewHolder holder, final Mode mode, int position) {
                holder.setText(R.id.no_tv, mode.getNo())
                        .setText(R.id.content_tv, mode.getContent());
                if (mode.getStatus().equals("1")) {
                    holder.setBackgroundResource(R.id.submit_btn, R.color.green);
                } else {
                    holder.setBackgroundResource(R.id.submit_btn, R.color.app_main_color);
                }

                holder.setOnclickListener(R.id.submit_btn, new ViewHolder.doOnclickListener() {
                    @Override
                    public void onclick(View view) {
                        Log.v(TAG, "onclick position: " + mode.getNo());
                        if (mode.getNo().equals("3")) {
                            initData2();
                            commonAdapter.refresh(modeList);
                        }
                        if (mode.getStatus().equals("1")) {
                            mode.setStatus("0");
                            view.setBackgroundResource(R.color.app_main_color);
                        } else {
                            mode.setStatus("1");
                            view.setBackgroundResource(R.color.green);
                        }
                    }
                });
            }
        };

        listView.setAdapter(commonAdapter);

    }
}
