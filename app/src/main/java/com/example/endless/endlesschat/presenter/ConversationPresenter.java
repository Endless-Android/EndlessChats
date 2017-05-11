package com.example.endless.endlesschat.presenter;

import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Created by endless .
 */
public interface ConversationPresenter {
    void loadConversation();

    List<EMConversation> getMessage();
}
