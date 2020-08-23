package com.osamaelsh3rawy.chat.ui.fragment.login;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.data.model.UsersData;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentRegister extends BaseFragment {


    @BindView(R.id.signup_logo)
    ImageView signupLogo;
    @BindView(R.id.signup_welcom)
    TextView signupWelcom;
    @BindView(R.id.signup_journy)
    TextView signupJourny;
    @BindView(R.id.signup_fullname)
    TextInputLayout signupFullname;
    @BindView(R.id.signup_username)
    TextInputLayout signupUsername;
    @BindView(R.id.signup_email)
    TextInputLayout signupEmail;
    @BindView(R.id.signup_phone)
    TextInputLayout signupPhone;
    @BindView(R.id.signup_password)
    TextInputLayout signupPassword;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.signup_signup)
    Button signupSignup;
    @BindView(R.id.signup_login)
    TextView signupLogin;

    protected FirebaseAuth auth;
    protected DatabaseReference reference;
    @BindView(R.id.signup_image)
    CircleImageView signupImage;
    @BindView(R.id.signup_password_conf)
    TextInputLayout signupPasswordConf;
    @BindView(R.id.signup_progressBar)
    ProgressBar progressBar;
    private String path;
    private boolean usernamformatt = false;
    private boolean usernameexists;

    public FragmentRegister() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        intiFragment();


        return view;
    }

    private boolean usernamformat(String str) {

        String Englishwordnum = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789_";
        char[] ch = new char[str.length()];

        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        // Printing content of array
        for (char c : ch) {
            if (Englishwordnum.contains(c + "")) {
                usernamformatt = true;
            } else {
                usernamformatt = false;
                break;
            }
        }
        return usernamformatt;
    }

    private void register(String name, String username, String email, String phone, String password) {
        signupSignup.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            UsersData usersData = new UsersData(userId, name, username, email, phone, password, "default");

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(usersData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                                        replace.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_login_container, new FragmentLogin());
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                        signupSignup.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }}
                });
    }

    private boolean isUsernameexists(final String username) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UsersData user = snapshot.getValue(UsersData.class);
                    assert user != null;
                    if (user.getUsername().trim().equals(username.trim())) {
                        usernameexists = true;
                        break;
                    } else {
                        usernameexists = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return usernameexists;
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick({R.id.signup_signup, R.id.signup_login, R.id.signup_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_image:
                replace.initImage(signupImage, getActivity());
                break;
            case R.id.signup_signup:
                String name = signupFullname.getEditText().getText().toString();
                String username = signupUsername.getEditText().getText().toString();
                String email = signupEmail.getEditText().getText().toString();
                String phone = signupPhone.getEditText().getText().toString();
                String password = signupPassword.getEditText().getText().toString();
                String passord_conf = signupPasswordConf.getEditText().getText().toString();

                if (name.isEmpty()) {
                    signupFullname.setError("Field cannot be empty");
                }
                else if (username.isEmpty()) {
                    signupUsername.setError("Field cannot by empty");
                } else if (email.isEmpty()) {
                    signupEmail.setError("Field cannot by empty");
                } else if (phone.isEmpty()) {
                    signupPhone.setError("Field cannot by empty");
                } else if (password.isEmpty()) {
                    signupPassword.setError("Field cannot by empty");
                } else if (!password.equals(passord_conf)) {
                    signupPasswordConf.setError("password dosnot by match");
                } else {
                    register(name, username, email, phone, password);
                }

                break;
            case R.id.signup_login:
                replace.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_login_container, new FragmentLogin());
                break;

        }
    }
}
