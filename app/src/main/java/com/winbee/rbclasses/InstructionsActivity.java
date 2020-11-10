package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.InstructionsModel;
import com.winbee.rbclasses.model.StartTestModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InstructionsActivity extends AppCompatActivity {
    private TextView tv_subject_name,text2;
    private RelativeLayout layout_start_test;
    String UserId;
    private ProgressBarUtil progressBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_instructions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        iniIDs();
         setData();

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs() {
        tv_subject_name=findViewById(R.id.tv_subject_name);

        layout_start_test=findViewById(R.id.layout_start_test);
        text2=findViewById(R.id.text2);
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        layout_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiService();
            }
        });
        callInstructionService();

    }
    private void setData() {
        tv_subject_name.setText(OnlineTestData.paperName);
    }

    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<StartTestModel> call = apiCAll.getStartTest(LocalData.OrgId,UserId,OnlineTestData.paperID,"1","true");
        call.enqueue(new Callback<StartTestModel>() {
            @Override
            public void onResponse(Call<StartTestModel> call, Response<StartTestModel> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    progressBarUtil.hideProgress();
                    Intent intent=new Intent(InstructionsActivity.this, OnlineQuestionActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"something went wrong" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StartTestModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    private void callInstructionService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<InstructionsModel> call = apiCAll.getInstruction(OnlineTestData.paperID);
        call.enqueue(new Callback<InstructionsModel>() {
            @Override
            public void onResponse(Call<InstructionsModel> call, Response<InstructionsModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 ) {
                    System.out.println("Suree body: " + response.body().getUpdate_user_status());
                    // Picasso.get().load(response.body().getFile()).into(img_video_thumbails);
                    text2.setText(Html.fromHtml(response.body().getUpdate_user_status()));
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InstructionsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }


}
