package com.winbee.rbclasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllPurchasedCourseAdapter;
import com.winbee.rbclasses.model.CourseModel;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeVideoList extends AppCompatActivity {
    private ArrayList<CourseModel> courseModels;
    private RecyclerView course_recycle;
    private ProgressBarUtil progressBarUtil;
    private AllPurchasedCourseAdapter adapter;
    private RelativeLayout today_classes;
    String UserID;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video_list);
        progressBarUtil   =  new ProgressBarUtil(this);
        course_recycle = findViewById(R.id.all_course);
        today_classes=findViewById(R.id.today_classes);
        UserID=SharedPrefManager.getInstance(this).refCode().getUserId();
        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(YouTubeVideoList.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(YouTubeVideoList.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(YouTubeVideoList.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YouTubeVideoList.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YouTubeVideoList.this, DoubtActivity.class);
                startActivity(intent);
            }
        });

        callCourseApiService();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            Intent intent = new Intent(YouTubeVideoList.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(YouTubeVideoList.this,AboutUsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    private void callCourseApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<CourseModel>> call = apiCAll.getPurchasedCourse(1,UserID,"WB_009","WB_009");
        call.enqueue(new Callback<ArrayList<CourseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CourseModel>> call, Response<ArrayList<CourseModel>> response) {
                int statusCode = response.code();
                courseModels = new ArrayList();
                if(statusCode==200) {
                    if (response.body().size()!=0) {
                        today_classes.setVisibility(View.GONE);
                        courseModels = response.body();
                        System.out.println("Suree body: " + response.body());
                        adapter = new AllPurchasedCourseAdapter(YouTubeVideoList.this, courseModels);
                        course_recycle.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    }else {
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
            public void onFailure(Call<ArrayList<CourseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });

    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}
