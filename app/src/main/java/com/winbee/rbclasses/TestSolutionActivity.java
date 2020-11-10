package com.winbee.rbclasses;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.TestSolutionAdapter;
import com.winbee.rbclasses.model.SIADDSolutionDataModel;
import com.winbee.rbclasses.model.SIADSolutionDataModel;
import com.winbee.rbclasses.model.SIADSolutionMainModel;
import com.winbee.rbclasses.model.ViewResult;

import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSolutionActivity extends AppCompatActivity {
    private RecyclerView test_recycler;
    private ArrayList<SIADDSolutionDataModel> list;
    private TestSolutionAdapter adapter;
    String UserID;
    PieChart pieChart;
    private ProgressBarUtil progressBarUtil;
    private ImageView WebsiteHome,img_share;
    private TextView tv_paper_name,tv_user_name,tv_total_question,tv_total_attempt,tv_total_correct,
            tv_total_review,tv_total_wrong,tv_section_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_solution);

        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil = new ProgressBarUtil(this);
        test_recycler = findViewById(R.id.test_solution_recycle);
        tv_paper_name=findViewById(R.id.tv_paper_name);
        tv_user_name=findViewById(R.id.tv_user_name);
        pieChart=findViewById(R.id.piechart);
        tv_total_question=findViewById(R.id.tv_total_question);
        tv_total_attempt=findViewById(R.id.tv_total_attempt);
        tv_total_correct=findViewById(R.id.tv_total_correct);
        tv_total_review=findViewById(R.id.tv_total_review);
        tv_total_wrong=findViewById(R.id.tv_total_wrong);
        tv_section_name=findViewById(R.id.tv_section_name);
        callApiService();
        callResultService();

    }


    private void callResultService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ViewResult> call = apiCAll.viewResult(LocalData.OrgId, OnlineTestData.paperID,UserID);
        call.enqueue(new Callback<ViewResult>() {
            @Override
            public void onResponse(Call<ViewResult> call, Response<ViewResult> response) {
                list = new ArrayList();
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    System.out.println("Suree body: "+response.body());
                    OnlineTestData.PaperID=response.body().getPaperID();
                    String Question= String.valueOf(response.body().getTotalQuestion());
                    tv_total_question.setText(Question);
                    String Attempt= String.valueOf(response.body().getAttempt());
                    tv_total_attempt.setText(Attempt);
                    String Correct= String.valueOf(response.body().getCorrect());
                    tv_total_correct.setText(Correct);
                    String Review= String.valueOf(response.body().getReview());
                    tv_total_review.setText(Review);
                    String Wrong= String.valueOf(response.body().getWrong());
                    tv_total_wrong.setText(Wrong);
                    String Total=response.body().getTotalMarks();
                    tv_section_name.setText(Total);
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Attempt",
                                    Integer.parseInt(tv_total_attempt.getText().toString()),
                                    Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Correct",
                                    Integer.parseInt(tv_total_correct.getText().toString()),
                                    Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Wrong++",
                                    Integer.parseInt(tv_total_wrong.getText().toString()),
                                    Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Review",
                                    Integer.parseInt(tv_total_review.getText().toString()),
                                    Color.parseColor("#29B6F6")));

                    // To animate the pie chart
                    pieChart.startAnimation();


                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
        tv_paper_name.setText(OnlineTestData.paperName);
        tv_user_name.setText(SharedPrefManager.getInstance(this).refCode().getName());
    }


    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIADSolutionMainModel> call = apiCAll.getTestSolution(LocalData.OrgId,OnlineTestData.paperID, UserID);
        call.enqueue(new Callback<SIADSolutionMainModel>() {
            @Override
            public void onResponse(Call<SIADSolutionMainModel> call, Response<SIADSolutionMainModel> response) {
                SIADSolutionMainModel purchasedMainModel = response.body();
                int statusCode = response.code();
                // list = new ArrayList();
                if (statusCode == 200) {
                    if (response.body().getMessage().equalsIgnoreCase("true")) {
                        //courses.setVisibility(View.VISIBLE);
                        SIADSolutionDataModel siadSolutionDataModel = purchasedMainModel.getData();
                        list = new ArrayList<>(Arrays.asList(siadSolutionDataModel.getQuestionData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new TestSolutionAdapter(TestSolutionActivity.this, list);
                        test_recycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        //nocourse.setVisibility(View.VISIBLE);
                    }
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SIADSolutionMainModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}