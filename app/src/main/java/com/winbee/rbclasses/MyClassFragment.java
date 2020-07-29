package com.winbee.rbclasses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllPurchasedCourseAdapter;
import com.winbee.rbclasses.model.CourseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyClassFragment extends Fragment {
    private ArrayList<CourseModel> courseModels;
    private RecyclerView course_recycle;
    private ProgressBarUtil progressBarUtil;
    private AllPurchasedCourseAdapter adapter;
    private RelativeLayout today_classes;
    String UserID;

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
        callCourseApiService();
    }

    private void callCourseApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<CourseModel>> call = apiCAll.getPurchasedCourse(1, UserID, "WB_009", "WB_009");
        call.enqueue(new Callback<ArrayList<CourseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CourseModel>> call, Response<ArrayList<CourseModel>> response) {
                int statusCode = response.code();
                courseModels = new ArrayList();
                if (statusCode == 200) {
                    if (response.body().size() != 0) {
                        courseModels = response.body();
                        System.out.println("Suree body: " + response.body());
                        adapter = new AllPurchasedCourseAdapter(getActivity(), courseModels);
                        course_recycle.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }

                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CourseModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}
