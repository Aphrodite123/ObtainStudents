package com.aphrodite.obtainstudents.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aphrodite.obtainstudents.config.BaseConfig;
import com.usher.greendao_demo.greendao.gen.DaoMaster;
import com.usher.greendao_demo.greendao.gen.DaoSession;

/**
 * Created by Aphrodite on 2017/5/16.
 */

public class GreenDaoManager {
    private static GreenDaoManager sInstance = null;
    private static DaoMaster sDaoMaster;
    private static DaoSession sDaoSession;

    private GreenDaoManager(Context context) {
// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper注意：默认的 DaoMaster
// .DevOpenHelper会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,
                BaseConfig.NAME_DATABASE, null);
        //注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
        sDaoMaster = new DaoMaster(sqLiteDatabase);
        sDaoSession = sDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (sInstance == null) {
                    sInstance = new GreenDaoManager(context);
                }
            }
        }
        return sInstance;
    }

    public DaoMaster getDaoMaster() {
        return sDaoMaster;
    }

    public DaoSession getDaoSession() {
        return sDaoSession;
    }


}
