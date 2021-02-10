package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.PdfWebActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.CourseContentModel;
import com.winbee.rbclasses.model.CourseContentPdfModel;
import com.winbee.rbclasses.model.NotesModel;

import java.util.ArrayList;

public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseContentPdfModel> list;

    public AllNotesAdapter(Context context, ArrayList<CourseContentPdfModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_material_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        if (list.get(position).getClassType().equals(2)){
            holder.gec_branchname.setText(Html.fromHtml(list.get(position).getTopic()));
            holder.date.setText(Html.fromHtml(list.get(position).getPublished()));
            if (list.get(position).getAccessType().equals(1)) {
                holder.img_lock.setVisibility(View.GONE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.NotesData = list.get(position).getURL();
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(context, PdfWebActivity.class);
                        bundle.putSerializable("Notes_data", list.get(position));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            } else if (list.get(position).getAccessType().equals(2)) {
                holder.img_lock.setVisibility(View.VISIBLE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("RB Classes");
                        builder.setMessage("To View Please Purchased Course");
                        builder.setCancelable(false);

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();

                            }
                        });
                        builder.show();
                    }
                });
            }
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gec_branchname,date;
        ImageView img_lock;
        private RelativeLayout branch_sem;
        CardView card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gec_branchname = itemView.findViewById(R.id.gec_branchname);
            date = itemView.findViewById(R.id.date);
            img_lock = itemView.findViewById(R.id.img_lock);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}

