package com.user.stak.stak.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.user.stak.stak.R;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.loading));
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }

    public void showProgress(final boolean show) {
        if(progressDialog != null) {
            if(show)
                progressDialog.show();
            else
                progressDialog.dismiss();
        }
    }
}
