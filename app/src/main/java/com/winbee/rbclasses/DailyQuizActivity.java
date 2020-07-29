package com.winbee.rbclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.McqAskedQuestion;
import com.winbee.rbclasses.model.McqAskedQuestionModel;
import com.winbee.rbclasses.model.McqQuestionModel;
import com.winbee.rbclasses.model.QuetionDatum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyQuizActivity extends AppCompatActivity {
    LinearLayout edit_solution;
    EditText editTextTitle,editTextQuestion,editTextOtion1,editTextOtion2,editTextOtion3,editTextOtion4,editTextSolution;
    Button buttonPostMcq,buttonPostMcq1;
    String UserID;
    private ProgressBarUtil progressBarUtil;
    RelativeLayout radioButtonYes,radioButtonNo;
    private RecyclerView mcq_recycycler;
    private List<QuetionDatum> list;
    private McqAskedQuestion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);
        progressBarUtil = new ProgressBarUtil(this);
        mcq_recycycler = findViewById(R.id.gec_asked_question_recycle);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextOtion1 =findViewById(R.id.editTextOtion1);
        editTextOtion2 = findViewById(R.id.editTextOtion2);
        editTextOtion3 = findViewById(R.id.editTextOtion3);
        editTextOtion4 = findViewById(R.id.editTextOtion4);
        editTextSolution =findViewById(R.id.editTextSolution);
        edit_solution = findViewById(R.id.edit_solution);
        radioButtonYes =findViewById(R.id.radioButtonYes);
        radioButtonNo = findViewById(R.id.radioButtonNo);
        buttonPostMcq=findViewById(R.id.buttonPostMcq);
        buttonPostMcq1=findViewById(R.id.buttonPostMcq1);
        radioButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_solution.setVisibility(View.VISIBLE);
                buttonPostMcq.setVisibility(View.VISIBLE);
                buttonPostMcq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation();
                    }
                });

            }
        });
        radioButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPostMcq1.setVisibility(View.VISIBLE);
                buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation1();
                    }
                });

            }
        });
        callApiService();

    }
    private void userValidation() {
        final String title = editTextTitle.getText().toString().trim();
        final String question = editTextQuestion.getText().toString().trim();
        final String option1 = editTextOtion1.getText().toString().trim();
        final String option2 = editTextOtion2.getText().toString().trim();
        final String option3 = editTextOtion3.getText().toString().trim();
        final String option4 = editTextOtion4.getText().toString().trim();
        final String solution = editTextSolution.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            editTextTitle.setError("Please enter title");
            editTextTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(question)) {
            editTextQuestion.setError("Please enter question");
            editTextQuestion.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(option1)) {
            editTextOtion1.setError("Enter option");
            editTextOtion1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option2)) {
            editTextOtion2.setError("Enter option");
            editTextOtion2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option3)) {
            editTextOtion3.setError("Enter option");
            editTextOtion3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option4)) {
            editTextOtion4.setError("Enter option");
            editTextOtion4.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(solution)) {
            editTextSolution.setError("Enter Solution");
            editTextSolution.requestFocus();
            return;
        }


        McqQuestionModel mcqQuestionModel;
        mcqQuestionModel = new McqQuestionModel();
        mcqQuestionModel.setQuestionTitle(title);
        mcqQuestionModel.setQuestion(question);
        mcqQuestionModel.setOpt1(option1);
        mcqQuestionModel.setOpt2(option2);
        mcqQuestionModel.setOpt3(option3);
        mcqQuestionModel.setOpt4(option4);
        mcqQuestionModel.setSolution(solution);


        CallSignupApi(mcqQuestionModel);
    }
    private void CallSignupApi(final McqQuestionModel mcqQuestionModel) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<McqQuestionModel> call = mService.mcqQuestionYes(UserID, mcqQuestionModel.getQuestionTitle(),mcqQuestionModel.getQuestion(),mcqQuestionModel.getOpt1(),
                mcqQuestionModel.getOpt2(), mcqQuestionModel.getOpt3(),mcqQuestionModel.getOpt4(),1,1,mcqQuestionModel.getSolution());
        call.enqueue(new Callback<McqQuestionModel>() {
            @Override
            public void onResponse(Call<McqQuestionModel> call, Response<McqQuestionModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getResponse() == true) {
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(DailyQuizActivity.this, DailyQuizActivity.class);
                    startActivity(intent);
                } else {
                    progressBarUtil.hideProgress();
                }
            }


            @Override
            public void onFailure(Call<McqQuestionModel> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(DailyQuizActivity.this,"Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void userValidation1() {
        final String title = editTextTitle.getText().toString().trim();
        final String question = editTextQuestion.getText().toString().trim();
        final String option1 = editTextOtion1.getText().toString().trim();
        final String option2 = editTextOtion2.getText().toString().trim();
        final String option3 = editTextOtion3.getText().toString().trim();
        final String option4 = editTextOtion4.getText().toString().trim();
        // final String solution = editTextSolution.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            editTextTitle.setError("Please enter title");
            editTextTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(question)) {
            editTextQuestion.setError("Please enter question");
            editTextQuestion.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(option1)) {
            editTextOtion1.setError("Enter option");
            editTextOtion1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option2)) {
            editTextOtion2.setError("Enter option");
            editTextOtion2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option3)) {
            editTextOtion3.setError("Enter option");
            editTextOtion3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option4)) {
            editTextOtion4.setError("Enter option");
            editTextOtion4.requestFocus();
            return;
        }


        McqQuestionModel mcqQuestionModel1;
        mcqQuestionModel1 = new McqQuestionModel();
        mcqQuestionModel1.setQuestionTitle(title);
        mcqQuestionModel1.setQuestion(question);
        mcqQuestionModel1.setOpt1(option1);
        mcqQuestionModel1.setOpt2(option2);
        mcqQuestionModel1.setOpt3(option3);
        mcqQuestionModel1.setOpt4(option4);


        CallNoApi(mcqQuestionModel1);
    }
    private void CallNoApi(final McqQuestionModel mcqQuestionModel1) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<McqQuestionModel> call = mService.mcqQuestionNo(UserID, mcqQuestionModel1.getQuestionTitle(), mcqQuestionModel1.getQuestion(), mcqQuestionModel1.getOpt1(),
                mcqQuestionModel1.getOpt2(), mcqQuestionModel1.getOpt3(), mcqQuestionModel1.getOpt4(), 1, 0, "NA");
        call.enqueue(new Callback<McqQuestionModel>() {
            @Override
            public void onResponse(Call<McqQuestionModel> call, Response<McqQuestionModel> response) {
                int statusCode = response.code();
                //List<RefUser> list ;
                if (statusCode == 200 && response.body().getResponse() == true) {
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(DailyQuizActivity.this, DailyQuizActivity.class);
                    startActivity(intent);
                } else {
                    progressBarUtil.hideProgress();
                    // Toast.makeText(getActivity(), "User Already exist", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<McqQuestionModel> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(DailyQuizActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<McqAskedQuestionModel> call = apiCAll.mcqAskedQuestion(UserID,3);
        call.enqueue(new Callback<McqAskedQuestionModel>() {
            @Override
            public void onResponse(Call<McqAskedQuestionModel> call, Response<McqAskedQuestionModel> response) {
                McqAskedQuestionModel purchasedMainModel=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getResponse() == true) {
                        //courses.setVisibility(View.VISIBLE);
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(purchasedMainModel).getData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new McqAskedQuestion(DailyQuizActivity.this, list);
                        mcq_recycycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        //nocourse.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(DailyQuizActivity.this, "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<McqAskedQuestionModel> call, Throwable t) {
                Toast.makeText(DailyQuizActivity.this,"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);

    }
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}
