package com.example.demo.activity;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.demo.R;
import com.example.demo.util.CameraUtil;

import java.io.File;
import java.util.List;

public class CameraActivity extends BaseActivity implements View.OnTouchListener, SurfaceHolder.Callback {
    private SurfaceView mySurfaceView = null;
    private SurfaceHolder mySurfaceHolder = null;
    int screenWidth, myzoommax;  // 屏幕宽度， 可缩放尺寸。
    private Camera mycamera = null;  // camera 对象
    private Camera.Parameters myParameters;  //相机的属性
    private boolean isSupportContinuous = false; // 判断手机是否支持  Continus聚焦模式
    private Camera.AutoFocusCallback myAutoFocusCallback; //自动聚焦
    private int cameraPosition = 1; // 前后置摄像头的标志。
    private View viewFocus;
    float pointX, pointY;
    private int ro; //纪录手机旋转的角度。
    private SensorManager mSensorManager = null;
    private Sensor mSensor = null;
    private float x1 = 0, y1 = 0, z1 = 0;   // 重力感应需要用到。  重力感应的作用： 1，根据重力旋转图片，2，设置成正确的角度属性
    private File file;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mSensorManager) {
            mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(lsn, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(lsn);
        mSensorManager = null;
    }

    private void initView() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mySurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, (int) (screenWidth * 4 / 3)));
        mySurfaceHolder = mySurfaceView.getHolder();
        mySurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);  //设置背景透明
        mySurfaceView.setOnTouchListener(this);
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        viewFocus = findViewById(R.id.view_focus);
        file = new File(Environment.getExternalStorageDirectory().getPath() + "/cameraDemo/");
        file.mkdirs();// 创建根目录文件夹

        // 自动对焦的回调函数  聚焦
        myAutoFocusCallback = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {// 调用一次自动对焦
                // TODO Auto-generated method stub
                if (success) {
                    if (cameraPosition == 1) {
                        if (isSupportContinuous) {
                            myParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                            camera.setParameters(myParameters);
//                            Log.e("======="," 聚焦成功 ");
                        }
                    }
                } else {
                    // 聚焦不成功。
                }
            }
        };
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 触摸对焦
        int nCnt = event.getPointerCount();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pointX = event.getX();
            pointY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mycamera != null) {
                CameraUtil.focusOnTouch(event, mySurfaceView, viewFocus, mycamera);  //测光区
                handler.postDelayed(runnable, 1000); // 聚焦
            }
            // 聚焦时 显示聚焦view
            RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(viewFocus.getLayoutParams());
            layout.setMargins((int) pointX, (int) pointY, 0, 0);
            viewFocus.setLayoutParams(layout);
            viewFocus.setVisibility(View.VISIBLE);
            ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f); //设置动画。
            sa.setDuration(800);
            viewFocus.startAnimation(sa);
            handler.postDelayed(runnable1, 800); // 0.8秒后让聚焦动画消失。
        } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt) {
        } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE && 2 == nCnt) {
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initcamera(90, 0);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mycamera != null) {  //释放掉 camera。
            mycamera.stopPreview();
            mycamera.release();
            mycamera = null;
        }
        handler.removeCallbacks(runnable);
    }

    /**
     * 初始化camera
     */
    public boolean initcamera(int rotation, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);// 得到每一个摄像头的信息
        int result = 0;
        if (mycamera == null) {
            try {
                mycamera = Camera.open(cameraId);// 打开相机
            } catch (Exception e) {
                // TODO: handle exceptionisview
                //打开相机失败。
                return false;
            }
        }
        if (mycamera != null) {
            try {
                myParameters = mycamera.getParameters();
                // 获取可支持的最大放大尺寸
                myzoommax = myParameters.getMaxZoom();
                myParameters.setPictureFormat(PixelFormat.JPEG);
                myParameters.set("jpeg-quality", 100);// 照片质量，从 1——100
                // 获取手机可支持的preview尺寸
                List<Camera.Size> previewsizes = myParameters.getSupportedPreviewSizes();// 获取系统可支持的预览尺寸
                List<Camera.Size> photoSizes = myParameters.getSupportedPictureSizes();// 获取系统可支持的图片尺寸
                int previewsize_width = 1;
                int previewsize_height = 1;
                int picturesize_width = 1;
                int picturesize_height = 1;
                for (Camera.Size size : previewsizes) {// 设置为4：3的预览尺寸,有些手机遍历出来的结果从大到小，有的手机从小到大，所以只要一个最大的尺寸
                    if (size.width / 4 == size.height / 3) {
                        if (size.width > previewsize_width) {
                            previewsize_width = size.width;
                            previewsize_height = size.height;
                        }
                    }
                }
                for (Camera.Size size : photoSizes) {// 设置为4：3图片尺寸，原理同上
                    if (size.width / 4 == size.height / 3) {
                        if (size.width > picturesize_width) {
                            picturesize_width = size.width;
                            picturesize_height = size.height;
                        }
                    }
                }

                myParameters.setPreviewSize(previewsize_width,
                        previewsize_height);
                myParameters.setPictureSize(picturesize_width,
                        picturesize_height);

                myParameters.set("rotation", rotation);// 旋转角度
                if (cameraId == 0) {
                    List<String> focuseMode = (myParameters
                            .getSupportedFocusModes());  // 支持的聚焦模式。
                    for (int i = 0; i < focuseMode.size(); i++) {
                        if (focuseMode.get(i).equals("continuous-picture")) {   // 该手机如果支持这种对焦模式的话。
                            myParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                            isSupportContinuous = true;
                            break;
                        } else {
                            myParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                            isSupportContinuous = false;
                        }
                    }
                }
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    result = (info.orientation) % 360;
                    result = (360 - result) % 360;
                } else {
                    result = (info.orientation + 360) % 360;
                }
                mycamera.setDisplayOrientation(result);// 显示方向旋转角度
                mycamera.setParameters(myParameters);
                mycamera.setPreviewDisplay(mySurfaceHolder);
                mycamera.startPreview();// 开始预览
                if (cameraId == 0) {
                    mycamera.cancelAutoFocus();// 取消当前的自动对焦，保证下一次对焦的开始
                    mycamera.autoFocus(myAutoFocusCallback);// 自动对焦
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return true;
    }

    //  延迟 一秒聚焦。
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mycamera.autoFocus(myAutoFocusCallback);
        }
    };

    //  延迟 隐藏聚焦图片。
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            viewFocus.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_takephoto:
                //点击拍照之后的操作。
                setCameraRotation();// 设置Camera 角度属性。
                mycamera.takePicture(null, null, myjpegCallback);
                break;
            default:
                break;
        }
    }

    /**
     * 设置Camara角度，极其重要。 在拍照前调用。
     */
    private void setCameraRotation() {
//        if(frame_imageView.isShown()){
//            //如果 照片电影模式存在。就不设置相机的属性。   如果不需要拍照之后转换成Bitmaop，则拍照前不设置camera角度属性。
//        }else{
        if (cameraPosition == 1) {
            ro = ro % 360;
        } else {
            ro = (360 - ro) % 360;
        }
        myParameters.set("rotation", ro);
        mycamera.setParameters(myParameters);
//        }
    }


    /*
    * 重力感应传感器，用于旋转图标 和 生成正确角度的照片。
    */
    // 重力感应 的监听，负责图标的动态旋转
    SensorEventListener lsn = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float axis_x, axis_y, axis_z;
            axis_x = event.values[SensorManager.DATA_X];
            axis_y = event.values[SensorManager.DATA_Y];
            axis_z = event.values[SensorManager.DATA_Z];

            if (x1 == 0 && y1 == 0 && z1 == 0) {
                x1 = axis_x;
                y1 = axis_y;
                z1 = axis_z;

            }
            //曾经 根据重力感应对焦。
