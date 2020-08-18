package com.winbee.rbclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.CurrentAdapter;
import com.winbee.rbclasses.adapter.SekHomeAdapter;
import com.winbee.rbclasses.model.CurrentAffairsModel;
import com.winbee.rbclasses.model.RefCode;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class CurrentAffairsActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private ArrayList<CurrentAffairsModel> currentAffairsModels;
    private CurrentAdapter currentAdapter;
    private RecyclerView sek_home_recycle;
    private RelativeLayout today_classes;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    String UserMobile,UserPassword,android_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs);
        progressBarUtil   =  new ProgressBarUtil(this);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(CurrentAffairsActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(CurrentAffairsActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
//            return;
//        }
//        IMEINumber = telephonyManager.getDeviceId();

        sek_home_recycle=findViewById(R.id.sek_home_recycle);
        today_classes=findViewById(R.id.today_classes);
        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(CurrentAffairsActivity.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(CurrentAffairsActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CurrentAffairsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentAffairsActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentAffairsActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });

        callUpdateApiService();
    }
    private void callUpdateApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<CurrentAffairsModel>> call = apiCAll.getCurrentAffairs();
        call.enqueue(new Callback<ArrayList<CurrentAffairsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CurrentAffairsModel>> call, Response<ArrayList<CurrentAffairsModel>> response) {
                int statusCode = response.code();
                currentAffairsModels = new ArrayList();
                if(statusCode==200){
                    if (response.body().size()!=0){
                        currentAffairsModels = response.body();
                        currentAdapter = new CurrentAdapter(CurrentAffairsActivity.this, currentAffairsModels);
                        sek_home_recycle.setAdapter(currentAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CurrentAffairsModel>> call, Throwable t) {
                today_classes.setVisibility(View.VISIBLE);
                progressBarUtil.hideProgress();
            }
        });
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
            Intent intent = new Intent(CurrentAffairsActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(CurrentAffairsActivity.this,AboutUsActivity.class);
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
                    SharedPrefManager.getInstance(CurrentAffairsActivity.this).logout();
                    startActivity(new Intent(CurrentAffairsActivity.this, LoginActivity.class));
                    //Objects.requireNonNull(this).finish();
                    finish();

                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(CurrentAffairsActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(CurrentAffairsActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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
