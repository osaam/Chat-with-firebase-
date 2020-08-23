package com.osamaelsh3rawy.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.osamaelsh3rawy.chat.data.model.MessageData;
import com.osamaelsh3rawy.chat.data.model.UsersData;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.activity.BaseActivity;
import com.osamaelsh3rawy.chat.ui.activity.MessageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private BaseActivity activity;
    private Context context;
    List<UsersData> users = new ArrayList<>();
    private boolean isChat;
    String theLastMsg;

    public UsersAdapter(BaseActivity activity, Context context, List<UsersData> usersDataList) {
        this.activity = activity;
        this.context = context;
        this.users = usersDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemuser, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersData usersData = users.get(position);

        holder.itemChatName.setText(usersData.getName());
        if (usersData.getImageURL().equals("default")) {
            holder.itemUsersImage.setImageResource(R.drawable.ic_profile);
        } else {
            Glide.with(context).load(usersData.getImageURL()).into(holder.itemUsersImage);
        }
        lastMessage(usersData.getId(),holder.itemUsersLastMsg);

        holder.constraintItemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra( "userId", usersData.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_users_image)
        CircleImageView itemUsersImage;
        @BindView(R.id.item_chat_name)
        TextView itemChatName;
        @BindView(R.id.item_chat_text)
        TextView itemChatText;
        @BindView(R.id.constraint_item_user)
        ConstraintLayout constraintItemUser;
        @BindView(R.id.item_users_last_msg)
        TextView itemUsersLastMsg;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }

    }

    private void lastMessage(String userId, TextView last_msg) {
        theLastMsg = "default";
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    MessageData messageData=snapshot.getValue(MessageData.class);
                if(messageData.getReciver().equals(firebaseUser.getUid()) &&messageData.getSender().equals(userId) ||
                        messageData.getReciver().equals(userId) &&messageData.getSender().equals(firebaseUser.getUid())){
                    theLastMsg=messageData.getMessage();
                }

                }
                switch (theLastMsg){
                    case"default" :
                        last_msg.setText("no message");
                        break;
                    default:
                        last_msg.setText(theLastMsg);
                       break;

                }
                theLastMsg="default";
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
