package com.osamaelsh3rawy.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.data.model.UsersData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    List<UsersData>chats=new ArrayList<>();

    public ChatAdapter(Context context, List<UsersData> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemchat, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_chat_image)
        CircleImageView itemChatImage;
        @BindView(R.id.item_chat_name)
        TextView itemChatName;
        @BindView(R.id.item_chat_text)
        TextView itemChatText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
