package com.example.demo.talon.image;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
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
                if (data != null){
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor =getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
                        dealData(bitmap);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }else {
                return;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

}

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
