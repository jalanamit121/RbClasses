package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        return  new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageAdapterViewHolder holder, final int position)
    {
        Log.d(TAG, "onBindViewHolder: "+messagesArrayList);
//            Message message= messages.get(position);
//        if (message.getName().equals(AllModel.name)){
//            holder.txtView.setText("you :"+ message.getMessage());
//            holder.txtView.setGravity(Gravity.START);
//            holder.ll.setBackgroundColor(Color.parseColor("#BD5252"));
//        }else{
//            holder.txtView.setText(message.getName()+":"+ message.getMessage());
//            holder.btn_delete.setVisibility(View.GONE);
//        }
//        if (messagesArrayList.get(position).getUserId().equals(ExoPlayer.userIdAuth)){
//            String message = messagesArrayList.get(position).getMessage();
//            Animation in = new AlphaAnimation(0.0f, 1.0f);
//            in.setDuration(500);
//            holder.txtView.setAnimation(in);
//            holder.txtView.setText("you :"+ message);
//            holder.txtView.setGravity(Gravity.START);
//            holder.btn_delete.setVisibility(View.GONE);
//            holder.ll.setBackgroundColor(Color.parseColor("#BD5252"));
//        }
//        else {
            db.collection("Users")
                    .document(messagesArrayList.get(position).getUserId())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String name = (String) documentSnapshot.get("Name");
                    Animation in = new AlphaAnimation(0.0f, 1.0f);
                    in.setDuration(500);
                    holder.txtView.setAnimation(in);
                    holder.name.setAnimation(in);
                    holder.txtView.setText(messagesArrayList.get(position).getMessage());
                    holder.name.setText(name);
                }
            });




        }

        //   holder.txtView.setText(messagesArrayList.get(position).getMessage());

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
