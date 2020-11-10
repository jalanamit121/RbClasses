package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.rbclasses.InstructionsActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.TestRankActivity;
import com.winbee.rbclasses.TestSolutionActivity;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.model.SIACDetailsDataModel;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.CustomViewHolder> {
    private Context context;
    private List<SIACDetailsDataModel> siacDetailsDataModelList;

    public TestListAdapter(Context context, List<SIACDetailsDataModel> siacDetailsDataModelList) {
        this.context=context;
        this.siacDetailsDataModelList = siacDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_list,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        final SIACDetailsDataModel siacDetailsDataModel = siacDetailsDataModelList.get(position);
        viewHolder.online_testname.setText(Html.fromHtml(siacDetailsDataModel.getPaperName()));
        viewHolder.total_question.setText(siacDetailsDataModel.getTotal_Number_of_question()+" Questions");
        viewHolder.total_time.setText(siacDetailsDataModel.getTime()+" Mins");

        if (!siacDetailsDataModel.getIs_closed_Notification_On()){
            viewHolder.txt_closed_date.setVisibility(View.GONE);
            viewHolder.txt_closed_message.setVisibility(View.GONE);
        } else if (siacDetailsDataModel.getIs_closed_Notification_On()) {
            viewHolder.txt_closed_date.setVisibility(View.VISIBLE);
            viewHolder.txt_closed_message.setVisibility(View.VISIBLE);
        }


        if (siacDetailsDataModel.getIsAttempted()) {
            viewHolder.layout_pervious.setVisibility(View.VISIBLE);
            viewHolder.layout_start.setVisibility(View.VISIBLE);
            viewHolder.layout_new_start.setVisibility(View.GONE);
            viewHolder.layout_rank.setVisibility(View.VISIBLE);

        }else if (!siacDetailsDataModel.getIsAttempted()){
            viewHolder.layout_pervious.setVisibility(View.GONE);
            viewHolder.layout_start.setVisibility(View.GONE);
            viewHolder.layout_new_start.setVisibility(View.VISIBLE);
            viewHolder.layout_rank.setVisibility(View.GONE);

        }
            viewHolder.layout_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                    OnlineTestData.totalQuestion = siacDetailsDataModel.getTotal_Number_of_question();
                    OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                    OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                    OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                    OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                    OnlineTestData.time = siacDetailsDataModel.getTime();
                    OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                    OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                    OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                    OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                    OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                    OnlineTestData.description = siacDetailsDataModel.getDescription();
                    Intent intent = new Intent(context, InstructionsActivity.class);
                    context.startActivity(intent);
                }
            });

        viewHolder.layout_new_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                OnlineTestData.time = siacDetailsDataModel.getTime();
                OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                OnlineTestData.description = siacDetailsDataModel.getDescription();
                Intent intent = new Intent(context, InstructionsActivity.class);
                context.startActivity(intent);
            }
        });

        viewHolder.layout_pervious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineTestData.bucketIDre = siacDetailsDataModel.getBucketID();
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                OnlineTestData.time = siacDetailsDataModel.getTime();
                OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                OnlineTestData.description = siacDetailsDataModel.getDescription();
                Intent intent = new Intent(context, TestSolutionActivity.class);
                context.startActivity(intent);
            }
        });

        viewHolder.layout_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                Intent intent = new Intent(context, TestRankActivity.class);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return siacDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1;
        LinearLayout layout_start,layout_pervious,layout_rank;
        RelativeLayout layout_new_start;
        ImageView live_image,img_lock,img_Unlock;
        TextView online_testname,total_question,total_time,txt_closed_message,txt_closed_date;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1=itemView.findViewById(R.id.branch_live1);
            live_image=itemView.findViewById(R.id.live_image);
            online_testname=itemView.findViewById(R.id.online_testname);
            layout_start=itemView.findViewById(R.id.layout_start);
            layout_new_start=itemView.findViewById(R.id.layout_new_start);
            layout_pervious=itemView.findViewById(R.id.layout_pervious);
            layout_rank=itemView.findViewById(R.id.layout_rank);
            total_question=itemView.findViewById(R.id.total_question);
            total_time=itemView.findViewById(R.id.total_time);
            txt_closed_message=itemView.findViewById(R.id.txt_closed_message);
            txt_closed_date=itemView.findViewById(R.id.txt_closed_date);
            img_lock=itemView.findViewById(R.id.img_lock);
            img_Unlock=itemView.findViewById(R.id.img_Unlock);
        }
    }
}
