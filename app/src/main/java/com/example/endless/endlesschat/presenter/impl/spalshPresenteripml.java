package com.example.endless.endlesschat.presenter.impl;

import com.example.endless.endlesschat.presenter.SplashPresenter;
import com.example.endless.endlesschat.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * Created by endless .
 */

public class spalshPresenteripml implements SplashPresenter{
    private SplashView mSplashView;

    public spalshPresenteripml(SplashView view){
        mSplashView = view;
    }

    @Override
    public void checkLoginStatus() {
        if(loginIn()){
            mSplashView.onLoginIn();
        }else {
            mSplashView.notLogin();
        }

    }

    public boolean loginIn(){
        return EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected();
    }

}
