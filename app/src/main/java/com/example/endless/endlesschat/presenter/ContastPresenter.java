package com.example.endless.endlesschat.presenter;

import com.example.endless.endlesschat.model.ContastList;

import java.util.List;
/**
 * Created by endless .
 */

public interface ContastPresenter {
    void loadcontast();

    List<ContastList> getDate();

    void refresh();

    void deleteFriend(String userName);
}
