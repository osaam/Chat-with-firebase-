package com.osamaelsh3rawy.chat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

public class BaseActivity extends AppCompatActivity {
    public BaseFragment baseFragment;

    public void superonBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
