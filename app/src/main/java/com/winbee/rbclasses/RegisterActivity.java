package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.balsikandar.crashreporter.CrashReporter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

  EditText editTextname, editTextEmail, editTextPassword, editTextPhone, editTextDob, editTextRePassword;
  TextView editTextReferalCode;
  Button register;
  private ProgressBarUtil progressBarUtil;

  private FirebaseAuth mAuth;
  private FirebaseUser currrentUser;
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
  private String username;
  private String email;
  private String password;
  private String phone;
  private String re_password;
  private String referal_code;
  private FirebaseAuth auth = FirebaseAuth.getInstance();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    try {
      initView();
    } catch (Exception e) {
      CrashReporter.logException(e);
    }


  }

  private void initView() {
    progressBarUtil = new ProgressBarUtil(this);
    editTextname = findViewById(R.id.editTextUsername);
    editTextEmail = findViewById(R.id.editTextEmail);
    editTextPassword = findViewById(R.id.editTextPassword);
    editTextPhone = findViewById(R.id.editTextPhone);
    editTextRePassword = findViewById(R.id.editTextre_Password);
    editTextReferalCode = findViewById(R.id.editTextreferal_code);

    mAuth = FirebaseAuth.getInstance();
    currrentUser = mAuth.getCurrentUser();

    register = (Button) findViewById(R.id.buttonRegister);
    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        userValidation();
      }
    });

    findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
      }
    });


    mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      @Override
      public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        //  signInWithPhoneAuthCredential(phoneAuthCredential);
      }

      @Override
      public void onVerificationFailed(FirebaseException e) {
        Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

        Log.d("TAGGG", "onVerificationFailed: " + e.getMessage());

      }

      @Override
      public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
//                progressBarUsernameandPassword.setVisibility(View.GONE);
//                cardUserNameAndPassword.setEnabled(true);
//    /            textViewUandP.setVisibility(View.VISIBLE);


        new android.os.Handler().postDelayed(
                new Runnable() {
                  @Override
                  public void run() {
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(RegisterActivity.this, OtpVerficationActivity.class);
                    intent.putExtra("otp", s);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("ref", referal_code);
                    intent.putExtra("username", username);
                    Toast.makeText(RegisterActivity.this, "otp sent successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                  }
                }, 5000
        );


      }
    };

  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

    FireBaseValidation();
    RefUser refUser = new RefUser();
    refUser.setName(username);
    refUser.setPassword(password);
    refUser.setEmail(email);
    refUser.setMobile(phone);
    refUser.setRefcode(referal_code);

    CallSignupApi(refUser);
    // TODO: 7/9/20 add php sign up code here


  }

  private void userValidation() {
    username = editTextname.getText().toString().trim();
    email = editTextEmail.getText().toString().trim();
    password = editTextPassword.getText().toString().trim();
    re_password = editTextRePassword.getText().toString().trim();
    referal_code = editTextReferalCode.getText().toString().trim();
    phone = editTextPhone.getText().toString().trim();

    if (TextUtils.isEmpty(username)) {
      editTextname.setError("Please enter username");
      editTextname.requestFocus();
      return;
    }

    if (TextUtils.isEmpty(phone)) {
      editTextPhone.setError("Please enter your mobile number");
      editTextPhone.requestFocus();
      return;
    }


    if (TextUtils.isEmpty(password)) {
      editTextPassword.setError("Enter a password");
      editTextPassword.requestFocus();
      return;
    }
    if (TextUtils.isEmpty(re_password)) {
      editTextRePassword.setError("Enter a password");
      editTextRePassword.requestFocus();
      return;
    }

    if (password.equals(re_password)) {

    } else {
      editTextRePassword.setError("Password are not matching");
      editTextRePassword.requestFocus();
      return;
    }
    RefUser refUser = new RefUser();
    refUser.setName(username);
    refUser.setPassword(password);
    refUser.setEmail(email);
    refUser.setMobile(phone);
    refUser.setRefcode(referal_code);


    //  CallSignupApi(refUser);
    verificationThroughFirebase(phone, username, password, email, referal_code);


  }

  private void verificationThroughFirebase(String phone, String username, String password, String email, String referal_code) {


    if (phone.length() == 10) {
      String phoneNumber = "+91" + " " + phone;
      //Todo add progress bar here
      progressBarUtil.showProgress();
      PhoneAuthProvider.getInstance().verifyPhoneNumber(
              phoneNumber,
              60,
              TimeUnit.SECONDS,
              RegisterActivity.this,
              mcallback
      );


    } else {
      //todo add warnign here
      Toast.makeText(RegisterActivity.this, "Enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
    }
  }


  private void CallSignupApi(final RefUser refUser) {
    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<RefUser> call = mService.refUserSignIn(2, refUser.getName(), refUser.getEmail(), refUser.getMobile(), refUser.getRefcode(), refUser.getPassword());
    call.enqueue(new Callback<RefUser>() {
      @Override
      public void onResponse(Call<RefUser> call, Response<RefUser> response) {
        int statusCode = response.code();
        //List<RefUser> list ;
        if (statusCode == 200 && response.body().getRegistration_id() != null) {
          progressBarUtil.hideProgress();
          Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
          intent.putExtra("message", refUser.getMobile());
          intent.putExtra("email", refUser.getEmail());
          intent.putExtra("name", refUser.getName());
          intent.putExtra("password", refUser.getPassword());
          LocalData.UserName = refUser.getEmail();
          LocalData.Password = refUser.getPassword();
          startActivity(intent);
        } else {
          progressBarUtil.hideProgress();
          Toast.makeText(RegisterActivity.this, "User Already exist", Toast.LENGTH_LONG).show();
        }
      }


      @Override
      public void onFailure(Call<RefUser> call, Throwable t) {
        progressBarUtil.hideProgress();
        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
      }
    });
  }

  public void FireBaseValidation() {


    if (!email.isEmpty() && !password.isEmpty()) {
      auth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {

                    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("Email", email);
                    userInfo.put("Name", username);
                    userInfo.put("Password", password);
                    userInfo.put("FireBaseMobile", phone);
                    userInfo.put("userId", userId);


                    db.collection("Users")
                            .document(userId)
                            .set(userInfo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//
                                  Log.d("TAG", "onComplete: Login successfully and data uploaded");

                                } else {
                                  // Toast.makeText(OtpVerficationActivity.this, "not successful", Toast.LENGTH_SHORT).show();
                                  Log.d("TAG", "onComplete: Login failed data failed");
                                }
                              }
                            });


                  }

                }

              });

    } else {
      Toast.makeText(this, "Fields can't be empty!!", Toast.LENGTH_SHORT).show();
    }


  }
}