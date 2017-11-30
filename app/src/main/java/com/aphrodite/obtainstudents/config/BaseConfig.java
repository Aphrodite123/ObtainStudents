package com.aphrodite.obtainstudents.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by Aphrodite on 2017/5/8.
 */

public class BaseConfig {
    /**
     * 域名
     */
    public static String sBaseAddress = "http://192.168.0.103:8080";
    /**
     * 查询学生信息URL
     */
    public static String sQueryStudentUrl = sBaseAddress + "/sma-upload/getStudentInfo";
    /**
     * 文件上传URL
     */
    public static String sUpFileUrl = sBaseAddress + "/sma-upload/UploadFile";
    /**
     * 服务器图片URL
     */
    public static String sDownFileUrl = sBaseAddress + "/image/ronger_2.jpg";

    /**
     * 断点续传文件Url
     */
    public static String sDownBreakPointUrl = sBaseAddress + "/image/izaodao_app.apk";


    /**
     * Sdcard根目录
     */
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator;

    /**
     * 应用根目录
     */
    public static final String APP_ROOT_PATH = SDCARD_PATH + "obtainstudent" + File.separator;

    /**
     * 上传照片本地路径
     */
    public static final String IMAGE_PATH = SDCARD_PATH + "ronger/ronger1.jpg";
    public static final String IMAGE_PATH_TWO = SDCARD_PATH + "ronger/ronger2.jpg";
    public static final String IMAGE_PATH_THREE = SDCARD_PATH + "ronger/ronger3.jpg";
    /**
     * 数据库名称
     */
    public static final String NAME_DATABASE = "student.db";

    /**
     * 断点续传文件下载本地保存路径
     */
    public static final String SAVE_FILE_PATH = APP_ROOT_PATH + "download" + File.separator;
}
