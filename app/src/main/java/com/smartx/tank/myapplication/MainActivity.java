package com.smartx.tank.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView weatherTV;
    private Observable<Weather> weatherObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        editText = (EditText) findViewById(R.id.city);
        TextView queryTV = (TextView) findViewById(R.id.query);
        weatherTV = (TextView) findViewById(R.id.weather);

        queryTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String city = editText.getText().toString().trim();
            Logger.d("city : "+city);
            if(TextUtils.isEmpty(city)){
                Toast.makeText(this,"城市不能为空",Toast.LENGTH_LONG).show();
                return;
            }
            jumpNextActivity();

    }

    private void observable( String city){
        weatherObservable = Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                try {
                    Weather weather = new Weather();
                    weather.init();
                    subscriber.onNext(weather);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });

        weatherObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                         Logger.d("加载完毕");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("加载错误");
                    }

                    @Override
                    public void onNext(Weather weather) {
                        Logger.d("加载完毕并显示:"+weather.toString());
                        weatherTV.setText(weather.toString());
                    }
                });
    }

    private void jumpNextActivity(){
        Observable.timer(2, TimeUnit.SECONDS,AndroidSchedulers.mainThread())
                  .map(new Func1<Long, Object>() {

                      @Override
                      public Object call(Long aLong) {
                          startActivity(new Intent(MainActivity.this,TestActivity.class));
                          finish();
                          return null;
                      }
                  }).subscribe();
    }


}
