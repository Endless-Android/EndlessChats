package com.example.endless.endlesschat.ui.Activity;

import android.os.Handler;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.presenter.SplashPresenter;
import com.example.endless.endlesschat.presenter.impl.spalshPresenteripml;
import com.example.endless.endlesschat.view.SplashView;

/**
 * Created by endless .
 */

public class SpashActivity extends BaseActivity implements SplashView {
    private static final int DELAY = 2000;
    private SplashPresenter mSplashPresenter;
    private Handler mHandler = new Handler();

    @Override
    public int getLayoutID() {
        return R.layout.activity_splash;
    }

    protected void init() {
        super.init();
        mSplashPresenter = new spalshPresenteripml(this);
        mSplashPresenter.checkLoginStatus();
    }

    private void navigateToMain() {
      /*  Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        GoTo(MainActivity.class);
    }

    private void navigateTOLogin() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent = new Intent(SpashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();*/
                GoTo(LoginActivity.class);
            }
        }, DELAY);
    }

    @Override
    public void onLoginIn() {
        navigateToMain();

    }

    @Override
    public void notLogin() {
        navigateTOLogin();
    }

}
