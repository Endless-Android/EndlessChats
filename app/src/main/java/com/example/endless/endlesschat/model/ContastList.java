package com.example.endless.endlesschat.model;

/**
 * Created by endless .
 */
public class ContastList {

    public String contact;
    public boolean showfirst = true;


    public String getFirstLetter() {
        return String.valueOf(contact.charAt(0)).toUpperCase();
    }
}
