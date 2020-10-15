package com.winbee.rbclasses.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
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
import com.winbee.rbclasses.NewModels.CourseContentArray;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.BranchName;
import com.winbee.rbclasses.model.CourseDatum;
import com.winbee.rbclasses.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseContentArray> list1;

    public AllCourseAdapter(Context context, ArrayList<CourseContentArray> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (list1.get(position).getIs_New_Batch().equals(1) && list1.get(position).getIs_Any_Class_live()) {
            holder.new_batch.setVisibility(View.VISIBLE);
            holder.live_class.setVisibility(View.VISIBLE);
        } else if (list1.get(position).getIs_New_Batch().equals(1) && !list1.get(position).getIs_Any_Class_live()) {
            holder.new_batch.setVisibility(View.VISIBLE);
            holder.live_class.setVisibility(View.GONE);
        } else if (list1.get(position).getIs_New_Batch().equals(0) && list1.get(position).getIs_Any_Class_live()) {
            holder.new_batch.setVisibility(View.GONE);
            holder.live_class.setVisibility(View.VISIBLE);
        } else if (list1.get(position).getIs_New_Batch().equals(0) && !list1.get(position).getIs_Any_Class_live()) {
            holder.new_batch.setVisibility(View.GONE);
            holder.live_class.setVisibility(View.GONE);
        }

        holder.tv_timer.setText(list1.get(position).getCourse_Closed_details().getRemaining_Time());

        if (holder.timer != null) {
            holder.timer.cancel();
        }
        long timer = Long.parseLong(list1.get(position).getCourse_Closed_details().getRemaining_Time());

        timer=timer*60;
        timer = (timer+1)*1000;

        holder.timer = new CountDownTimer(timer, 1000) {
            public void onTick(long millisUntilFinished) {
                int hrs= (int) (millisUntilFinished/(60*60*1000));
                long remainingMs= millisUntilFinished%(60*60*1000);
                int min= (int) (remainingMs/(60*1000));
                int sec= (int) ((remainingMs%(60*1000))/1000);
                holder.tv_timer.setText(""+hrs+":"+min+":"+sec);
            }

            public void onFinish() {
                holder.tv_timer.setText("00:00:00");
            }
        }.start();



        holder.txt_message.setText(Html.fromHtml(list1.get(position).getCourse_Closed_details().getClosed_Course_Message()));
      if (list1.get(position).getIs_Course_Closed_notification()){
          holder.view.setVisibility(View.VISIBLE);
          if (list1.get(position).getCourse_Closed_details().getIs_Course_Closed()){
              holder.txt_message.setVisibility(View.VISIBLE);
          }else if (!list1.get(position).getCourse_Closed_details().getIs_Course_Closed()){
              holder.txt_message.setVisibility(View.VISIBLE);
          }

          if (list1.get(position).getCourse_Closed_details().getIs_timing_show()){
              holder.tv_timer.setVisibility(View.VISIBLE);
          }else if (!list1.get(position).getCourse_Closed_details().getIs_timing_show()){
              holder.tv_timer.setVisibility(View.GONE);
          }

      }else if (!list1.get(position).getIs_Course_Closed_notification()){
          holder.txt_message.setVisibility(View.GONE);
          holder.tv_timer.setVisibility(View.GONE);
          holder.view.setVisibility(View.GONE);

      }

            if (list1.get(position).getPaid().equals(0) && list1.get(position).getShow_Course()) {
                holder.layout1.setVisibility(View.VISIBLE);
                holder.txt_course.setText(Html.fromHtml(list1.get(position).getBucket_Name()));
                holder.txt_discount.setText(Html.fromHtml(list1.get(position).getDisplay_price()));
                holder.txt_discount.setPaintFlags(holder.txt_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txt_actual_price.setText(Html.fromHtml(list1.get(position).getDiscount_price()));
                Picasso.get().load(list1.get(position).getBucket_Image()).placeholder(R.drawable.wateer_mark_image).fit().into(holder.course_image);
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
                        LocalData.CourseClosed = list1.get(position).getCourse_Closed_details().getIs_Course_Closed();
                        LocalData.IsNotification = list1.get(position).getNotification().getIs_Active();
                        LocalData.NotificationMessage = list1.get(position).getNotification().getMessage();
                        LocalData.PaymentClosed = list1.get(position).getCourse_Closed_details().getIs_Course_Closed_purchase_Message();
                        LocalData.CourseMessage = list1.get(position).getCourse_Closed_details().getClosed_Course_Message();
                        Intent intent = new Intent(context, LiveDataActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else if (list1.get(position).getPaid().equals(1)) {
                holder.layout1.setVisibility(View.GONE);
            }
        if (list1.get(position).getDiscount_price().equalsIgnoreCase("0.00")) {
            holder.img_rupee.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.txt_actual_price.setVisibility(View.GONE);
            holder.txt_free.setVisibility(View.VISIBLE);
            holder.txt_free.setText("Free");
            holder.txt_discount.setVisibility(View.GONE);
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
                    LocalData.IsNotification = list1.get(position).getNotification().getIs_Active();
                    LocalData.NotificationMessage = list1.get(position).getNotification().getMessage();
                    Intent intent = new Intent(context, LiveDataPurchasedActivity.class);
                    context.startActivity(intent);
                }
            });
        }


    }



    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public CountDownTimer timer;
        private TextView txt_course, txt_discount, txt_actual_price, txt_free, txt_message, tv_timer;
        private ImageView course_image, img_rupee, img_rupee1;
        private GifImageView new_batch, live_class;
        View view;
        int milliTimer, cntMillitimer, countTimer;
        int ReHrs, ReMin, ReSec;
        CountDownTimer countDownTimer;

        private RelativeLayout layout1;
        RelativeLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course = itemView.findViewById(R.id.txt_course);
            view = itemView.findViewById(R.id.view);
            txt_message = itemView.findViewById(R.id.txt_message);
            tv_timer = itemView.findViewById(R.id.tv_timer);
            txt_free = itemView.findViewById(R.id.txt_free);
            new_batch = itemView.findViewById(R.id.new_batch);
            live_class = itemView.findViewById(R.id.live_class);
            cardView = itemView.findViewById(R.id.branch_sem);
            course_image = itemView.findViewById(R.id.course_image);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            img_rupee = itemView.findViewById(R.id.img_rupee);
            img_rupee1 = itemView.findViewById(R.id.img_rupee1);
            txt_actual_price = itemView.findViewById(R.id.txt_actual_price);
            layout1 = itemView.findViewById(R.id.layout1);

