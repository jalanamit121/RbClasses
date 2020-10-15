package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.NewModels.DailyUpdateArray;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.List;

public class SekHomeAdapter extends RecyclerView.Adapter<SekHomeAdapter.ViewHolder> {
  private Context context;
  private List<DailyUpdateArray> courseDatumList;

  public SekHomeAdapter(Context context, List<DailyUpdateArray> courseDatumList){
    this.context = context;
    this.courseDatumList = courseDatumList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sek_home_addapter,parent, false);
    return  new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    //setting data toAd apter List

    if (courseDatumList.get(position).getItemAttachmentType().equalsIgnoreCase("1"))
    {
      holder.branch_image.setVisibility(View.GONE);
      holder.branchname.setVisibility(View.VISIBLE);
    }else if (courseDatumList.get(position).getItemAttachmentType().equalsIgnoreCase("2")){
      holder.branch_image.setVisibility(View.VISIBLE);
      holder.branchname.setVisibility(View.GONE);
    } else if (courseDatumList.get(position).getItemAttachmentType().equalsIgnoreCase("3")){
      holder.branch_image.setVisibility(View.VISIBLE);
      holder.branchname.setVisibility(View.VISIBLE);
    }

    holder.branchname.setText(Html.fromHtml(courseDatumList.get(position).getItemDescription()));
    holder.txt_date.setText(Html.fromHtml(courseDatumList.get(position).getDisplayDate()));
    Picasso.get().load(courseDatumList.get(position).getItemAttachment()).placeholder(R.drawable.water_mark_update_image).into(holder.branch_image);
    holder.btn_share.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Intent shareIntent = new Intent(Intent.ACTION_SEND);
          shareIntent.setType("text/plain");
          shareIntent.putExtra(Intent.EXTRA_SUBJECT, "RB Classes");
          String shareMessage= String.valueOf((Html.fromHtml(courseDatumList.get(position).getItemDescription())));
          String screenshotUri = courseDatumList.get(position).getItemAttachment();
          shareMessage = screenshotUri+shareMessage  ;
          shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
          context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
        }
      }
    });

  }


  @Override
  public int getItemCount() {
    return courseDatumList==null ? 0 : courseDatumList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView branchname,txt_date,gec_enquiry_no;
    private ImageView branch_image,btn_share;
    private RelativeLayout branch_name1;
    RelativeLayout cardView;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      branchname = itemView.findViewById(R.id.gec_branchname);
      gec_enquiry_no = itemView.findViewById(R.id.gec_enquiry_no);
      cardView = itemView.findViewById(R.id.branch_sem);
      branch_image=itemView.findViewById(R.id.branch_image);
      branch_name1=itemView.findViewById(R.id.branch_name1);
      btn_share=itemView.findViewById(R.id.btn_share);
      txt_date=itemView.findViewById(R.id.txt_date);
      gec_enquiry_no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Uri u = Uri.parse("tel:" + "9795299311");
          Intent i = new Intent(Intent.ACTION_DIAL, u);

          try
          {
            context.startActivity(i);
          }
          catch (SecurityException s)
          {
            Toast.makeText(context, "Permisiion is necessary", Toast.LENGTH_SHORT).show();
          }
        }
      });

    }
  }
}

