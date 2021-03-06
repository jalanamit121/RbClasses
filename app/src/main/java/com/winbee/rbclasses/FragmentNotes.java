package com.winbee.rbclasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllLiveClassAdapter;
import com.winbee.rbclasses.adapter.AllNotesAdapter;
import com.winbee.rbclasses.model.CourseContentModel;
import com.winbee.rbclasses.model.CourseContentPdfModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotes extends Fragment {
  private ArrayList<CourseContentPdfModel> liveList;
  private RecyclerView video_list_recycler;
  private ProgressBarUtil progressBarUtil;
  private AllNotesAdapter adapter;
  RelativeLayout today_classes;
  private SwipeRefreshLayout refresh_course;
  private ImageView img_share,WebsiteHome;
  String UserID;



  public FragmentNotes() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_notes, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil   =  new ProgressBarUtil(getContext());
    video_list_recycler =view.findViewById(R.id.all_notesClasses);
    today_classes =view.findViewById(R.id.today_classes);
    UserID=SharedPrefManager.getInstance(getContext()).refCode().getUserId();
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
    Call<ArrayList<CourseContentPdfModel>> call = apiCAll.getPurchasedCoursePdfContent(4,UserID,"WB_009",LocalData.ChildId);
    call.enqueue(new Callback<ArrayList<CourseContentPdfModel>>() {
      @Override
      public void onResponse(Call<ArrayList<CourseContentPdfModel>> call, Response<ArrayList<CourseContentPdfModel>> response) {
        int statusCode = response.code();
        liveList = new ArrayList();
        if(statusCode==200) {
          if (response.body().size() != 0) {
            System.out.println("Suree body: " + response.body());
            liveList = response.body();
            adapter = new AllNotesAdapter(getActivity(), liveList);
            video_list_recycler.setAdapter(adapter);
            progressBarUtil.hideProgress();
            refresh_course.setRefreshing(false);
          }else{
            today_classes.setVisibility(View.VISIBLE);
            progressBarUtil.hideProgress();
            refresh_course.setRefreshing(false);
          }
        }
        else{
          System.out.println("Suree: response code"+response.message());
          Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ArrayList<CourseContentPdfModel>> call, Throwable t) {
        Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error "+t.getMessage());
      }
    });
  }


}
