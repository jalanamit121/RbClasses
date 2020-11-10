package com.winbee.rbclasses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.ViewResult;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewResultActivity extends AppCompatActivity {

    private TextView tv_paper_name,tv_section_name,tv_total_question,tv_total_attempt,tv_total_correct,
            tv_total_review,tv_total_wrong,tv_total_marks,tv_user_name,backbtn,btn_solution;
    RelativeLayout layout_home,layout_solution;
    String UserID;
    PieChart pieChart;
    private ViewResult viewResult;
    private List<ViewResult> list;
    private RecyclerView view_result_recycle;
    //private ViewResultAdapter viewResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        tv_paper_name=findViewById(R.id.tv_paper_name);
        layout_home=findViewById(R.id.layout_home);
        layout_solution=findViewById(R.id.layout_solution);
        pieChart = findViewById(R.id.piechart);
        tv_user_name=findViewById(R.id.tv_user_name);
        tv_total_question=findViewById(R.id.tv_total_question);
        tv_total_attempt=findViewById(R.id.tv_total_attempt);
        tv_total_correct=findViewById(R.id.tv_total_correct);
        tv_total_review=findViewById(R.id.tv_total_review);
        tv_total_wrong=findViewById(R.id.tv_total_wrong);
        tv_total_marks=findViewById(R.id.tv_total_marks);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        backbtn=findViewById(R.id.backbtn);
        btn_solution=findViewById(R.id.btn_solution);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ViewResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        layout_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ViewResultActivity.this,TestSolutionActivity.class);
                startActivity(intent);
            }
        });


        callApiService();

    }

    private void callApiService() {
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
                    String Question=String.valueOf(response.body().getTotalQuestion());
                    tv_total_question.setText(Question);
                    String Attempt=String.valueOf(response.body().getAttempt());
                    tv_total_attempt.setText(Attempt);
                    String Correct=String.valueOf(response.body().getCorrect());
                    tv_total_correct.setText(Correct);
                    String Review=String.valueOf(response.body().getReview());
                    tv_total_review.setText(Review);
                    String Wrong=String.valueOf(response.body().getWrong());
                    tv_total_wrong.setText(Wrong);
                    String Total=response.body().getTotalMarks();
                    String ReviewQ=String.valueOf(response.body().getReview());
                    tv_total_marks.setText(Total);
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
                                    Integer.parseInt(ReviewQ),
                                    Color.parseColor("#29B6F6")));

                    // To animate the pie chart
                    pieChart.startAnimation();

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
        tv_paper_name.setText(OnlineTestData.paperName);
        tv_user_name.setText(SharedPrefManager.getInstance(this).refCode().getName());
    }
}
