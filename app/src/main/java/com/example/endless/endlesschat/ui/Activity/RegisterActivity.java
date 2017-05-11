package com.example.endless.endlesschat.ui.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.presenter.RegisterPresenter;
import com.example.endless.endlesschat.presenter.impl.RegisterPresenterimpl;
import com.example.endless.endlesschat.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by endless .
 */

public class RegisterActivity extends BaseActivity implements RegisterView {


    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.register)
    Button mRegister;
    private RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }


    protected void init() {
        super.init();
        mRegisterPresenter = new RegisterPresenterimpl(this);
        mConfirmPassword.setOnEditorActionListener(mOnEditorActionListener);

    }


    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register();
                hintKeyBoard();
                return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void onClick() {
        register();
        showProgressDialog("正在注册");
        hintKeyBoard();
    }

    private void register() {
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmpassword = mConfirmPassword.getText().toString().trim();
        hintKeyBoard();
        mRegisterPresenter.register(userName, password, confirmpassword);
    }

    @Override
    public void confirmpasswordError() {
        mConfirmPassword.setError(getString(R.string.confirmpasswor_error));
    }

    @Override
    public void passwordError() {
        mPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void onUserNameError() {
        mUserName.setError(getString(R.string.username_error));
    }

    @Override
    public void Registersucces() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
        hintProgressDialog();
        GoTo(LoginActivity.class);
    }

    @Override
    public void Registerfailed() {
        Toast.makeText(this, "注册失败", Toast.LENGTH_LONG).show();
        hintProgressDialog();
    }
}
