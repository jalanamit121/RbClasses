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
        holder.text_question.setText(list.get(position).getFile_name_to_show());
        holder.text_like.setText(list.get(position).getFile_likes());
        holder.text_comments.setText(list.get(position).getFile_comments()+" Comments");
        holder.text_user.setText(list.get(position).getFile_create_name());
//        String test = list.get(position).getFile_create_name();
//        //char first = test.charAt(0);
//        holder.question_image.setText(list.get(position).getFile_create_name().charAt(0));

        holder.branch_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.FileName=list.get(position).getFile_name();
                LocalData.FileNameToShow=list.get(position).getFile_name_to_show();
                LocalData.FileCreateName=list.get(position).getFile_create_name();
                LocalData.FileDate=list.get(position).getFile_create_date();
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
        private TextView text_question,text_like,text_comments,text_user,question_image;
        private RelativeLayout branch_live;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_question = itemView.findViewById(R.id.text_question);
            text_like = itemView.findViewById(R.id.text_like);
            text_comments = itemView.findViewById(R.id.text_comments);
            text_user = itemView.findViewById(R.id.text_user);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}


