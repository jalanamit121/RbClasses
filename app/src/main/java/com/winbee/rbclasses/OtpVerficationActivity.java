package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
  Button otpVerify;
  TextView mobile;
  EditText otp;
  private ProgressBarUtil progressBarUtil;
  private FirebaseAuth auth = FirebaseAuth.getInstance();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();


  private FirebaseAuth mAuth;
  private FirebaseUser currrentUser;
  private String credential;
  private OtpView otpView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_otp_verfication);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("message");
//        String Email = bundle.getString("email");
//        String Name = bundle.getString("name");
//        String Password = bundle.getString("password");
    //otp = findViewById(R.id.link_otp);
    otpView = findViewById(R.id.otp_view);
    otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
      @Override
      public void onOtpCompleted(String otp) {
        chechOtpIsCorrect(otp);
        FireBaseValidation();
      }
    });
    progressBarUtil = new ProgressBarUtil(this);
    otpVerify = findViewById(R.id.buttonOtp);
//        mobile = (TextView) findViewById(R.id.text_mobile3);
//        name = (TextView) findViewById(R.id.name);
//        email = (TextView) findViewById(R.id.email);
//        password = (TextView) findViewById(R.id.password);
//        mobile.setText(message);
//        name.setText(Name);
//        email.setText(Email);
//        password.setText(Password);
//

    // validate nhi hua

    mAuth = FirebaseAuth.getInstance();
    currrentUser = mAuth.getCurrentUser();

    credential = getIntent().getStringExtra("otp");


    otpVerify.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//                FireBaseValidation();
//                userValidation();



      }
    });

  }

  private void chechOtpIsCorrect(String otp) {


    if (!otp.isEmpty()) {
//            progressBarConfirmation.setVisibility(View.VISIBLE);
//            textCOnfirm.setVisibility(View.GONE);
//            cardConfirmationcode.setEnabled(false);
      progressBarUtil.showProgress();
      PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(credential, otp);

      signInWithPhoneAuthCredential(phoneAuthCredential);


    } else {
      Toast.makeText(this, "Enter Verification Code", Toast.LENGTH_SHORT).show();
    }


  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

    FireBaseValidation();

    // TODO: 7/9/20 add php sign up code here




  }

  private void userValidation() {
    final String otp1 = otp.getText().toString();
    final String mobile1 = mobile.getText().toString();

    if (TextUtils.isEmpty(otp1)) {
      otp.setError("Please enter your mobile no");
      otp.requestFocus();
      return;
    }

    OtpVerify otpVerify = new OtpVerify();
    otpVerify.setUsername(mobile1);
    otpVerify.setOtp(otp1);

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
//                    progressBarUtil.hideProgress();
//                    Toast.makeText(OtpVerficationActivity.this, "Registration Successful ", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(OtpVerficationActivity.this, LoginActivity.class));
//                    finish();
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

  public void FireBaseValidation() {
    final String Email = getIntent().getStringExtra("email");
    final String Name = getIntent().getStringExtra("username");
    final String Password = getIntent().getStringExtra("password");
    final String FireBaseMobile = getIntent().getStringExtra("phone");


    if (!Email.isEmpty() && !Password.isEmpty()) {
      auth.createUserWithEmailAndPassword(Email, Password)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {

                    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("Email", Email);
                    userInfo.put("Name", Name);
                    userInfo.put("Password", Password);
                    userInfo.put("FireBaseMobile", FireBaseMobile);
                    userInfo.put("userId", userId);
                    //sab sahi he boss


                    db.collection("Users")
                            .document(userId)
                            .set(userInfo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                  final String Email = getIntent().getStringExtra("email");
                                  final String Name = getIntent().getStringExtra("username");
                                  final String Password = getIntent().getStringExtra("password");
                                  final String FireBaseMobile = getIntent().getStringExtra("phone");
                                  final String referal_code = getIntent().getStringExtra("ref");


                                  RefUser refUser = new RefUser();
                                  refUser.setName(Name);
                                  refUser.setPassword(Password);
                                  refUser.setEmail(Email);
                                  refUser.setMobile(FireBaseMobile);
                                  refUser.setRefcode(referal_code);

                                  CallSignupApi(refUser);

//                                                    Toast.makeText(OtpVerficationActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
//                                                    finish();
//                                                    Intent intent = new Intent(OtpVerficationActivity.this, GroupChatActivity.class);
//                                                    startActivity(intent);
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

  private void CallSignupApi(final RefUser refUser) {
    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<RefUser> call = mService.refUserSignIn(2, refUser.getName(),refUser.getEmail(),refUser.getMobile(),refUser.getRefcode(), refUser.getPassword());
    call.enqueue(new Callback<RefUser>() {
      @Override
      public void onResponse(Call<RefUser> call, Response<RefUser> response) {
        int statusCode = response.code();

        Log.d("tagforregistration", "onResponse: "+statusCode+" "+response.body().getRegistration_id());
        //List<RefUser> list ;
        if (statusCode == 200 && response.body().getRegistration_id() != null) {
          progressBarUtil.hideProgress();
          Toast.makeText(OtpVerficationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
          Intent intent = new Intent(OtpVerficationActivity.this, LoginActivity.class);
          intent.putExtra("message",refUser.getMobile());
          intent.putExtra("email",refUser.getEmail());
          intent.putExtra("name",refUser.getName());
          intent.putExtra("password",refUser.getPassword());
          LocalData.UserName=refUser.getEmail();
          LocalData.Password=refUser.getPassword();
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        } else {
          progressBarUtil.hideProgress();
          Toast.makeText(OtpVerficationActivity.this, "User Already exist", Toast.LENGTH_LONG).show();
        }
      }


      @Override
      public void onFailure(Call<RefUser> call, Throwable t) {
        progressBarUtil.hideProgress();
        Toast.makeText(OtpVerficationActivity.this,"Failed", Toast.LENGTH_LONG).show();
      }
    });
  }
}
