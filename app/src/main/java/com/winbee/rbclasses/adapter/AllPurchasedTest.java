package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.OnlineTestActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.model.SectionDetailsDataModel;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AllPurchasedTest extends RecyclerView.Adapter<AllPurchasedTest.CustomViewHolder> {
    private Context context;
    private List<SectionDetailsDataModel> sectionDetailsDataModelList;

    public AllPurchasedTest(Context context, List<SectionDetailsDataModel> sectionDetailsDataModelList) {
        this.context=context;
        this.sectionDetailsDataModelList = sectionDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_purchased_online_subject,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, final int position) {
//        final SectionDetailsDataModel sectionDetailsDataModel = sectionDetailsDataModelList.get(position);

        if (sectionDetailsDataModelList.get(position).getIs_Section_Live().equals(1)){
            viewHolder.image_gif.setVisibility(View.VISIBLE);
        }else if (sectionDetailsDataModelList.get(position).getIs_Section_Live().equals(0)){
            viewHolder.image_gif.setVisibility(View.GONE);
        }

        viewHolder.online_subjectname.setText(sectionDetailsDataModelList.get(position).getBucketName());
            viewHolder.online_totaltest.setText("Total test-" + " " + sectionDetailsDataModelList.get(position).getTotalTest());

            if (sectionDetailsDataModelList.get(position).getIsPaid().equals(1)){
                viewHolder.branch_live1.setVisibility(View.VISIBLE);
                viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnlineTestData.CoachingID=sectionDetailsDataModelList.get(position).getCoachingID();
                        OnlineTestData.bucketID=sectionDetailsDataModelList.get(position).getBucketID();
                        OnlineTestData.bucketName=sectionDetailsDataModelList.get(position).getBucketName();
                        OnlineTestData.bucketInfo=sectionDetailsDataModelList.get(position).getBucketInfo();
                        OnlineTestData.logData=sectionDetailsDataModelList.get(position).getLogData();
                        OnlineTestData.status=sectionDetailsDataModelList.get(position).getStatus();
                        OnlineTestData.totalTest=sectionDetailsDataModelList.get(position).getTotalTest();
                        Intent intent=new Intent(context, OnlineTestActivity.class);
                        context.startActivity(intent);

                    }
                });
            }else{
                viewHolder.branch_live1.setVisibility(View.GONE);
            }



//           // viewHolder.branch_live1.setVisibility(View.VISIBLE);
//            viewHolder.online_subjectname.setText(sectionDetailsDataModelList.get(position).getBucketName());
//            viewHolder.online_totaltest.setText("Total test-" + " " + sectionDetailsDataModelList.get(position).getTotalTest());
//            viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    OnlineTestData.CoachingID = sectionDetailsDataModelList.get(position).getCoachingID();
//                    OnlineTestData.bucketID = sectionDetailsDataModelList.get(position).getBucketID();
//                    OnlineTestData.bucketName = sectionDetailsDataModelList.get(position).getBucketName();
//                    OnlineTestData.bucketInfo = sectionDetailsDataModelList.get(position).getBucketInfo();
//                    OnlineTestData.logData = sectionDetailsDataModelList.get(position).getLogData();
//                    OnlineTestData.status = sectionDetailsDataModelList.get(position).getStatus();
//                    OnlineTestData.totalTest = sectionDetailsDataModelList.get(position).getTotalTest();
//                    Intent intent = new Intent(context, OnlineTestActivity.class);
//                    context.startActivity(intent);
//                }
//            });




    }
    @Override
    public int getItemCount() {
        return sectionDetailsDataModelList==null ? 0 : sectionDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1;
        ImageView live_image;
        GifImageView image_gif;
        TextView online_subjectname,online_totaltest;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1=itemView.findViewById(R.id.branch_live1);
            image_gif=itemView.findViewById(R.id.image_gif);
            live_image=itemView.findViewById(R.id.live_image);
            online_subjectname=itemView.findViewById(R.id.online_subjectname);
            online_totaltest=itemView.findViewById(R.id.online_totaltest);
        }
    }
}
