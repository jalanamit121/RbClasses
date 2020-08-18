package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.DoubtSolutionActivity;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.AskDoubtQuestion;

import java.util.ArrayList;

public class AskDoubtAdapter extends RecyclerView.Adapter<AskDoubtAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AskDoubtQuestion> list;

    public AskDoubtAdapter(Context context, ArrayList<AskDoubtQuestion> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AskDoubtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.askedquestionadapter,parent, false);
        return  new AskDoubtAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AskDoubtAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.txt_ask_title.setText(list.get(position).getFile_name_to_show());
        holder.txt_ask_question.setText(list.get(position).getFile_description());
        holder.txt_user.setText(list.get(position).getFile_create_name());
        holder.txt_time.setText(list.get(position).getFile_duration_time());
        holder.txt_commments.setText(list.get(position).getFile_comments());
        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.FileName=list.get(position).getFile_name();
                LocalData.User=list.get(position).getFile_create_name();
                LocalData.Title=list.get(position).getFile_name_to_show();
                LocalData.Discription=list.get(position).getFile_description();
                LocalData.Duration=list.get(position).getFile_duration_time();
                LocalData.Commnts=list.get(position).getFile_comments();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, DoubtSolutionActivity.class);
                bundle.putSerializable("file_name",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_ask_title,txt_ask_question,txt_time,txt_user,txt_commments;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            txt_ask_question = itemView.findViewById(R.id.txt_ask_question);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_commments= itemView.findViewById(R.id.txt_commments);
            txt_user = itemView.findViewById(R.id.txt_user);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}


