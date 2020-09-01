package com.winbee.rbclasses;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.SolutionAdapter;
import com.winbee.rbclasses.model.RefCode;
import com.winbee.rbclasses.model.SolutionDoubtQuestion;
import com.winbee.rbclasses.model.SolutionQuestion;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class DoubtSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private SolutionAdapter adapter;
    private ArrayList<SolutionQuestion> list;
    private RecyclerView askedSolution;
    EditText editTextGiveSolution;
    ImageView submit_solution,WebsiteHome,img_share;
    private TextView txt_user,txt_time,txt_ask_title,txt_ask_question,txt_commments;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    String UserMobile,UserPassword,android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_solution);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();

        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        editTextGiveSolution=findViewById(R.id.editTextGiveSolution);
        submit_solution=findViewById(R.id.submit_solution);
        txt_user=findViewById(R.id.txt_user);
        txt_user.setText(LocalData.User);
        txt_time=findViewById(R.id.txt_time);
        txt_time.setText(LocalData.Duration);
        txt_ask_title=findViewById(R.id.txt_ask_title);
        txt_ask_title.setText(LocalData.Title);
        txt_ask_question=findViewById(R.id.txt_ask_question);
        txt_ask_question.setText(LocalData.Discription);
        txt_commments=findViewById(R.id.txt_commments);
        txt_commments.setText(LocalData.Commnts);
        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(DoubtSolutionActivity.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(DoubtSolutionActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DoubtSolutionActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtSolutionActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtSolutionActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        askedSolution.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new SolutionAdapter(this, list);
        askedSolution.setAdapter(adapter);
       // list.add(items);
        adapter.notifyDataSetChanged();

        submit_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solutionValidation();
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
        callAskedSolutionApiService();


    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<SolutionQuestion>> call = apiCall.getSolution(LocalData.FileName);

        call.enqueue(new Callback<ArrayList<SolutionQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<SolutionQuestion>> call, Response<ArrayList<SolutionQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new SolutionAdapter(DoubtSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SolutionQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void solutionValidation() {
        final String solution = editTextGiveSolution.getText().toString();
        final String question = LocalData.FileName;
        final String userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        Log.d("tag", "solutionValidation: "+solution+question+userid);
        if (TextUtils.isEmpty(solution)) {
            editTextGiveSolution.setError("Please enter solution");
            editTextGiveSolution.requestFocus();
            return;
        }
        SolutionDoubtQuestion solutionDoubtQuestion = new SolutionDoubtQuestion();
        solutionDoubtQuestion.setFilename(question);
        solutionDoubtQuestion.setAnswer(solution);
        solutionDoubtQuestion.setUserid(userid);



        callSolutionApiService(solutionDoubtQuestion);

    }
    private void callSolutionApiService(SolutionDoubtQuestion solutionDoubtQuestion) {
        progressBarUtil.showProgress();
        // final UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution(solution, question,userid, documentid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<SolutionDoubtQuestion> call = apiCall.getNewSolution(solutionDoubtQuestion.getUserid(),solutionDoubtQuestion.getFilename(),solutionDoubtQuestion.getAnswer());
        Log.d("tag", "callSolutionApiService: "+solutionDoubtQuestion.getFilename()+solutionDoubtQuestion.getAnswer()+solutionDoubtQuestion.getUserid());
        call.enqueue(new Callback<SolutionDoubtQuestion>() {
            @Override
            public void onResponse(Call<SolutionDoubtQuestion> call, Response<SolutionDoubtQuestion> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(DoubtSolutionActivity.this, DoubtSolutionActivity.class));
                    finish();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SolutionDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

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
            Intent intent = new Intent(DoubtSolutionActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(DoubtSolutionActivity.this,AboutUsActivity.class);
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
                        SharedPrefManager.getInstance(DoubtSolutionActivity.this).logout();
                        startActivity(new Intent(DoubtSolutionActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                DoubtSolutionActivity.this);
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
                    Toast.makeText(DoubtSolutionActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(DoubtSolutionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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
