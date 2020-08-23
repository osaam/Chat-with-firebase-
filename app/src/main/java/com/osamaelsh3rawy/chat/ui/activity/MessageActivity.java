package com.osamaelsh3rawy.chat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.adapter.MessageAdapter;
import com.osamaelsh3rawy.chat.data.model.MessageData;
import com.osamaelsh3rawy.chat.data.model.UsersData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends BaseActivity {

    public UsersData usersData;
    @BindView(R.id.message_image)
    CircleImageView messageImage;
    @BindView(R.id.message_username)
    TextView messageUsername;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @BindView(R.id.message_toolbar)
    Toolbar messageToolbar;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.message_text)
    EditText messageText;
    @BindView(R.id.message_takephoto)
    ImageButton messageTakephoto;
    @BindView(R.id.message_send)
    ImageButton messageSend;
    Intent intent;
    Context context;

    MessageAdapter messageAdapter;
    List<MessageData> messageData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        ButterKnife.bind(this);

        setSupportActionBar(messageToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intent = getIntent();
        String UserId = intent.getStringExtra("userId");

        rvMessage.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        rvMessage.setLayoutManager(layoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(UserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersData usersData = dataSnapshot.getValue(UsersData.class);
                messageUsername.setText(usersData.getUsername());
                if (usersData.getImageURL().equals("default")) {
                    messageImage.setImageResource(R.drawable.ic_profile);
                } else {
                    Glide.with(getApplicationContext()).load(usersData.getImageURL()).into(messageImage);
                }
                readMessage(firebaseUser.getUid(), UserId, usersData.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mes = messageText.getText().toString().trim();
                if (!mes.equals("")) {
                    sendmessage(String.valueOf(firebaseUser),UserId, mes);
                } else {
                    Toast.makeText(getApplicationContext(), "you cant send empty message", Toast.LENGTH_SHORT).show();
                }
                messageText.setText("");
            }
        });
    }


    private void sendmessage(String sender, String reciver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciver", reciver);
        hashMap.put("message", message);

        reference.child("chats").push().setValue(hashMap);
    }


    private void readMessage(String myId, String userId, String imageUrl) {
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageData message = snapshot.getValue(MessageData.class);

                    if (message.getReciver().equals(myId) && message.getSender().equals(userId) ||
                            message.getReciver().equals(userId) && message.getSender().equals(myId)) {
                        messageData.add(message);
                    }
                    messageAdapter = new MessageAdapter(context, messageData, imageUrl);
                    rvMessage.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
