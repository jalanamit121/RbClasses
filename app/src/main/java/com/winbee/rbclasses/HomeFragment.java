package com.winbee.rbclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.NewModels.DailyUpdate;
import com.winbee.rbclasses.NewModels.DailyUpdateArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.SekHomeAdapter;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class HomeFragment extends Fragment {
    private ProgressBarUtil progressBarUtil;
    private ArrayList<DailyUpdateArray> updateModels;
    private SekHomeAdapter sekHomeAdapter;
    private RecyclerView sek_home_recycle;
    private RelativeLayout today_classes;
    private SwipeRefreshLayout refresh_home;
    private String UserId,android_id;



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        progressBarUtil   =  new ProgressBarUtil(getContext());
        sek_home_recycle=view.findViewById(R.id.sek_home_recycle);
        today_classes=view.findViewById(R.id.today_classes);
        UserId=SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        refresh_home=view.findViewById(R.id.refresh_home);
        refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callUpdateApiService();
            }
        });
        callUpdateApiService();
    }


    private void callUpdateApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<DailyUpdate> call = apiCAll.getDailyupdate(UserId,android_id);
        call.enqueue(new Callback<DailyUpdate>() {
            @Override
            public void onResponse(Call<DailyUpdate> call, Response<DailyUpdate> response) {
                DailyUpdate dailyUpdate= response.body();
                int statusCode = response.code();
                updateModels = new ArrayList();
                if(statusCode==200){
                    if (response.body().getError()==false){
                        if (response.body().getData()!=null){
                        updateModels = new ArrayList<>(Arrays.asList(Objects.requireNonNull(dailyUpdate).getData()));
                        sekHomeAdapter = new SekHomeAdapter(getActivity(), updateModels);
                        sek_home_recycle.setAdapter(sekHomeAdapter);
                        progressBarUtil.hideProgress();
                        refresh_home.setRefreshing(false);
                        }else{
                            today_classes.setVisibility(View.VISIBLE);
                            progressBarUtil.hideProgress();
                            refresh_home.setRefreshing(false);
                        }
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                    refresh_home.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<DailyUpdate> call, Throwable t) {
                today_classes.setVisibility(View.VISIBLE);
                progressBarUtil.hideProgress();
                refresh_home.setRefreshing(false);
            }
        });
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(getContext()).logout();
        startActivity(new Intent(getContext(), LoginActivity.class));
        Objects.requireNonNull(this).getActivity().finish();
    }
}
