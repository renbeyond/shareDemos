package com.example.demo.peng.function.androidseven.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *
 * http://www.devio.org/2016/09/28/Android7.0适配心得/
 * */
public class FileProviderActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_takePhoto;
    ImageView img;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static String IMG_PATH = getSDPath() + java.io.File.separator
    + "test/";

    private static final int PHOTO_CAPTURE = 0x11;
    private static final int PHOTO_RESULT = 0x12;
    private boolean hasPermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_provider);
        btn_takePhoto = (Button) findViewById(R.id.takePhoto);
        img = (ImageView) findViewById(R.id.imageview);
        btn_takePhoto.setOnClickListener(this);
        requesPermission();

    }

    public static boolean checkPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }else if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }

    private void requesPermission() {
       hasPermission =  checkPermission(FileProviderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) && checkPermission(FileProviderActivity.this, Manifest.permission.CAMERA);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(FileProviderActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_STORAGE_PERMISSION);
            return;
        }
    }

    @Override
    public void onClick(View view) {
        if (!hasPermission) {
            Toast.makeText(FileProviderActivity.this, "设置读写权限", Toast.LENGTH_SHORT).show();
        }

        switch (view.getId()) {
            case R.id.takePhoto:
                File file = new File(IMG_PATH, "temp.jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();

                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Uri imageUri = FileProvider.getUriForFile(FileProviderActivity.this, "com.pictureworks.www.fileprovider", file);
                Log.e("imageUri", imageUri.toString());
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PHOTO_CAPTURE);
                break;
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    Bitmap bitmapSelected;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("result","cancle");
            return;
        }

        if (requestCode == PHOTO_CAPTURE) {
            Log.e("result", "PHOTO_CAPTURE");
            startPhotoCrop(FileProvider.getUriForFile(FileProviderActivity.this, "com.pictureworks.www.fileprovider", new File(IMG_PATH, "temp.jpg")));

        }

        if (requestCode == PHOTO_RESULT) {
            Log.e("result", "PHOTO_RESULT");
            bitmapSelected = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH,
                    "temp_cropped.jpg")));
            img.setImageBitmap(bitmapSelected);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startPhotoCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Uri uri1 = FileProvider.getUriForFile(FileProviderActivity.this, "com.pictureworks.www.fileprovider", new File(IMG_PATH, "temp_cropped.jpg"));
        intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri1 ));
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri1);
		intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_RESULT);
    }

}
