package com.winbee.rbclasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.NewModels.CourseContent;
import com.winbee.rbclasses.NewModels.CourseContentArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllCourseAdapter;
import com.winbee.rbclasses.adapter.AllLiveClassAdapter;
import com.winbee.rbclasses.model.CourseDatum;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.LiveClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {
  private ArrayList<CourseContentArray> courseModels;
  private RecyclerView course_recycle;
  private ProgressBarUtil progressBarUtil;
  private AllCourseAdapter adapter;
  private SwipeRefreshLayout refresh_course;
  String UserID,android_id;

  public CourseFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_course, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil   =  new ProgressBarUtil(getContext());
    course_recycle =view.findViewById(R.id.all_course);
    refresh_course =view.findViewById(R.id.refresh_course);
    refresh_course.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        callCourseApiService();
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            refresh_course.setRefreshing(false);
          }
        },4000);
      }
    });
    UserID = SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
    android_id = Settings.Secure.getString(getContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);
    callCourseApiService();
    // runLayoutAnimation(course_recycle);


  }

  private void callCourseApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<CourseContent> call = apiCAll.getPurchasedCourse(1,UserID,"WB_009","WB_009",android_id);
    call.enqueue(new Callback<CourseContent>() {
      @Override
      public void onResponse(Call<CourseContent> call, Response<CourseContent> response) {
        CourseContent courseContent=response.body();
        int statusCode = response.code();
        courseModels = new ArrayList();
        if(statusCode==200) {
          courseModels=new ArrayList<>(Arrays.asList(Objects.requireNonNull(courseContent).getData()));
          System.out.println("Suree body: " + response.body());
          adapter = new AllCourseAdapter(getActivity(), courseModels);
          course_recycle.setAdapter(adapter);
          progressBarUtil.hideProgress();

        }
        else{
          System.out.println("Suree: response code"+response.message());
          Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<CourseContent> call, Throwable t) {
        Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error "+t.getMessage());
      }
    });
  }

}
