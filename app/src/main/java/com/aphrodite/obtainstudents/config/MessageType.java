package com.aphrodite.obtainstudents.config;

/**
 * Created by Aphrodite on 2017/5/10.
 */

public interface MessageType {
    public interface DownloadFileMessage {
        /**
         * 此MessageType的基数
         */
        int BASE = 0x10000000;

        /**
         * 下载成功
         */
        int DOWNLOAD_SUCCESS = BASE + 1;

        /**
         * 下载失败
         */
        int DOWNLOAD_FAILED = BASE + 2;
    }

    public interface BreakPointMessage {
        /**
         * 此MessageType的基数
         */
        int BASE = 0x20000000;
        /**
         * 断点续传初始化成功
         */
        int INIT_DOWN = BASE + 1;
    }
}
