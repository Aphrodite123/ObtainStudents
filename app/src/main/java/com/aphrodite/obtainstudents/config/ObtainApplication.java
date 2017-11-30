package com.aphrodite.obtainstudents.config;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aphrodite.obtainstudents.db.GreenDaoManager;
import com.usher.greendao_demo.greendao.gen.DaoMaster;

/**
 * Created by Aphrodite on 2017/5/7.
 */

public class ObtainApplication extends Application {
    private static final String TAG = ObtainApplication.class.getSimpleName();
    private static ObtainApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initSystem(this);
        initClient();
        initDatabase();
    }

    private void initSystem(Context context) {
        mApplication = this;
    }

    public static ObtainApplication getApp() {
        return mApplication;
    }

    public static Context getAppContext() {
        return mApplication.getApplicationContext();
    }

    /**
     * OkHttpUtils初始化
     */
    private void initClient() {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化数据库SQlite
     */
    private void initDatabase() {
        GreenDaoManager.getInstance(this);
    }
}
