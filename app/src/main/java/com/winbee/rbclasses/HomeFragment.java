package com.winbee.rbclasses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.SekHomeAdapter;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ProgressBarUtil progressBarUtil;
    private ArrayList<UpdateModel> updateModels;
    private SekHomeAdapter sekHomeAdapter;
    private RecyclerView sek_home_recycle;
    private RelativeLayout today_classes;
    private SwipeRefreshLayout refresh_home;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.principal, container, false);
//        refresh_home = (SwipeRefreshLayout)findViewById(R.id.refresh_home);
//        refresh_home.setOnRefreshListener((SwipeRefreshLa getContext());
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        progressBarUtil   =  new ProgressBarUtil(getContext());
        sek_home_recycle=view.findViewById(R.id.sek_home_recycle);
//        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager manager = ((LinearLayoutManager)recyclerView.getLayoutManager());
//                boolean enabled =manager.findFirstCompletelyVisibleItemPosition() == 0;
//                refresh_home.setEnabled(enabled);
//            }
//        };//ye me try karraha tha okay boss
        today_classes=view.findViewById(R.id.today_classes);
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
        Call<ArrayList<UpdateModel>> call = apiCAll.getDailyupdate();
        call.enqueue(new Callback<ArrayList<UpdateModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UpdateModel>> call, Response<ArrayList<UpdateModel>> response) {
                int statusCode = response.code();
                updateModels = new ArrayList();
                if(statusCode==200){
                    if (response.body().size()!=0){
                        updateModels = response.body();
                        sekHomeAdapter = new SekHomeAdapter(getActivity(), updateModels);
                        sek_home_recycle.setAdapter(sekHomeAdapter);
                        progressBarUtil.hideProgress();
                        refresh_home.setRefreshing(false);

                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                        refresh_home.setRefreshing(false);
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                    refresh_home.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UpdateModel>> call, Throwable t) {
//                Toast.makeText(NotificationActivity.this, "Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                System.out.println("Suree: Error "+t.getMessage());
                today_classes.setVisibility(View.VISIBLE);
                progressBarUtil.hideProgress();
                refresh_home.setRefreshing(false);
            }
        });
    }

}
