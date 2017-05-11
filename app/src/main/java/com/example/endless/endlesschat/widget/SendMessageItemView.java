package com.example.endless.endlesschat.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
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

public class SendMessageItemView extends RelativeLayout {
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.send_message)
    TextView mSendMessage;
    @BindView(R.id.send_message_progress)
    ImageView mSendMessageProgress;

    public SendMessageItemView(Context context) {
        this(context, null);
    }

    public SendMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_item, this);
        ButterKnife.bind(this);
    }

    public void bindView(EMMessage emMessage,boolean showTime) {
        updateMessageBody(emMessage,showTime);
    }

    private void updateMessageBody(EMMessage emMessage,boolean showTime) {
        updateTime(showTime,emMessage);
        updateText(emMessage);
        updateProgress(emMessage);
    }

    private void updateProgress(EMMessage emMessage) {
        switch (emMessage.status()){
            case INPROGRESS:
                mSendMessageProgress.setVisibility(VISIBLE);
                mSendMessageProgress.setImageResource(R.drawable.progress);
                AnimationDrawable drawable = (AnimationDrawable) mSendMessageProgress.getDrawable();
                drawable.start();
                break;

            case SUCCESS:
                mSendMessageProgress.setVisibility(GONE);
                break;

            case FAIL:
                mSendMessageProgress.setVisibility(VISIBLE);
                mSendMessageProgress.setImageResource(R.mipmap.msg_error);
                break;
        }
    }

    private void updateText(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            mSendMessage.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mSendMessage.setText("非文本消息");
        }
    }

    private void updateTime(boolean showtime,EMMessage emMessage) {
        if(showtime){
            mTimestamp.setVisibility(VISIBLE);
            long msgTime = emMessage.getMsgTime();
            String time = DateUtils.getTimestampString(new Date(msgTime));
            mTimestamp.setText(time);
        }
        else{
            mTimestamp.setVisibility(GONE);

        }

    }

}
