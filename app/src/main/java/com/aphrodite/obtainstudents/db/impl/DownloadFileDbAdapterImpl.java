package com.aphrodite.obtainstudents.db.impl;

import android.content.Context;
import android.util.Log;

import com.aphrodite.obtainstudents.db.GreenDaoManager;
import com.aphrodite.obtainstudents.db.inter.IDownloadFileDbAdapter;
import com.aphrodite.obtainstudents.entity.DownloadFileInfo;
import com.usher.greendao_demo.greendao.gen.DownloadFileInfoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aphrodite on 2017/5/16.
 */

public class DownloadFileDbAdapterImpl implements IDownloadFileDbAdapter {
    private static final String TAG = DownloadFileDbAdapterImpl.class.getSimpleName();
    private static DownloadFileDbAdapterImpl sFileDbAdapter = null;

    private Context mContext;
    private DownloadFileInfoDao mInfoDao;
    private List<DownloadFileInfo> mFileInfos;

    public DownloadFileDbAdapterImpl(Context context) {
        this.mContext = context;
        mInfoDao = GreenDaoManager.getInstance(context).getDaoSession().getDownloadFileInfoDao();
    }

    public static DownloadFileDbAdapterImpl getInstance(Context context) {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (null == sFileDbAdapter) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (DownloadFileDbAdapterImpl.class) {
                //未初始化，则初始instance变量
                if (null == sFileDbAdapter) {
                    sFileDbAdapter = new DownloadFileDbAdapterImpl(context);
                }
            }
        }
        return sFileDbAdapter;
    }

    @Override
    public void saveFile(DownloadFileInfo fileInfo) throws Exception {
        if (null == fileInfo) {
            return;
        }
        mInfoDao.insert(fileInfo);
        Log.d(TAG, "Enter saveFile method,DownloadFileInfo: " + fileInfo.toString());
    }

    @Override
    public List<DownloadFileInfo> getFile() throws Exception {
        Log.d(TAG, "Enter getFile method !");
        mFileInfos = new ArrayList<DownloadFileInfo>();
        mFileInfos = mInfoDao.queryBuilder().list();
        return mFileInfos;
    }

    @Override
    public void updateFile(DownloadFileInfo fileInfo) throws Exception {
        Log.d(TAG, "Enter updateFile method,DownloadFileInfo: " + fileInfo.toString());
        if (null == fileInfo) {
            return;
        }
        mInfoDao.update(fileInfo);
    }

    @Override
    public void deleteFile(String fileName) throws Exception {
        DownloadFileInfo fileInfo = mInfoDao.queryBuilder().where(DownloadFileInfoDao.Properties
                .FileName.eq(fileName)).build().unique();
        if (null == fileInfo) {
            return;
        }
        mInfoDao.deleteByKey(fileInfo.getId());
        Log.d(TAG, "Enter deleteFile method,DownloadFileInfo: " + fileInfo.toString());
    }
}
