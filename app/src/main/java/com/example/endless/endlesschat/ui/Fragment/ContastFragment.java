package com.example.endless.endlesschat.ui.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.ContastListAdapter;
import com.example.endless.endlesschat.presenter.ContastPresenter;
import com.example.endless.endlesschat.presenter.impl.ContastPersenterimpl;
import com.example.endless.endlesschat.ui.Activity.AddfrienfActivity;
import com.example.endless.endlesschat.ui.Activity.ChatActivity;
import com.example.endless.endlesschat.view.ContastView;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */

public class ContastFragment extends BaseFragment implements ContastView {


    @BindView(R.id.recyclreview)
    RecyclerView mRecyclreview;

    @BindView(R.id.title_bar)
    TextView mTitle;
    @BindView(R.id.add)
    ImageView madd;
    @BindView(R.id.swip_refresh)
    SwipeRefreshLayout mSwipRefresh;
    private ContastListAdapter mContastListAdapter;
    private ContastPresenter mContastPresenter;


    @Override
    public int getLayoutRes() {
        return R.layout.contast_fragment;
    }

    @Override
    public void init() {
        super.init();
        mTitle.setText("联系人");
        madd.setVisibility(View.VISIBLE);
        mContastPresenter = new ContastPersenterimpl(this);
        initRecyclreview();
        mContastPresenter.loadcontast();
        mSwipRefresh.setColorSchemeResources(R.color.qq_blue, R.color.qq_blue_dark);
        mSwipRefresh.setOnRefreshListener(mOnRefreshListener);
        madd.setOnClickListener(mOnClickListener);

        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);

    }

    private void initRecyclreview() {
        mContastListAdapter = new ContastListAdapter(getContext(), mContastPresenter.getDate());
        mRecyclreview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclreview.setHasFixedSize(true);
        mRecyclreview.setAdapter(mContastListAdapter);
        mContastListAdapter.setOnClick(mOnItemClick);
    }


    private ContastListAdapter.OnItemClick mOnItemClick = new ContastListAdapter.OnItemClick() {
        @Override
        public void OnClick(String userName) {
            //跳转到聊天界面
            Intent intent = new Intent(getContext(),ChatActivity.class);
            intent.putExtra("User_Name",userName);
            startActivity(intent);


        }

        @Override
        public void OnLongClick(String userName) {
            //跳转到删除联系人界面
            showDeleteFriend(userName);

        }
    };

    private void showDeleteFriend(final String userName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String msg = String.format(getString(R.string.delete_friend_message), userName);
        builder.setTitle("删除好友").setMessage(msg).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消删除
                dialog.dismiss();
            }
        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除好友

                        mContastPresenter.deleteFriend(userName);
                    }
                });

        builder.show();

    }

    @Override
    public void Contastloadsusses() {
        mContastListAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "联系人加载成功", Toast.LENGTH_LONG).show();
        mSwipRefresh.setRefreshing(false);
    }

    @Override
    public void Contastloadfail() {
        Toast.makeText(getContext(), "联系人加载失败", Toast.LENGTH_LONG).show();
        mSwipRefresh.setRefreshing(false);
    }

    @Override
    public void onDeleteFriendSuccess() {
        Toast.makeText(getContext(), "删除联系人成功", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDeleteFriendFailed() {
        Toast.makeText(getContext(), "删除联系人失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContastPresenter.refresh();
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goTo(AddfrienfActivity.class, false);
        }
    };

    private EMContactListener mEMContactListener = new EMContactListener() {
        @Override
        public void onContactAdded(String s) {
            //增加了联系人时回调此方法
            Log.d("TAG", "onContactAdded: " + s);
            mContastPresenter.loadcontast();

        }

        @Override
        public void onContactDeleted(String s) {
            //被删除时回调此方法
            mContastPresenter.loadcontast();
            Log.d("TAG", "onContactDeleted: " + s);

        }

        @Override
        public void onContactInvited(String s, String s1) {
            //收到好友邀请
            Log.d("TAG", "onContactInvited: " + s);
        }

        @Override
        public void onContactAgreed(String s) {
            //好友请求被同意
            Log.d("TAG", "onContactAgreed: " + s);

        }

        @Override
        public void onContactRefused(String s) {
            //好友请求被拒绝
            Log.d("TAG", "onContactRefused: " + s);

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().contactManager().removeContactListener(mEMContactListener);
    }
}

