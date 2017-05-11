package com.example.endless.endlesschat.utils;

/**
 * Created by endless .
 */
public class StringUtils {
    private static final String REGEX_USER_NAME = "^[a-zA-Z]\\w{2,19}$";
    private static final String REGEX_PASSWORD = "^[0-9]{3,20}$";


    public static boolean isUsername(String username) {
        return username.matches(REGEX_USER_NAME);
    }

    public static boolean isPassword(String password) {
        return password.matches(REGEX_PASSWORD);
    }

}
