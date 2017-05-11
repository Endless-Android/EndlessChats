package com.example.endless.endlesschat.factory;

import android.support.v4.app.Fragment;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.ui.Fragment.ContastFragment;
import com.example.endless.endlesschat.ui.Fragment.ConversationFragment;
import com.example.endless.endlesschat.ui.Fragment.DynamicFragment;
/**
 * Created by endless .
 */

public class FragmentFactory {
    private static FragmentFactory mFragmentFactory;
    private  ContastFragment mContastFragment;
    private  ConversationFragment mConversationFragment;
    private  DynamicFragment mDynamicFragment;


    public static FragmentFactory getInstance() {
        if (mFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (mFragmentFactory == null) {
                    mFragmentFactory = new FragmentFactory();
                }
            }

        }
        return mFragmentFactory;
    }

    public  Fragment getFragment(int tabId) {
        switch (tabId) {
            case R.id.tab_contast:
                return getcontastfragment();
            case R.id.tab_conversation:
                return getconversationfragment();
            case R.id.tab_dynamic:
                return getdynamicfragment();
        }
        return null;
    }


    public  Fragment getcontastfragment() {
        if (mContastFragment == null) {
            mContastFragment = new ContastFragment();
        }
        return mContastFragment;
    }

    public  Fragment getconversationfragment() {
        if (mConversationFragment == null) {
            mConversationFragment = new ConversationFragment();
        }

        return mConversationFragment;
    }

    public  Fragment getdynamicfragment() {
        if (mDynamicFragment == null) {
            mDynamicFragment = new DynamicFragment();
        }
        return mDynamicFragment;
    }
}
