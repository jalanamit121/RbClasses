package com.winbee.rbclasses;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class LoginActivity extends AppCompatActivity {


    EditText editTextUsername, editTextPassword;
    TextView referalCode;
    private long backPressedTime;
    private ProgressBarUtil progressBarUtil;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LinearLayout forgetPassword;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
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
        referalCode = (TextView) findViewById(R.id.link_referal_code);
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
                // FireBaseValidation();
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
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
//            return;
//        }
//        IMEINumber = telephonyManager.getDeviceId();

    }

    private void userValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String referaCode = referalCode.getText().toString();

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
        refCode.setRef_code(referaCode);
        callRefCodeSignInApi(refCode);


    }

    private void callRefCodeSignInApi(final RefCode refCode) {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(1, refCode.getUsername(), refCode.getPassword(), refCode.getRef_code(),android_id);
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
                    Log.d("check", "onResponse: "+response.body().getEmail()+response.body().getPassword()+refCode.getCred()+refCode.getUsername());
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


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
//                } else {
//                    //Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

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


}