package com.example.endless.endlesschat.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.SearchResultAdapter;
import com.example.endless.endlesschat.presenter.AddFriendPresenter;
import com.example.endless.endlesschat.presenter.impl.AddFriendPersenterimpl;
import com.example.endless.endlesschat.view.AddFriendView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */
public class AddfrienfActivity extends BaseActivity implements AddFriendView {
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.title_bar)
    TextView mtitle_bar;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.recyclreview)
    RecyclerView mRecyclreview;
    private AddFriendPresenter mAddFriendPresenter;
    private SearchResultAdapter mSearchResultAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_add;
    }

    @Override
    protected void init() {
        super.init();
        mtitle_bar.setText("添加好友");
        mBack.setVisibility(View.VISIBLE);
        mAddFriendPresenter = new AddFriendPersenterimpl(this);
        mBtnSearch.setOnEditorActionListener(mOnEditorActionListener);
        mBtnSearch.setOnClickListener(mOnClickListener);
        mBack.setOnClickListener(mOnClickListener);
        mSearchResultAdapter = new SearchResultAdapter(this, mAddFriendPresenter.getSearchRuslt());
        initRecyclreview();


    }

    private void initRecyclreview() {
        mRecyclreview.setHasFixedSize(true);
        mRecyclreview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclreview.setAdapter(mSearchResultAdapter);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            search();
            if (v.getId() == R.id.btn_search) {
                showProgressDialog("搜索中");
                hintKeyBoard();
            } else {
                finish();
            }

        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                hintKeyBoard();
                showProgressDialog("搜索中");
            }
            return false;
        }
    };

    private void search() {
        String keyword = mEdtSearch.getText().toString().trim();
        mAddFriendPresenter.SearchFriend(keyword);
    }


    public void searchempty() {
        Toast.makeText(this, "没有符合条件得的用户", Toast.LENGTH_LONG).show();
        hintProgressDialog();

    }

    @Override
    public void searchsuccess() {
        Toast.makeText(this, "查找成功", Toast.LENGTH_LONG).show();
        mSearchResultAdapter.notifyDataSetChanged();
        hintProgressDialog();

    }

    @Override
    public void searchfailed() {
        Toast.makeText(this, "查找失败", Toast.LENGTH_LONG).show();
        hintProgressDialog();

    }

    @Override
    public void addFriendSuccess() {
        Toast.makeText(this, "发送添加好友请求成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void addFriendFailed() {
        Toast.makeText(this, "发送添加好友请求失败", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddFriendPresenter.destroy();
    }
}
