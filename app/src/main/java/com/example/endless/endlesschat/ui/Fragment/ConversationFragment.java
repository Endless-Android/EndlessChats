package com.example.endless.endlesschat.ui.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.ConversationListAdapter;
import com.example.endless.endlesschat.adapter.EMMessageListenerAdapter;
import com.example.endless.endlesschat.presenter.ConversationPresenter;
import com.example.endless.endlesschat.presenter.impl.ConversationPresenterimpl;
import com.example.endless.endlesschat.view.ConversationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
/**
 * Created by endless .
 */

public class ConversationFragment extends BaseFragment implements ConversationView{
    @BindView(R.id.title_bar)
    TextView mTitleBar;
    @BindView(R.id.recyclreview)
    RecyclerView mRecyclreview;
    private ConversationPresenter mConversationPresenter;
    private ConversationListAdapter mConversationListAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.conversation_fragment;

    }

    @Override
    public void init() {
        super.init();
        mConversationPresenter = new ConversationPresenterimpl(this);
        initRecyclreView();
        mTitleBar.setText(getString(R.string.conversation));
        mConversationPresenter.loadConversation();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
    }

    private void initRecyclreView() {
        mRecyclreview.setHasFixedSize(true);
        mRecyclreview.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationListAdapter = new ConversationListAdapter(getContext(),mConversationPresenter.getMessage());
        mRecyclreview.setAdapter(mConversationListAdapter);

    }

    @Override
    public void loadConversationSuccess() {

        Toast.makeText(getContext(),getString(R.string.loadconversation_success),Toast.LENGTH_LONG).show();
        mConversationListAdapter.notifyDataSetChanged();
    }
    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter(){
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            mConversationPresenter.loadConversation();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mConversationPresenter.loadConversation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }
}
