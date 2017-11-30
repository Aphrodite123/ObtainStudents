package com.aphrodite.obtainstudents.http.down.impl;

import android.util.Log;

import com.aphrodite.obtainstudents.http.down.inter.ITaskDriver;
import com.aphrodite.obtainstudents.task.impl.DownloadTaskImpl;
import com.aphrodite.obtainstudents.util.FileUtil;
import com.aphrodite.obtainstudents.util.StringUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Aphrodite on 2017/6/5.
 */

public class TaskDriverImpl implements ITaskDriver {
    private static final String TAG = TaskDriverImpl.class.getSimpleName();

    /**
     * 默认的缓冲区大小
     */
    private static final int BYTE_LENGTH = 102400;

    private DownloadTaskImpl mDownloadTask = null;

    private static final String REQUEST_METHOD_POST = "POST";
    private static final String REQUEST_METHOD_GET = "GET";

    private static final int TIME_OUT = 5 * 1000;

    /**
     * http网络连接对象
     */
    private HttpURLConnection connection = null;
    /**
     * 读取流
     */
    private InputStream is = null;

    /**
     * 进度更新时间
     */
    private static final int REPORT_TIME = 1000;

    /**
     * 进度更新时间
     */
    private static final int SLEEP_TIME = 500;

    public TaskDriverImpl(DownloadTaskImpl downloadTask) {
        this.mDownloadTask = downloadTask;
    }

    @Override
    public void connect() throws Exception {
        Log.d(TAG, "Enter connect method !");
        DataOutputStream os = null;
        long length;
        long curSize = mDownloadTask.getReadLength();
        long totalSize = mDownloadTask.getTotalLength();

        URL url = null;

        try {
            url = new URL(mDownloadTask.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            if (mDownloadTask.isProxy()) {
                connection =
                        (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,
                                new InetSocketAddress(mDownloadTask.getProxyHost(), mDownloadTask
                                        .getProxyPort())));
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            // 不使用Cache
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIME_OUT);
            connection.setReadTimeout(TIME_OUT);

            /**
             * 设置请求类型
             */
            if (mDownloadTask.isPost()) {
                connection.setRequestMethod(REQUEST_METHOD_POST);
            } else {
                connection.setRequestMethod(REQUEST_METHOD_GET);
            }

            if (curSize > 0 && totalSize > curSize) {
                connection.addRequestProperty("RANGE", "bytes=" + curSize + "-" + totalSize);
            }
            Log.d(TAG, "Enter connect method,{curSize,totalSize}: {" + curSize + "," + totalSize
                    + "}");

            // 以内容实体方式发送请求参数
            byte[] postBuf = mDownloadTask.getPostBuf();
            length = postBuf != null ? postBuf.length : curSize;
            if (mDownloadTask.isPost() && length > 0) {
                // 发送POST请求必须设置允许输出
                connection.setDoOutput(true);
                // 维持长连接
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Content-Length", String.valueOf(length));
                connection.setRequestProperty("Content-Type", "application/octet-stream");

                // 开始写入数据
                os = new DataOutputStream(connection.getOutputStream());
                os.write(postBuf);
                os.flush();
            }
        } catch (IOException ex) {
            Log.d(TAG, "Enter connect method and write data,IOException: " + ex);
        } finally {
            // 关流
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.d(TAG, "Enter connect method and in finally,IOException: " + e);
                }
            }
        }
        try {
            long hSize = 0L;
            // 从返回头中获取返回长度
            String value = connection.getHeaderField("Content-Length");
            if (value != null && value.length() > 0) {
                try {
                    hSize = Long.parseLong(value);
                    Log.d(TAG, "Enter connect method,Content-Length: " + value);
                } catch (Exception e) {
                    Log.d(TAG, "Enter connect method,Exception: " + e);
                }
            } else {
                String contentRange = connection.getHeaderField("content-range");
                Log.d(TAG, "Enter connect method,content-range: " + contentRange);
                if (null != contentRange) {
                    hSize =
                            Long.parseLong(StringUtil.split(contentRange, "/")[1])
                                    - mDownloadTask.getReadLength();
                }
            }
            long cSize = connection.getContentLength();

            // 从流中获取返回长度
            is = connection.getInputStream();

            cSize = cSize > hSize ? cSize : hSize;
            cSize += curSize;
            mDownloadTask.setTotalLength(cSize);
        } catch (IOException ex) {
            Log.w(TAG, "Enter connect method and back length,IOException: " + ex);
        }
    }

    @Override
    public void read() throws Exception {
        Log.d(TAG, "Enter read method !");
        if (mDownloadTask.getSavePath() != null) {
            readDownloadFile();
        } else {
            readBytes();
        }
    }

    private void readDownloadFile() throws Exception {
        long currentSize = mDownloadTask.getReadLength();
        String storePath = mDownloadTask.getSavePath();
        // 存储每次从网络层读取到的数据
        // 临时数据缓冲区
        int len = 0;
        byte[] buff = new byte[BYTE_LENGTH];
        byte[] bytes = null;
        long time = 0;
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        RandomAccessFile file = null;
        try {
            FileUtil.createFolder(storePath);
            file = new RandomAccessFile(FileUtil.getFileByPath(storePath), "rw");
            if ((len = is.read(buff)) == -1) {
                return;
            }
            bytes = new byte[len];
            System.arraycopy(buff, 0, bytes, 0, len);
            Log.d(TAG, "readFileData : file.length()=" + file.length());
            file.seek(file.length());

            file.write(bytes);
            currentSize += len;
            Log.d(TAG, "readFileData : currentSize=" + currentSize);
            long currentTime = System.currentTimeMillis();
            if (time == 0 || (currentTime - time > REPORT_TIME)) {
                mDownloadTask.onProgress();
                time = currentTime;
            }
            mDownloadTask.setReadLength(currentSize);
            Thread.sleep(SLEEP_TIME);
        } catch (Exception ex) {
            Log.d(TAG, "Enter readDownloadFile method,Exception: " + ex);
        } finally {
            try {
                if (null != file) {
                    file.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "Enter readDownloadFile method and read file fail,IOException: " + e);
            }
        }

    }

    @Override
    public void close() {
        Log.d(TAG, "Enter close method !");
    }
}
