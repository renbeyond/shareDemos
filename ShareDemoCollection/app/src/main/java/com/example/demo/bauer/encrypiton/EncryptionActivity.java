package com.example.demo.bauer.encrypiton;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;
import com.example.demo.bauer.encrypiton.db.PictureAirDbManager;
import com.gc.materialdesign.widgets.Dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by bauer_bao on 16/4/11.
 */
public class EncryptionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button aesButton;
    private Button sqlCipherButton;
    private Button httpsButton;
    private Button md5Button;
    private Button sha1Button;
    private Button base64Button;
    private Button desButton;
    private Button rsaButton;
    private Button diyButton;
    private Button ndkButton;

    private boolean base64Flag = false;
    private boolean aesFlag = false;
    private boolean sqlcipherFlag = false;

    private PictureAirDbManager pictureAirDbManager;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    Dialog httpsDialog = new Dialog(EncryptionActivity.this, "HTTPS:", msg.obj.toString());
                    httpsDialog.addCancelButton("Cancel");
                    httpsDialog.show();
                    httpsDialog.getButtonAccept().setText("OK");
                    httpsDialog.getButtonAccept().setBackgroundColor(Color.RED);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        md5Button = (Button) findViewById(R.id.encryption_md5);
        sha1Button = (Button) findViewById(R.id.encryption_sha1);
        base64Button = (Button) findViewById(R.id.encryption_base64);
        aesButton = (Button) findViewById(R.id.encryption_aes);
        desButton = (Button) findViewById(R.id.encryption_des);
        rsaButton = (Button) findViewById(R.id.encryption_rsa);
        diyButton = (Button) findViewById(R.id.encryption_diy);
        sqlCipherButton = (Button) findViewById(R.id.encryption_sqlcipher);
        httpsButton = (Button) findViewById(R.id.encryption_https);
        ndkButton = (Button) findViewById(R.id.encryption_ndk);

        md5Button.setOnClickListener(this);
        sha1Button.setOnClickListener(this);
        base64Button.setOnClickListener(this);
        aesButton.setOnClickListener(this);
        desButton.setOnClickListener(this);
        rsaButton.setOnClickListener(this);
        diyButton.setOnClickListener(this);
        sqlCipherButton.setOnClickListener(this);
        httpsButton.setOnClickListener(this);
        ndkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Dialog dialog;
        switch (v.getId()) {
            case R.id.encryption_md5:
                dialog = new Dialog(EncryptionActivity.this, "MD5", "1.一般用于校验\n2.不可逆，非严格意义上的加密\n3.分16位和32位");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            case R.id.encryption_sha1:
                dialog = new Dialog(EncryptionActivity.this, "SHA1:安全哈希算法", "1.一般用于校验\n2.不可逆，非严格意义上的加密");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            case R.id.encryption_base64://"1.8Bit编码方式\n2.用于在HTTP环境下传递较长的标识信息\n3.标准的Base64并不适合直接放在URL里传输"
                if (!base64Flag) {
                    base64Flag = true;
                    base64Button.setText(Base64.encodeToString(base64Button.getText().toString().getBytes(), Base64.DEFAULT));
                } else {
                    base64Flag = false;
                    base64Button.setText(new String(Base64.decode(base64Button.getText().toString().getBytes(), Base64.DEFAULT)));
                }
                break;

            /**
             * 高级加密标准,对称密钥加密中最流行的算法之一
             * 加密和解密使用同一个密钥
             * 采用对称分组密码体制，密钥长度的最少支持为128、192、256，分组长度128位
             * 在应用方面，尽管DES在安全上是脆弱的，但由于快速DES芯片的大量生产，使得DES仍能暂时继续使用，
             * 为提高安全强度，通常使用独立密钥的三级DES。但是DES迟早要被AES代替。
             * Android中需要16字节密码
             */
            case R.id.encryption_aes:
                if (!aesFlag) {
                    aesFlag = true;
                    aesButton.setText(AESKeyHelper.encryptString(aesButton.getText().toString(), "key"));
                } else {
                    aesButton.setText(AESKeyHelper.decryptString(aesButton.getText().toString(), "key"));
                    aesFlag = false;
                }
                break;

            case R.id.encryption_des:
                dialog = new Dialog(EncryptionActivity.this, "对称加密：DES", "1.使用率不高\n2.性能方面都比AES差\n3.迟早被AES代替");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            /**
             * RSA的算法涉及三个参数，n、e1、e2。
             其中，n是两个大质数p、q的积，n的二进制表示时所占用的位数，就是所谓的密钥长度。
             e1和e2是一对相关的值，e1可以任意取，但要求e1与(p-1)*(q-1)互质；再选择e2，要求(e2*e1)mod((p-1)*(q-1))=1。
             （n，e1),(n，e2)就是密钥对。
             RSA加解密的算法完全相同，设A为明文，B为密文，则：A=B^e2 mod n；B=A^e1 mod n；（公钥加密体制中，一般用公钥加密，私钥解密）
             */
            case R.id.encryption_rsa:
                dialog = new Dialog(EncryptionActivity.this, "非对称加密：RSA", "1.有公钥和私钥之分\n2.金融支付行业用的比较多\n3.速度要比对称加密慢，一般用于少量的数据加密");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            case R.id.encryption_diy:
                dialog = new Dialog(EncryptionActivity.this, "自定义加解密算法：", "1.自己DIY加密解密算法\n2.保证难以找出其规律即可");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            /**
             * 数据库加密方式
             * 1.部分字段加密
             -缺点：字段名称可见，CRUD操作都要进行加解密
             2.整个数据库加密
             -SQLCipher，缺点：1.库比较大，加大APK大小，2.会影响主UI，尽量放线程中
             */
            case R.id.encryption_sqlcipher:
                final String currentTime = System.currentTimeMillis() + "";
                if (!sqlcipherFlag) {
                    sqlcipherFlag = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pictureAirDbManager == null) {
                                pictureAirDbManager = new PictureAirDbManager(EncryptionActivity.this);
                            }
                            pictureAirDbManager.insertRecord(currentTime);
                            Dialog dbDialog = new Dialog(EncryptionActivity.this, "SQLCipher:", "已经当前时间：" + currentTime + "添加至数据库");
                            dbDialog.addCancelButton("Cancel");
                            dbDialog.show();
                            dbDialog.getButtonAccept().setText("OK");
                            dbDialog.getButtonAccept().setBackgroundColor(Color.RED);
                        }
                    });
                } else {
                    sqlcipherFlag = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pictureAirDbManager == null) {
                                pictureAirDbManager = new PictureAirDbManager(EncryptionActivity.this);
                            }
                            String lastRecord = pictureAirDbManager.getLastRecord();
                            Dialog dbDialog = new Dialog(EncryptionActivity.this, "SQLCipher：", "上次存放的时间是：" + lastRecord);
                            dbDialog.addCancelButton("Cancel");
                            dbDialog.show();
                            dbDialog.getButtonAccept().setText("OK");
                            dbDialog.getButtonAccept().setBackgroundColor(Color.RED);
                        }
                    });
                }
                break;

            /**
             * 1.HTTP + SSL
             -单向验证
             -双向验证, 用于金融行业
             2.两种配置方式
             -直接使用cer
             -加密cer
             3.SSL
             -信任所有证书，不安全，等于没有设置ssl
             -值信任当前证书
             参考链接：http://blog.csdn.net/lmj623565791/article/details/48129405
             */
            case R.id.encryption_https:
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        StringBuffer result = null;
                        try {
                            HttpURLConnection connection = createConnection("https://certs.cac.washington.edu/CAtest/");
//                            HttpURLConnection connection = createConnection("https://kyfw.12306.cn/otn/");
                            InputStream in = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            String line = "";
                            result = new StringBuffer();
                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                        } catch (IOException e) {
                            result = new StringBuffer();
                            e.printStackTrace();
                        }
                        handler.obtainMessage(111, result.toString()).sendToTarget();
                    }
                }.start();
                break;

            case R.id.encryption_ndk:
                dialog = new Dialog(EncryptionActivity.this, "NDK：", "1.android反编译之后都能看到加解密算法过程\n2.C语言中so文件安全性高\n3.so文件反编译只能看到String常量，看不到其他代码");
                dialog.addCancelButton("Cancel");
                dialog.show();
                dialog.getButtonAccept().setText("OK");
                dialog.getButtonAccept().setBackgroundColor(Color.RED);
                break;

            default:
                break;
        }
    }

    private HttpURLConnection createConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(20000);
        conn.setDoInput(true);
        conn.setDoOutput(false);
        conn.setRequestMethod("GET");

        if (url.contains("https")) {
            InputStream ins = null;
            try {
                ins = getAssets().open("certs.cac.washington.edu.cer");
                CertificateFactory cerFactory = CertificateFactory
                        .getInstance("X.509");
                Certificate cer = cerFactory.generateCertificate(ins);
                KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
                keyStore.load(null, null);
                keyStore.setCertificateEntry("cer", cer);

                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(url).openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                urlConnection.setHostnameVerifier(new HostnameVerifier() {

                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        // TODO Auto-generated method stub
                        return true;
                    }
                });
                conn = urlConnection;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


}
