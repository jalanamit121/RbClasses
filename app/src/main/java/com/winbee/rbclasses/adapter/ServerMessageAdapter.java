package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.NewModels.LiveMessageArray;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.Message;
import java.util.ArrayList;
import java.util.List;

public class ServerMessageAdapter extends RecyclerView.Adapter<ServerMessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiveMessageArray> list;

    public ServerMessageAdapter(Context context, ArrayList<LiveMessageArray> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ServerMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent, false);
        return  new ServerMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerMessageAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.txt_user.setText(list.get(position).getUserName());
        holder.txt_time.setText(list.get(position).getDATE());
        holder.txt_ask_title.setText(list.get(position).getMessage());





    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_user,txt_time,txt_ask_title;
        private RelativeLayout branch_live;
        private ImageView img_question,img_solution;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_user = itemView.findViewById(R.id.txt_user);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}


