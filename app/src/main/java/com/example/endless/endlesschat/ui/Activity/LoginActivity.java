package com.example.endless.endlesschat.ui.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by endless .
 */
public class LoginActivity extends BaseActivity {


    private static final int PERMISSIONREQEST = 1001;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.new_user)
    TextView mNewUser;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }


    protected void init() {
        super.init();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.login, R.id.new_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if(hasWriteExternalStoragePermission()){
                    login();
                }else{
                    applyWriteExternalStoragePermission();
                }

                break;
            case R.id.new_user:
                GoTo(RegisterActivity.class);
                break;
        }
    }

    private void applyWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSIONREQEST);
    }

    private boolean hasWriteExternalStoragePermission() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void login() {
        showProgressDialog("正在登录");
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        hintProgressDialog();
                        hintKeyBoard();

                    }
                });
                GoTo(MainActivity.class);
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                showProgressDialog("登录失败");
                Log.d("main", "登录聊天服务器失败！"+code);
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        hintProgressDialog();
                        Toast.makeText(LoginActivity.this,"此账号已经登录",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSIONREQEST){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                login();
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
