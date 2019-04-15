package com.yexin.http;

import com.yexin.httplib.SimpleHttp;

import org.junit.Test;

import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHttp() {
        SimpleHttp simpleHttp = new SimpleHttp.Builder().baseUrl("https://free-api.heweather.com/").client(new OkHttpClient.Builder().build()).build();
        Api api = simpleHttp.create(Api.class);
        api.getWeatherService("北京", "c036bc576a184f0882c8e74e25c1a076", 0);
    }
}