package com.example.demo.bauer.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.util.ScreenUtil;

@SuppressLint("NewApi") public class ObjectAnimatorActivity extends Activity implements OnClickListener{
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
			System.out.println("onclick");
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
		start_location[0] = ScreenUtil.getScreenWidth(this)/2- 80;//减去的值和图片大小有关系
		start_location[1] = ScreenUtil.getScreenHeight(this)/2- 76;
		// 将组件添加到我们的动画层上
		final View view = addViewToAnimLayout(animMaskLayout, v,start_location);
		int[] end_location = new int[2];
		cartCountTextView.getLocationInWindow(end_location);
		// 计算位移
		final int endX = end_location[0] - start_location[0];
		final int endY = end_location[1] - start_location[1];
		System.out.println("translate x is " + endX);
		
		final int startX = start_location[0];
		final int startY = start_location[1];
		
		ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f).setDuration(500);
		ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f).setDuration(500);
		AnimatorSet animationSet = new AnimatorSet();
		animationSet.playTogether(objectAnimatorX, objectAnimatorY);
		
		animationSet.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				ObjectAnimator translateX = ObjectAnimator.ofFloat(view, "translationX", 0, endX - 160).setDuration(800);
				ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", 0, endY - 152).setDuration(800);
//				ObjectAnimator translateX = ObjectAnimator.ofFloat(view, "x", startX, startX + endX - 160).setDuration(800);
//				ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "y", startY, startY + endY - 152).setDuration(800);
				ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.1f).setDuration(800);
				ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.1f).setDuration(800);
				translateX.setInterpolator(new LinearInterpolator());
				translateY.setInterpolator(new AccelerateInterpolator());
				AnimatorSet animationSet = new AnimatorSet();
				animationSet.playTogether(scaleX, translateX, scaleY, translateY);
//				animationSet.playSequentially(scaleX, translateX, scaleY, translateY);
				animationSet.setStartDelay(200);
				
				animationSet.addListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animator animation) {
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {
						v.setVisibility(View.GONE);//控件消失    
						cartCountTextView.setVisibility(View.VISIBLE);
						i++;
						cartCountTextView.setText(i+"");
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
					}
				});
				animationSet.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		
		
		animationSet.start();
		
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
