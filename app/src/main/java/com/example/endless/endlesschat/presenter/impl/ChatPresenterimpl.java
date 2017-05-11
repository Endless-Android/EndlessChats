package com.example.endless.endlesschat.presenter.impl;

import com.example.endless.endlesschat.presenter.ChatPresenter;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.ChatView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endless .
 */
public class ChatPresenterimpl implements ChatPresenter {
    private ChatView mChatView;
    private List<EMMessage> mEMMessageList = new ArrayList<EMMessage>();
    private static final int DEFAULT_SIZE = 20;
    private boolean hasMoreDate = true;

    public ChatPresenterimpl(ChatView chatView) {
        mChatView = chatView;

    }

    @Override
    public void sendMessage(final String userName, final String msg) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(msg, userName);
                message.setStatus(EMMessage.Status.INPROGRESS);
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onStartSendMessages();
                    }
                });

                message.setMessageStatusCallback(mEMCallBack);

                mEMMessageList.add(message);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);

            }
        });
    }

    @Override
    public List<EMMessage> getMessages() {
        return mEMMessageList;
    }

    @Override
    public void loadMessage(final String username) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                if (conversation != null) {
                    //获取此会话的所有消息
                    List<EMMessage> messages = conversation.getAllMessages();
                    mEMMessageList.addAll(messages);
                    //指定会话消息未读数清零
                   conversation.markAllMessagesAsRead();
                }
                ThreadUtils.RunMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.loadSuccess();
                    }
                });
                //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
                //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
                // List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
            }
        });


    }

    @Override
    public void loadMoreMessage(final String user_name) {
        if (hasMoreDate) {
            ThreadUtils.runOnBackgroundThread(new Runnable() {
                @Override
                public void run() {
                    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user_name);
                    //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
                    String msgId = mEMMessageList.get(0).getMsgId();
                    final List<EMMessage> messages = conversation.loadMoreMsgFromDB(msgId, DEFAULT_SIZE);
                    if (messages.size() < DEFAULT_SIZE) {
                        hasMoreDate = false;
                    }
                    mEMMessageList.addAll(0, messages);
                    ThreadUtils.RunMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.loadMoreMessageSuccess(messages.size());
                        }
                    });
                }
            });
        } else {
            mChatView.notMoreDate();
        }

    }

    @Override
    public void markRead(String user_name) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user_name);
        conversation.markAllMessagesAsRead();
    }


    private EMCallBack mEMCallBack = new EMCallBack() {
        @Override
        public void onSuccess() {
            ThreadUtils.RunMainThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.onSendMessageSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.RunMainThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.onSendMessageError();
                }
            });
        }

        @Override
        public void onProgress(int i, String s) {

        }
    };


}
