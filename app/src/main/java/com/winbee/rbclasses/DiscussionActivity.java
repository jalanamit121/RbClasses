package com.winbee.rbclasses;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefCode;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class DiscussionActivity extends AppCompatActivity {

    TextView txtAsk,txtMcq;
    private boolean onMcqFragment=false;
    private boolean onAskFragment=true;
    private ImageView WebsiteHome,img_share;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    String UserMobile,UserPassword,android_id;
    private  ProgressBarUtil progressBarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        progressBarUtil   =  new ProgressBarUtil(this);
        txtAsk= findViewById(R.id.txtViewAsk);
        txtMcq= findViewById(R.id.txtViewMcq);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(DiscussionActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(DiscussionActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
//            return;
//        }
//        IMEINumber = telephonyManager.getDeviceId();




        defalutAskTab(false);

        txtAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onAskFragment){
                    defalutAskTab(true);
                    onAskFragment=true;
                    onMcqFragment=false;
                }
            }
        });

        txtMcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onMcqFragment) {
                    McqTab(true);
                    onMcqFragment=true;
                    onAskFragment=false;
                }
            }
        });

    }

    private void defalutAskTab(boolean addToBackStack) {


        AskFragment askFragment = new AskFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"AskFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onAskFragment=true;
    }
    private void McqTab(boolean addToBackStack) {


        McqFragment askFragment = new McqFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"McqFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onMcqFragment=true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.logout) {
            logout();
        }else if(id == R.id.website){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rbclasses.com"));
            startActivity(intent);

        }else if(id == R.id.share){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "RB Classes");
                String shareMessage= "\nRB Classes download the application.\n ";
                shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
            }

        }else if(id == R.id.rate){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));

        }else if(id == R.id.profile){
            Intent intent = new Intent(DiscussionActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(DiscussionActivity.this,AboutUsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
    private void logout() {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeLogout(3, UserMobile, UserPassword, "RBC001",android_id);
        Log.i("tag", "callRefCodeSignInApi: "+IMEINumber+UserMobile+UserPassword);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    progressBarUtil.hideProgress();
                    SharedPrefManager.getInstance(DiscussionActivity.this).logout();
                    startActivity(new Intent(DiscussionActivity.this, LoginActivity.class));
                    //Objects.requireNonNull(this).finish();
                    finish();

                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(DiscussionActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(DiscussionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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

}
