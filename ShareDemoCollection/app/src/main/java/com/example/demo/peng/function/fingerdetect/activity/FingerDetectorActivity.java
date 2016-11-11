package com.example.demo.peng.function.fingerdetect.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.peng.function.fingerdetect.crypto.CryptoObjectHelper;

import java.lang.ref.WeakReference;

/**
 * FingerprintManagerCompat要在6.0以上才可以使用
 *
 * <br/>
 *
 * 如果要满足6.0一下版本使用，需要通过反射获取设备是否支持
 *  <br/>
 * <uses-permission android:name="android.permission.USE_FINGERPRINT"/>
 * <br/>
 *
 * http://www.cnblogs.com/Fndroid/p/5204986.html
 * <br/>
 * http://blog.csdn.net/baniel01/article/details/51991764
 *
 * */
public class FingerDetectorActivity extends AppCompatActivity implements View.OnClickListener{


    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    TextView tv;
    private static final String SETTING = "setting";
    private static final String HAS_FINGERPRINT_API = "hasFingerApi";
    private FingerprintManagerCompat fingerprintManagerCompat;
    private CancellationSignal cancellationSignal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_detect);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        tv = (TextView) findViewById(R.id.tv);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn5.setEnabled(false);


        SharedPreferences sp = getSharedPreferences(SETTING, MODE_PRIVATE);
        if (sp.contains(HAS_FINGERPRINT_API)) { // 检查是否存在该值，不必每次都通过反射来检查
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager"); // 通过反射判断是否存在该类
            editor.putBoolean(HAS_FINGERPRINT_API, true);
        } catch (ClassNotFoundException e) {
            editor.putBoolean(HAS_FINGERPRINT_API, false);
            e.printStackTrace();
        }
        editor.apply();

    }


    @Override
    public void onClick(View view) {
        if (getSharedPreferences(SETTING, MODE_PRIVATE).getBoolean(HAS_FINGERPRINT_API, false)) {
            if (fingerprintManagerCompat == null) {
                fingerprintManagerCompat = FingerprintManagerCompat.from(this);
            }
            switch (view.getId()) {
                case R.id.btn1:
                    if (!fingerprintManagerCompat.isHardwareDetected()) { //硬件是否支持
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("指纹识别").setMessage("当前设备不支持指纹").setCancelable(true).setPositiveButton("确定", null).create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("指纹识别").setMessage("当前设备支持指纹").setCancelable(true).setPositiveButton("确定", null).create().show();
                    }
                    break;

                case R.id.btn2:
                /*
                 * 这个条件的意思是，你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行。为什么是这样呢？因为google原生的逻辑就是：想要使用指纹识别的话，必须首先使能屏幕锁才行
                 * */
                    if (Build.VERSION.SDK_INT >= 16) {
                        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                        if (!keyguardManager.isKeyguardSecure()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("指纹识别").setMessage("当前设备没有前提密码").setCancelable(true).setPositiveButton("确定", null).create().show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("指纹识别").setMessage("当前设备有前提密码").setCancelable(true).setPositiveButton("确定", null).create().show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("指纹识别").setMessage("此功能需要APi在16以上包含16").setCancelable(true).setPositiveButton("确定", null).create().show();
                    }
                    break;

                case R.id.btn3:
                    if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("指纹识别").setMessage("当前设备没有设置指纹").setCancelable(true).setPositiveButton("确定", null).create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("指纹识别").setMessage("当前设备有设置指纹").setCancelable(true).setPositiveButton("确定", null).create().show();
                    }
                    break;

                case R.id.btn4:

                    if (fingerprintManagerCompat.isHardwareDetected() && fingerprintManagerCompat.hasEnrolledFingerprints()) {

                        try {
                            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
                            if (cancellationSignal == null) {
                                cancellationSignal = new CancellationSignal();
                            }
                            /**
                             * 开始验证，什么时候停止由系统来确定，如果验证成功，那么系统会关系sensor，如果失败，则允许
                             * 多次尝试，如果依旧失败，则会拒绝一段时间，然后关闭sensor，过一段时候之后再重新允许尝试
                             *
                             * 第四个参数为重点，需要传入一个FingerprintManagerCompat.AuthenticationCallback的子类
                             * 并重写一些方法，不同的情况回调不同的函数
                             */
                            fingerprintManagerCompat.authenticate(cryptoObjectHelper.buildCryptoObject(), 0, cancellationSignal, new AuthCallBack(FingerDetectorActivity.this), null);
                            btn4.setEnabled(false);
                            btn5.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case R.id.btn5:

                    if (cancellationSignal != null) {
                        cancellationSignal.cancel();
                        cancellationSignal = null;
                        btn4.setEnabled(true);
                        btn5.setEnabled(false);
                    }
                    break;
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("指纹识别").setMessage("当前设备不支持指纹").setCancelable(true).setPositiveButton("确定", null).create().show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
    }

    private void handlerError(int errId) {
        switch (errId) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                tv.setText(R.string.ErrorCanceled_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                tv.setText(R.string.ErrorHwUnavailable_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                tv.setText(R.string.ErrorLockout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                tv.setText(R.string.ErrorNoSpace_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                tv.setText(R.string.ErrorTimeout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                tv.setText(R.string.ErrorUnableToProcess_warning);
                break;
        }
    }

    private void handlerFailed() {
        tv.setText("指纹验证失败，请重试。。。");
    }

    private void handlerHelp(int id) {
        switch (id) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:
                tv.setText(R.string.AcquiredGood_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:
                tv.setText(R.string.AcquiredImageDirty_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:
                tv.setText(R.string.AcquiredInsufficient_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:
                tv.setText(R.string.AcquiredPartial_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:
                tv.setText(R.string.AcquiredTooFast_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:
                tv.setText(R.string.AcquiredToSlow_warning);
                break;
        }
    }

    private void handlerSuccess() {
        tv.setText("指纹验证成功");
    }

    static class AuthCallBack extends FingerprintManagerCompat.AuthenticationCallback{

        WeakReference<FingerDetectorActivity> mActivity;

        public AuthCallBack(FingerDetectorActivity activity) {
            mActivity = new WeakReference<FingerDetectorActivity>(activity);
        }

        /**
         * 这个接口会再系统指纹认证出现不可恢复的错误的时候才会调用，并且参数errorCode就给出了错误码，标识了错误的原因。这个时候app能做的只能是提示用户重新尝试一遍。
         * */

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().handlerError(errMsgId);

        }

        /**
         * OnAuthenticationFailed() 这个接口会在系统指纹认证失败的情况的下才会回调。
         * 注意这里的认证失败和上面的认证错误是不一样的，虽然结果都是不能认证。认证失败是指所有的信息都采集完整，
         * 并且没有任何异常，但是这个指纹和之前注册的指纹是不相符的；但是认证错误是指在采集或者认证的过程中出现了错误，
         * 比如指纹传感器工作异常等。也就是说认证失败是一个可以预期的正常情况，而认证错误是不可预期的异常情况
         *
         * */

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            if (mActivity.get() == null) {
                return;
            }

            mActivity.get().handlerFailed();
        }

        /**
         *
         * 上面的认证失败是认证过程中的一个异常情况，
         * 我们说那种情况是因为出现了不可恢复的错误，
         * 而我们这里的OnAuthenticationHelp方法是出现了可以回复的异常才会调用的。
         * 什么是可以恢复的异常呢？一个常见的例子就是：手指移动太快，当我们把手指放到传感器上的时候，如果我们很快地将手指移走的话，
         * 那么指纹传感器可能只采集了部分的信息，因此认证会失败。
         * 但是这个错误是可以恢复的，因此只要提示用户再次按下指纹，并且不要太快移走就可以解决。
         * */
        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().handlerHelp(helpMsgId);
        }

        /**
         * 我们的CryptoObject不是null的话，
         * 那么我们在这个方法中可以通过AuthenticationResult来获得Cypher对象然后调用它的doFinal方法。
         * doFinal方法会检查结果是不是会拦截或者篡改过，如果是的话会抛出一个异常。
         * 当我们发现这些异常的时候都应该将认证当做是失败来来处理，为了安全建议大家都这么做。
         * */

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().handlerSuccess();

        }
    }
}
