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
    public McqSolutionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asksolutionadapter,parent, false);
        return  new McqSolutionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull McqSolutionAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List

            holder.text_solution.setText(list.get(position).getUservalue());
            holder.text_date_solution.setText(list.get(position).getTime());
            holder.text_user_solution.setText(list.get(position).getUsername());





    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_question,text_user,text_date,text_solution,text_user_solution,text_date_solution;
        private RelativeLayout branch_live;
        private ImageView img_question,img_solution;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_question = itemView.findViewById(R.id.text_question);
            text_user = itemView.findViewById(R.id.text_user);
            text_date = itemView.findViewById(R.id.text_date);
            text_solution = itemView.findViewById(R.id.text_solution);
            text_user_solution = itemView.findViewById(R.id.text_user_solution);
            text_date_solution = itemView.findViewById(R.id.text_date_solution);
            branch_live = itemView.findViewById(R.id.branch_live);
        }
    }
}
