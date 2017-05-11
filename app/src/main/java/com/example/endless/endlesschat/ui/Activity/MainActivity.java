package com.example.endless.endlesschat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.EMMessageListenerAdapter;
import com.example.endless.endlesschat.factory.FragmentFactory;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
        EMClient.getInstance().addConnectionListener(mEMConnectionListener);
        initmsg();
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);

    }

    private void initmsg() {
        ThreadUtils.RunMainThread(new Runnable() {
            @Override
            public void run() {
                int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                BottomBarTab nearby = mBottomBar.getTabWithId(R.id.tab_conversation);
                nearby.setBadgeCount(unreadMsgsCount);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initmsg();
    }

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentContainer, FragmentFactory.getInstance().getFragment(tabId));
            fragmentTransaction.commit();
        }
    };

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            initmsg();
        }
    };

    private EMConnectionListener mEMConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int i) {
            if (i == EMError.USER_ALREADY_LOGIN) {
                ThreadUtils.runOnBackgroundThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "此账号已在别处登录，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
        EMClient.getInstance().removeConnectionListener(mEMConnectionListener);
    }
}
