package com.winbee.rbclasses.adapter;



import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.LiveDataActivity;
import com.winbee.rbclasses.LiveDataPurchasedActivity;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.NewModels.CourseContentArray;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.BranchName;
import com.winbee.rbclasses.model.CourseContentModel;
import com.winbee.rbclasses.model.CourseDatum;
import com.winbee.rbclasses.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AllPurchasedCourseAdapter extends RecyclerView.Adapter<AllPurchasedCourseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseContentArray> list1;

    public AllPurchasedCourseAdapter(Context context,ArrayList<CourseContentArray> horizontalList){
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
      //  holder.img_lock.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
        holder.tv_timer.setVisibility(View.GONE);
        holder.txt_message.setVisibility(View.GONE);

        if (list1.get(position).getIs_Any_Class_live()){
            holder.live_class.setVisibility(View.VISIBLE);
        }else if (!list1.get(position).getIs_Any_Class_live()){
            holder.live_class.setVisibility(View.GONE);
        }



        if (list1.get(position).getPaid().equals(1)) {

            holder.txt_course.setText(Html.fromHtml(list1.get(position).getBucket_Name()));
            holder.img_rupee.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.new_batch.setVisibility(View.GONE);
            Picasso.get().load(list1.get(position).getBucket_Image()).placeholder(R.drawable.wateer_mark_image).fit().into(holder.course_image);
            holder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.ChildId=list1.get(position).getChild_Link();
                    LocalData.NotificationMessage = list1.get(position).getNotification().getMessage();
                    LocalData.IsNotification=list1.get(position).getNotification().getIs_Active();
                    Intent intent = new Intent(context, LiveDataPurchasedActivity.class);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.layout1.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return list1==null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course,txt_discount,txt_actual_price,txt_message, tv_timer;
        private ImageView course_image,img_lock,img_rupee,img_rupee1;
        private GifImageView live_class,new_batch;

        private RelativeLayout layout1,today_classes;
        private Button purchase_btn,free_btn,purchased_btn;
        RelativeLayout cardView;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course = itemView.findViewById(R.id.txt_course);
            view = itemView.findViewById(R.id.view);
            txt_message = itemView.findViewById(R.id.txt_message);
            tv_timer = itemView.findViewById(R.id.tv_timer);
            live_class = itemView.findViewById(R.id.live_class);
            cardView = itemView.findViewById(R.id.branch_sem);
            course_image=itemView.findViewById(R.id.course_image);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            txt_actual_price=itemView.findViewById(R.id.txt_actual_price);
            img_lock=itemView.findViewById(R.id.img_lock);
            img_rupee=itemView.findViewById(R.id.img_rupee);
            img_rupee1=itemView.findViewById(R.id.img_rupee1);
            layout1=itemView.findViewById(R.id.layout1);
            today_classes=itemView.findViewById(R.id.today_classes);
            new_batch=itemView.findViewById(R.id.new_batch);
        }
    }
}


