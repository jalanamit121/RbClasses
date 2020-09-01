package com.winbee.rbclasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllNotesAdapter;
import com.winbee.rbclasses.model.NotesModel;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyMaterial extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
  RelativeLayout today_classes;
  private RecyclerView all_study_material;
  private ArrayList<NotesModel> noteList;
  private ProgressBarUtil progressBarUtil;
  private AllNotesAdapter adapter;
  SwipeRefreshLayout refresh_study;
  private ImageView img_share,WebsiteHome;
  private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_study_material);
    progressBarUtil   =  new ProgressBarUtil(this);
    all_study_material = findViewById(R.id.all_study_material);
    Toolbar toolbar = (Toolbar) findViewById(R.id.tabs);
    setSupportActionBar(toolbar);
    WebsiteHome = findViewById(R.id.WebsiteHome);
    today_classes = findViewById(R.id.today_classes);
    WebsiteHome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        //return true;
      }
    });
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    img_share = findViewById(R.id.img_share);
    img_share.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
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
      }
    });

    layout_home = findViewById(R.id.layout_home);
    layout_home.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent home = new Intent(StudyMaterial.this,MainActivity.class);
        startActivity(home);
      }
    });

    layout_course = findViewById(R.id.layout_course);
    layout_course.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent live = new Intent(StudyMaterial.this, YouTubeVideoList.class);
        startActivity(live);
      }
    });
    layout_test = findViewById(R.id.layout_test);
    layout_test.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(StudyMaterial.this, "Coming Soon", Toast.LENGTH_SHORT).show();
      }
    });
    layout_current = findViewById(R.id.layout_current);
    layout_current.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(StudyMaterial.this, CurrentAffairsActivity.class);
        startActivity(intent);
      }
    });
    layout_doubt = findViewById(R.id.layout_doubt);
    layout_doubt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(StudyMaterial.this, DiscussionActivity.class);
        startActivity(intent);
      }
    });
    refresh_study=findViewById(R.id.refresh_study);
    refresh_study.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        callNotesApiService();
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            refresh_study.setRefreshing(false);
          }
        },4000);
      }
    });
    callNotesApiService();
  }
  private void callNotesApiService() {
    progressBarUtil.showProgress();
    ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
    Call<ArrayList<NotesModel>> call = apiCAll.getNotes(LocalData.LiveId);
    call.enqueue(new Callback<ArrayList<NotesModel>>() {
      @Override
      public void onResponse(Call<ArrayList<NotesModel>> call, Response<ArrayList<NotesModel>> response) {
        int statusCode = response.code();
        noteList = new ArrayList();
        if(statusCode==200){
          if (response.body().size()!=0) {
            System.out.println("Suree body: " + response.body());
            noteList = response.body();
            // adapter = new AllNotesAdapter(StudyMaterial.this, noteList);
            all_study_material.setAdapter(adapter);
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
      public void onFailure(Call<ArrayList<NotesModel>> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

        System.out.println("Suree: Error "+t.getMessage());
      }
    });
  }
  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_home) {
      Intent home=new Intent(StudyMaterial.this,MainActivity.class);
      startActivity(home);
    } else if (id == R.id.nav_profile) {
      Intent profile = new Intent(StudyMaterial.this,DashboardCourseActivity.class);
      startActivity(profile);

    } else if (id == R.id.nav_about) {
      Intent about = new Intent(StudyMaterial.this,AboutUsActivity.class);
      startActivity(about);

    } else if (id == R.id.nav_rate) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));


    } else if (id == R.id.nav_share) {
      try {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "RB Classes");
        String shareMessage= "\nRB Classes download the application.\n ";
        shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));
      } catch(Exception e) {
        //e.toString();
        //+ BuildConfig.APPLICATION_ID +"\n\n"
      }

    } else if (id == R.id.nav_logout) {
      logout();


    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
  private void logout() {
    SharedPrefManager.getInstance(this).logout();
    startActivity(new Intent(this, LoginActivity.class));
    Objects.requireNonNull(this).finish();
  }
}
