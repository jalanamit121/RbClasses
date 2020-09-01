package com.winbee.rbclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.NewModels.CourseContent;
import com.winbee.rbclasses.NewModels.CourseContentArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllPurchasedCourseAdapter;
import com.winbee.rbclasses.model.CourseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyClassFragment extends Fragment {
  private ArrayList<CourseContentArray> courseModels;
  private RecyclerView course_recycle;
  private ProgressBarUtil progressBarUtil;
  private AllPurchasedCourseAdapter adapter;
  private RelativeLayout today_classes;
  String UserID,android_id;

  public MyClassFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_my_class, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil = new ProgressBarUtil(getContext());
    course_recycle = view.findViewById(R.id.all_course);
    UserID = SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
    android_id = Settings.Secure.getString(getContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);
    callCourseApiService();
  }

  private void forceLogout() {
    SharedPrefManager.getInstance(getContext()).logout();
    startActivity(new Intent(getContext(), LoginActivity.class));
    Objects.requireNonNull(this).getActivity().finish();
  }

  private void callCourseApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<CourseContent> call = apiCAll.getPurchasedCourse(1, UserID, "WB_009", "WB_009",android_id);
    call.enqueue(new Callback<CourseContent>() {
      @Override
      public void onResponse(Call<CourseContent> call, Response<CourseContent> response) {
        CourseContent courseContent =response.body();
        int statusCode = response.code();
        courseModels = new ArrayList();
        if (statusCode == 200) {
          if (response.body().getError()==false) {
            courseModels = new ArrayList<>(Arrays.asList(Objects.requireNonNull(courseContent).getData()));
            System.out.println("Suree body: " + response.body());
            adapter = new AllPurchasedCourseAdapter(getActivity(), courseModels);
            course_recycle.setAdapter(adapter);
            progressBarUtil.hideProgress();
          } else {
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
          }

        } else {
          System.out.println("Suree: response code" + response.message());
          Toast.makeText(getContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<CourseContent> call, Throwable t) {
        Toast.makeText(getContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error " + t.getMessage());
      }
    });


  }
}
