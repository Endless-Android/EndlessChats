package com.example.endless.endlesschat.event;

/**
 * Created by endless .
 */

public class AddFriendEvent {
    public AddFriendEvent(String userName, String reason){
        this.userName = userName;
        this.reason = reason;
    }
    public String userName;
    public String reason;

}
