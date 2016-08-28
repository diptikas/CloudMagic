package com.diptika.webyog.cloudmagicassignment.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.diptika.webyog.cloudmagicassignment.R;

/**
 * Created by diptika.s on 27/08/16.
 */

public class SplashActivity extends BaseActivity implements Runnable {

    private static final int SPLASH_TIME_OUT = 3000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(this, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
        super.onDestroy();
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MsgListActivity.class));
        finish();
    }


}





