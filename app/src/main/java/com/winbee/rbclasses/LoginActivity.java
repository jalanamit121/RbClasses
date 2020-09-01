package com.winbee.rbclasses;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefCode;
import com.winbee.rbclasses.model.ResendOtp;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    private long backPressedTime;
    private ProgressBarUtil progressBarUtil;
    LinearLayout forgetPassword;
    String android_id;
    String UserMobile,UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        initViews();
    }

    private void initViews() {
        progressBarUtil = new ProgressBarUtil(this);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        forgetPassword = (LinearLayout) findViewById(R.id.link_forget_password);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });


        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void userValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            editTextUsername.setError("Please enter your mobile no");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        RefCode refCode = new RefCode();
        refCode.setUsername(mobile);
        refCode.setPassword(password);
        callRefCodeSignInApi(refCode);


    }

    private void loginValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            editTextUsername.setError("Please enter your mobile no");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        RefCode refCode = new RefCode();
        refCode.setUsername(mobile);
        refCode.setPassword(password);
        ForceLogout(refCode);


    }



    private void callRefCodeSignInApi(final RefCode refCode) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(1, refCode.getUsername(), refCode.getPassword(),"RBC001",android_id);
        Log.i("tag", "callRefCodeSignInApi: "+android_id);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    progressBarUtil.hideProgress();
                    Constants.CurrentUser = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userid", refCode.getUserId());
                    intent.putExtra("username", response.body().getEmail());
                    intent.putExtra("password", response.body().getPassword());
                    LocalData.Password=response.body().getPassword();
                    LocalData.Email=response.body().getEmail();

                    startActivity(intent);
                    finish();
                } else {
                    if (response.body().getError_code()==1){// wrong password
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        txt_login.setVisibility(View.GONE);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }else if (response.body().getError_code()==2){//already login
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        txt_login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loginValidation();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else if (response.body().getError_code()==3){// verify pending
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        txt_login.setVisibility(View.GONE);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        txt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        txt_login_verify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callResendOtp();
                                dialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this,OtpVerficationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.show();

                    }else if (response.body().getError_code()==4){// technical issue
                        progressBarUtil.hideProgress();
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert);
                        TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                        TextView txt_login=dialog.findViewById(R.id.txt_login);
                        TextView txt_login_verify=dialog.findViewById(R.id.txt_login_verify);
                        txt_cancel.setVisibility(View.GONE);
                        txt_login.setVisibility(View.GONE);
                        txt_login_verify.setVisibility(View.GONE);
                        TextView txt_choose=dialog.findViewById(R.id.txt_choose);
                        txt_choose.setText(response.body().getMessageFailure());
                        dialog.show();

                    }

                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime+2000> System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(getBaseContext(),"Press Exit again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime= System.currentTimeMillis();
    }


    private void callResendOtp() {
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ResendOtp> call = mService.getResendOtp( LocalData.Mobile,1);
        call.enqueue(new Callback<ResendOtp>() {
            @Override
            public void onResponse(Call<ResendOtp> call, Response<ResendOtp> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getSuccess() == true) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResendOtp> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
    private void ForceLogout( final  RefCode refCode) {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeForceLogout(4,refCode.getUsername(),refCode.getPassword(), "RBC001",android_id);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    progressBarUtil.hideProgress();
                    Constants.CurrentUser = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userid", refCode.getUserId());
                    intent.putExtra("username", response.body().getEmail());
                    intent.putExtra("password", response.body().getPassword());
                    LocalData.Password=response.body().getPassword();
                    LocalData.Email=response.body().getEmail();

                    startActivity(intent);
                    finish();

                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(LoginActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}