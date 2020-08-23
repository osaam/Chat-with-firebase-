package com.osamaelsh3rawy.chat.ui.activity;

import android.os.Bundle;

import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.fragment.login.FragmentLogin;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        replace.replaceFragment(getSupportFragmentManager(), R.id.activity_login_container, new FragmentLogin());
    }



}
