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
import com.winbee.rbclasses.model.SolutionQuestion;

import java.util.ArrayList;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SolutionQuestion> list;

    public SolutionAdapter(Context context, ArrayList<SolutionQuestion> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SolutionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asksolutionadapter,parent, false);
        return  new SolutionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.txt_user.setText(list.get(position).getUser());
        holder.txt_time.setText(list.get(position).getDuration());
        holder.txt_ask_title.setText(list.get(position).getQuestion());





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

