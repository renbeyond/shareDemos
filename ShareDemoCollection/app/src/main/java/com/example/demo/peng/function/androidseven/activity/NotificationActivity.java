package com.example.demo.peng.function.androidseven.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.demo.R;

public class NotificationActivity extends Activity implements View.OnClickListener{

    NotificationManager manager;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.bind);
        btn3 = (Button) findViewById(R.id.define);
        btn4 = (Button) findViewById(R.id.reply);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }
    public static final String KEY_GROUP = "key_group";
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.define:

                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.manual_decorate);
                remoteViews.setImageViewResource(R.id.img1, R.drawable.ic_launcher);
                remoteViews.setTextViewText(R.id.tv_title1,"标题");
                remoteViews.setTextViewText(R.id.tv_content1,"内容++++++++++++");

                RemoteViews bigRemoteViews = new RemoteViews(getPackageName(), R.layout.manul_notification);
                bigRemoteViews.setImageViewResource(R.id.img, R.drawable.ic_launcher);
                bigRemoteViews.setTextViewText(R.id.tv_title,"标题");
                bigRemoteViews.setTextViewText(R.id.tv_content,"内容++++++++++++");

                Notification.Builder builder7 = new Notification.Builder(NotificationActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setCustomContentView(remoteViews)
                        .setCustomBigContentView(bigRemoteViews)
                        .setStyle(new Notification.DecoratedCustomViewStyle())
                        .setAutoCancel(true);
                Notification notification7 = builder7.build();
                manager.notify(++i, notification7);

                break;

            case R.id.button:

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher).
                                setContentTitle("这是标题").
                                setContentText("这是内容").
                                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher)).
                                setAutoCancel(true);

                Notification notification = builder.build();
                manager.notify(++i, notification);
                break;

            case R.id.bind:
                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(NotificationActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher).
                                setContentTitle("111111").
                                setContentText("这是里面的内容").
                                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher)).
                        setAutoCancel(true)
                        .setGroup(KEY_GROUP);
                Notification notification1 = builder1.build();
                NotificationCompat.Builder builder2 = new NotificationCompat.Builder(NotificationActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher).
                                setContentTitle("这是里面标题").
                                setContentText("这是里面内容").
                        setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher)).
                        setAutoCancel(true)
                        .setGroup(KEY_GROUP);//将Notification添加到组中
                Notification notification2 = builder2.build();
                NotificationCompat.Builder builder3 = new NotificationCompat.Builder(NotificationActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher).
                        setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher)).
                        setAutoCancel(true)
                        .setGroupSummary(true)
                        .setGroup(KEY_GROUP);
                Notification notification3 = builder3.build();
                manager.notify(++i,notification1);
                manager.notify(++i,notification2);
                manager.notify(++i,notification3);
                break;

            case R.id.reply:
                //android.app.PendingIntent.FLAG_CANCEL_CURRENT   如果PendingIntent已经存在，那么当前的PendingIntent会取消掉，然后产生一个新的PendingIntent
               //android.app.PendingIntent.FLAG_ONE_SHOT PendingIntent只能使用一次。调用了实例方法send()之后，它会被自动cancel掉，再次调用send()方法将失败
                //android.app.PendingIntent.FLAG_NO_CREATE   如果PendingIntent不存在，简单了当返回null。
                //[1]  添加操作行为  远程输入
                RemoteInput remoteInput1 = new RemoteInput.Builder(KEY_REPLY)
                        .setLabel(LABEL_REPLY).build();//setlabel类似于 setHint

                //[2] 定义点击后的行为
                Intent intent1 = new Intent(NotificationActivity.this, AndroidSevenActivity.class);
                intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                PendingIntent replyIntent = PendingIntent.getActivity(NotificationActivity.this,
                        10003,intent1, PendingIntent.FLAG_UPDATE_CURRENT/**如果PendingIntent已经存在，保留它并且只替换它的extra数据*/);

                PendingIntent archiveIntent = PendingIntent.getActivity(NotificationActivity.this,
                        10003,intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                //【3】定义有哪些行为
                NotificationCompat.Action replyAction =
                        new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                                LABEL_REPLY,replyIntent).addRemoteInput(remoteInput1).build();

                NotificationCompat.Action archiveAction =
                        new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                                LABEL_Archive,archiveIntent).build();

                //【4】添加行为到notification
                Notification notification5 =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("Remote Input")
                                .setContentText("内容")
                                .addAction(replyAction)
                                .addAction(archiveAction)
                                .setAutoCancel(true)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .build();

                manager.notify(101, notification5);
                break;
        }
    }

//    private static Intent getDirectReplyIntent(Context context, String label) {
//        return NotificationActivity.getStartIntent(context)
//                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
//                .setAction(REPLY_ACTION)
//                .putExtra(CONVERSATION_LABEL, label);
//    }

    public static final String KEY_REPLY = "123"; //用于接收到intent获取数据的key
    private static final String LABEL_REPLY = "Reply";
    private static final String LABEL_Archive = "Archive";


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}
