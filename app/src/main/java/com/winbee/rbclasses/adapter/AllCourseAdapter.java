package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.LiveDataActivity;
import com.winbee.rbclasses.LiveDataPurchasedActivity;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.BranchName;
import com.winbee.rbclasses.model.CourseDatum;
import com.winbee.rbclasses.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseModel> list1;

    public AllCourseAdapter(Context context,ArrayList<CourseModel> horizontalList){
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (list1.get(position).getPaid().equals(0)) {
            holder.layout1.setVisibility(View.VISIBLE);
            holder.txt_course.setText(list1.get(position).getBucket_Name());
            holder.txt_discount.setText(list1.get(position).getDisplay_price());
            holder.txt_discount.setPaintFlags(holder.txt_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_actual_price.setText(list1.get(position).getDiscount_price());
            Picasso.get().load(list1.get(position).getBucket_Image()).fit().into(holder.course_image);
            holder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.ChildId = list1.get(position).getChild_Link();
                    LocalData.PayImage = list1.get(position).getBucket_Cover_Image();
                    LocalData.Discription = list1.get(position).getBucket_Name();
                    LocalData.TotalVideo = String.valueOf(list1.get(position).getTotal_Video());
                    LocalData.TotalDocument = String.valueOf(list1.get(position).getTotal_Document());
                    LocalData.DiscountPrice = list1.get(position).getDiscount_price();
                    LocalData.ActualPrice = list1.get(position).getDisplay_price();
                    LocalData.CourseId = list1.get(position).getBucket_ID();
                    Intent intent = new Intent(context, LiveDataActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if (list1.get(position).getPaid().equals(1)){
            holder.layout1.setVisibility(View.GONE);
        }
        if (list1.get(position).getDiscount_price().equalsIgnoreCase("0.00")){
            holder.img_rupee.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.txt_actual_price.setVisibility(View.VISIBLE);
            holder.txt_actual_price.setText("Free");
            holder.txt_discount.setVisibility(View.GONE);
            holder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.ChildId=list1.get(position).getChild_Link();
                    LocalData.PayImage=list1.get(position).getBucket_Cover_Image();
                    LocalData.Discription=list1.get(position).getBucket_Name();
                    LocalData.TotalVideo=String.valueOf(list1.get(position).getTotal_Video());
                    LocalData.TotalDocument=String.valueOf(list1.get(position).getTotal_Document());
                    LocalData.DiscountPrice=list1.get(position).getDiscount_price();
                    LocalData.ActualPrice=list1.get(position).getDisplay_price();
                    LocalData.CourseId=list1.get(position).getBucket_ID();
                    Intent intent = new Intent(context, LiveDataPurchasedActivity.class);
                    context.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return list1==null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course,txt_discount,txt_actual_price;
        private ImageView course_image,img_rupee,img_rupee1;

        private RelativeLayout layout1;
        private Button purchase_btn,free_btn,purchased_btn;
        RelativeLayout cardView;
        CardView course_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course = itemView.findViewById(R.id.txt_course);
            cardView = itemView.findViewById(R.id.branch_sem);
            course_image=itemView.findViewById(R.id.course_image);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            img_rupee=itemView.findViewById(R.id.img_rupee);
            img_rupee1=itemView.findViewById(R.id.img_rupee1);
            txt_actual_price=itemView.findViewById(R.id.txt_actual_price);
            layout1=itemView.findViewById(R.id.layout1);
        }
    }
}

