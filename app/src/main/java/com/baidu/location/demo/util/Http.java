package com.baidu.location.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

// Http class
public class Http {

    public static final String URI = "http://192.168.3.14:8081/wms/loginbase/";
    public static final String URI1 = "http://192.168.3.14:8081/wms/eventinfoapp/";

    //iphone _hostpost
//    public static final String URI = "http://172.20.10.3:8081/wms/loginbase/";
//    public static final String URI1 = "http://172.20.10.3:8081/wms/eventinfoapp/";



    //public static final String URI = "http://192.168.0.100:8081/wms/loginbase/";
    //public static final String URI1 = "http://192.168.0.100:8081/wms/eventinfoapp/";


    //HOme _ Crystal Ave
//    public static final String URI = "http://192.168.50.216:8081/wms/loginbase/";
//    public static final String URI1 = "http://192.168.50.216:8081/wms/eventinfoapp/";




    // Post Request
    public static <T> HttpResponse<T> Request(final String uri, final Object data, final Class<T> type) {
        final HttpResponse<T>[] result = new HttpResponse[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String aa = JSON.toJSONString(data);
                    result[0] = JSON.parseObject(Post(uri, data == null ? "{}" : JSON.toJSONString(data)), new TypeReference<HttpResponse<T>>(type) {});
                } catch (IOException e) {
                    result[0] = null;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            return null;
        }
        return result[0];
    }

    public static <T> HttpResponse<T> Request1(final String uri, final Object data, final Class<T> type) {
        final HttpResponse<T>[] result = new HttpResponse[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String aa = JSON.toJSONString(data);
                    result[0] = JSON.parseObject(Post1(uri, data == null ? "{}" : JSON.toJSONString(data)), new TypeReference<HttpResponse<T>>(type) {});
                } catch (IOException e) {
                    result[0] = null;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            return null;
        }
        return result[0];
    }

    private static String Post(String uri, String data) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(URI + uri)
                .post(RequestBody.create(data, MediaType.parse("application/json;charset=utf-8")))
                .build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    private static String Post1(String uri, String data) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(URI1 + uri)
                .post(RequestBody.create(data, MediaType.parse("application/json;charset=utf-8")))
                .build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    public static <T> HttpResponse<T> Request2(final String uri, final Object data, final Class<T> type) {
        final HttpResponse<T>[] result = new HttpResponse[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String aa = JSON.toJSONString(data);
                    result[0] = JSON.parseObject(Get2(uri, data == null ? "{}" : JSON.toJSONString(data)), new TypeReference<HttpResponse<T>>(type) {});
                } catch (IOException e) {
                    result[0] = null;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            return null;
        }
        return result[0];
    }

    private static String Get2(String uri, String data) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(URI1 + uri)
                .get()
                .build();
        return okHttpClient.newCall(request).execute().body().string();
    }
}
