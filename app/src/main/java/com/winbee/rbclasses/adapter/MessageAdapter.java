package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.LiveChatModel;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {
    private static final String TAG = "MessageAdapter";

    private Context context;
    private ArrayList<LiveChatModel> messagesArrayList;
    FirebaseFirestore db= FirebaseFirestore.getInstance();


    public MessageAdapter(Context context, ArrayList<LiveChatModel> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;

    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageAdapterViewHolder(view);
    }

//    public void updateData(ArrayList<LiveChatModel> messagesList) {
//        if (messagesArrayList == null) {
//            return;
//        }
//        this.messagesArrayList.clear();
//        messagesArrayList.addAll(messagesList);
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(final MessageAdapterViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + messagesArrayList);

        db.collection("Users")
                .document(messagesArrayList.get(position).getUserId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = (String) documentSnapshot.get("Name");
//                Animation in = new AlphaAnimation(0.0f, 1.0f);
//                in.setDuration(500);
//                holder.txtView.setAnimation(in);
//                holder.name.setAnimation(in);
                holder.txtView.setText(messagesArrayList.get(position).getMessage());
                holder.name.setText(name);
            }
        });




    }

    //   }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtView ,name;
        ImageButton btn_delete;
        LinearLayout ll;

        public MessageAdapterViewHolder( View itemView) {
            super(itemView);

            txtView =itemView.findViewById(R.id.chatText);
            name =itemView.findViewById(R.id.nameChat);
            //  btn_delete=itemView.findViewById(R.id.btn_delete);

//            btn_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    db.collection("LiveONE")
//                            .document(messagesArrayList.get(getAdapterPosition()).getDocId())
//                            .delete();
//                }
//            });
        }
    }
}

