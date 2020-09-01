package com.winbee.rbclasses;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.google.firebase.firestore.auth.User;
import com.winbee.rbclasses.NewModels.AskDoubt;
import com.winbee.rbclasses.NewModels.AskDoubtArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AskDoubtAdapter;
import com.winbee.rbclasses.model.AskDoubtQuestion;
import com.winbee.rbclasses.model.NewDoubtQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment {
    EditText editTextQuestionTitle,editTextQuestionDescription;
    private ProgressBarUtil progressBarUtil;
    Button submit;
    private ImageView WebsiteHome,img_share;
    private AskDoubtAdapter adapter;
    private ArrayList<AskDoubtArray> list;
    private RecyclerView askedQuestion;
    String User_id,android_id;


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
        User_id = SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        WebsiteHome=view.findViewById(R.id.WebsiteHome);
        img_share=view.findViewById(R.id.img_share);
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
        Call<AskDoubt> call =apiCall.getQuestion(User_id,android_id);
        call.enqueue(new Callback<AskDoubt>() {
            @Override
            public void onResponse(Call<AskDoubt> call, Response<AskDoubt> response) {
                AskDoubt askDoubt=response.body();

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){

                    if (response.body().getError()==false){
                        System.out.println("Suree body: "+response.body());
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(askDoubt).getData()));
                        adapter = new AskDoubtAdapter(getActivity(),list);
                        askedQuestion.setAdapter(adapter);
                        progressBarUtil.hideProgress();
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
                    }

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AskDoubt> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(getContext()).logout();
        startActivity(new Intent(getContext(), LoginActivity.class));
        Objects.requireNonNull(this).getActivity().finish();
    }

}
