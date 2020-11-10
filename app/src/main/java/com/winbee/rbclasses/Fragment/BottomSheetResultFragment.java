package com.winbee.rbclasses.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.winbee.rbclasses.OnlineQuestionActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.model.StudentQAModel;

import java.util.List;

public class BottomSheetResultFragment extends BottomSheetDialogFragment {
    private Context mContext;
    private View view;
    private TextView total_attempted,total_review,total_unattempted,total_submit_review,total_question;
    private Button button_submit,button_back_test;
    public BottomSheetResultFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.bottom_sheet_result_fragment, container, false);
        iniIDs();
        GetQuestionList();
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnlineQuestionActivity)mContext).submitData();
            }
        });
        button_back_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnlineQuestionActivity)mContext).backToTest();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void iniIDs() {
        total_attempted=view.findViewById(R.id.total_attempted);
        total_review=view.findViewById(R.id.total_review);
        total_unattempted=view.findViewById(R.id.total_unattempted);
        total_submit_review=view.findViewById(R.id.total_submit_review);
        total_question=view.findViewById(R.id.total_question);
        button_submit=view.findViewById(R.id.button_submit);
        button_back_test=view.findViewById(R.id.button_back_test);
    }
    private void GetQuestionList() {
        List<StudentQAModel> studentQAModelList= OnlineTestData.studentQAModels;
        String ques="Question : "+studentQAModelList.size();
        if(studentQAModelList.size()>0){
            countQuestionStatus(studentQAModelList);
        }
    }
    private void countQuestionStatus(List<StudentQAModel> studentQAModelList){
        int total_attemptedInt=0;
        int total_reviewInt=0;
        int total_unattemptedInt=0;
        int total_submit_reviewInt=0;
        for(int i=0;i<studentQAModelList.size();i++){
            String ansStatus=studentQAModelList.get(i).getAnsStatusCode();
            //1-submitand next,2-next,3-review,4-submitand review,0-not_visited
            if(ansStatus.equalsIgnoreCase("1"))
                total_attemptedInt++;
            else if(ansStatus.equalsIgnoreCase("2"))
                total_unattemptedInt++;
            else if(ansStatus.equalsIgnoreCase("3"))
                total_reviewInt++;
            else if(ansStatus.equalsIgnoreCase("4"))
                total_submit_reviewInt++;
            else if(ansStatus.equalsIgnoreCase("0"))
                total_unattemptedInt++;
        }
        total_attempted.setText(String.valueOf(total_attemptedInt));
        total_review.setText(String.valueOf(total_reviewInt));
        total_unattempted.setText(String.valueOf(total_unattemptedInt));
        total_submit_review.setText(String.valueOf(total_submit_reviewInt));
        total_question.setText(Integer.toString(OnlineTestData.totalQuestion));
    }

}