package com.aphrodite.obtainstudents.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Aphrodite on 2017/5/15.
 */
@Entity//表明该类是持久化的类(持久化含义，存入数据库文件中，作本地化处理)
public class DownloadFileInfo {
    /**
     * id
     */
    @Id(autoincrement = true)//ID 表示标识主键 且主键不能用int autoincrement = true 表示主键会自增
    private long id;
    /**
     * 文件名
     */
    @Property(nameInDb = "file_name")
    /*允许你定义一个当前属性映射到的数据库列的非默认名称。如果为空，greenDAO将以SQL-ish方式使用字段名（大写字母，下划线代替驼峰，例如customName
    将成为CUSTOM_NAME）。 注意：当前只能使用内联常量来指定列名称*/
    private String fileName;
    /**
     * 存储路径
     */
    @Property(nameInDb = "save_path")
    private String savePath;
    /**
     * 下载路径Url
     */
    @Property(nameInDb = "url")
    private String url;
    /**
     * 文件总长度
     */
    @Property(nameInDb = "total_length")
    private long totalLength;
    /**
     * 下载长度
     */
    @Property(nameInDb = "read_length")
    private long readLength;

    @Generated(hash = 2098429623)
    public DownloadFileInfo(long id, String fileName, String savePath, String url,
                            long totalLength, long readLength) {
        this.id = id;
        this.fileName = fileName;
        this.savePath = savePath;
        this.url = url;
        this.totalLength = totalLength;
        this.readLength = readLength;
    }

    @Generated(hash = 1515350355)
    public DownloadFileInfo() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo: {" +
                "mFileName='" + fileName + '\'' +
                ", mId=" + id +
                ", mSavePath='" + savePath + '\'' +
                ", mUrl='" + url + '\'' +
                ", mTotalLength=" + totalLength +
                ", mReadLength=" + readLength +
                '}';
    }
}
