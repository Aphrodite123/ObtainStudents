package com.aphrodite.obtainstudents.http.impl;

import com.aphrodite.obtainstudents.config.BaseConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aphrodite on 2017/5/11.
 */

public class RetrofitAPIManager {

    public static RetrofitAPI provideClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConfig.sBaseAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .build();
        return retrofit.create(RetrofitAPI.class);
    }


    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; " +
                                        "charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }
}
