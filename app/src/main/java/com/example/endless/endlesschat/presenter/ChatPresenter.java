package com.example.endless.endlesschat.presenter;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by endless .
 */
public interface ChatPresenter {
    void sendMessage(String userName,String msg);

    List<EMMessage> getMessages();

    void loadMessage(String mUser_name);

    void loadMoreMessage(String user_name);

    void markRead(String user_name);
}
