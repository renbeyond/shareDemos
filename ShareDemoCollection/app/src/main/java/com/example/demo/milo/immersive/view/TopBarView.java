package com.example.demo.milo.immersive.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Created by milo on 15/11/24.
 * 自定义topbar
 * 1.自定义属性（attr.xml）
 * 2.获取属性（TypedArray）
 * 3.添加View并赋值 (LayoutParams)
 * 4.添加事件（interface）
 * 5.创建特殊方法
 */
public class TopBarView extends RelativeLayout {
    private TextView leftTextView, rightTextView, titleTextView;//声明控件

    //声明左边TextView属性
    private String leftText;
    private int leftColor;
    private int leftSize;
    private Drawable leftBackground;

    //声明右边TextView属性
    private String rightText;
    private int rightColor;
    private int rightSize;
    private Drawable rightBackground;

    //声明中间TextView属性
    private String title;
    private int titleSize;
    private int titleColor;

    //事件监听
    private onClickListener listener;

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //2.获取自定义属性数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarViewAttr);

        //获取左边TextView属性
        leftText = typedArray.getString(R.styleable.TopBarViewAttr_leftText);
        leftColor = typedArray.getColor(R.styleable.TopBarViewAttr_leftColor, getResources().getColor(R.color.app_main_bg_color));
        leftSize = typedArray.getDimensionPixelSize(R.styleable.TopBarViewAttr_leftSize, 14);
        leftBackground = typedArray.getDrawable(R.styleable.TopBarViewAttr_leftBackground);


        //获取右边TextView属性
        rightText = typedArray.getString(R.styleable.TopBarViewAttr_rightText);
        rightColor = typedArray.getColor(R.styleable.TopBarViewAttr_rightColor, getResources().getColor(R.color.app_main_bg_color));
        rightSize = typedArray.getDimensionPixelSize(R.styleable.TopBarViewAttr_rightSize, 14);
        rightBackground = typedArray.getDrawable(R.styleable.TopBarViewAttr_rightBackground);

        //获取中间TextView属性
        title = typedArray.getString(R.styleable.TopBarViewAttr_titleText);
        titleSize = typedArray.getDimensionPixelSize(R.styleable.TopBarViewAttr_titleSize, 24);
        titleColor = typedArray.getColor(R.styleable.TopBarViewAttr_titleColor, getResources().getColor(R.color.app_main_bg_color));

        //释放、回收
        typedArray.recycle();

        //3.添加View并赋值
        leftTextView = new TextView(context);
        rightTextView = new TextView(context);
        titleTextView = new TextView(context);

        //设置左边TextView显示位置和值
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftTextView.setText(leftText);
        leftTextView.setTextColor(leftColor);
        leftTextView.setTextSize(leftSize);
        leftTextView.setBackground(leftBackground);


        //设置右边TextView显示位置和值
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        rightTextView.setText(rightText);
        rightTextView.setTextColor(rightColor);
        rightTextView.setTextSize(rightSize);
        rightTextView.setBackground(rightBackground);


        //设置中间标题显示位置和值
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        titleTextView.setText(title);
        titleTextView.setTextColor(titleColor);
        titleTextView.setTextSize(titleSize);

        //添加视图
        addView(leftTextView, leftParams);
        addView(rightTextView, rightParams);
        addView(titleTextView, titleParams);


        //监听左边TextView点击事件
        leftTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.leftOnclick();
            }
        });

        //监听右边TextView点击事件
        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.rightOnclick();
            }
        });

    }

    /**
     * 设置左边TextView背景
     *
     * @param id
     */
    public void setLeftBackground(int id) {
        leftTextView.setBackgroundResource(id);
    }

    /**
     * 设置标题栏左边TextView不可点击
     */
    public void setLeftClickable(boolean clickable) {
        leftTextView.setClickable(clickable);
    }

    /**
     * 设置标题栏左边TextView隐藏
     */
    public void setLeftGONE() {
        leftTextView.setVisibility(View.GONE);
    }


    /**
     * 4.1 定义点击事件接口
     */
    public interface onClickListener {
        void leftOnclick();

        void rightOnclick();
    }

    /**
     * 4.2 创建点击监听的方法
     *
     * @param listener
     */
    public void onClickListener(onClickListener listener) {
        this.listener = listener;
    }


}
