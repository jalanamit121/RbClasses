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

import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.McqSolutionModel;

import java.util.ArrayList;

public class McqSolutionAdapter extends RecyclerView.Adapter<McqSolutionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<McqSolutionModel> list;

    public McqSolutionAdapter(Context context, ArrayList<McqSolutionModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_solution_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.text_user.setText(list.get(position).getUsername());
        holder.text_user_answer.setText(list.get(position).getUservalue());
        holder.text_user_message.setText(list.get(position).getMessage());
        holder.text_user_date.setText(list.get(position).getTime());





    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_user_message,text_user_answer,text_user,text_user_date;
        private RelativeLayout branch_live;
        private ImageView img_question,img_solution;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_user_message = itemView.findViewById(R.id.text_user_message);
            text_user_answer = itemView.findViewById(R.id.text_user_answer);
            text_user = itemView.findViewById(R.id.text_user);
            text_user_date = itemView.findViewById(R.id.text_user_date);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}
