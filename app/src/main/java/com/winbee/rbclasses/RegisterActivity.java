package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.balsikandar.crashreporter.CrashReporter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

  EditText editTextname, editTextEmail, editTextPassword, editTextPhone, editTextRePassword;
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
  private FirebaseAuth auth = FirebaseAuth.getInstance();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_register);
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


  }
  public void ShowHidePass(View view){

    if(view.getId()==R.id.show_pass_btn){

      if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
        ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

        //Show Password
        editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
      }
      else{
        ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

        //Hide Password
        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

      }
    }
  }
  public void ReShowHidePass(View view){

    if(view.getId()==R.id.show_repass_btn){

      if(editTextRePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
        (( ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

        //Show Password
        editTextRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
      }
      else{
        ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

        //Hide Password
        editTextRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

      }
    }
  }


  private void userValidation() {
    username = editTextname.getText().toString().trim();
    email = editTextEmail.getText().toString().trim();
    password = editTextPassword.getText().toString().trim();
    re_password = editTextRePassword.getText().toString().trim();
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

     CallSignupApi(refUser);


  }
  private void CallSignupApi(final RefUser refUser) {
    progressBarUtil.showProgress();
    ClientApi mService = ApiClient.getClient().create(ClientApi.class);
    Call<RefUser> call = mService.refUserSignIn(2, refUser.getName(), refUser.getEmail(), refUser.getMobile(), "RBC001", refUser.getPassword());
    Log.i("tag", "CallSignupApi: "+refUser.getName()+ refUser.getEmail()+ refUser.getMobile()+refUser.getRefcode()+refUser.getPassword());
    call.enqueue(new Callback<RefUser>() {
      @Override
      public void onResponse(Call<RefUser> call, Response<RefUser> response) {
        int statusCode = response.code();
        if (statusCode == 200 && response.body().getSuccess() == true) {
          Log.i("tag", "onResponse: "+response.body().getSuccess());
          LocalData.UserName = refUser.getEmail();
          LocalData.Mobile=refUser.getMobile();
          LocalData.Password = refUser.getPassword();
          progressBarUtil.hideProgress();
          Intent intent = new Intent(RegisterActivity.this,OtpVerficationActivity.class);
          startActivity(intent);
          finish();

        } else {
          progressBarUtil.hideProgress();
          Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
        }
      }


      @Override
      public void onFailure(Call<RefUser> call, Throwable t) {
        progressBarUtil.hideProgress();
        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
      }
    });
  }

}