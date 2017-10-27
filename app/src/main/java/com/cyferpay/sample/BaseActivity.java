package com.cyferpay.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements LoadingBridge {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initializeToolbarUpEnabled() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initializeToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public synchronized void showProgress() {
        ProgressDialogFragment.show(getSupportFragmentManager());
    }

    @Override
    public synchronized void hideProgress() {
        ProgressDialogFragment.hide(getSupportFragmentManager());
    }

    public synchronized void showDummyProgress(int second) {
        showProgress();
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            hideProgress();
            performAction();
        }, second * 1000);
    }

    public synchronized void performAction() {

    }
}
