package com.example.endless.endlesschat.ui.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/**
 * Created by endless .
 */

public abstract class BaseActivity extends AppCompatActivity {


    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        init();
    }

    protected void init() {

    }


    public abstract int getLayoutID();

    public void GoTo(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
        finish();
    }

    public void showProgressDialog(String msg){
    if(mProgressDialog==null){
        mProgressDialog = new ProgressDialog(this);
    }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }


    public void hintProgressDialog(){
        mProgressDialog.hide();
    }

    public void hintKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    public void GoTo(Class activity,boolean isfinish){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
        if (isfinish){
            finish();
        }
    }

}
