package com.example.endless.endlesschat.view;
/**
 * Created by endless .
 */

public interface ChatView {


    void onStartSendMessages();

    void onSendMessageSuccess();

    void onSendMessageError();

    void loadSuccess();

    void loadMoreMessageSuccess(int size);

    void notMoreDate();
}
