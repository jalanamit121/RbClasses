package com.winbee.rbclasses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllLiveClassAdapter;
import com.winbee.rbclasses.adapter.AllPurchasedLiveClassAdapter;
import com.winbee.rbclasses.model.CourseContentModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LivePurchasedFragment extends Fragment {
  private ArrayList<CourseContentModel> liveList;
  private RecyclerView video_list_recycler;
  private ProgressBarUtil progressBarUtil;
  private AllPurchasedLiveClassAdapter adapter;
  RelativeLayout today_classes;
  SwipeRefreshLayout refresh_list;
  private ImageView img_share,WebsiteHome;
  String UserID;



  public LivePurchasedFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_live_purchased, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil   =  new ProgressBarUtil(getContext());
    video_list_recycler =view.findViewById(R.id.all_liveClasses);
    today_classes =view.findViewById(R.id.today_classes);
    UserID=SharedPrefManager.getInstance(getContext()).refCode().getUserId();
    callLiveApiService();
  }
  private void callLiveApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<ArrayList<CourseContentModel>> call = apiCAll.getPurchasedCourseContent(3,UserID,"WB_009",LocalData.ChildId);
    call.enqueue(new Callback<ArrayList<CourseContentModel>>() {
      @Override
      public void onResponse(Call<ArrayList<CourseContentModel>> call, Response<ArrayList<CourseContentModel>> response) {
        int statusCode = response.code();
        liveList = new ArrayList();
        if(statusCode==200) {
          if (response.body().size() != 0) {
            System.out.println("Suree body: " + response.body());
            liveList = response.body();
            adapter = new AllPurchasedLiveClassAdapter(getActivity(), liveList);
            video_list_recycler.setAdapter(adapter);
            progressBarUtil.hideProgress();
          }else{
            today_classes.setVisibility(View.VISIBLE);
            progressBarUtil.hideProgress();
          }
        }
        else{
          System.out.println("Suree: response code"+response.message());
          Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ArrayList<CourseContentModel>> call, Throwable t) {
        Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error "+t.getMessage());
      }
    });
  }


}
