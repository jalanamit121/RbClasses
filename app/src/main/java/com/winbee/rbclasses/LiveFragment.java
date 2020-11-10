package com.winbee.rbclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.NewModels.VideoContent;
import com.winbee.rbclasses.NewModels.VideoContentArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllLiveClassAdapter;
import com.winbee.rbclasses.model.CourseContentModel;
import com.winbee.rbclasses.model.LiveClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFragment extends Fragment {
  private ArrayList<VideoContentArray> liveList;
  private RecyclerView video_list_recycler;
  private ProgressBarUtil progressBarUtil;
  private AllLiveClassAdapter adapter;
  RelativeLayout today_classes;
  private SwipeRefreshLayout refresh_course;
  private ImageView img_share,WebsiteHome;
  String UserID,android_id;



  public LiveFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_live, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil   =  new ProgressBarUtil(getContext());
    video_list_recycler =view.findViewById(R.id.all_liveClasses);
    today_classes =view.findViewById(R.id.today_classes);
    UserID=SharedPrefManager.getInstance(getContext()).refCode().getUserId();
    android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
    refresh_course =view.findViewById(R.id.refresh_course);
    refresh_course.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        callLiveApiService();
      }
    });

    callLiveApiService();
  }
  private void callLiveApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<VideoContent> call = apiCAll.getPurchasedCourseContent(3,UserID,"WB_009",LocalData.ChildId,android_id);
    call.enqueue(new Callback<VideoContent>() {
      @Override
      public void onResponse(Call<VideoContent> call, Response<VideoContent> response) {
        VideoContent videoContent=response.body();
        int statusCode = response.code();
        liveList = new ArrayList();
        if(statusCode==200) {
          if (!response.body().getError()) {
            if (response.body().getData()!=null) {
              System.out.println("Suree body: " + response.body());
              liveList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(videoContent).getData()));
              adapter = new AllLiveClassAdapter(getActivity(), liveList);
              video_list_recycler.setAdapter(adapter);
              progressBarUtil.hideProgress();
              refresh_course.setRefreshing(false);
            }else {
              today_classes.setVisibility(View.VISIBLE);
              progressBarUtil.hideProgress();
              refresh_course.setRefreshing(false);
            }
          }else{
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Alert");
            alertDialogBuilder
                    .setMessage(response.body().getError_Message())
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog,int id) {
                        forceLogout();
                      }
                    });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            progressBarUtil.hideProgress();
          }
        }
        else{
          progressBarUtil.hideProgress();
          refresh_course.setRefreshing(false);
          System.out.println("Suree: response code"+response.message());
          Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<VideoContent> call, Throwable t) {
        Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error "+t.getMessage());
      }
    });
  }

  private void forceLogout() {
    SharedPrefManager.getInstance(getContext()).logout();
    startActivity(new Intent(getContext(), LoginActivity.class));
    Objects.requireNonNull(this).getActivity().finish();
  }
}
