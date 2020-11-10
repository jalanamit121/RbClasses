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


import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.OnlineTestActivity;
import com.winbee.rbclasses.R;

import com.winbee.rbclasses.TestSubscriptionActivity;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.model.SectionDetailsDataModel;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.CustomViewHolder> {
    private Context context;
    private List<SectionDetailsDataModel> sectionDetailsDataModelList;

    public SubjectAdapter(Context context, List<SectionDetailsDataModel> sectionDetailsDataModelList) {
        this.context=context;
        this.sectionDetailsDataModelList = sectionDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_online_subject,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, final int position) {
        //final SectionDetailsDataModel sectionDetailsDataModel = sectionDetailsDataModelList.get(position);

        if (sectionDetailsDataModelList.get(position).getIs_Section_Live().equals(1)){
            viewHolder.image_gif.setVisibility(View.VISIBLE);
        }else if (sectionDetailsDataModelList.get(position).getIs_Section_Live().equals(0)){
            viewHolder.image_gif.setVisibility(View.GONE);
        }

        if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("1")){
            if (sectionDetailsDataModelList.get(position).getIsPaid().equals(1)){
                viewHolder.img_lock.setVisibility(View.GONE);
                viewHolder.img_Unlock.setVisibility(View.GONE);
            }else  if (sectionDetailsDataModelList.get(position).getIsPaid().equals(0)){
                viewHolder.img_lock.setVisibility(View.VISIBLE);
                viewHolder.img_Unlock.setVisibility(View.GONE);
            }

        }else if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("0")){
            viewHolder.img_lock.setVisibility(View.GONE);
            viewHolder.img_Unlock.setVisibility(View.VISIBLE);
        }
        viewHolder.online_subjectname.setText(Html.fromHtml(sectionDetailsDataModelList.get(position).getBucketName()));
        viewHolder.online_totaltest.setText("Total test-" + " " +sectionDetailsDataModelList.get(position).getTotalTest());



        if (sectionDetailsDataModelList.get(position).getIsCommingSoon().equalsIgnoreCase("No")){
            if (sectionDetailsDataModelList.get(position).getIsPaid().equals(0)) {
                viewHolder.branch_live1.setVisibility(View.VISIBLE);
                if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("1") && sectionDetailsDataModelList.get(position).getIsPaid().equals(0)) {
                    //premium hai but not purchased
                    viewHolder.layout_buy.setVisibility(View.VISIBLE);
                    viewHolder.layout_free.setVisibility(View.GONE);
                    viewHolder.layout_purchased.setVisibility(View.GONE);
                    viewHolder.layout_coming_soon.setVisibility(View.GONE);
                    viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.TestBuckedId = sectionDetailsDataModelList.get(position).getBucketID();
                            LocalData.TestName = sectionDetailsDataModelList.get(position).getBucketName();
                            LocalData.TestDiscription = sectionDetailsDataModelList.get(position).getDecription();
                            LocalData.TotalTest = sectionDetailsDataModelList.get(position).getTotalTest();
                            Intent intent = new Intent(context, TestSubscriptionActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("1")) {
                    if (sectionDetailsDataModelList.get(position).getIsPaid().equals(1)) {
                        //premium hai aur purchase also
                        viewHolder.layout_buy.setVisibility(View.GONE);
                        viewHolder.layout_free.setVisibility(View.GONE);
                        viewHolder.layout_purchased.setVisibility(View.VISIBLE);
                        viewHolder.layout_coming_soon.setVisibility(View.GONE);
                        viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OnlineTestData.CoachingID = sectionDetailsDataModelList.get(position).getCoachingID();
                                OnlineTestData.bucketID = sectionDetailsDataModelList.get(position).getBucketID();
                                OnlineTestData.bucketName = sectionDetailsDataModelList.get(position).getBucketName();
                                OnlineTestData.bucketInfo = sectionDetailsDataModelList.get(position).getBucketInfo();
                                OnlineTestData.logData = sectionDetailsDataModelList.get(position).getLogData();
                                OnlineTestData.status = sectionDetailsDataModelList.get(position).getStatus();
                                OnlineTestData.totalTest = sectionDetailsDataModelList.get(position).getTotalTest();
                                Intent intent = new Intent(context, OnlineTestActivity.class);
                                context.startActivity(intent);

                            }
                        });
                    } else if (sectionDetailsDataModelList.get(position).getIsPaid().equals(0)) {
                        viewHolder.layout_buy.setVisibility(View.VISIBLE);
                        viewHolder.layout_free.setVisibility(View.GONE);
                        viewHolder.layout_purchased.setVisibility(View.GONE);
                        viewHolder.layout_coming_soon.setVisibility(View.GONE);
                        viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.TestBuckedId = sectionDetailsDataModelList.get(position).getBucketID();
                                LocalData.TotalTest = sectionDetailsDataModelList.get(position).getTotalTest();
                                LocalData.TestName = sectionDetailsDataModelList.get(position).getBucketName();
                                Intent intent = new Intent(context, TestSubscriptionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("0") && sectionDetailsDataModelList.get(position).getIsPaid().equals(0)) {
                    //free test
                    viewHolder.layout_buy.setVisibility(View.GONE);
                    viewHolder.layout_free.setVisibility(View.VISIBLE);
                    viewHolder.layout_buy.setVisibility(View.GONE);
                    viewHolder.layout_free.setVisibility(View.VISIBLE);
                    viewHolder.layout_buy.setVisibility(View.GONE);
                    viewHolder.layout_free.setVisibility(View.VISIBLE);
                    viewHolder.layout_purchased.setVisibility(View.GONE);
                    viewHolder.layout_coming_soon.setVisibility(View.GONE);
                    viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OnlineTestData.CoachingID = sectionDetailsDataModelList.get(position).getCoachingID();
                            OnlineTestData.bucketID = sectionDetailsDataModelList.get(position).getBucketID();
                            OnlineTestData.bucketName = sectionDetailsDataModelList.get(position).getBucketName();
                            OnlineTestData.bucketInfo = sectionDetailsDataModelList.get(position).getBucketInfo();
                            OnlineTestData.logData = sectionDetailsDataModelList.get(position).getLogData();
                            OnlineTestData.status = sectionDetailsDataModelList.get(position).getStatus();
                            OnlineTestData.totalTest = sectionDetailsDataModelList.get(position).getTotalTest();
                            Intent intent = new Intent(context, OnlineTestActivity.class);
                            context.startActivity(intent);

                        }
                    });
                } else if (sectionDetailsDataModelList.get(position).getIsPremium().equalsIgnoreCase("0") && sectionDetailsDataModelList.get(position).getIsPaid().equals(1)) {
                    //free test
                    viewHolder.layout_buy.setVisibility(View.GONE);
                    viewHolder.layout_free.setVisibility(View.VISIBLE);
                    viewHolder.layout_buy.setVisibility(View.GONE);
                    viewHolder.layout_free.setVisibility(View.VISIBLE);
                    viewHolder.layout_purchased.setVisibility(View.GONE);
                    viewHolder.layout_coming_soon.setVisibility(View.GONE);
                    viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OnlineTestData.CoachingID = sectionDetailsDataModelList.get(position).getCoachingID();
                            OnlineTestData.bucketID = sectionDetailsDataModelList.get(position).getBucketID();
                            OnlineTestData.bucketName = sectionDetailsDataModelList.get(position).getBucketName();
                            OnlineTestData.bucketInfo = sectionDetailsDataModelList.get(position).getBucketInfo();
                            OnlineTestData.logData = sectionDetailsDataModelList.get(position).getLogData();
                            OnlineTestData.status = sectionDetailsDataModelList.get(position).getStatus();
                            OnlineTestData.totalTest = sectionDetailsDataModelList.get(position).getTotalTest();
                            Intent intent = new Intent(context, OnlineTestActivity.class);
                            context.startActivity(intent);

                        }
                    });
                }
            }else{
                viewHolder.branch_live1.setVisibility(View.GONE);
            }
        }else {
            viewHolder.layout_buy.setVisibility(View.GONE);
            viewHolder.layout_free.setVisibility(View.GONE);
            viewHolder.layout_purchased.setVisibility(View.GONE);
            viewHolder.layout_coming_soon.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return sectionDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1,layout_free,layout_buy,layout_purchased,layout_coming_soon;
        ImageView live_image,img_lock,img_Unlock;
        GifImageView image_gif;
        TextView online_subjectname,online_totaltest;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1=itemView.findViewById(R.id.branch_live1);
            layout_free=itemView.findViewById(R.id.layout_free);
            layout_buy=itemView.findViewById(R.id.layout_buy);
            layout_purchased=itemView.findViewById(R.id.layout_purchased);
            layout_coming_soon=itemView.findViewById(R.id.layout_coming_soon);
            image_gif=itemView.findViewById(R.id.image_gif);
            live_image=itemView.findViewById(R.id.live_image);
            img_lock=itemView.findViewById(R.id.img_lock);
            img_Unlock=itemView.findViewById(R.id.img_Unlock);
            online_subjectname=itemView.findViewById(R.id.online_subjectname);
            online_totaltest=itemView.findViewById(R.id.online_totaltest);
        }
    }
}
