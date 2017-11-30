package com.aphrodite.obtainstudents.db.inter;

import com.aphrodite.obtainstudents.entity.DownloadFileInfo;

import java.util.List;

/**
 * Created by Aphrodite on 2017/5/16.
 */

public interface IDownloadFileDbAdapter {
    /**
     * 保存已下载任务信息
     *
     * @param fileInfo
     * @throws Exception
     */
    void saveFile(DownloadFileInfo fileInfo) throws Exception;

    /**
     * 查询已保存信息
     *
     * @return
     * @throws Exception
     */
    List<DownloadFileInfo> getFile() throws Exception;

    /**
     * 更新数据
     *
     * @param apkInfo
     * @throws Exception
     */
    void updateFile(DownloadFileInfo apkInfo) throws Exception;

    /**
     * 删除数据
     *
     * @param fileName
     * @throws Exception
     */
    void deleteFile(String fileName) throws Exception;

}