//
//               countTimer = Integer.parseInt(list1.get(getAdapterPosition()).getCourse_Closed_details().getRemaining_Time());
//              //  countTimer = 60;
//
//                countTimer = countTimer * 60;
//                milliTimer = (countTimer + 1) * 1000;
//                Log.i("tag", "ViewHolder: "+milliTimer);
//                cntMillitimer = milliTimer;
//                setTimer();


        }

//        private void setTimer() {
//
//            countDownTimer=new CountDownTimer(milliTimer,1000) {
//                @Override
//                public void onTick(long l) {
//                    int hrs= (int) (l/(60*60*1000));
//                    long remainingMs= l%(60*60*1000);
//                    int min= (int) (remainingMs/(60*1000));
//                    int sec= (int) ((remainingMs%(60*1000))/1000);
//                    getTime(hrs);
//                    tv_timer.setText(String.format("%s:%s:%s", getTime(hrs), getTime(min), getTime(sec)));
//
//                    long fTime=cntMillitimer-l;
//
//                    ReHrs= (int) (fTime/(60*60*1000));
//                    long RmSEC= fTime%(60*60*1000);
//                    ReMin= (int) (RmSEC/(60*1000));
//                    ReSec= (int) ((RmSEC%(60*1000))/1000);
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            }.start();
//        }
//        private String getTime(int timeData) {
//            String time= String.valueOf(timeData);
//            if (time.length()==1)
//                return "0"+time;
//            return time;
//        }


    }
}

