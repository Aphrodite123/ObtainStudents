package com.aphrodite.obtainstudents.http.down.inter;

/**
 * Created by Aphrodite on 2017/6/5.
 */

public interface ITaskDriver {
    /**
     * 创建连接
     *
     * @throws Exception
     */
    public void connect() throws Exception;

    /**
     * 读取数据
     *
     * @throws Exception
     */
    public void read() throws Exception;

    /**
     * 关闭连接
     */
    public void close();
}
