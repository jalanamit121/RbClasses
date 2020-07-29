package com.winbee.rbclasses;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.McqSolutionAdapter;
import com.winbee.rbclasses.model.McqSolutionModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private McqSolutionAdapter adapter;
    private ArrayList<McqSolutionModel> list;
    private RecyclerView askedSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_solution);
        progressBarUtil   =  new ProgressBarUtil(this);
        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        callAskedSolutionApiService();
    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<McqSolutionModel>> call = apiCall.getMcqQuestionSolution(LocalData.QuestionID);
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<McqSolutionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<McqSolutionModel>> call, Response<ArrayList<McqSolutionModel>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new McqSolutionAdapter(McqSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<McqSolutionModel>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


}
