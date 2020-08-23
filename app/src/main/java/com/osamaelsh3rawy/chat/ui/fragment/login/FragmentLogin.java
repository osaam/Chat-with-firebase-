package com.osamaelsh3rawy.chat.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.activity.HomeActivity;
import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentLogin extends BaseFragment {


    @BindView(R.id.login_logo)
    ImageView loginLogo;
    @BindView(R.id.login_helloThere)
    TextView loginHelloThere;
    @BindView(R.id.login_username_layout)
    TextInputLayout loginUsernameLayout;
    @BindView(R.id.login_password_layout)
    TextInputLayout loginPasswordLayout;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.login_forget)
    TextView loginForget;
    @BindView(R.id.login_go)
    Button loginGo;
    @BindView(R.id.login_signup)
    TextView loginSignup;
    FirebaseAuth auth;
    @BindView(R.id.login_progressBar)
    ProgressBar loginProgressBar;

    private FirebaseAuth Auth;

    public FragmentLogin() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        intiFragment();


        return view;
    }

    private Boolean validateUsername() {
        String name = loginUsernameLayout.getEditText().getText().toString();
        if (name.isEmpty()) {
            loginUsernameLayout.setError("Field cannot by empty");
        }
        loginUsernameLayout.setError(null);
        loginUsernameLayout.setErrorEnabled(false);
        return true;
    }

    private Boolean validatepassword() {
        String password = loginPasswordLayout.getEditText().getText().toString();
        if (password.isEmpty()) {
            loginPasswordLayout.setError("Field cannot by empty");
        }
        loginPasswordLayout.setError(null);
        loginPasswordLayout.setErrorEnabled(false);
        return true;
    }

    public void login() {
        if (!validateUsername() | !validatepassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        String username = loginUsernameLayout.getEditText().getText().toString().trim();
        String password = loginPasswordLayout.getEditText().getText().toString().trim();
        auth = FirebaseAuth.getInstance();
        loginProgressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Welcome Back :)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    loginProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

                }
            }
        );

    }


    @Override
    public void onBack() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login_forget, R.id.login_go, R.id.login_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forget:
                replace.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_login_container, new FragmentForget());
                break;
            case R.id.login_go:
                login();
                break;
            case R.id.login_signup:
                replace.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_login_container, new FragmentRegister());
                break;
        }
    }
}
