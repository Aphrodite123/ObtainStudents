package com.aphrodite.obtainstudents.http.down.inter;

/**
 * Created by Aphrodite on 2017/5/15.
 * 下载过程中监听状态处理
 */

public abstract class AbsDownloadingListener<T> {
    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 下载进度
     *
     * @param readSize
     * @param totalSize
     */
    public abstract void onProgress(long readSize, long totalSize);

    /**
     * 成功后回调方法
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 完成下载
     */
    public abstract void onComplete();

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     *
     * @param t
     */
    public void onError(Throwable t) {
    }

    /**
     * 暂停下载
     */
    public void onPuase() {
    }

    /**
     * 停止下载销毁
     */
    public void onStop() {
    }
}
