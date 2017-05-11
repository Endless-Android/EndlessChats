package com.example.endless.endlesschat.presenter.impl;

import com.example.endless.endlesschat.database.Contast;
import com.example.endless.endlesschat.database.DataBaseManager;
import com.example.endless.endlesschat.model.ContastList;
import com.example.endless.endlesschat.presenter.ContastPresenter;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.ContastView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by endless .
 */
public class ContastPersenterimpl implements ContastPresenter {
    private ContastView mContastView;
    private List<ContastList> mContastLists;


    public ContastPersenterimpl(ContastView view) {
        mContastView = view;
        mContastLists = new ArrayList<ContastList>();
    }

    @Override
    public void loadcontast() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (int i = 0; i < usernames.size(); i++) {
                        ContastList item = new ContastList();
                        item.contact = usernames.get(i);
                        mContastLists.add(item);
                     Contast contast = new Contast();
                        contast.userName = usernames.get(i);
                        DataBaseManager.getInstant().savaContast(contast);
                    }
                    Collections.sort(mContastLists, new Comparator<ContastList>() {
                        @Override
                        public int compare(ContastList o1, ContastList o2) {
                            return o1.contact.charAt(0) - o2.contact.charAt(0);
                        }
                    });
                    for (int i = 0; i < mContastLists.size(); i++) {
                        ContastList item = mContastLists.get(i);
                        if (i > 0 && item.getFirstLetter().equals(mContastLists.get(i - 1).getFirstLetter())) {
                            item.showfirst = false;
                        }
                    }


                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContastView.Contastloadsusses();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContastView.Contastloadfail();
                        }
                    });
                }
            }
        });

    }

    @Override
    public List<ContastList> getDate() {
        return mContastLists;
    }

    @Override
    public void refresh() {
        mContastLists.clear();
        loadcontast();
    }

    @Override
    public void deleteFriend(final String userName) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(userName);
                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContastView.onDeleteFriendSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    mContastView.onDeleteFriendFailed();
                }
            }
        });
    }
}
