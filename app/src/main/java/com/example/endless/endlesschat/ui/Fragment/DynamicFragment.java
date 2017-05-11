package com.example.endless.endlesschat.ui.Fragment;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.ui.Activity.LoginActivity;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by endless .
 */
public class DynamicFragment extends BaseFragment {

    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.title_bar)
    TextView mtitle_bar;


    @Override
    public int getLayoutRes() {
        return R.layout.dynamic_fragment;
    }



    @OnClick(R.id.logout)
    public void onClick() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        goTo(LoginActivity.class);
                        Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onError(int i, String s) {
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "退出登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    @Override
    public void init() {
        super.init();
        mtitle_bar.setText(R.string.dynamic);
        String logout = String.format(getString(R.string.logout), EMClient.getInstance().getCurrentUser());
        mLogout.setText(logout);
    }

}
