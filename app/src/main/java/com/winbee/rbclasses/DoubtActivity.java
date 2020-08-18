package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AskDoubtAdapter;
import com.winbee.rbclasses.model.AskDoubtQuestion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoubtActivity extends AppCompatActivity {
    private Button btn_asked;
    private ProgressBarUtil progressBarUtil;
    private AskDoubtAdapter adapter;
    private ArrayList<AskDoubtQuestion> list;
    private RecyclerView askedQuestion;
    private RelativeLayout today_classes;
    SwipeRefreshLayout doubt_activity;
    private ImageView WebsiteHome,img_share;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);
        btn_asked=findViewById(R.id.btn_asked);
        askedQuestion = findViewById(R.id.gec_asked_question_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        today_classes=findViewById(R.id.today_classes);
        doubt_activity=findViewById(R.id.doubt_activity);

        doubt_activity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAskedQuestionApiService();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubt_activity.setRefreshing(false);
                    }
                },4000);
            }
        });
        btn_asked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtActivity.this,NewDoubtActivity.class);
                startActivity(intent);
            }
        });
        callAskedQuestionApiService();
    }
    private void callAskedQuestionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<AskDoubtQuestion>> call =apiCall.getQuestion();
        call.enqueue(new Callback<ArrayList<AskDoubtQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<AskDoubtQuestion>> call, Response<ArrayList<AskDoubtQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AskDoubtAdapter(DoubtActivity.this,list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AskDoubtQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                // Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();
                today_classes.setVisibility(View.VISIBLE);

            }
        });
    }



}
