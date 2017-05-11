package com.example.endless.endlesschat.app;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.EMMessageListenerAdapter;
import com.example.endless.endlesschat.database.DataBaseManager;
import com.example.endless.endlesschat.ui.Activity.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BuildConfig;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;

/**
 * Created by endless .
 */

public class MyAppliction extends Application {

    private int mDuanSound;
    private int mYuluSound;
    private SoundPool mSoundPool;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(), "66265bb8990f6b08627f7a8ab90e3297");
        initEaseMob();
        initSoundPool();
        DataBaseManager.getInstant().initDatabase(getApplicationContext());
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
    }

    private void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mDuanSound = mSoundPool.load(this, R.raw.duan, 1);
        mYuluSound = mSoundPool.load(this, R.raw.yulu, 1);
    }

    private void initEaseMob() {
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
        EMClient.getInstance().init(getApplicationContext(), options);
        if (BuildConfig.DEBUG) {
            EMClient.getInstance().setDebugMode(true);
        }
    }

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //判断是否在后前台
            if (isForeground()) {
                mSoundPool.play(mDuanSound, 1, 1, 0, 0, 1);
            } else {
                mSoundPool.play(mYuluSound, 1, 1, 0, 0, 1);
                showNotification(list.get(0));
            }

        }
    };

    private void showNotification(EMMessage emMessage) {
        String content = "";
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("User_Name",emMessage.getUserName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (emMessage.getBody() instanceof EMTextMessageBody) {
            content = ((EMTextMessageBody) emMessage.getBody()).getMessage();

        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Notification notification = builder.setContentTitle(getString(R.string.reciver_newmessage))
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.avatar6))
                .setSmallIcon(R.mipmap.ic_contact_selected_2)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);

    }

    public boolean isForeground() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo info = runningAppProcesses.get(i);
            if (runningAppProcesses == null) {
                return false;
            }
            if (info.processName.equals(getPackageName()) && info.importance == IMPORTANCE_FOREGROUND) {
                return true;

            }

        }
        return false;
    }
}
