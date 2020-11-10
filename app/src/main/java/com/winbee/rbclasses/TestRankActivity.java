package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.rbclasses.NewModels.TestMyRank;
import com.winbee.rbclasses.NewModels.TestTopRanker;
import com.winbee.rbclasses.NewModels.TestTopRankerArray;
import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.Utils.OnlineTestData;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.TestRankAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRankActivity extends AppCompatActivity {
    private TestRankAdapter adapter;
    RecyclerView rank_reycle;
    TextView test_name,txt_marks,txt_rank;
    private ImageView WebsiteHome,img_share,image_performance;
    private ArrayList<TestTopRankerArray> testTopRankerArrays;
    private ArrayList<TestMyRank>testMyRanks;
    private ProgressBarUtil progressBarUtil;
    String UserId,Marks;
    int Ranks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rank);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rank_reycle=findViewById(R.id.rank_reycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        test_name=findViewById(R.id.test_name);
        image_performance=findViewById(R.id.image_performance);
        test_name.setText( OnlineTestData.paperName);
        txt_marks=findViewById(R.id.txt_marks);
        txt_rank=findViewById(R.id.txt_rank);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();

        getTopperName();
    }
    private void getTopperName() {
        progressBarUtil.showProgress();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<TestTopRanker> call=apiClient.fetchTopRanker(OnlineTestData.paperID,UserId);
        call.enqueue(new Callback<TestTopRanker>() {
            @Override
            public void onResponse(Call<TestTopRanker> call, Response<TestTopRanker> response) {
                progressBarUtil.hideProgress();
                TestTopRanker testTopRanker=response.body();
                if(testTopRanker!=null){
                    if (testTopRanker.getIs_User_in_top()==true){
                        image_performance.setVisibility(View.VISIBLE);
                    }else{
                        image_performance.setVisibility(View.GONE);
                    }
                    testTopRankerArrays=new ArrayList<>(Arrays.asList(Objects.requireNonNull(testTopRanker).getData()));
                    testMyRanks=new ArrayList<>(Arrays.asList(Objects.requireNonNull(testTopRanker).getMyData()));
                    Marks=testMyRanks.get(0).getTotalScore();
                    Ranks=testMyRanks.get(0).getRank();
                    txt_marks.setText(Marks);
                    txt_rank.setText(Integer.toString(Ranks));
                    adapter=new TestRankAdapter(TestRankActivity.this,testTopRankerArrays);
                    rank_reycle.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(TestRankActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<TestTopRanker> call, Throwable t) {
                System.out.println("call fail "+t);
                progressBarUtil.hideProgress();
            }
        });
    }
}