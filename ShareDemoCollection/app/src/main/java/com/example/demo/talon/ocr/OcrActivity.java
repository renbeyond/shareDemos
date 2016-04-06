package com.example.demo.talon.ocr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OcrActivity extends Activity implements View.OnClickListener{
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Button btnOpen;
    private Button btnDeal;
    private ImageView ivMain;
    private TextView tvShow;
    Bitmap bitmap;
    private String PATH = Environment.getExternalStorageDirectory().getPath() + "/Demo/";
    private String OCR_PATH = Environment.getExternalStorageDirectory().getPath() + "/Demo/tessdata/";
    private String OCR_DATA_PATH = Environment.getExternalStorageDirectory().getPath() + "/Demo/tessdata/eng.traineddata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        btnOpen = (Button) findViewById(R.id.btn_open);
        btnOpen.setOnClickListener(this);
        btnDeal = (Button) findViewById(R.id.btn_deal);
        btnDeal.setOnClickListener(this);
        ivMain = (ImageView) findViewById(R.id.imageView_main);
        tvShow = (TextView) findViewById(R.id.tv_show);
        File ocrFile = new File(PATH);
        if (!ocrFile.exists()){
            ocrFile.mkdirs();
        }

        File tessdata = new File(OCR_PATH); //创建文件夹。
        if (!tessdata.exists()){
            tessdata.mkdirs();
        }

        // 移动OCR 需要的data 到SD卡上。
        if (!(new File(OCR_DATA_PATH)).exists()){
            try {
                copyDataToSD(OCR_DATA_PATH);
            }catch (Exception e){

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open:
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
                break;
            case R.id.btn_deal:
                String text = doOcr(bitmap, "eng");
                tvShow.setText(text);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
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
                        bitmap = BitmapFactory.decodeFile(picturePath);
                        ivMain.setImageBitmap(bitmap);
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
//        intent.putExtra("aspectX", 16);
//        intent.putExtra("aspectY", 7);
//        // outputX,outputY 是剪裁图片的宽高
//        intent.putExtra("outputX", 1600);
//        intent.putExtra("outputY", 700);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);// 缩放
        intent.putExtra("scaleUpIfNeeded", true);// 如果小于要求输出大小，就放大
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);// 关闭人脸识别
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }



    /**
     * 具体的识别方法
     * @param bitmap
     * @param language
     * @return
     */
    private String doOcr(Bitmap bitmap, String language) {
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(PATH, language);

        // 必须加此行，tess-two要求BMP必须为此配置
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        baseApi.setImage(bitmap);
        String text = baseApi.getUTF8Text();
        baseApi.clear();
        baseApi.end();
        return text;
    }




    /**
     * 复制文件 到 SD 卡中
     * @param strOutFileName
     * @throws IOException
     */
    private void copyDataToSD(String strOutFileName) throws IOException
    {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = this.getAssets().open("ocrdata/eng.traineddata");
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }
}
