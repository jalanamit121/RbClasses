package com.winbee.rbclasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.OtpVerify;
import com.winbee.rbclasses.model.RefUser;
import com.winbee.rbclasses.model.ResendOtp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
  TextView text_mobile2,resend_otp;
  private ProgressBarUtil progressBarUtil;
  private FirebaseAuth auth = FirebaseAuth.getInstance();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();


  private FirebaseAuth mAuth;
  private FirebaseUser currrentUser;
  private String credential;
  private OtpView otpView;
  private Button buttonLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_otp_verfication);
    otpView = findViewById(R.id.otp_view);
    text_mobile2 = findViewById(R.id.text_mobile2);
    buttonLogin = findViewById(R.id.buttonLogin);
    buttonLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(OtpVerficationActivity.this, "Please Enter Otp", Toast.LENGTH_SHORT).show();
      }
    });
    resend_otp = findViewById(R.id.resend_otp);
    resend_otp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        callResendOtp();
      }
    });
    text_mobile2.setText(LocalData.Mobile);
    otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
      @Override
      public void onOtpCompleted(String otp) {
        Log.i("tag", "onOtpCompleted: "+otp);
        LocalData.Otp=otp;
        chechOtpIsCorrect(otp);
//        FireBaseValidation();
      }
    });
    progressBarUtil = new ProgressBarUtil(this);

    mAuth = FirebaseAuth.getInstance();
    currrentUser = mAuth.getCurrentUser();

    credential = getIntent().getStringExtra("otp");


  }

  private void chechOtpIsCorrect(String otp) {


    if (!otp.isEmpty()) {
      progressBarUtil.showProgress();
      userValidation();
    } else {
      Toast.makeText(this, "Enter Verification Code", Toast.LENGTH_SHORT).show();
    }
  }

  private void userValidation() {
    OtpVerify otpVerify = new OtpVerify();
    otpVerify.setUsername(LocalData.Mobile);
    otpVerify.setOtp(LocalData.Otp);
    callOtpVerifySignInApi(otpVerify);
  }

  private void callOtpVerifySignInApi(final OtpVerify otpVerify) {

    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<OtpVerify> call = mService.getOtpVerify(3, otpVerify.getUsername(), otpVerify.getOtp());
    call.enqueue(new Callback<OtpVerify>() {
      @Override
      public void onResponse(Call<OtpVerify> call, Response<OtpVerify> response) {
        int statusCode = response.code();
        if (statusCode == 200 && response.body().getSuccess() == true) {
          Intent intent =new Intent(OtpVerficationActivity.this,LoginActivity.class);
          startActivity(intent);
          finish();
        } else {
          progressBarUtil.hideProgress();
          Toast.makeText(OtpVerficationActivity.this, "Invalid UserName Password ", Toast.LENGTH_LONG).show();
        }


      }

      @Override
      public void onFailure(Call<OtpVerify> call, Throwable t) {
        Toast.makeText(OtpVerficationActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(t.getLocalizedMessage());
      }
    });
  }

  private void callResendOtp() {
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<ResendOtp> call = mService.getResendOtp( LocalData.Mobile,1);
    call.enqueue(new Callback<ResendOtp>() {
      @Override
      public void onResponse(Call<ResendOtp> call, Response<ResendOtp> response) {
        int statusCode = response.code();
        if (statusCode == 200 && response.body().getSuccess() == true) {
          Toast.makeText(OtpVerficationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(OtpVerficationActivity.this, "Invalid UserName Password ", Toast.LENGTH_LONG).show();
        }


      }

      @Override
      public void onFailure(Call<ResendOtp> call, Throwable t) {
        Toast.makeText(OtpVerficationActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(t.getLocalizedMessage());
      }
    });
  }



  @Override
  public void onBackPressed () {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("RB Classes");
    builder.setMessage("Wait Still Your Account get Verified ");
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
