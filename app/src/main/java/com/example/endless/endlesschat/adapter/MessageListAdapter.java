package com.example.endless.endlesschat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.endless.endlesschat.widget.ReceiveMessageItemView;
import com.example.endless.endlesschat.widget.SendMessageItemView;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endless .
 */


public class MessageListAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<EMMessage> mEMMessages = new ArrayList<EMMessage>();
    private static final int ITEM_TYPE_SEND_MESSAGE = 0;
    private static final int ITEM_TYPE_RECEIVE_MESSAGE = 1;


    public MessageListAdapter(Context context , List<EMMessage> emMessages){
        mContext = context;
        mEMMessages = emMessages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_SEND_MESSAGE){
            return new SendViewHolder(new SendMessageItemView(mContext));
        }else{
            return new ReceiveViewHolder(new ReceiveMessageItemView(mContext));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean showTime;
        if (position == 0 || showTime(position)){
            showTime = true;
        }else{
        showTime = false;
        }
        if(holder instanceof SendViewHolder){
            ((SendViewHolder) holder).mSendMessageItemView.bindView(mEMMessages.get(position),showTime);
        }else {
            ((ReceiveViewHolder) holder).mReceiveMessageItemView.bindView(mEMMessages.get(position),showTime);

        }

    }

    private boolean showTime(int position) {
        long msgTime = mEMMessages.get(position).getMsgTime();

        long preTime = mEMMessages.get(position - 1).getMsgTime();

        return msgTime - preTime >30000; //大于30s显示
    }

    @Override
    public int getItemCount() {
        return mEMMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = mEMMessages.get(position);
        return message.direct() == EMMessage.Direct.SEND ? ITEM_TYPE_SEND_MESSAGE : ITEM_TYPE_RECEIVE_MESSAGE;
    }

    public void addNewMessage(EMMessage emMessage) {
        mEMMessages.add(emMessage);
        notifyDataSetChanged();
    }


    public class ReceiveViewHolder extends RecyclerView.ViewHolder{
        public ReceiveMessageItemView mReceiveMessageItemView;
        public ReceiveViewHolder(ReceiveMessageItemView itemView) {
            super(itemView);
            mReceiveMessageItemView = itemView;
        }
    }

    public class SendViewHolder extends RecyclerView.ViewHolder{
    public SendMessageItemView mSendMessageItemView;
        public SendViewHolder(SendMessageItemView itemView) {
            super(itemView);
            mSendMessageItemView = itemView;
        }
    }
}
