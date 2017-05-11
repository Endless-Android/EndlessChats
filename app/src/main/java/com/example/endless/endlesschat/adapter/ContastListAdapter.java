package com.example.endless.endlesschat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.endless.endlesschat.model.ContastList;
import com.example.endless.endlesschat.widget.ContastItemView;

import java.util.List;

/**
 * Created by endless .
 */

public class ContastListAdapter extends RecyclerView.Adapter<ContastListAdapter.ContastViewHolder> {
    private Context mContext;
    private List<ContastList> mLists;
    private OnItemClick mOnItemClick;
    public  ContastListAdapter(Context context, List<ContastList> contastLists){
        mContext = context;
        mLists= contastLists;
    }

    @Override
    public ContastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContastViewHolder(new ContastItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ContastViewHolder holder, final int position) {
        holder.mContastItemView.bindview(mLists.get(position));

        holder.mContastItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick!=null){
                    mOnItemClick.OnClick(mLists.get(position).contact);
                }

            }
        });

        holder.mContastItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClick != null){
                    mOnItemClick.OnLongClick(mLists.get(position).contact);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }



    public class ContastViewHolder extends RecyclerView.ViewHolder{
    private ContastItemView mContastItemView;

        public ContastViewHolder( ContastItemView itemView) {
            super(itemView);
            mContastItemView = itemView;
        }
    }

    public interface OnItemClick{
        void OnClick(String userName);
        void OnLongClick(String userName);
    }

    public void setOnClick(OnItemClick l){
        mOnItemClick = l;

    }


}
