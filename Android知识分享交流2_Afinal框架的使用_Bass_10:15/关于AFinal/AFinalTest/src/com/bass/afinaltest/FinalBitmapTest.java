package com.bass.afinaltest;

import android.os.Bundle;
import android.widget.ImageView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
/**
 * Bitmap
 * 1、初始化FinalBitmap.create(this);
 * @author bass
 *
 */
public class FinalBitmapTest extends FinalActivity {
	private FinalBitmap finalBitmap;
	@ViewInject(id=R.id.img)ImageView imageView1;
	@ViewInject(id=R.id.img2)ImageView imageView2;
	@ViewInject(id=R.id.img3)ImageView imageView3;
	private String urlString="http://pic7.nipic.com/20100609/5142239_155513016157_2.jpg";
	private String urlString2="http://pic31.nipic.com/20130720/13137154_114650398154_2.jpg";
	private String urlString3="http://b.hiphotos.baidu.com/zhidao/pic/item/32fa828ba61ea8d384f77810970a304e241f58c8.jpg";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_bitmap);
		finalBitmap = FinalBitmap.create(this);
		finalBitmap.display(imageView1, urlString);
		finalBitmap.display(imageView2, urlString2);
		finalBitmap.display(imageView3, urlString3);
	}
	
}
