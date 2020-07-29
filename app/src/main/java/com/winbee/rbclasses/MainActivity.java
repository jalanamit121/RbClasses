package com.winbee.rbclasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.winbee.rbclasses.ViewPager.ViewPagerAdapter;

import java.io.IOException;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh_main;
    private boolean onLiveFragment = false;
    private boolean onHomeFragment = true;
    private ImageView WebsiteHome, img_share;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    String Dail;
    String sCurrentVersion,sLastestVersion;

    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // new GetLastesVersion().execute();
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
                Intent intent = new Intent(MainActivity.this, DoubtActivity.class);
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


    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

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
//                float cVersion = Float.parseFloat(sCurrentVersion);
//                float lVersion = Float.parseFloat(sLastestVersion);
//                // check the condition whether the lastet version is greater than current version
//                if (lVersion>cVersion){
//                    // create update alert dilog box
//                    updateAlertDialog();
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
//
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
}

