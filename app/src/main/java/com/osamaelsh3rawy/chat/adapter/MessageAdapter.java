package com.osamaelsh3rawy.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.data.model.MessageData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int Mes_Type_Left = 0;
    public static final int Mes_Type_right = 1;

    private Context context;
    List<MessageData> messageData = new ArrayList<>();
    private String Imageurl;
    FirebaseUser firebaseUser;


    public MessageAdapter(Context context, List<MessageData> messageData, String ImageUrl) {
        this.context = context;
        this.messageData = messageData;
        this.Imageurl = ImageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Mes_Type_right){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item_text_right, parent, false));
    } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item_text_left, parent, false));
        }

}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MessageData Data= messageData.get(position);
        holder.messageItemText.setText(Data.getMessage());
        if (Imageurl.equals("default")) {
            holder.messageItemImage.setImageResource(R.drawable.ic_profile);
        } else {
            Glide.with(context).load(Imageurl).into(holder.messageItemImage);
        }
    }

    @Override
    public int getItemCount() {
        return messageData.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {
   public TextView messageItemText;
    public CircleImageView messageItemImage;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
         messageItemText= itemView.findViewById(R.id.message_item_text);
         messageItemImage= itemView.findViewById(R.id.message_item_image);
    }
}

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messageData.get(position).getSender().equals(firebaseUser.getUid())) {
            return Mes_Type_right;
        } else {
            return Mes_Type_Left;
        }

    }

}

