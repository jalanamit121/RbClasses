package com.winbee.rbclasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.NewModels.TestSubscription;
import com.winbee.rbclasses.NewModels.TestSubscriptionArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.TestSubscriptionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class TestSubscriptionActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private ArrayList<TestSubscriptionArray> list;
    private RecyclerView gec_test_recycle;
    private TestSubscriptionAdapter adapter;
    RelativeLayout description_layout,layout_discription_details,image_layout,layout_success,layout_failed;
    private ImageView WebsiteHome,img_share,image_expand_more,image_expand_less;
    String UserId,android_id,UserMobile,UserPassword;
    TextView txt_no_subject,course_name,txt_discription_click,txt_discription,txt_course_discription,txt_total_test,
            txt_amount,txt_course,txt_txn_id;
    private ProgressBarUtil progressBarUtil;
    Button btn_demo,go_back,btn_course,go_back_failed;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt,layout_download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subscription);
   //     getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        gec_test_recycle = findViewById(R.id.gec_test_recycle);
        txt_no_subject = findViewById(R.id.txt_no_subject);
        course_name = findViewById(R.id.course_name);
        course_name.setText(LocalData.TestName);

        progressBarUtil   =  new ProgressBarUtil(this);
        UserId=SharedPrefManager.getInstance(this).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        description_layout=findViewById(R.id.description_layout);
        layout_discription_details=findViewById(R.id.layout_discription_details);
        image_layout=findViewById(R.id.image_layout);
        txt_discription_click=findViewById(R.id.txt_discription_click);
        txt_discription=findViewById(R.id.txt_discription);
        txt_course_discription=findViewById(R.id.txt_course_discription);
        txt_course_discription.setText(LocalData.TestDiscription);
        txt_total_test=findViewById(R.id.txt_total_test);
        txt_total_test.setText("Total Test ( "+LocalData.TotalTest+" )");
        image_expand_more=findViewById(R.id.image_expand_more);
        image_expand_less=findViewById(R.id.image_expand_less);
        layout_success=findViewById(R.id.layout_success);
        layout_failed=findViewById(R.id.layout_failed);
        txt_amount=findViewById(R.id.txt_amount);
        txt_amount.setText("Amount :-"+LocalData.TestDiscountPrice);
        txt_course=findViewById(R.id.txt_course);
        txt_course.setText("Test Name :-"+LocalData.TestName);
        txt_txn_id=findViewById(R.id.txt_txn_id);
        txt_txn_id.setText("Reference No :- "+LocalData.Org_id);
        btn_demo=findViewById(R.id.btn_demo);
        btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,DemoOnlineTestActivity.class);
                startActivity(intent);
            }
        });

        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        go_back_failed=findViewById(R.id.go_back_failed);
        go_back_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_download = findViewById(R.id.layout_download);
        layout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(TestSubscriptionActivity.this, ShowDownloadCourse.class);
                startActivity(live);
            }
        });
        btn_course=findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,SubjectActivity.class);
                startActivity(intent);
            }
        });
        description_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.VISIBLE);
                txt_discription.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.GONE);
                image_expand_less.setVisibility(View.VISIBLE);

            }
        });

        image_expand_less=findViewById(R.id.image_expand_less);
        image_expand_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.GONE);
                txt_discription.setVisibility(View.VISIBLE);
                image_expand_less.setVisibility(View.GONE);
            }
        });



        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(TestSubscriptionActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doubt = new Intent(TestSubscriptionActivity.this,SubjectActivity.class);
                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSubscriptionActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSubscriptionActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });

        callCourseSubjectApiService();

    }

    private void callCourseSubjectApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<TestSubscription> call = mService.getSubscriptionDetails(LocalData.TestBuckedId,LocalData.AuthCode);
        call.enqueue(new Callback<TestSubscription>() {
            @Override
            public void onResponse(Call<TestSubscription> call, Response<TestSubscription> response) {
                TestSubscription testSubscription =response.body();

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                        System.out.println("Suree body: " + response.body());
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(testSubscription).getData()));
                        if (list.size()!=0){
                            txt_no_subject.setVisibility(View.GONE);
                            image_layout.setVisibility(View.VISIBLE);
                            adapter = new TestSubscriptionAdapter(TestSubscriptionActivity.this, list);
                            gec_test_recycle.setAdapter(adapter);
                            progressBarUtil.hideProgress();
                        }else{
                            txt_no_subject.setVisibility(View.VISIBLE);
                            image_layout.setVisibility(View.GONE);
                            progressBarUtil.hideProgress();
                        }

                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TestSubscription> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }




    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();
            layout_success.setVisibility(View.VISIBLE);
            btn_demo.setClickable(false);
            image_layout.setAlpha((float) 0.2);
//        Toast.makeText(TestSubscriptionActivity.this,"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(TestSubscriptionActivity.this, MainActivity.class);
//        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        layout_failed.setVisibility(View.VISIBLE);
        btn_demo.setClickable(false);
        image_layout.setAlpha((float) 0.2);
       // Toast.makeText(TestSubscriptionActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(TestSubscriptionActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(TestSubscriptionActivity.this,AboutUsActivity.class);
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
                        SharedPrefManager.getInstance(TestSubscriptionActivity.this).logout();
                        startActivity(new Intent(TestSubscriptionActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                TestSubscriptionActivity.this);
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
                    Toast.makeText(TestSubscriptionActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(TestSubscriptionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
    @Override
    public void onBackPressed () {
//        finish();
//        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
         finish();
    }
}