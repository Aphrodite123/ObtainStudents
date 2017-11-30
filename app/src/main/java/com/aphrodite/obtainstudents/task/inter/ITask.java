package com.aphrodite.obtainstudents.task.inter;

/**
 * Created by Aphrodite on 2017/6/6.
 */

public interface ITask {
    /**
     * 新任务
     */
    int TASK_STATUS_NEW = 0;

    /**
     * 运行中任务
     */
    int TASK_STATUS_RUNNING = 1;

    /**
     * 任务进度
     */
    int TASK_STATUS_PROCESS = 2;

    /**
     * 停止任务
     */
    int TASK_STATUS_STOPPED = 3;

    /**
     * 删除任务
     */
    int TASK_STATUS_DELETED = 4;

    /**
     * 关闭任务
     */
    int TASK_STATUS_FINISHED = 5;

    /**
     * 等待任务
     */
    int TASK_STATUS_WAITTING = 6;

    /**
     * 出错任务
     */
    int TASK_STATUS_ERROR = 7;
}
