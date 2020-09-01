package com.winbee.rbclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.ViewPager.ViewPagerLiveAdapter;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.PaymentModel;
import com.winbee.rbclasses.model.RefCode;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;
import static com.winbee.rbclasses.LocalData.UserName;

public class LiveDataActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private Button buy_course;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    private ProgressBarUtil progressBarUtil;
    String UserMobile,UserPassword,android_id;
    String TAG="payment activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        progressBarUtil   =  new ProgressBarUtil(this);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();

        buy_course =findViewById(R.id.buy_course);
        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(LiveDataActivity.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(LiveDataActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LiveDataActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LiveDataActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LiveDataActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });

        buy_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo
//                Intent intent =new Intent(LiveDataActivity.this,PermiumSellActivity.class);
//                startActivity(intent);
                final Dialog dialog = new Dialog(LiveDataActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_course=dialog.findViewById(R.id.txt_course);
                TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                txt_actual_price.setText(LocalData.DiscountPrice);
                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(LocalData.ActualPrice);

                txt_course.setText(LocalData.Discription);
                TextView txt_pervious_attempt=dialog.findViewById(R.id.txt_pervious_attempt);
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(false);
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Live Class"));
        tabLayout.addTab(tabLayout.newTab().setText("Notes"));
        final ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        final ViewPagerLiveAdapter viewPagerLiveAdapter = new ViewPagerLiveAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerLiveAdapter);
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
        Checkout.preload(getApplicationContext());
    }
    public void startPayment() {
        Checkout checkout = new Checkout();

        String str = LocalData.DiscountPrice;
        Double inum = Double.parseDouble(str);
        Double sum = inum*100;
        String str1 = Double.toString(sum);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", UserName);




            options.put("description", "Purchase Course");
            options.put("order_id",LocalData.RazorpayOrderId);
            // options.put("image", "http://edu.rbclasses.com/api/images/RBClasses-logo.jpeg");
            options.put("currency", "INR");
            options.put("amount",str1);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();
        Toast.makeText(getApplicationContext(),"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LiveDataActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

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
            Intent intent = new Intent(LiveDataActivity.this,DashboardCourseActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(LiveDataActivity.this,AboutUsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }


    private void userValidation() {
        final String Course_id = LocalData.CourseId;
        final String User_id = SharedPrefManager.getInstance(this).refCode().getUserId();
        final String Amount_org_id =LocalData.DiscountPrice;
        final String Org_id = "WB_009";



        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setCourse_id(Course_id);
        paymentModel.setUser_id(User_id);
        paymentModel.setAmount_org_id(Amount_org_id);
        paymentModel.setOrg_id(Org_id);




        callPayment(paymentModel);

    }
    private void callPayment(final PaymentModel paymentModel){
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<PaymentModel> call =apiCall.fetchPaymentData(paymentModel.getCourse_id(),paymentModel.getUser_id(),paymentModel.getAmount_org_id(),paymentModel.getOrg_id());
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    LocalData.Org_id=response.body().getOrgOrderId();
                    LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
                    Toast.makeText(getApplicationContext(),"Payment started" , Toast.LENGTH_SHORT).show();
                    startPayment();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());

                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();

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
                        SharedPrefManager.getInstance(LiveDataActivity.this).logout();
                        startActivity(new Intent(LiveDataActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                LiveDataActivity.this);
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
                    Toast.makeText(LiveDataActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(LiveDataActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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
