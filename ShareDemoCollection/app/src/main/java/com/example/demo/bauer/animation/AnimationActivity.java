package com.example.demo.bauer.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.util.ScreenUtil;

public class AnimationActivity extends Activity implements OnClickListener{
	private Button startButton;
	private ImageView buyImg;
	private ViewGroup animMaskLayout;//动画层
	private TextView cartCountTextView;
	private int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_animation);
		startButton = (Button) findViewById(R.id.start_btn);
		cartCountTextView = (TextView) findViewById(R.id.textview_cart_count);
		startButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_btn:
			buyImg = new ImageView(this);// buyImg是动画的图片
			buyImg.setImageResource(R.drawable.addtocart);// 设置buyImg的图片
			setAnim(buyImg);
			break;

		default:
			break;
		}

	}


	private void setAnim(final View v) {
		animMaskLayout = null;
		animMaskLayout = createAnimLayout();
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		start_location[0] = ScreenUtil.getScreenWidth(this)/2-80;//减去的值和图片大小有关系
		start_location[1] = ScreenUtil.getScreenHeight(this)/2-76;
		// 将组件添加到我们的动画层上
		final View view = addViewToAnimLayout(animMaskLayout, v,start_location);
		int[] end_location = new int[2];
		cartCountTextView.getLocationInWindow(end_location);
		// 计算位移
		final int endX = end_location[0] - start_location[0];
		final int endY = end_location[1] - start_location[1];
		System.out.println("endx is " + endX);
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setInterpolator(new LinearInterpolator());//匀速
		scaleAnimation.setRepeatCount(0);//不重复
		scaleAnimation.setFillAfter(true);//停在最后动画
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(scaleAnimation);
		set.setDuration(500);//动画整个时间
		view.startAnimation(set);//开始动画
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				//x轴的路径动画，匀速
				TranslateAnimation translateAnimationX = new TranslateAnimation(0,
						endX, 0, 0);
				translateAnimationX.setInterpolator(new LinearInterpolator());
				translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
				//y轴的路径动画，加速
				TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
						0, endY);
				translateAnimationY.setInterpolator(new AccelerateInterpolator());
				translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.1f, 1.0f, 0.1f, 
						Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				AnimationSet set2 = new AnimationSet(false);
				//要先添加形状的，后添加位移的，不然动画效果不能达到要求
				set2.addAnimation(scaleAnimation);
				set2.addAnimation(translateAnimationY);
				set2.addAnimation(translateAnimationX);

				set2.setFillAfter(false);
				set2.setStartOffset(200);//等待时间
				set2.setDuration(800);// 动画的执行时间
				view.startAnimation(set2);
				set2.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						v.setVisibility(View.GONE);//控件消失    
						cartCountTextView.setVisibility(View.VISIBLE);
						i++;
						cartCountTextView.setText(i+"");
					}
				});
			}
		});
	}

	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		vg.addView(view);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

}
