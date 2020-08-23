package com.osamaelsh3rawy.chat.ui.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentForget extends BaseFragment {

    @BindView(R.id.forget_email)
    TextInputLayout forgetEmail;
    @BindView(R.id.forget_btn)
    Button forgetBtn;
    FirebaseAuth firebaseAuth;

    public FragmentForget() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        firebaseAuth=FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.forget_btn)
    public void onViewClicked() {
        String email=forgetEmail.getEditText().getText().toString().trim();
        if(email.equals("")){
            Toast.makeText(getContext(),"email fiald is empty",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(),"check your email",Toast.LENGTH_SHORT).show();
                        replace.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_login_container,new FragmentLogin());
                    }
                    else{
                        String error =task.getException().getMessage();
                        Toast.makeText(getContext(), error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}