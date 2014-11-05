package com.example.ankit.imgshare2android;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.client.CookieStore;

/**
 * Created by ankit on 10/26/14.
 * Documentation:
 * http://loopj.com/android-async-http/
 */
public class HttpClient {
    private static final String BASE_URL = "http://imgshare2.appspot.com/"; //"http://localhost:8080/";//
    private static final String LOG_TAG = "HttpClient";
    private static AsyncHttpClient client;
    private static AsyncHttpClient syncClient;
    private static CookieStore cookieStore;

    public HttpClient(Context context) {
        if (client == null) {
            client = new AsyncHttpClient();
            cookieStore = new PersistentCookieStore(context);
            syncClient = new SyncHttpClient();
            client.setCookieStore(cookieStore);
        }
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static CookieStore getCookieStore() {
        return cookieStore;
    }

    public static void enableRedirects(Boolean enable1, Boolean enable2, Boolean enable3) {
        client.setEnableRedirects(enable1, enable2, enable3);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getBinary(String url, RequestParams params, FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler) {
        Log.d(LOG_TAG, "Getting Binary File from " + url);
        client.get(url, fileAsyncHttpResponseHandler );
    };


    public static void getSync(final String url, final RequestParams params, final ResponseHandlerInterface responseHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Sync HTTP", "Before Request");
                client.get(getAbsoluteUrl(url), params, responseHandler);
                Log.d("Sync HTTP", "After Request");
            }
        }).start();
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
