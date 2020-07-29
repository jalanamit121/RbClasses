package com.winbee.rbclasses;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.NewDoubtQuestion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDoubtActivity extends AppCompatActivity {
    EditText editTextQuestionTitle,editTextQuestionDescription,editTextUserid,editTextDocumentId;
    private String CurrentUserName;
    private ProgressBarUtil progressBarUtil;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doubt);
        editTextQuestionTitle=findViewById(R.id.editTextQuestionTitle);
        editTextQuestionDescription=findViewById(R.id.editTextQuestionDescription);
        progressBarUtil   =  new ProgressBarUtil(this);
        submit=findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });

    }
    private void userValidation() {
        final String title = editTextQuestionTitle.getText().toString();
        final String description = editTextQuestionDescription.getText().toString();
        final String userid = SharedPrefManager.getInstance(this).refCode().getUserId();

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
                    editTextQuestionTitle.getText().clear();
                    editTextQuestionDescription.getText().clear();
//                    startActivity(new Intent(NewDoubtActivity.this, NewDoubtActivity.class));
//
//                    finish();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void submitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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
}
