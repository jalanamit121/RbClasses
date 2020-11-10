package com.winbee.rbclasses.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.adapter.QuestionListAdapter;
import com.winbee.rbclasses.model.StudentQAModel;


import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class BottomSheetQuestionFragment extends BottomSheetDialogFragment {
    private Context mContext;
    private View view;
    private TextView tv_subject_name,tv_total_question;
    private Toast toast_msg;
    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_question_list;
    private TextView tv_answered,tv_not_answered,tv_reviewed,tv_not_visited,tv_answered_reviewed;
    public BottomSheetQuestionFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.bottom_sheet_question_fragment, container, false);
        iniIDs();
        GetQuestionList();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void iniIDs() {
        tv_subject_name=view.findViewById(R.id.tv_subject_name);
        tv_total_question=view.findViewById(R.id.tv_total_question);
        recycle_question_list=view.findViewById(R.id.recycle_question_list);
        shimmerLayout=view.findViewById(R.id.shimmerLayout);
        tv_answered=view.findViewById(R.id.tv_answered);
        tv_not_answered=view.findViewById(R.id.tv_not_answered);
        tv_reviewed=view.findViewById(R.id.tv_reviewed);
        tv_not_visited=view.findViewById(R.id.tv_not_visited);
        tv_answered_reviewed=view.findViewById(R.id.tv_answered_reviewed);
    }
    private void GetQuestionList() {
        tv_subject_name.setText(OnlineTestData.paperName);
        apiCall();
        List<StudentQAModel> studentQAModelList= OnlineTestData.studentQAModels;
        String ques="Question : "+studentQAModelList.size();
        if(studentQAModelList.size()>0){
            tv_total_question.setText(ques);
            QuestionListAdapter questionListAdapter=new QuestionListAdapter(mContext,studentQAModelList);
            RecyclerView.LayoutManager layoutManager=new GridLayoutManager(mContext,5);
            recycle_question_list.setLayoutManager(layoutManager);
            recycle_question_list.setItemAnimator(new DefaultItemAnimator());
            recycle_question_list.setAdapter(questionListAdapter);
            apiCalled();
        }
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
    private void countQuestionStatus(List<StudentQAModel> studentQAModelList){
        int answered=0;
        int not_answered=0;
        int not_visited=0;
        int review=0;
        int review_and_answered=0;
        for(int i=0;i<studentQAModelList.size();i++){
            String ansStatus=studentQAModelList.get(i).getAnsStatus();
            if(ansStatus.equalsIgnoreCase("answered"))
                answered++;
            else if(ansStatus.equalsIgnoreCase("not_answered"))
                not_answered++;
            else if(ansStatus.equalsIgnoreCase("review"))
                not_visited++;
            else if(ansStatus.equalsIgnoreCase("not_visited"))
                review++;
            else if(ansStatus.equalsIgnoreCase("review_and_answered"))
                review_and_answered++;
        }
        tv_answered.setText(String.valueOf(answered));
        tv_not_answered.setText(String.valueOf(not_answered));
        tv_not_visited.setText(String.valueOf(not_visited));
        tv_reviewed.setText(String.valueOf(review));
        tv_answered_reviewed.setText(String.valueOf(review_and_answered));

    }

}