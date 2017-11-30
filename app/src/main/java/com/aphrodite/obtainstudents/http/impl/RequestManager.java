package com.aphrodite.obtainstudents.http.impl;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aphrodite on 2017/5/11.
 */

public class RequestManager<T> {
    private static final String TAG = RequestManager.class.getSimpleName();
    private static RequestManager mRequestManager = null;
    private Context mContext;
    private Class<T> mClazz;

    public RequestManager() {
    }

    public RequestManager(Class<T> clazz) {
        this.mClazz = clazz;
    }

    public static RequestManager getInstance() {
        if (null == mRequestManager) {
            synchronized (RequestManager.class) {
                if (null == mRequestManager) {
                    mRequestManager = new RequestManager();
                }
            }
        }
        return mRequestManager;
    }

    public interface OnResponseListener<T> {
        /**
         * 请求成功
         *
         * @param respone
         */
        void onSuccess(T respone);

        /**
         * 请求失败
         *
         * @param t
         */
        void onFailure(Throwable t);
    }

    /**
     * 发送请求
     *
     * @param call
     * @param responseListener
     */
    public void sendRequest(Call<T> call, final OnResponseListener responseListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                T t = response.body();
                responseListener.onSuccess(t);
                Log.d(TAG, "Enter sendRequest method and into onResponse,response: " + t);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(TAG, "Enter sendRequest method and into onFailure,Throwable: " + t);
                responseListener.onFailure(t);
            }
        });
    }
}
