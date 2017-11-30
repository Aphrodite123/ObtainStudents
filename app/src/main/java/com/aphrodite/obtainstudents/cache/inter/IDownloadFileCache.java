package com.aphrodite.obtainstudents.cache.inter;

import com.aphrodite.obtainstudents.entity.DownloadFileInfo;

import java.util.List;

/**
 * Created by Aphrodite on 2017/5/16.
 */

public interface IDownloadFileCache {
    List<DownloadFileInfo> getFileInfo();
}
