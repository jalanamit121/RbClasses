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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.LiveDataActivity;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.PaymentModel;
import com.winbee.rbclasses.model.TxnModel;

import java.util.ArrayList;

public class AllTxnAdapter  extends RecyclerView.Adapter<AllTxnAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TxnModel> list1;

    public AllTxnAdapter(Context context, ArrayList<TxnModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public AllTxnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.txn_adapter, parent, false);
        return new AllTxnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTxnAdapter.ViewHolder holder, final int position) {
        holder.text_course_name.setText(list1.get(position).getBucketName());
        holder.text_amount.setText(list1.get(position).getAmount_order_org());
        holder.text_txnid.setText("Txn_id - "+list1.get(position).getOrder_id());
        if (list1.get(position).getPaid().equalsIgnoreCase("1")) {
            holder.text_status.setText("Success");
            holder.text_date.setText(list1.get(position).getSuccessDate());
        } else if (list1.get(position).getPaid().equalsIgnoreCase("Null")) {
            holder.text_status.setText("Pending");
            holder.text_date.setText(list1.get(position).getInitaitedDate());
        }


    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_course_name, text_amount, text_txnid, text_status,text_date;
        private ImageView course_image;

        private RelativeLayout layout1;
        private Button purchase_btn, free_btn, purchased_btn;
        RelativeLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_course_name = itemView.findViewById(R.id.text_course_name);
            cardView = itemView.findViewById(R.id.branch_sem);
            course_image = itemView.findViewById(R.id.course_image);
            text_amount = itemView.findViewById(R.id.text_amount);
            text_txnid = itemView.findViewById(R.id.text_txnid);
            text_status = itemView.findViewById(R.id.text_status);
            text_date = itemView.findViewById(R.id.text_date);
            layout1 = itemView.findViewById(R.id.layout1);
        }
    }
}