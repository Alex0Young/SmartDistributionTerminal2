package com.hyphenate.chatuidemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class EMChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_fragment);

        EaseChatFragment chatFragment = new EaseChatFragment();

        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
    }
}
