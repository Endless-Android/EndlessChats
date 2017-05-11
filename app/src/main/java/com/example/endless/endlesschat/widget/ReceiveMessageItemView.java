package com.example.endless.endlesschat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.endless.endlesschat.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */

public class ReceiveMessageItemView extends RelativeLayout {
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.receive_message)
    TextView mReceiveMessage;

    public ReceiveMessageItemView(Context context) {
        this(context, null);
    }

    public ReceiveMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_message_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(EMMessage emMessage, boolean showTime) {
        updateText(emMessage);
        updateTime(emMessage, showTime);
    }

    private void updateText(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            mReceiveMessage.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mReceiveMessage.setText("非文本消息");
        }
    }

    private void updateTime(EMMessage emMessage, boolean showTime) {
        if (showTime) {
            mTimestamp.setVisibility(VISIBLE);
            long msgTime = emMessage.getMsgTime();
            String time = DateUtils.getTimestampString(new Date(msgTime));
            mTimestamp.setText(time);
        } else {
            mTimestamp.setVisibility(GONE);
        }

    }
}
