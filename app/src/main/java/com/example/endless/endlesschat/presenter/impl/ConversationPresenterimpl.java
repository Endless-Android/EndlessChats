package com.example.endless.endlesschat.presenter.impl;

import com.example.endless.endlesschat.presenter.ConversationPresenter;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.ConversationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by endless .
 */
public class ConversationPresenterimpl implements ConversationPresenter{

    private ConversationView mConversationView;
    private List<EMConversation> mEMConversations;


    public ConversationPresenterimpl(ConversationView view){
        mEMConversations = new ArrayList<EMConversation>();
        mConversationView = view;
    }

    @Override
    public void loadConversation() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
                mEMConversations.clear();
                mEMConversations.addAll(conversations.values());
                Collections.sort(mEMConversations, new Comparator<EMConversation>() {
                    @Override
                    public int compare(EMConversation o1, EMConversation o2) {
                        return (int)(o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime());
                    }
                });
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mConversationView.loadConversationSuccess();
                    }
                });
            }
        });


    }

    @Override
    public List<EMConversation> getMessage() {
        return mEMConversations;
    }
}
