package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.CurrentAffairsModel;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.List;

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.ViewHolder> {
    private Context context;
    private List<CurrentAffairsModel> courseDatumList;

    public CurrentAdapter(Context context, List<CurrentAffairsModel> courseDatumList){
        this.context = context;
        this.courseDatumList = courseDatumList;
    }

    @NonNull
    @Override
    public CurrentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_adapter,parent, false);
        return  new CurrentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.branchname.setText(Html.fromHtml(courseDatumList.get(position).getItemDescription()));
        holder.txt_date.setText(Html.fromHtml(courseDatumList.get(position).getDisplayDate()));
        Picasso.get().load(courseDatumList.get(position).getItemAttachment()).placeholder(R.drawable.wateer_mark_image).into(holder.branch_image);

    }


    @Override
    public int getItemCount() {
        return courseDatumList==null ? 0 : courseDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView branchname,txt_date;
        private ImageView branch_image,btn_share;
        private RelativeLayout branch_name1;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branchname = itemView.findViewById(R.id.gec_branchname);
            cardView = itemView.findViewById(R.id.branch_sem);
            branch_image=itemView.findViewById(R.id.branch_image);
            branch_name1=itemView.findViewById(R.id.branch_name1);
            txt_date=itemView.findViewById(R.id.txt_date);
//            btn_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("text/plain");
//                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sekhawati Academy");
//                        String shareMessage= "\nSekhawati Academy Application Download link";
//                        shareMessage = shareMessage + Picasso.get().load(courseDatumList.get(position).getItemAttachment()).into(holder.branch_image);  ;
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                       context.startActivity(Intent.createChooser(shareIntent, "choose one"));
//                    } catch(Exception e) {
//                    }
//                }
//            });
        }
    }
}

