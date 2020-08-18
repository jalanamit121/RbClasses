package com.winbee.rbclasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllPurchasedCourseAdapter;
import com.winbee.rbclasses.adapter.AllTxnAdapter;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.TxnModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TxnFragment extends Fragment {
  private ArrayList<TxnModel> txnModels;
  private RecyclerView txn_recycle;
  private ProgressBarUtil progressBarUtil;
  private AllTxnAdapter adapter;
  String UserID;
  private RelativeLayout today_classes;
  public TxnFragment() {
    // Required empty public constructor
  }





  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_txn, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    progressBarUtil = new ProgressBarUtil(getContext());
    txn_recycle = view.findViewById(R.id.all_txn);
    today_classes = view.findViewById(R.id.today_classes);
    UserID = SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
    callCourseApiService();

  }
  private void callCourseApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<ArrayList<TxnModel>> call = apiCAll.getPaymentData(UserID, "WB_009");
    call.enqueue(new Callback<ArrayList<TxnModel>>() {
      @Override
      public void onResponse(Call<ArrayList<TxnModel>> call, Response<ArrayList<TxnModel>> response) {
        int statusCode = response.code();
        txnModels = new ArrayList();
        if (statusCode == 200) {
          if (response.body().size() != 0) {
            txnModels = response.body();
            System.out.println("Suree body: " + response.body());
            adapter = new AllTxnAdapter(getActivity(), txnModels);
            txn_recycle.setAdapter(adapter);
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
      public void onFailure(Call<ArrayList<TxnModel>> call, Throwable t) {
        Toast.makeText(getContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error " + t.getMessage());
      }
    });
  }
}
