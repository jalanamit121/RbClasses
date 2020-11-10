package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balsikandar.crashreporter.CrashReporter;
import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.DemoTestListAdapter;
import com.winbee.rbclasses.model.SIACDetailsDataModel;
import com.winbee.rbclasses.model.SIACDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoOnlineTestActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    ImageView WebsiteHome,img_share;
    String UserId;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_online_test);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try{
            UserId=SharedPrefManager.getInstance(this).refCode().getUserId();

            layout_home = findViewById(R.id.layout_home);
            layout_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent home = new Intent(DemoOnlineTestActivity.this,MainActivity.class);
                    startActivity(home);
                }
            });

            layout_course = findViewById(R.id.layout_course);
            layout_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent live = new Intent(DemoOnlineTestActivity.this, YouTubeVideoList.class);
                    startActivity(live);
                }
            });
            layout_test = findViewById(R.id.layout_test);
            layout_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent doubt = new Intent(DemoOnlineTestActivity.this,SubjectActivity.class);
                    startActivity(doubt);
                }
            });
            layout_current = findViewById(R.id.layout_current);
            layout_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DemoOnlineTestActivity.this, CurrentAffairsActivity.class);
                    startActivity(intent);
                }
            });
            layout_doubt = findViewById(R.id.layout_doubt);
            layout_doubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DemoOnlineTestActivity.this, DiscussionActivity.class);
                    startActivity(intent);
                }
            });


            iniIDs();
            getTestList();

        }catch (Exception e){
            CrashReporter.logException(e);
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_test=findViewById(R.id.recycle_test);
    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(LocalData.OrgId,LocalData.AuthCode,LocalData.TestBuckedId,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        DemoTestListAdapter testListAdapter=new DemoTestListAdapter(DemoOnlineTestActivity.this,siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DemoOnlineTestActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
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
        toast_msg = Toast.makeText(DemoOnlineTestActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}
