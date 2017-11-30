package com.aphrodite.obtainstudents.config;

/**
 * Created by Aphrodite on 2017/6/1.
 */

public interface IntentAction {

    public interface BreakPointDownAction {
        String ACTION = "com.aphrodite.obtainstudents.ui.activity.BREAKPOINTDOWN";
    }

    public interface DownloadServiceAction {
        String ACTION = "com.aphrodite.obtainstudents.service.DOWNLOADSERVICE";

        String ACTION_START = "action_start";
        String ACTION_STOP = "action_stop";
        String ACTION_UPDATE = "action_update";
        String EXTRA_FILE_INFO = "extra_file_info";
    }

}
