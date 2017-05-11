package com.example.endless.endlesschat.presenter.impl;

import android.util.Log;

import com.example.endless.endlesschat.database.DataBaseManager;
import com.example.endless.endlesschat.event.AddFriendEvent;
import com.example.endless.endlesschat.model.SearchResultItem;
import com.example.endless.endlesschat.model.User;
import com.example.endless.endlesschat.presenter.AddFriendPresenter;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.AddFriendView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by endless .
 */

public class AddFriendPersenterimpl implements AddFriendPresenter {
    private AddFriendView mAddFriendView;
    private List<SearchResultItem> mSearchResultItems;


    public AddFriendPersenterimpl(AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
        mSearchResultItems = new ArrayList<SearchResultItem>();
        EventBus.getDefault().register(this);
    }


    @Override
    public void SearchFriend(String keyword) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereContains("username", keyword).addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        mAddFriendView.searchempty();
                    } else {
                        mAddFriendView.searchsuccess();
                        for (int i = 0; i < list.size(); i++) {
                            SearchResultItem item = new SearchResultItem();
                            item.userName = list.get(i).getUsername();
                            item.timestamp = list.get(i).getCreatedAt();
                           List<String> contasts = DataBaseManager.getInstant().queryContast();
                            item.isadd = contasts.contains(list);
                            mSearchResultItems.add(item);
                        }
                    }
                } else {
                    mAddFriendView.searchfailed();
                    Log.i("AAAAA", e.toString());
                }
            }
        });

    }

    @Override
    public List<SearchResultItem> getSearchRuslt() {
        return mSearchResultItems;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleAddFriendEvent(AddFriendEvent event){
        //参数为要添加的好友的username和添加理由
        try {
            EMClient.getInstance().contactManager().addContact(event.userName, event.reason);
            ThreadUtils.RunMainThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.addFriendSuccess();
                }
            });

        } catch (HyphenateException e) {
            e.printStackTrace();
            ThreadUtils.RunMainThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.addFriendFailed();
                }
            });

        }

    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }

}
