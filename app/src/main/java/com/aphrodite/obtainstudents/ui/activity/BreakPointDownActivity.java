package com.aphrodite.obtainstudents.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.aphrodite.obtainstudents.R;

import butterknife.BindView;

/**
 * Created by Aphrodite on 2017/6/1.
 */

public class BreakPointDownActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.start_button)
    Button mStartBtn;
    @BindView(R.id.stop_button)
    Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_breakpoint;
    }

    private void initView() {
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                break;
            case R.id.stop_button:
                break;
        }
    }
}
