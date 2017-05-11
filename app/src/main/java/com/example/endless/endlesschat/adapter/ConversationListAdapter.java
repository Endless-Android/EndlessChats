package com.example.endless.endlesschat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.endless.endlesschat.ui.Activity.ChatActivity;
import com.example.endless.endlesschat.view.ConversationItemView;
import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Created by endless .
 */

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationItemHolder> {
    private Context mContext;
    private List<EMConversation> mEMConversations;

    public ConversationListAdapter(Context context, List<EMConversation> emConversations) {
        mContext = context;
        mEMConversations = emConversations;
    }


    @Override
    public ConversationItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ConversationItemHolder(new ConversationItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ConversationItemHolder holder, final int position) {
        holder.mConversationView.bindView(mEMConversations.get(position));
        holder.mConversationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("User_Name", mEMConversations.get(position).getUserName());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mEMConversations.size();
    }

    public class ConversationItemHolder extends RecyclerView.ViewHolder {
        private ConversationItemView mConversationView;

        public ConversationItemHolder(ConversationItemView itemView) {
            super(itemView);
            mConversationView = itemView;
        }


    }

}
