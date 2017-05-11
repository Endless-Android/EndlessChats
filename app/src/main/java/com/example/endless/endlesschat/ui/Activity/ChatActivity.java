package com.example.endless.endlesschat.ui.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endless.endlesschat.R;
import com.example.endless.endlesschat.adapter.MessageListAdapter;
import com.example.endless.endlesschat.presenter.ChatPresenter;
import com.example.endless.endlesschat.presenter.impl.ChatPresenterimpl;
import com.example.endless.endlesschat.utils.ThreadUtils;
import com.example.endless.endlesschat.view.ChatView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by endless .
 */
public class ChatActivity extends BaseActivity implements ChatView {
    @BindView(R.id.title_bar)
    TextView mTitleBar;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.recyclreview)
    RecyclerView mRecyclreview;
    @BindView(R.id.message)
    EditText mMessage;
    @BindView(R.id.send)
    Button mSend;
    private ChatPresenter mChatPresenter;
    private String mUser_name;
    private MessageListAdapter mMessageListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public int getLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        mUser_name = getIntent().getStringExtra("User_Name");
        String title = String.format(getString(R.string.chattitle), mUser_name);
        mTitleBar.setText(title);
        mBack.setVisibility(View.VISIBLE);
        mMessage.addTextChangedListener(mTextWatcher);
        mSend.setOnEditorActionListener(mOnEditorActionListener);
        mChatPresenter = new ChatPresenterimpl(this);
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
        initRecyclerView();
        mChatPresenter.loadMessage(mUser_name);
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclreview.setHasFixedSize(true);
        mRecyclreview.setLayoutManager(mLinearLayoutManager);
        mMessageListAdapter = new MessageListAdapter(this, mChatPresenter.getMessages());
        mRecyclreview.setAdapter(mMessageListAdapter);
        mRecyclreview.addOnScrollListener(mOnScrollListener);

    }

    @OnClick({R.id.back, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        String msg = mMessage.getText().toString().trim();
        mChatPresenter.sendMessage(mUser_name, msg);

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            mSend.setEnabled(s.length() > 0);
        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;

            }
            return false;
        }
    };

    private EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(final List<EMMessage> list) {
            //收到消息
            ThreadUtils.RunMainThread(new Runnable() {
                @Override
                public void run() {
                    //收到消息
                    mMessageListAdapter.addNewMessage(list.get(0));
                    mChatPresenter.markRead(mUser_name);
                    smoothScrollToBottom();
                }
            });

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {
            //收到已读回执

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
            //消息状态变动

        }
    };

    @Override
    public void onStartSendMessages() {
        updateList();
    }

    private void smoothScrollToBottom() {
        mRecyclreview.smoothScrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    @Override
    public void onSendMessageSuccess() {
        mMessage.getText().clear();
        hideProgress();
        Toast.makeText(this, "发送消息成功", Toast.LENGTH_LONG).show();
        updateList();
    }

    private void updateList() {
        mMessageListAdapter.notifyDataSetChanged();
        smoothScrollToBottom();
    }

    private void hideProgress() {

    }

    @Override
    public void onSendMessageError() {
        hideProgress();
        updateList();
        Toast.makeText(this, "发送消息失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadSuccess() {
        mMessageListAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void loadMoreMessageSuccess(int size) {
        mMessageListAdapter.notifyDataSetChanged();
        mRecyclreview.scrollToPosition(size);
        Toast.makeText(this,"加载数据成功",Toast.LENGTH_LONG).show();

    }

    @Override
    public void notMoreDate() {
        Toast.makeText(this,"没有数据可以加载了",Toast.LENGTH_LONG).show();
    }

    private void scrollToBottom() {
        mRecyclreview.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition == 0) {
                    mChatPresenter.loadMoreMessage(mUser_name);
                }

            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);
    }
}
