package com.osamaelsh3rawy.chat.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.adapter.UsersAdapter;
import com.osamaelsh3rawy.chat.data.model.MessageData;
import com.osamaelsh3rawy.chat.data.model.UsersData;
import com.osamaelsh3rawy.chat.notification.Token;
import com.osamaelsh3rawy.chat.ui.activity.BaseActivity;
import com.osamaelsh3rawy.chat.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChats extends BaseFragment {

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    LinearLayoutManager layoutManager;
    private List<UsersData> usersList = new ArrayList<>();
    private List<String> chatList = new ArrayList<>();
    UsersAdapter usersAdapter;

    public FragmentChats() {
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
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        inti();

        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);

    }

    private void inti() {
        layoutManager = new LinearLayoutManager(getContext());
        rvChat.setLayoutManager(layoutManager);

        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageData messageData = snapshot.getValue(MessageData.class);

                    if (messageData.getSender().equals(firebaseUser.getUid())) {
                        chatList.add(messageData.getReciver());
                    }
                    if (messageData.getReciver().equals(firebaseUser.getUid())) {
                        chatList.add(messageData.getSender());
                    }
                    readChats();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void readChats() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UsersData user = snapshot.getValue(UsersData.class);

                    for (String id : chatList) {
                        if (user.getId().equals(id)) {
                            if (usersList.size() != 0) {
                                for (UsersData user1 : usersList) {
                                    if (user.getId().equals(user1.getId())) {
                                        usersList.add(user);
                                    }
                                }

                            } else {
                                usersList.add(user);
                            }
                        }
                    }
                }
                usersAdapter = new UsersAdapter((BaseActivity) getActivity(), getContext(), usersList);
                rvChat.setAdapter(usersAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}
