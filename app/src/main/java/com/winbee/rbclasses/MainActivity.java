package com.winbee.rbclasses;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.ViewPager.ViewPagerAdapter;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.RefCode;

import org.jsoup.Jsoup;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh_layout;
    private boolean onLiveFragment = false;
    private boolean onHomeFragment = true;
    private ImageView WebsiteHome, img_share;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    boolean version = false;
    String sCurrentVersion,sLastestVersion;
    private ProgressBarUtil progressBarUtil;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
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
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notifications","Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
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
//
                        //Toast.makeText(GecHomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        Uservalidation();
        layout_home = findViewById(R.id.layout_home);
        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(MainActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
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
        tabLayout.addTab(tabLayout.newTab().setText("Courses"));
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
        }
        return super.onOptionsItemSelected(item);

    }
    public void Uservalidation() {

        String Email = LocalData.Email;
        String Password=LocalData.Password;
        Log.d("TAG", "Uservalidation: "+Email+" "+Password);
        if (!Email.isEmpty()&& !Password.isEmpty()){
            auth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //  Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(MainActivity.this, "NetWork Issue Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    @Override
    public void onBackPressed () {
        super.onBackPressed();
    }


//    private void logout() {
//        SharedPrefManager.getInstance(this).logout();
//        startActivity(new Intent(this, LoginActivity.class));
//        Objects.requireNonNull(this).finish();
//    }

//    // showing the update pop up to user
//    private class GetLastesVersion extends AsyncTask<String,Void,String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                sLastestVersion = Jsoup
//                        .connect("https://play.google.com/store/apps/details?id="+getPackageName())
//                        .timeout(3000)
//                        .get()
//                        .select("div.hAyfc:nth-child(4)>"+"span:nth-child(2)> div:nth-child(1)"+"> span:nth-child(1)")
//                        .first()
//                        .ownText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return sLastestVersion;
//        }
//        @Override
//        protected void onPostExecute(String s) {
//            sCurrentVersion = BuildConfig.VERSION_NAME;
//            if (sLastestVersion !=null){
//                String[] ver1 =sCurrentVersion.split("\\.");//"1.3.1" 1.3
//                String[] ver2 =sLastestVersion.split("\\.");//"1.3.2" 1.2
//                Log.i("tag", "sLastestVersion: "+sLastestVersion);
//                int len1= ver1.length;
//                int len2= ver2.length;
//
//
//                for(int i = 0; i < len1 ; i++)
//                {
//                    if(!ver1[i].equals(ver2[i]))
//                    {
//                        Log.i("log", "onPostExecute: "+ver1[i]+" "+ver2[i]);
//                        version = true;
//                        updateAlertDialog();
//                    }
//                }
//            }
//        }
//    }
//
//    private void updateAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        //Set title
//        builder.setTitle(getResources().getString(R.string.app_name));
//        builder.setMessage("update available");
//        builder.setCancelable(false);
//        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("market://details?id="+getPackageName())));
//
//                //dismiss dialog
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.cancel();
//
//            }
//        });
//
//        builder.show();
//    }

    private void logout() {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeLogout(3, UserMobile, UserPassword, "RBC001","0");
        Log.i("tag", "callRefCodeSignInApi: "+android_id+UserMobile+UserPassword);
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    progressBarUtil.hideProgress();
                    SharedPrefManager.getInstance(MainActivity.this).logout();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    //Objects.requireNonNull(this).finish();
                    finish();

                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(MainActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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

