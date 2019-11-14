package com.agilefinger.labourservice.http;

import android.support.annotation.Nullable;
import android.util.Log;

import com.agilefinger.labourservice.utils.HandleUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Mulberry
 * create on 2018/5/9.
 */

public class HttpEngine {
    private final String TAG = "HttpEngine";

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient okHttpClient;
    public boolean isUnittest = false;

    public HttpEngine() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(16, TimeUnit.SECONDS)
                .readTimeout(16, TimeUnit.SECONDS)
                .readTimeout(16, TimeUnit.SECONDS)
                .build();
    }

    public <R extends HttpResponse> void requestAsyncThread(final Request request, final Class<R> rClass, final OnResponseCallback<R> callback) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //LogUtils.d(TAG, "request failure,  request url = " + request.url());
                if (callback != null) {
                    if (isUnittest) {
                        callback.onResponse(1, "网络请求超时，请检查网络", null);
                    } else {
                        callback.onResponse(1, "网络请求超时，请检查网络", null);
                    }
                }
            }

            //
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(TAG, "request url = " + request.url());
                    Log.d(TAG, "response body = " + body);
                    Gson gson = new Gson();
                try {
                    final R resp = gson.fromJson(body, rClass);
                    if (resp == null) {
                        return;
                    }
                    String errorMessage = resp.message;
                    if (resp.code != 0) {
                        errorMessage += ("[err=" + resp.message + "]");
                    }
                    if (callback != null) {
                        final String finalErrorMessage = errorMessage;
                        if (isUnittest) {
                            callback.onResponse(resp.code, finalErrorMessage, resp);
                        } else {
                            callback.onResponse(resp.code, finalErrorMessage, resp);

                        }
                    }

                } catch (JsonSyntaxException e) {
                    onFailure(call, new IOException(e.getMessage()));
                }
            }
        });
    }

    ConcurrentHashMap<String, Call> map = new ConcurrentHashMap<>();

    public <R extends HttpResponse> void request(final Request request, final Class<R> rClass, final OnResponseCallback<R> callback) {
        Call call = okHttpClient.newCall(request);
        map.put(request.url().toString(), call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //LogUtils.d(TAG, "request failure,  request url = " + request.url());
                if (callback != null) {
                    if (isUnittest) {
                        callback.onResponse(1, "网络请求超时，请检查网络", null);
                    } else {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(1, "网络请求超时，请检查网络", null);
                            }
                        });
                    }
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 String body = response.body().string();
                Log.d(TAG, "request url = " + request.url());
                Log.d(TAG, "response body = " + body);
                Gson gson = new Gson();
                try {
                    final R resp = gson.fromJson(body, rClass);
                    if (resp == null) {
                        return;
                    }
                    String errorMessage = resp.message;
                    if (resp.code != 0) {
                        //errorMessage += (/*"[err=" +*/ resp.message /*+ "]"*/);
                    }

                    if (callback != null) {
                        final String finalErrorMessage = errorMessage;
                        if (isUnittest) {
                            callback.onResponse(resp.code, finalErrorMessage, resp);
                        } else {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(resp.code, finalErrorMessage, resp);
                                }
                            });
                        }
                    }

                } catch (JsonSyntaxException e) {
                    onFailure(call, new IOException(e.getMessage()));
                }
            }
        });
    }

    public <R extends HttpResponse> void requestJson(final Request request, final OnResponseJsonCallback<R> callback) {
        Call call = okHttpClient.newCall(request);
        map.put(request.url().toString(), call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //LogUtils.d(TAG, "request failure,  request url = " + request.url());
                if (callback != null) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(1,e.getLocalizedMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.d(TAG, "request url = " + request.url());
                Log.d(TAG, "response body = " + body);
                if (callback != null) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(0,body);
                        }
                    });
                }
            }
        });
    }

    private void post(Runnable r) {
        HandleUtils.getMainThreadHandler().post(r);
    }

    public void cancel(String url) {
        if (map != null && map.containsKey(url)) {
            Call remove = map.remove(url);
            remove.cancel();
        }
    }

    public interface OnResponseCallback<T> {
        void onResponse(final int result, final @Nullable String retmsg, final @Nullable T data);
    }

    public interface OnResponseJsonCallback<T> {
        void onResponse(final int result,String data);
    }
}
