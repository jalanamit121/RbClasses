package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.DailyQuizActivity;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.McqSolutionActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.SharedPrefManager;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.McqQuestionSolutionModel;
import com.winbee.rbclasses.model.QuetionDatum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqAskedQuestion extends RecyclerView.Adapter<McqAskedQuestion.ViewHolder> {
    private Context context;
    private List<QuetionDatum> courseDatumList;

    public McqAskedQuestion(Context context, List<QuetionDatum> courseDatumList){
        this.context = context;
        this.courseDatumList = courseDatumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mcq_asked_question,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.txt_user.setText(courseDatumList.get(position).getUser());
        holder.txt_ask_title.setText(courseDatumList.get(position).getQuestionTitle());
        holder.txt_ask_question.setText(courseDatumList.get(position).getQuestion());
        holder.editTextOtion1.setText(courseDatumList.get(position).getOpt1());
        holder.editTextOtion2.setText(courseDatumList.get(position).getOpt2());
        holder.editTextOtion3.setText(courseDatumList.get(position).getOpt3());
        holder.editTextOtion4.setText(courseDatumList.get(position).getOpt4());
        if (courseDatumList.get(position).getIs_Attempted()==false){
            holder.edit_solution.setVisibility(View.VISIBLE);
            holder.buttonPostMcq1.setVisibility(View.GONE);
        }else {
            holder.buttonPostMcq1.setVisibility(View.VISIBLE);
            holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.QuestionID=courseDatumList.get(position).getQuestionId();
                    Intent intent = new Intent(context, McqSolutionActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return courseDatumList==null ? 0 : courseDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_user, txt_ask_title, txt_ask_question, editTextOtion1, editTextOtion2, editTextOtion3, editTextOtion4, editTextSolution;
        private ImageView branch_image;
        private RelativeLayout layout_mcq;
        RelativeLayout cardView;
        LinearLayout edit_solution;
        Button button_submit_solution,buttonPostMcq1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_user = itemView.findViewById(R.id.txt_user);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            txt_ask_question = itemView.findViewById(R.id.txt_ask_question);
            editTextOtion1 = itemView.findViewById(R.id.editTextOtion1);
            editTextOtion2 = itemView.findViewById(R.id.editTextOtion2);
            editTextOtion3 = itemView.findViewById(R.id.editTextOtion3);
            editTextOtion4 = itemView.findViewById(R.id.editTextOtion4);
            editTextSolution = itemView.findViewById(R.id.editTextSolution);
            buttonPostMcq1 = itemView.findViewById(R.id.buttonPostMcq1);
            cardView = itemView.findViewById(R.id.branch_sem);
            branch_image = itemView.findViewById(R.id.branch_image);
            layout_mcq = itemView.findViewById(R.id.layout_mcq);
            edit_solution = itemView.findViewById(R.id.edit_solution);
            button_submit_solution = itemView.findViewById(R.id.button_submit_solution);
            button_submit_solution.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solutionValidation();
                }
            });
        }

        private void solutionValidation() {
            final String solution = editTextSolution.getText().toString();
            final String question = String.valueOf(courseDatumList.get(getAdapterPosition()).getQuestionId());
            final String userid = SharedPrefManager.getInstance(context).refCode().getUserId();
            if (TextUtils.isEmpty(solution)) {
                editTextSolution.setError("Please enter solution");
                editTextSolution.requestFocus();
                return;
            }
            McqQuestionSolutionModel mcqQuestionSolutionModel = new McqQuestionSolutionModel();
            mcqQuestionSolutionModel.setQuestionID(question);
            mcqQuestionSolutionModel.setSolution(solution);
            mcqQuestionSolutionModel.setUserID(userid);


            callSolutionApiService(mcqQuestionSolutionModel);

        }

        private void callSolutionApiService(McqQuestionSolutionModel mcqQuestionSolutionModel) {
            ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
            Call<McqQuestionSolutionModel> call = apiCall.getMcqSolution(mcqQuestionSolutionModel.getUserID(),2, mcqQuestionSolutionModel.getSolution(), mcqQuestionSolutionModel.getQuestionID());
            call.enqueue(new Callback<McqQuestionSolutionModel>() {
                @Override
                public void onResponse(Call<McqQuestionSolutionModel> call, Response<McqQuestionSolutionModel> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 && response.body() != null) {
                        context.startActivity(new Intent(context, DailyQuizActivity.class));
                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<McqQuestionSolutionModel> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());
                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
}


