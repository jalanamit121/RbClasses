package com.winbee.rbclasses;


import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.Utils.SpinnerAdapter;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.McqAskedQuestion;
import com.winbee.rbclasses.model.McqAskedQuestionModel;
import com.winbee.rbclasses.model.McqQuestionModel;
import com.winbee.rbclasses.model.QuetionDatum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class McqFragment extends Fragment implements McqAskedQuestion.OnSolutionClicked {
    LinearLayout edit_solution;
    EditText editTextTitle,editTextQuestion,editTextOtion1,editTextOtion2,editTextOtion3,editTextOtion4,editTextSolution;
    Button buttonPostMcq,buttonPostMcq1;
    String UserID;
    private ProgressBarUtil progressBarUtil;
    RelativeLayout radioButtonYes,radioButtonNo;
    private RecyclerView mcq_recycycler;
    private List<QuetionDatum> list;
    private McqAskedQuestion adapter;
    private Spinner select_option;
    SwipeRefreshLayout fragment_mcq;



    public McqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_mcq, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        progressBarUtil = new ProgressBarUtil(getContext());
        mcq_recycycler = getActivity().findViewById(R.id.gec_asked_question_recycle);
        UserID = SharedPrefManager.getInstance(getActivity()).refCode().getUserId();
        editTextTitle = getActivity().findViewById(R.id.editTextTitle);
        editTextQuestion = getActivity().findViewById(R.id.editTextQuestion);
        editTextOtion1 = getActivity().findViewById(R.id.editTextOtion1);
        editTextOtion2 = getActivity().findViewById(R.id.editTextOtion2);
        editTextOtion3 = getActivity().findViewById(R.id.editTextOtion3);
        editTextOtion4 = getActivity().findViewById(R.id.editTextOtion4);
        editTextSolution = getActivity().findViewById(R.id.editTextSolution);
        edit_solution = getActivity().findViewById(R.id.edit_solution);
        select_option = getActivity().findViewById(R.id.select_option);
        radioButtonYes = getActivity().findViewById(R.id.radioButtonYes);
        radioButtonNo = getActivity().findViewById(R.id.radioButtonNo);
        buttonPostMcq=getActivity().findViewById(R.id.buttonPostMcq);
        buttonPostMcq1=getActivity().findViewById(R.id.buttonPostMcq1);
        fragment_mcq=getActivity().findViewById(R.id.fragment_mcq);
        fragment_mcq.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiService();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragment_mcq.setRefreshing(false);
                    }
                },4000);
            }
        });
        radioButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_solution.setVisibility(View.VISIBLE);
                buttonPostMcq.setVisibility(View.VISIBLE);
                buttonPostMcq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation();
                    }
                });

            }
        });
        radioButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_solution.setVisibility(View.GONE);
                buttonPostMcq1.setVisibility(View.VISIBLE);
                buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation1();
                    }
                });

            }
        });
        callApiService();
        String[] titleArray = getResources ( ).getStringArray ( R.array.option );
        SpinnerAdapter adapter = new SpinnerAdapter( getActivity() , titleArray );
        //adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        select_option.setAdapter ( adapter );
        String valToSet = select_option.getSelectedItem().toString();
        System.out.println("Suree: "+valToSet);

    }

    private void userValidation() {
        final String title = editTextTitle.getText().toString().trim();
        final String question = editTextQuestion.getText().toString().trim();
        final String option1 = editTextOtion1.getText().toString().trim();
        final String option2 = editTextOtion2.getText().toString().trim();
        final String option3 = editTextOtion3.getText().toString().trim();
        final String option4 = editTextOtion4.getText().toString().trim();
        final Object solution = select_option.getSelectedItem();

        if (TextUtils.isEmpty(title)) {
            editTextTitle.setError("Please enter title");
            editTextTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(question)) {
            editTextQuestion.setError("Please enter question");
            editTextQuestion.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(option1)) {
            editTextOtion1.setError("Enter option");
            editTextOtion1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option2)) {
            editTextOtion2.setError("Enter option");
            editTextOtion2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option3)) {
            editTextOtion3.setError("Enter option");
            editTextOtion3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option4)) {
            editTextOtion4.setError("Enter option");
            editTextOtion4.requestFocus();
            return;
        }


        McqQuestionModel mcqQuestionModel;
        mcqQuestionModel = new McqQuestionModel();
        mcqQuestionModel.setQuestionTitle(title);
        mcqQuestionModel.setQuestion(question);
        mcqQuestionModel.setOpt1(option1);
        mcqQuestionModel.setOpt2(option2);
        mcqQuestionModel.setOpt3(option3);
        mcqQuestionModel.setOpt4(option4);
        mcqQuestionModel.setSolution(String.valueOf(solution));


        CallSignupApi(mcqQuestionModel);
    }
    private void CallSignupApi(final McqQuestionModel mcqQuestionModel) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<McqQuestionModel> call = mService.mcqQuestionYes(UserID, mcqQuestionModel.getQuestionTitle(),mcqQuestionModel.getQuestion(),mcqQuestionModel.getOpt1(),
                mcqQuestionModel.getOpt2(), mcqQuestionModel.getOpt3(),mcqQuestionModel.getOpt4(),1,1,mcqQuestionModel.getSolution());
        call.enqueue(new Callback<McqQuestionModel>() {
            @Override
            public void onResponse(Call<McqQuestionModel> call, Response<McqQuestionModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getResponse() == true) {
                    progressBarUtil.hideProgress();
//                    Intent intent = new Intent(String.valueOf(getActivity()));
//                    startActivity(intent);
                    McqFragment mcqFragment = new McqFragment();
                    if (getActivity()!=null){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerDisscussion,mcqFragment,"McqFragment");
                        ft.commit();}else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
                    Toast.makeText(getContext(), "Query Submitted ", Toast.LENGTH_SHORT).show();
                    //callApiService();
                } else {
                    progressBarUtil.hideProgress();
                }
            }


            @Override
            public void onFailure(Call<McqQuestionModel> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(getActivity(),"Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void userValidation1() {
        final String title = editTextTitle.getText().toString().trim();
        final String question = editTextQuestion.getText().toString().trim();
        final String option1 = editTextOtion1.getText().toString().trim();
        final String option2 = editTextOtion2.getText().toString().trim();
        final String option3 = editTextOtion3.getText().toString().trim();
        final String option4 = editTextOtion4.getText().toString().trim();
        // final String solution = editTextSolution.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            editTextTitle.setError("Please enter title");
            editTextTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(question)) {
            editTextQuestion.setError("Please enter question");
            editTextQuestion.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(option1)) {
            editTextOtion1.setError("Enter option");
            editTextOtion1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option2)) {
            editTextOtion2.setError("Enter option");
            editTextOtion2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option3)) {
            editTextOtion3.setError("Enter option");
            editTextOtion3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(option4)) {
            editTextOtion4.setError("Enter option");
            editTextOtion4.requestFocus();
            return;
        }


        McqQuestionModel mcqQuestionModel1;
        mcqQuestionModel1 = new McqQuestionModel();
        mcqQuestionModel1.setQuestionTitle(title);
        mcqQuestionModel1.setQuestion(question);
        mcqQuestionModel1.setOpt1(option1);
        mcqQuestionModel1.setOpt2(option2);
        mcqQuestionModel1.setOpt3(option3);
        mcqQuestionModel1.setOpt4(option4);


        CallNoApi(mcqQuestionModel1);
    }
    private void CallNoApi(final McqQuestionModel mcqQuestionModel1) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<McqQuestionModel> call = mService.mcqQuestionNo(UserID, mcqQuestionModel1.getQuestionTitle(), mcqQuestionModel1.getQuestion(), mcqQuestionModel1.getOpt1(),
                mcqQuestionModel1.getOpt2(), mcqQuestionModel1.getOpt3(), mcqQuestionModel1.getOpt4(), 1, 0, "N.A.");
        call.enqueue(new Callback<McqQuestionModel>() {
            @Override
            public void onResponse(Call<McqQuestionModel> call, Response<McqQuestionModel> response) {
                int statusCode = response.code();
                //List<RefUser> list ;
                if (statusCode == 200 && response.body().getResponse() == true) {
                    progressBarUtil.hideProgress();
//                    Intent intent = new Intent(String.valueOf(getActivity()));
//                    startActivity(intent);
                    McqFragment mcqFragment = new McqFragment();
                    if (getActivity()!=null){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerDisscussion,mcqFragment,"McqFragment");
                        ft.commit();}else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
                    Toast.makeText(getContext(), "Query Submitted", Toast.LENGTH_SHORT).show();
                    callApiService();
                } else {
                    progressBarUtil.hideProgress();
                    // Toast.makeText(getActivity(), "User Already exist", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<McqQuestionModel> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<McqAskedQuestionModel> call = apiCAll.mcqAskedQuestion(UserID,3);
        call.enqueue(new Callback<McqAskedQuestionModel>() {
            @Override
            public void onResponse(Call<McqAskedQuestionModel> call, Response<McqAskedQuestionModel> response) {
                McqAskedQuestionModel purchasedMainModel=response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200) {
                    if (response.body().getResponse() == true) {
                        //courses.setVisibility(View.VISIBLE);
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(purchasedMainModel).getData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new McqAskedQuestion(getActivity(), list,McqFragment.this);
                        mcq_recycycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        //nocourse.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getActivity(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<McqAskedQuestionModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    @Override
    public void onsolutionclicked() {
        McqFragment mcqFragment = new McqFragment();
        if (getActivity()!=null){
            FragmentTransaction ft = getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerDisscussion,mcqFragment,"McqFragment");
            ft.commit();}else{
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
    }
}

