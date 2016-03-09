package com.example.demo.bauer.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.demo.R;
import com.example.demo.util.ScreenUtil;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,};

    private String[] contents = {"SnackBar",//0
            "TextInputLayout", //1
            "FloatingActionButton",//2
            "CoordinatorLayout",//3
            "AppBarLayout", //4
            "CollapsingToolbarLayout", //5
            "CardView", //6
            "RippleView",//7
            "item9", "item10", "item11", "item12", "item13", "itme14"};

    private Context mContext;

    private int orientation;

    public RecyclerViewAdapter(Context mContext, int orientation) {
        this.mContext = mContext;
        this.orientation = orientation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bauer_recycler_item, parent, false);
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = ScreenUtil.getScreenWidth(mContext) / 3;
            params.height = ScreenUtil.getScreenHeight(mContext) * 2 / 3;
            view.setLayoutParams(params);
        } else if (orientation == LinearLayoutManager.VERTICAL) {

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setBackgroundColor(mContext.getResources().getColor(colors[position % (colors.length)]));
        holder.mTextView.setText(contents[position]);

        holder.rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                switch (position) {
                    case 0://snackbar
//                        Snackbar.make(v, "SnackBar", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(mContext, "点击了SnackBar", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
                        Snackbar snackbar = Snackbar.make(rippleView, "SnackBar", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "点击了SnackBar", Toast.LENGTH_SHORT).show();
                            }
                        });
                        snackbar.getView().setBackgroundColor(mContext.getResources().getColor(colors[position % (colors.length)]));
                        snackbar.show();
                        break;

                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        mContext.startActivity(new Intent(mContext, DesignDetailActivity.class));
                        break;

                    case 7:
                        mContext.startActivity(new Intent(mContext, RippleViewActivity.class));
                        break;

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;
        public final RippleView rippleView;

        public ViewHolder(View view) {
            super(view);
            rippleView = (RippleView) view.findViewById(R.id.rippleView);
            mTextView = (TextView) view.findViewById(R.id.ripple_tv);
        }
    }
}
