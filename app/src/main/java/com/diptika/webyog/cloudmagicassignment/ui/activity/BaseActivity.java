package com.diptika.webyog.cloudmagicassignment.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.diptika.webyog.cloudmagicassignment.R;

import retrofit2.Callback;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

    }

    /**
     * @param isShowHome
     */
    protected void initToolBar(boolean isHome,boolean isShowHome) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHome);
            getSupportActionBar().setDisplayShowHomeEnabled(isShowHome);

        }
    }
    protected void showSnackbar(String message, boolean isLong) {
        Snackbar.make(findViewById(R.id.rootLayout), message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void showProgress() {
        if (mProgressBar != null) {
            if (mProgressBar.isShowing()) {
                return;
            }
        }
        setProgressBarIndeterminateVisibility(true);
        mProgressBar = new ProgressDialog(BaseActivity.this);
        mProgressBar.setCancelable(true);
        mProgressBar.setMessage(getString(R.string.loading));
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            mProgressBar.show();
        }
    }
protected void hideProgress(){
    setProgressBarIndeterminateVisibility(false);
    mProgressBar.dismiss();
}
}
