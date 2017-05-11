package com.example.endless.endlesschat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.endless.endlesschat.model.SearchResultItem;
import com.example.endless.endlesschat.view.SearchResultItemView;

import java.util.List;

/**
 * Created by endless .
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemHolder> {
    private Context mContext;
    private List<SearchResultItem> mSearchResultItems;

    public SearchResultAdapter(Context context, List<SearchResultItem> searchResultItemList) {
        mContext = context;
        mSearchResultItems = searchResultItemList;
    }

    @Override
    public SearchResultItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItemHolder(new SearchResultItemView(mContext));
    }

    @Override
    public void onBindViewHolder(SearchResultItemHolder holder, int position) {
        holder.mSearchResultItemView.bindview(mSearchResultItems.get(position));

    }

    @Override
    public int getItemCount() {
        return mSearchResultItems.size();
    }

    public class SearchResultItemHolder extends RecyclerView.ViewHolder {
        private SearchResultItemView mSearchResultItemView;

        public SearchResultItemHolder(SearchResultItemView itemView) {
            super(itemView);
            mSearchResultItemView = itemView;
        }
    }

}
