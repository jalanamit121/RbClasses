package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.PdfWebActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.CourseContentPdfModel;

import java.util.ArrayList;

public class AllPurchasedNotesAdapter extends RecyclerView.Adapter<AllPurchasedNotesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseContentPdfModel> list;

    public AllPurchasedNotesAdapter(Context context, ArrayList<CourseContentPdfModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllPurchasedNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_material_purchased,parent, false);
        return  new AllPurchasedNotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPurchasedNotesAdapter.ViewHolder holder, final int position) {




            holder.gec_branchname.setText(list.get(position).getTopic());
                holder.img_lock.setVisibility(View.GONE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.NotesData = list.get(position).getURL();
                        Intent intent = new Intent(context, PdfWebActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gec_branchname;
        ImageView img_lock;
        private CardView card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gec_branchname = itemView.findViewById(R.id.gec_branchname);
            img_lock = itemView.findViewById(R.id.img_lock);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}


