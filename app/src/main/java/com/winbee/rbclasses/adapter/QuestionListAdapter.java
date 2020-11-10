package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.OnlineQuestionActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.StudentQAModel;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.CustomViewHolder> {
    private Context context;
    private List<StudentQAModel> studentQAModelList;

    public QuestionListAdapter(Context context, List<StudentQAModel> studentQAModelList) {
        this.context=context;
        this.studentQAModelList = studentQAModelList;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_question_list,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, final int position) {
        final StudentQAModel studentQAModel = studentQAModelList.get(position);
        String quesNum=String.valueOf(position+1);
        viewHolder.tv_question_num.setText(quesNum);
        setBackGround(viewHolder.tv_question_num,studentQAModel.getAnsStatus());
//        viewHolder.rl_question_num.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((OnlineQuestionActivity)context).questionSelected(position);
//            }
//        });
        viewHolder.rl_question_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnlineQuestionActivity)context).questionSelected(position);
            }
        });
    }

    private void setBackGround(TextView tv_question_num, String ansStatus) {
        if(ansStatus.equalsIgnoreCase("answered"))
            tv_question_num.setBackground(context.getDrawable(R.drawable.ic_circle_as_answered));
        else if(ansStatus.equalsIgnoreCase("not_answered"))
            tv_question_num.setBackground(context.getDrawable(R.drawable.ic_circle_as_not_answered));
        else if(ansStatus.equalsIgnoreCase("review"))
            tv_question_num.setBackground(context.getDrawable(R.drawable.ic_circle_as_review));
        else if(ansStatus.equalsIgnoreCase("not_visited"))
            tv_question_num.setBackground(context.getDrawable(R.drawable.ic_circle_as_not_visited));
        else if(ansStatus.equalsIgnoreCase("review_and_answered"))
            tv_question_num.setBackground(context.getDrawable(R.drawable.ic_circle_as_review_and_answered));
    }

    @Override
    public int getItemCount() {
        return studentQAModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_question_num;
        TextView tv_question_num;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_question_num=itemView.findViewById(R.id.rl_question_num);
            tv_question_num=itemView.findViewById(R.id.tv_question_num);
        }
    }
}
