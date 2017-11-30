package com.aphrodite.obtainstudents.task.impl;

import com.aphrodite.obtainstudents.entity.DownloadFileInfo;

import static android.R.attr.id;

/**
 * Created by Aphrodite on 2017/6/4.
 */

public class DownloadTaskImpl extends Task {
    private static final String TAG = DownloadTaskImpl.class.getSimpleName();

    private DownloadFileInfo mFileInfo = new DownloadFileInfo();

    /**
     * 是否是post请求
     */
    private boolean isPost = false;

    /**
     * 是否有代理
     */
    private boolean isProxy = false;

    /**
     * 代理地址
     */
    private String proxyHost;

    /**
     * 代理端口
     */
    private int proxyPort;

    /**
     * 缓冲字节区
     */
    private byte[] postBuf = null;

    /**
     * 任务状态
     */
    private int status;

    public String getFileName() {
        return mFileInfo.getFileName();
    }

    public void setFileName(String fileName) {
        mFileInfo.setFileName(fileName);
    }

    public long getId() {
        return mFileInfo.getId();
    }

    public void setId(long id) {
        mFileInfo.setId(id);
    }

    public long getReadLength() {
        return mFileInfo.getReadLength();
    }

    public void setReadLength(long readLength) {
        mFileInfo.setReadLength(readLength);
    }

    public String getSavePath() {
        return mFileInfo.getSavePath();
    }

    public void setSavePath(String savePath) {
        mFileInfo.setSavePath(savePath);
    }

    public long getTotalLength() {
        return mFileInfo.getTotalLength();
    }

    public void setTotalLength(long totalLength) {
        mFileInfo.setTotalLength(totalLength);
    }

    public String getUrl() {
        return mFileInfo.getUrl();
    }

    public void setUrl(String url) {
        mFileInfo.setUrl(url);
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public byte[] getPostBuf() {
        return postBuf;
    }

    public void setPostBuf(byte[] postBuf) {
        this.postBuf = postBuf;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 任务进行中的状态通知
     */
    public final void onProgress() throws Exception {
        setStatus(TASK_STATUS_PROCESS);
    }

    @Override
    public void run() {

    }
}