//				if (Math.sqrt((axis_x - x1) * (axis_x - x1) + (axis_y - y1)
//						* (axis_y - y1) + (axis_z - z1) * (axis_z - z1)) > 1) {
//					if (cameraPosition == 1) {
//						if (!isfocus) {
//							if(System.currentTimeMillis() - timeLong > 4000){
//								System.out.println("need do focus");
//								if(camera_flash == 2){
//									myParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
//									mycamera.setParameters(myParameters);
//								}
//								if(!taking_flag){
//									handler2.postDelayed(runnable2, 2000);
//								}
//
//								isfocus = true;
//							}
//						}
//					}
//
//				}
            if (axis_y > Math.abs(axis_x)) {// 手机竖直的时候,先判断方向，再判断是不是处于临界点
                if (axis_y > Math.abs(axis_x) + 1) {// 通过+1，区分临界点
                    ro = 90;
                }
            } else if (axis_x > Math.abs(axis_y)) {// 向左旋转了90的状态
                if (axis_x > Math.abs(axis_y) + 1) {
                    ro = 0;
                }
            } else if ((axis_y < axis_x && axis_x < 0)
                    || (Math.abs(axis_y) > axis_x && axis_x > 0)) {// 手机倒立的状态
                if ((axis_y < axis_x - 1 && axis_x < 0)
                        || (Math.abs(axis_y) > axis_x + 1 && axis_x > 0)) {
                    ro = 270;
                }
            } else if ((axis_x < axis_y && axis_y < 0)
                    || (Math.abs(axis_x) > axis_y && axis_y > 0)) {// 向右旋转了90的状态
                if ((axis_x < axis_y - 1 && axis_y < 0)
                        || (Math.abs(axis_x) > axis_y + 1 && axis_y > 0)) {
                    ro = 180;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    // 回调函数，合成图片保存图片都在这里处理
    Camera.PictureCallback myjpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File photoFile = new File(file + "/"
                    + System.currentTimeMillis() + ".JPG");
            mycamera.stopPreview();
            // 正常模式拍照 .  直接保存二进制文件。 提高保存速度。
            CameraUtil.outputPhotoForStream(photoFile, data);
            mycamera.startPreview();
            CameraUtil.scan(photoFile.toString(), CameraActivity.this); //刷新系统图库。

            //如果需要转化为Bitmap，
//            if (pictBitmap.getWidth() > pictBitmap.getHeight()) {  // 三星的所有拍照都经过这里。适用，代表 另一个系列手机。
//                if (cameraPosition == 0) {
//                    pictBitmap = rotate(pictBitmap, 90 , true);
//                }else{
//                    pictBitmap = rotate(pictBitmap, 90 , false);
//                }
//            }else{
//                //vivo 代表国产手机
//                if (cameraPosition == 0) { //vivo 前置摄像头。
//                    pictBitmap = rotate(pictBitmap, 0 , true);
//                }
//            }
        }
    };


    /*
        * 根据角度旋转Bitmap，flag 为 是否打开镜像开关。
        */
//    private Bitmap rotate(Bitmap bitmap,int oritation,boolean flag){
//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
//        Matrix mtx = new Matrix();
//        if (flag) {
//            mtx.postScale(-1, 1);
//        }
//        mtx.postRotate(oritation);
//        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
//    }
}
