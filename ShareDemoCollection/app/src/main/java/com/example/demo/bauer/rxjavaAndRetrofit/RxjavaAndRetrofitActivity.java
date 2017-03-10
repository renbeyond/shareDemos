package com.example.demo.bauer.rxjavaAndRetrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;
import com.example.demo.peng.activity.SplashActivity;
import com.example.demo.peng.retrofitwithokhttp.http.Common;
import com.example.demo.peng.retrofitwithokhttp.pictureworks.Installation;
import com.pictureair.jni.keygenerator.PWJniUtil;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by bauer_bao on 16/10/31.
 */
public class RxjavaAndRetrofitActivity extends RxAppCompatActivity {
    private Button button7, button9, button10, button11, button12;
    private RequestServes requestServes;
    private ProgressDialog progressDialog;
    private String uuid, key;
    private int time = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_retrofit);
        ButterKnife.bind(this);
        button7 = (Button) findViewById(R.id.rxjava_btn7);
        button9 = (Button) findViewById(R.id.rxjava_btn9);
        button10 = (Button) findViewById(R.id.rxjava_btn10);
        button11 = (Button) findViewById(R.id.rxjava_btn11);
        button12 = (Button) findViewById(R.id.rxjava_btn12);


        uuid = Installation.id(this);
        key = SplashActivity.md5(PWJniUtil.getAPPKey(Common.APP_TYPE_SHDRPP) + PWJniUtil.getAppSecret(Common.APP_TYPE_SHDRPP));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.disneyphotopass.com.cn/photoPass/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//retrofit + rxjava 主要的代码
                .build();

        requestServes = retrofit.create(RequestServes.class);
        progressDialog = new ProgressDialog(RxjavaAndRetrofitActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", " pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", " stop");
    }

    @OnClick({R.id.rxjava_btn1, R.id.rxjava_btn2, R.id.rxjava_btn3, R.id.rxjava_btn4, R.id.rxjava_btn5,
            R.id.rxjava_btn6, R.id.rxjava_btn7, R.id.rxjava_btn8, R.id.rxjava_btn9,
            R.id.rxjava_btn10, R.id.rxjava_btn11, R.id.rxjava_btn12})
    void rxJavaOnClick (View view) {
        switch (view.getId()) {
            case R.id.rxjava_btn1:
                function1();
                break;

            case R.id.rxjava_btn2:
                function2();
                break;

            case R.id.rxjava_btn3:
                function3();
                break;

            case R.id.rxjava_btn4:
                function4();
                break;

            case R.id.rxjava_btn5:
                function5();
                break;

            case R.id.rxjava_btn6:
                function6();
                break;

            case R.id.rxjava_btn7:
                function7();
                break;

            case R.id.rxjava_btn8:
                function8();
                break;

            case R.id.rxjava_btn9:
                function9();
                break;

            case R.id.rxjava_btn10:
                function10();
                break;

            case R.id.rxjava_btn11:
                function11();
                break;

            case R.id.rxjava_btn12:
                function12();
                break;

        }
    }

    /**
     * 跟随生命周期管理
     */
    private void function12() {
        Observable.timer(2, TimeUnit.SECONDS)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        button12.setText("3s之后开始10s倒计时");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d("tag", " unsubscribe");

                    }
                })
                .flatMap(new Func1<Object, Observable<?>>() {
                    @Override
                    public Observable<?> call(Object aLong) {
                        return Observable.interval(1, 1, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindUntilEvent(ActivityEvent.PAUSE))
//                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {
                        Log.d("tag", " oncompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", " error");
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d("tag", " next" + time);

                        button12.setText(time + "s");
                        time --;
                        if (time < 0) {
                        button12.setText("结束");

                            time = 10;
                            unsubscribe();
                        }

                    }
                });
    }

    /**
     * 延迟3s + 10s倒计时
     */
    private void function11() {
        Observable.timer(2, TimeUnit.SECONDS)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        button11.setText("3s之后开始10s倒计时");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d("tag", " unsubscribe");

                    }
                })
                .flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        return Observable.interval(1, 1, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {
                        Log.d("tag", " oncompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", " error");
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d("tag", " next" + time);

                        button11.setText(time + "s");
                        time --;
                        if (time < 0) {
                        button11.setText("结束");

                            time = 10;
                            unsubscribe();
                        }

                    }
                });
    }

    /**
     * merge
     */
    private void function10() {//顺序调用api
        Observable.merge(requestServes.getTokenId1("android", uuid, key),//请求1
                requestServes.getTokenId2("andnroid", uuid, key))//请求2
                .subscribeOn(Schedulers.io())

                .doOnSubscribe(new Action0() {//在主线程中执行请求的准备工作
                    @Override
                    public void call() {
                        progressDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())

                .observeOn(AndroidSchedulers.mainThread())//请求结束，在主线程中更新UI
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("tag", "get the result");
                        progressDialog.dismiss();
                        button10.setText("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", "error");
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("tag", "get the result11111111--->" + s);

                    }
                });
    }

    /**
     * zip
     */
    private void function9() {//顺序调用api
        Observable.zip(requestServes.getTokenId1("android", uuid, key),//请求1
                requestServes.getTokenId2("andnroid", uuid, key),//请求2
                new Func2<String, String, String>() {//将请求1和请求2 返回的数据 进行合并

                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .subscribeOn(Schedulers.io())

                .doOnSubscribe(new Action0() {//在主线程中执行请求的准备工作
                    @Override
                    public void call() {
                        progressDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())

//                .flatMap(new Func1<String, Observable<String>>() {//变换
//                    @Override
//                    public Observable<String> call(String s) {
//                        Log.d("tag", "get the result--->" + s);
//                        return requestServes.getTokenId2("android", uuid, key);
//                    }
//                })

                .observeOn(AndroidSchedulers.mainThread())//请求结束，在主线程中更新UI
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("tag", "get the result");
                        progressDialog.dismiss();
                        button9.setText("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", "error");
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("tag", "get the result11111111--->" + s);

                    }
                });
    }

    private void function8() {//顺序调用api
        requestServes.getTokenId1("android", uuid, key)
                .subscribeOn(Schedulers.io())//在线程中分发

                .doOnSubscribe(new Action0() {//在主线程中执行请求的准备工作
                    @Override
                    public void call() {
                        progressDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())

                .flatMap(new Func1<String, Observable<String>>() {//变换
                    @Override
                    public Observable<String> call(String s) {
                        Log.d("tag", "get the result--->" + s);
                        return requestServes.getTokenId2("android", uuid, key);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())//请求结束，在主线程中更新UI
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("tag", "get the result");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", "error");
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("tag", "get the result11111111--->" + s);

                    }
                });
    }

    private void function7() {
        String[] urls = new String[]{"bauer", "milo", "peng", "beyond"};
        Log.d("start", "start rxjava");
        Subscription subscription = Observable.from(urls)//等于遍历循环
                .subscribeOn(Schedulers.io())//使用subscribeOn()指定被观察者代码运行的线程
                .flatMap(new Func1<String, Observable<String>>() {//转换了一次，重新生成了一个Observable被观察者。
                    @Override
                    public Observable<String> call(String s) {
                        Log.d("rxjava6:", "flatMap=====");
                        return Observable.just(s + " is learning rxjava very hard");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//使用observerOn()指定订阅者运行的线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d("rxjava6:", "onStart=====");

                    }

                    @Override
                    public void onCompleted() {
                        Log.d("rxjava6:", "onCompleted=====");
                        button7.setText("completed");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("rxjava6:", "onError");

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("rxjava6:", "onNext--->" + s);

                    }
                });
        Log.d("tag", "over--》" + subscription.isUnsubscribed());
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();//可以取消订阅
        Log.d("tag", "done");
    }

    private void function6() {
        String[] urls = new String[]{"bauer", "milo", "peng", "beyond"};
        Observable.from(urls)//等于遍历循环
                .flatMap(new Func1<String, Observable<String>>() {//转换了一次，重新生成了一个Observable被观察者。
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s + " is learning rxjava very hard");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("rxjava6:", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("rxjava6:", "onError");

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("rxjava6:", "onNext--->" + s);

                    }
                });
    }

    private void function5() {
        String[] urls = new String[]{"bauer", "milo", "peng", "beyond", "zhangxiaoming", "zhangxiaoming", "lixiaoming", "wangxiaoming", "zhaoxiaoming"};
        Observable.from(urls)//等于遍历循环
                .flatMap(new Func1<String, Observable<String>>() {//转换了一次，重新生成了一个Observable被观察者。
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s + " is learning rxjava");
                    }
                })
                .filter(new Func1<String, Boolean>() {//添加过滤，如果不需要的，可以删除
                    @Override
                    public Boolean call(String s) {
                        return s.contains("xiaoming");
                    }
                })
                .distinct()
                .take(2)//指定输出的数量
                .doOnNext(new Action1<String>() {//在给订阅者结果之前做的额外的事情
                    @Override
                    public void call(String s) {
                        Log.d("rxjava5", s + " very bad");

                    }
                })
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        Log.d("rxjava5", "please get out");
                    }
                });
    }

    private void function4() {
        String[] urls = new String[]{"bauer", "milo", "peng", "beyond"};
        Observable.from(urls)//等于遍历循环  将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        Log.d("name", s);
                    }
                });
    }

    private void function3() {
        Observable.just("bauer", "milo", "peng", "beyond")//快速create，可以传多个参数， 将传入的参数依次发送出来。
                .map(new Func1<String, String>() {//中间的变换，可以执行多次变换, map基本和 func函数一起出现
                    @Override
                    public String call(String s) {
                        return s + " is learning";
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " rxJava";
                    }
                })
                .subscribe(new Action1<String>() {//订阅者的逻辑操作
                    @Override
                    public void call(String s) {
                        Log.d("rxjava3", s);
                    }
                });
    }

    private void function2() {
        Observable<String> mObservable = Observable.just("learning rxjava now");
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("rxjava2:", "let us " + s);
            }
        };
        mObservable.subscribe(onNextAction);
    }

    private void function1() {
        Observable<String> mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onNext("hello");
                subscriber.onNext("rxjava");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                System.out.println("start learning rxjava");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };
        mObservable.subscribe(mySubscriber);
    }

    public interface RequestServes {
        @POST("auth/getTokenId")
        Observable<String> getTokenId1(@Query("terminal") String loginname,
                                     @Query("UUID") String nloginpwd,
                                     @Query("appID") String appid);

        @POST("auth/getTokenId")
        Observable<String> getTokenId2(@Query("terminal") String loginname,
                                 @Query("UUID") String nloginpwd,
                                 @Query("appID") String appid);
    }

}
