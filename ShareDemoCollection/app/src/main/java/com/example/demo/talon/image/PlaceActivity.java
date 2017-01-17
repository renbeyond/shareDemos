package com.example.demo.talon.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.demo.R;
import com.example.demo.filter.FilmFilter;
import com.example.demo.util.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PlaceActivity extends Activity implements View.OnClickListener{
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Button btnOpen,btnDeal;
    private ImageView ivMain;
    private Bitmap cutBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        ivMain = (ImageView) findViewById(R.id.ivMain);
        btnOpen.setOnClickListener(this);
        btnDeal = (Button) findViewById(R.id.btnDeal);
        btnDeal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpen:
                Intent getAlbum = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
                break;
            case R.id.btnDeal:
                if (cutBitmap != null){
                    dealBitmap();
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    startPhotoZoom(data.getData());
                } else {
                    return;
                }
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                try {
                    if (cropUri != null){
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(cropUri));
                        if (bitmap != null) {
                            dealData(bitmap);
                        }
                    }else {
                        return;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    Uri cropUri;
    /**
     * 裁减图片
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 7);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 1600);
        intent.putExtra("outputY", 700);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);// 缩放
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scaleUpIfNeeded", true);// 如果小于要求输出大小，就放大
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);// 关闭人脸识别
        String cropImageName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date()) +
                "-1-" + System.currentTimeMillis() + ".jpg";
        File cropFile = new File(getExternalCacheDir(), cropImageName);
        //注意到此处使用的file:// uri类型.
        cropUri = Uri.fromFile(cropFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 处理截图返回的数据
     */
    private void dealData(Bitmap bitmap){
        cutBitmap = bitmap;
        ivMain.setImageBitmap(bitmap);
    }

    /**
     * 具体处理图像的方法
     */
    private void dealBitmap(){
        cutBitmap = new FilmFilter().transform(cutBitmap,getResources());
        Bitmap resultBitmap = BitmapUtils.filmBitmap(cutBitmap);
        ivMain.setImageBitmap(resultBitmap);
//        BitmapUtils.saveBitmap(resultBitmap, Environment.getExternalStorageDirectory().toString() + "/DCIM/test.jpg"); //保存到手机
    }

}
