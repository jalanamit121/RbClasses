package com.winbee.rbclasses;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.SekHomeAdapter;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
  private ProgressBarUtil progressBarUtil;
  private ArrayList<UpdateModel> updateModels;
  private SekHomeAdapter sekHomeAdapter;
  private RecyclerView sek_home_recycle;
  private RelativeLayout today_classes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    progressBarUtil   =  new ProgressBarUtil(this);
    sek_home_recycle=findViewById(R.id.sek_home_recycle);
    today_classes=findViewById(R.id.today_classes);
    callUpdateApiService();
  }
  private void callUpdateApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<ArrayList<UpdateModel>> call = apiCAll.getDailyupdate();
    call.enqueue(new Callback<ArrayList<UpdateModel>>() {
      @Override
      public void onResponse(Call<ArrayList<UpdateModel>> call, Response<ArrayList<UpdateModel>> response) {
        int statusCode = response.code();
        updateModels = new ArrayList();
        if(statusCode==200){
          if (response.body().size()!=0){
            updateModels = response.body();
            sekHomeAdapter = new SekHomeAdapter(NotificationActivity.this, updateModels);
            sek_home_recycle.setAdapter(sekHomeAdapter);
            progressBarUtil.hideProgress();
          }else{
            today_classes.setVisibility(View.VISIBLE);
            progressBarUtil.hideProgress();
          }
        }
        else{
          System.out.println("Suree: response code"+response.message());
          Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ArrayList<UpdateModel>> call, Throwable t) {
//                Toast.makeText(NotificationActivity.this, "Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                System.out.println("Suree: Error "+t.getMessage());
        today_classes.setVisibility(View.VISIBLE);
        progressBarUtil.hideProgress();
      }
    });
  }
}
