package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.ResetPassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassWordActivity extends AppCompatActivity {

  EditText editTextOtp,editTextNewPassword,editTextRePassword;
  Button resetPassword;
  private ProgressBarUtil progressBarUtil;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_pass_word);

    editTextOtp = findViewById(R.id.editTextResetOtp);
    editTextNewPassword=findViewById(R.id.editTextNewPassword);
    editTextRePassword=findViewById(R.id.editTextRePassword);
    progressBarUtil = new ProgressBarUtil(this);
    resetPassword=findViewById(R.id.buttonReset);
    resetPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        userValidation();

      }
    });

  }
  private void userValidation() {
    final String otp = editTextOtp.getText().toString();
    final String password = editTextNewPassword.getText().toString();
    final String repassword = editTextRePassword.getText().toString();
    final String usernameVerify = SharedPrefManager.getInstance(this).refCode().getUsername();
    if (TextUtils.isEmpty(otp)) {
      editTextOtp.setError("Please enter otp");
      editTextOtp.requestFocus();
      return;
    }
    if (TextUtils.isEmpty(password)) {
      editTextNewPassword.setError("Please enter your password");
      editTextNewPassword.requestFocus();
      return;
    }
    if (TextUtils.isEmpty(repassword)) {
      editTextRePassword.setError("Please enter your password");
      editTextRePassword.requestFocus();
      return;
    }
    if (password.equals(repassword)) {

    }else{
      editTextRePassword.setError("Password are not matching");
      editTextRePassword.requestFocus();
      return;
    }


    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setUsername(usernameVerify);
    resetPassword.setOtp(otp);
    resetPassword.setNew_password(password);
    callResetPasswordApi(resetPassword);
  }
  private void callResetPasswordApi(final ResetPassword resetPassword){

    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<ResetPassword> call = mService.getResetPassword(2,resetPassword.getUsername(),resetPassword.getOtp(),resetPassword.getNew_password());
    call.enqueue(new Callback<ResetPassword>() {
      @Override
      public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
        int statusCode  = response.code();
        if(statusCode==200 && response.body().getSuccess()==true ) {
          progressBarUtil.hideProgress();
          Toast.makeText(getApplicationContext(),"Password Updated Successfully" , Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(ResetPassWordActivity.this, LoginActivity.class);
          startActivity(intent);

        }else {
          progressBarUtil.hideProgress();
          Toast.makeText(ResetPassWordActivity.this, "Incorrect Password ", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<ResetPassword> call, Throwable t) {
        Toast.makeText(ResetPassWordActivity.this,"Failed"+t.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(t.getLocalizedMessage());
      }
    });
  }
}
