package com.osamaelsh3rawy.chat.ui.fragment;

import androidx.fragment.app.Fragment;

import com.osamaelsh3rawy.chat.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;

    public void intiFragment() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superonBackPressed();
    }

}
