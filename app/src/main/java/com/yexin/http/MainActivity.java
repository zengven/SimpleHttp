package com.yexin.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yexin.httplib.SimpleHttp;
import com.yexin.httplib.response.ResponseCallback;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btnGet;
    private Button btnPost;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        SimpleHttp simpleHttp = new SimpleHttp.Builder().baseUrl("https://free-api.heweather.com/").client(new OkHttpClient.Builder().build()).build();
        mApi = simpleHttp.create(Api.class);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == btnGet.getId()) {
            mApi.getWeatherService("北京", "c036bc576a184f0882c8e74e25c1a076", new ResponseCallback<WeatherBean>(WeatherBean.class) {

                @Override
                public void onStart() {
                    Log.i(TAG, "onStart: ");
                }

                @Override
                public void onSuccess(WeatherBean weatherBean) {
                    System.out.println("weatherBean : " + weatherBean.toString());
                    Log.i(TAG, "onSuccess current thread : " + Thread.currentThread());
                }

                @Override
                public void onFailure(Exception e) {
                    Log.i(TAG, "onFailure: ");
                }
            });
        } else {
            mApi.getShortUrl("http://www.baidu.com", new ResponseCallback<ShortUrlBean>(ShortUrlBean.class) {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(ShortUrlBean shortUrlBean) {
                    Log.i(TAG, "onSuccess: shortUrlBean: " + shortUrlBean.toString());
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }

    }
}
