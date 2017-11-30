package com.aphrodite.obtainstudents.cache.impl;

import android.util.Log;

import com.aphrodite.obtainstudents.cache.inter.IDownloadFileCache;
import com.aphrodite.obtainstudents.config.ObtainApplication;
import com.aphrodite.obtainstudents.db.impl.DownloadFileDbAdapterImpl;
import com.aphrodite.obtainstudents.entity.DownloadFileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aphrodite on 2017/5/16.
 */

public class DownloadFileCacheImpl implements IDownloadFileCache {
    private static final String TAG = DownloadFileCacheImpl.class.getSimpleName();
    private static DownloadFileCacheImpl sFileCacheImpl = null;

    private List<DownloadFileInfo> mFileInfos = new ArrayList<DownloadFileInfo>();

    public static DownloadFileCacheImpl getInstance() {
        if (null == sFileCacheImpl) {
            synchronized (sFileCacheImpl) {
                if (null == sFileCacheImpl) {
                    sFileCacheImpl = new DownloadFileCacheImpl();
                }
            }
        }
        return sFileCacheImpl;
    }

    @Override
    public List<DownloadFileInfo> getFileInfo() {
        Log.d(TAG, "Enter getFileInfo method");
        if (null == mFileInfos || mFileInfos.size() < 1) {
            initFileInfo();
        }
        return mFileInfos;
    }

    private synchronized void initFileInfo() {
        if (null != mFileInfos) {
            return;
        }
        mFileInfos.clear();
        try {
            mFileInfos = DownloadFileDbAdapterImpl.getInstance(ObtainApplication.getAppContext())
                    .getFile();
        } catch (Exception e) {
            Log.d(TAG, "Enter initFileInfo method,Exception: " + e);
        }
    }
}
