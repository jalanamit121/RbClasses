package com.winbee.rbclasses;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AskDoubtAdapter;
import com.winbee.rbclasses.model.AskDoubtQuestion;
import com.winbee.rbclasses.model.NewDoubtQuestion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment {
    EditText editTextQuestionTitle,editTextQuestionDescription;
    private ProgressBarUtil progressBarUtil;
    Button submit;
    private ImageView WebsiteHome,img_share;
    private Button btn_asked;
    private AskDoubtAdapter adapter;
    private ArrayList<AskDoubtQuestion> list;
    private RecyclerView askedQuestion;


    public AskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editTextQuestionTitle=view.findViewById(R.id.editTextQuestionTitleFragment);
        editTextQuestionDescription=view.findViewById(R.id.editTextQuestionDescriptionFragment);
        progressBarUtil   =  new ProgressBarUtil(getContext());
        WebsiteHome=view.findViewById(R.id.WebsiteHome);
        img_share=view.findViewById(R.id.img_share);
        btn_asked=view.findViewById(R.id.btn_asked);
        askedQuestion = view.findViewById(R.id.gec_asked_question_recycle);
        submit=view.findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
        callAskedQuestionApiService();
    }
    private void userValidation() {
        final String title = editTextQuestionTitle.getText().toString();
        final String description = editTextQuestionDescription.getText().toString();
        final String userid = SharedPrefManager.getInstance(getContext()).refCode().getUserId();

        if (TextUtils.isEmpty(title)) {
            editTextQuestionTitle.setError("Please enter title");
            editTextQuestionTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            editTextQuestionDescription.setError("Please enter description");
            editTextQuestionDescription.requestFocus();
            return;
        }

        NewDoubtQuestion newDoubtQuestion = new NewDoubtQuestion();
        newDoubtQuestion.setTitle(title);
        newDoubtQuestion.setQuestion(description);
        newDoubtQuestion.setUserId(userid);




        callNewAskedQuestionApiService(newDoubtQuestion);

    }

    private void callNewAskedQuestionApiService(NewDoubtQuestion newDoubtQuestion){
        progressBarUtil.showProgress();
        // final UrlNewQuestion urlNewQuestion = new UrlNewQuestion(title,description,documentid,userid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<NewDoubtQuestion> call =apiCall.getNewQuestion(newDoubtQuestion.getTitle(),newDoubtQuestion.getQuestion(),newDoubtQuestion.getUserId());
        Log.d("TAG", "callNewAskedQuestionApiService: "+newDoubtQuestion.getTitle()+""+newDoubtQuestion.getQuestion()+""+newDoubtQuestion.getUserId());
        call.enqueue(new Callback<NewDoubtQuestion>() {
            @Override
            public void onResponse(Call<NewDoubtQuestion> call, Response<NewDoubtQuestion> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    progressBarUtil.hideProgress();
                    submitAlertDialog();
                    AskFragment mcqFragment = new AskFragment();
                    if (getActivity()!=null){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerDisscussion,mcqFragment,"AskFragment");
                        ft.commit();}else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
                    editTextQuestionTitle.getText().clear();
                    editTextQuestionDescription.getText().clear();
//                    startActivity(new Intent(NewDoubtActivity.this, NewDoubtActivity.class));
//
//                    finish();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void submitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //Set title
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Your Doubt Has Been Submitted");
        builder.setCancelable(false);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }

    private void callAskedQuestionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<AskDoubtQuestion>> call =apiCall.getQuestion();
        call.enqueue(new Callback<ArrayList<AskDoubtQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<AskDoubtQuestion>> call, Response<ArrayList<AskDoubtQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AskDoubtAdapter(getActivity(),list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AskDoubtQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }


}
