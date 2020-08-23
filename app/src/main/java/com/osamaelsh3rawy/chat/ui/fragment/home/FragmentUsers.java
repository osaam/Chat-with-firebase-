package com.osamaelsh3rawy.chat.ui.fragment.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.adapter.UsersAdapter;
import com.osamaelsh3rawy.chat.data.model.UsersData;
import com.osamaelsh3rawy.chat.ui.activity.BaseActivity;
import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUsers extends BaseFragment {

    UsersAdapter usersAdapter;
    @BindView(R.id.users_search)
    EditText usersSearch;
    @BindView(R.id.users_btn_search)
    ImageView usersBtnSearch;
    private List<UsersData> usersList = new ArrayList<>();
    @BindView(R.id.rv_users)
    RecyclerView rvUsers;

    public FragmentUsers() {
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
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        //
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (usersSearch.getText().toString().equals("")) {
                    usersList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UsersData user = snapshot.getValue(UsersData.class);

                        assert user != null;
                        assert firebaseUser != null;
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            usersList.add(user);
                        }
                    }
                    usersAdapter = new UsersAdapter((BaseActivity) getActivity(), getContext(), usersList);
                    rvUsers.setAdapter(usersAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//

        usersSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void SearchUsers(String s) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("username")
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UsersData usersData = snapshot.getValue(UsersData.class);
                    if (!usersData.getId().equals(firebaseUser.getUid())) {
                        usersList.add(usersData);
                    }
                }
                usersAdapter = new UsersAdapter((BaseActivity) getActivity(), getContext(), usersList);
                rvUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onBack() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
