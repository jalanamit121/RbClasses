package com.winbee.rbclasses;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.balsikandar.crashreporter.BuildConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.NewModels.RazorPayModel;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.Utils.AppUpdateChecker;
import com.winbee.rbclasses.ViewPager.ViewPagerAdapter;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefCode;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh_layout;
    private boolean onLiveFragment = false;
    private boolean onHomeFragment = true;
    private ImageView WebsiteHome, img_share;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt,layout_download;
    boolean version = false;
    String sCurrentVersion,sLastestVersion;
    private ProgressBarUtil progressBarUtil;
    String UserMobile,UserPassword;
    String android_id;

    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarUtil = new ProgressBarUtil(this);
        //   new GetLastesVersion().execute();
        //firebase push notification


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }


        if (connected) {
            doYourStuff();
        }else {

            startActivity(new Intent(this,OfflineDownloadActivity.class));
            this.finish();
        }

    }

    private void doYourStuff(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("","Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here

        callRazorPayService();
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        FirebaseMessaging.getInstance().subscribeToTopic("rbclasses")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                    }
                });
        layout_home = findViewById(R.id.layout_home);
        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(MainActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_download = findViewById(R.id.layout_download);
        layout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(MainActivity.this, ShowDownloadCourse.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                Intent doubt = new Intent(MainActivity.this,SubjectActivity.class);
                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Live Courses"));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
            Intent intent = new Intent(MainActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(intent);
        }else if(id == R.id.download){
            startActivity(new Intent(this,ShowDownloadCourse.class));
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed () {
        finish();
        super.onBackPressed();
    }
    // for razor pay
    private void callRazorPayService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<RazorPayModel> call = apiCAll.getRazorPay();
        call.enqueue(new Callback<RazorPayModel>() {
            @Override
            public void onResponse(Call<RazorPayModel> call, Response<RazorPayModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body()!=null ) {
                    progressBarUtil.hideProgress();
                    LocalData.razorPayKey=response.body().getAPI_Key();
//
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RazorPayModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(MainActivity.this,ServerDownMessage.class);
//                startActivity(intent);
//                finish();
                progressBarUtil.hideProgress();
                //System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }


    private void logout() {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<LogOut> call = mService.refCodeLogout(3, UserMobile, UserPassword, "RBC001",android_id);
        call.enqueue(new Callback<LogOut>() {
            @Override
            public void onResponse(Call<LogOut> call, Response<LogOut> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    if (response.body().getError()==false){
                        progressBarUtil.hideProgress();
                        SharedPrefManager.getInstance(MainActivity.this).logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                MainActivity.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }


                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(MainActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }


}

