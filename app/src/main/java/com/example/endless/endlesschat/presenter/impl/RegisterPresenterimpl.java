package com.example.endless.endlesschat.presenter.impl;

import com.example.endless.endlesschat.presenter.RegisterPresenter;
import com.example.endless.endlesschat.utils.StringUtils;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.RegisterView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by endless .
 */
public class RegisterPresenterimpl implements RegisterPresenter {
    private RegisterView mRegisterView;
    private static final String TAG = "RegisterPresenterimpl";


    public RegisterPresenterimpl(RegisterView view) {
        mRegisterView = view;
    }

    @Override
    public void register(String userName, String password, String confirmpassword) {
        if (StringUtils.isUsername(userName)) {
            if (StringUtils.isPassword(password)) {
                if (password.equals(confirmpassword)) {
                    registerBmob(userName, password);
                } else {
                    mRegisterView.confirmpasswordError();
                }

            } else {
                mRegisterView.passwordError();
            }
        } else {
            mRegisterView.onUserNameError();
        }


    }

    private void registerBmob(final String userName, final String password) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(userName);
        bmobUser.setPassword(password);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                  registerEastMob(userName,password);
                } else{
                    mRegisterView.Registerfailed();
            }
            }
        });
    }

    private void registerEastMob(final String userName, final String password) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, password);
                    mRegisterView.Registersucces();
                    Log.i(TAG, "run: Success---Success");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, password);
                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.Registersucces();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.Registerfailed();
                        }
                    });
                }
            }
        });



    }
}
