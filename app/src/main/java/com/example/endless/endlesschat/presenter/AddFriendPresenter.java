package com.example.endless.endlesschat.presenter;

import com.example.endless.endlesschat.model.SearchResultItem;

import java.util.List;
/**
 * Created by endless .
 */

public interface AddFriendPresenter {

    void SearchFriend(String keyword);

    List<SearchResultItem> getSearchRuslt();

    void destroy();
}
