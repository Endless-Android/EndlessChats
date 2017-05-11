package com.example.endless.endlesschat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.model.ContastList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by endless .
 */

public class ContastItemView extends RelativeLayout {


    @BindView(R.id.first_title)
    TextView mFirstTitle;
    @BindView(R.id.contact)
    TextView mContact;

    public ContastItemView(Context context) {
        this(context, null);

    }

    public ContastItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindview(ContastList contastList) {
        mContact.setText(contastList.contact);
        if(contastList.showfirst){
            mFirstTitle.setVisibility(VISIBLE);
            mFirstTitle.setText(contastList.getFirstLetter());
        }else {
            mFirstTitle.setVisibility(GONE);
        }
    }
}
