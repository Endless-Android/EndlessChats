package com.example.endless.endlesschat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.endless.endlesschat.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */
public class ConversationItemView extends RelativeLayout {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.last_message)
    TextView mLastMessage;
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.unread_count)
    TextView mUnreadCount;

    public ConversationItemView(Context context) {
        this(context, null);
    }

    public ConversationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_conversation_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(EMConversation emConversation) {
        String userName = emConversation.getUserName();
        mUserName.setText(userName);
        long msgTime = emConversation.getLastMessage().getMsgTime();
        String timestampString = DateUtils.getTimestampString(new Date(msgTime));
        mTimestamp.setText(timestampString);
        EMMessageBody body = emConversation.getLastMessage().getBody();
        if (body instanceof EMTextMessageBody) {
            mLastMessage.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mLastMessage.setText(getContext().getString(R.string.unread_msg));
        }
        int unreadMsgCount = emConversation.getUnreadMsgCount();
        if(unreadMsgCount == 0){
            mUnreadCount.setVisibility(GONE);
        }else{
            mUnreadCount.setVisibility(VISIBLE);
            mUnreadCount.setText(String.valueOf(unreadMsgCount));
        }
    }
}
