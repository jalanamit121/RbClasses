package com.winbee.rbclasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.balsikandar.crashreporter.CrashReporter;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.AllPurchasedTest;
import com.winbee.rbclasses.model.SectionDetailsDataModel;
import com.winbee.rbclasses.model.SectionDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class AllPurchasedTestActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_subject;
    private List<SectionDetailsDataModel> list;
    private Toast toast_msg;
    private AllPurchasedTest allPurchasedTest;
    String UserId,android_id,UserMobile,UserPassword;
    private ProgressBarUtil progressBarUtil;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchased_test);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try{
            layout_home = findViewById(R.id.layout_home);
            layout_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent home = new Intent(AllPurchasedTestActivity.this,MainActivity.class);
                    startActivity(home);
                }
            });

            layout_course = findViewById(R.id.layout_course);
            layout_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent live = new Intent(AllPurchasedTestActivity.this, YouTubeVideoList.class);
                    startActivity(live);
                }
            });
            layout_test = findViewById(R.id.layout_test);
            layout_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent doubt = new Intent(AllPurchasedTestActivity.this,SubjectActivity.class);
                    startActivity(doubt);
                }
            });
            layout_current = findViewById(R.id.layout_current);
            layout_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllPurchasedTestActivity.this, CurrentAffairsActivity.class);
                    startActivity(intent);
                }
            });
            layout_doubt = findViewById(R.id.layout_doubt);
            layout_doubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllPurchasedTestActivity.this, DiscussionActivity.class);
                    startActivity(intent);
                }
            });

            iniIDs();
            getSubjectName();
        }catch (Exception e){
            CrashReporter.logException(e);
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_subject=findViewById(R.id.recycle_subject);
        UserId=SharedPrefManager.getInstance(this).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        progressBarUtil   =  new ProgressBarUtil(this);
    }
    private void getSubjectName() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SectionDetailsMainModel> call=apiClient.fetchSectionDetails(LocalData.OrgId,LocalData.AuthCode,UserId);
        call.enqueue(new Callback<SectionDetailsMainModel>() {
            @Override
            public void onResponse(Call<SectionDetailsMainModel> call, Response<SectionDetailsMainModel> response) {
                apiCalled();
                SectionDetailsMainModel sectionDetailsMainModel=response.body();
                if(sectionDetailsMainModel!=null){
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")){
                        list=new ArrayList<>(Arrays.asList(Objects.requireNonNull(sectionDetailsMainModel).getData()));
                        allPurchasedTest=new AllPurchasedTest(AllPurchasedTestActivity.this,list);
//                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SubjectActivity.this, LinearLayoutManager.VERTICAL, false);
//                        recycle_subject.setLayoutManager(layoutManager);
//                        recycle_subject.setItemAnimator(new DefaultItemAnimator());
                        recycle_subject.setAdapter(allPurchasedTest);
                    }
                    else
                        doToast(sectionDetailsMainModel.getMessage());
                }
                else
                    doToast("No Test Available");
            }
            @Override
            public void onFailure(Call<SectionDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(AllPurchasedTestActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
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
            Intent intent = new Intent(AllPurchasedTestActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(AllPurchasedTestActivity.this,AboutUsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

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
                        SharedPrefManager.getInstance(AllPurchasedTestActivity.this).logout();
                        startActivity(new Intent(AllPurchasedTestActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                AllPurchasedTestActivity.this);
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
                    Toast.makeText(AllPurchasedTestActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(AllPurchasedTestActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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
