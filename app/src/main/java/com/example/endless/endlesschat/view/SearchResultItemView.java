package com.example.endless.endlesschat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.event.AddFriendEvent;
import com.example.endless.endlesschat.model.SearchResultItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by endless .
 */

public class SearchResultItemView extends LinearLayout {
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.timetmp)
    TextView mTimetmp;
    @BindView(R.id.add_friend)
    Button mAddFriend;

    public SearchResultItemView(Context context) {
        this(context, null);

    }

    public SearchResultItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_searchitem, this);
        ButterKnife.bind(this,this);
    }

    public void bindview(SearchResultItem searchResultItem) {
        mUserName.setText(searchResultItem.userName);
        mTimetmp.setText(searchResultItem.timestamp);
        if(searchResultItem.isadd){
            mAddFriend.setEnabled(false);
            mAddFriend.setText("已添加");
        }else{
            mAddFriend.setEnabled(true);
            mAddFriend.setText("添加好友");
        }
    }

    @OnClick(R.id.add_friend)
    public void onClick() {
        EventBus.getDefault().post(new AddFriendEvent(mUserName.getText().toString().trim(),"Eddit请求加你为好友"));
    }
}
