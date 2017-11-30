package com.aphrodite.obtainstudents.http.down.inter;

/**
 * Created by Aphrodite on 2017/5/15.
 */

public interface IProgressListener {
    /**
     * 下载进度
     *
     * @param readSize
     * @param totalSize
     * @param isDone
     */
    void onProgress(long readSize, long totalSize, boolean isDone);
}
