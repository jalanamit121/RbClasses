package com.winbee.rbclasses;

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
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.PaymentModel;
import com.winbee.rbclasses.model.RefCode;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class PermiumSellActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    Button pay_btn,view_demo;
    String TAG="payment activity";
    TextView course_id;
    TextView user_id;
    TextView amount_org_id;
    TextView org_id,bucket_id;
    private CourseModel courseModel;
    private String UserId,UserName;
    RelativeLayout home,histroy,logout;
    private ProgressBarUtil progressBarUtil;
    ImageView payment_image;
    private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;
    private static final int REQUEST_CODE = 101;
    String IMEINumber;
    String UserMobile,UserPassword,android_id;


    TextView gec_branchname, someTextView,txt_total_video,txt_total_document,txt_actual_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permium_sell);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        UserName = SharedPrefManager.getInstance(this).refCode().getUsername();
        someTextView = (TextView) findViewById(R.id.txt_discount);
        someTextView.setText(LocalData.DiscountPrice);
        layout_home = findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(PermiumSellActivity.this,MainActivity.class);
                startActivity(home);
            }
        });

        layout_course = findViewById(R.id.layout_course);
        layout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent live = new Intent(PermiumSellActivity.this, YouTubeVideoList.class);
                startActivity(live);
            }
        });
        layout_test = findViewById(R.id.layout_test);
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doubt = new Intent(PermiumSellActivity.this,SubjectActivity.class);
                startActivity(doubt);
            }
        });
        layout_current = findViewById(R.id.layout_current);
        layout_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermiumSellActivity.this, CurrentAffairsActivity.class);
                startActivity(intent);
            }
        });
        layout_doubt = findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermiumSellActivity.this, DiscussionActivity.class);
                startActivity(intent);
            }
        });



        course_id=findViewById(R.id.course_id);
        user_id=findViewById(R.id.user_id);
        view_demo=findViewById(R.id.view_demo);
        payment_image=findViewById(R.id.payment_image);
        Picasso.get().load(LocalData.PayImage).into(payment_image);
        txt_total_video=findViewById(R.id.txt_total_video);
        txt_total_video.setText("Total Videos("+ LocalData.TotalVideo+")");
        txt_total_document=findViewById(R.id.txt_total_document);
        txt_total_document.setText("Total Documents("+LocalData.TotalDocument+")");
        txt_actual_price=findViewById(R.id.txt_actual_price);
        txt_actual_price.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txt_actual_price.setText(LocalData.ActualPrice);
        gec_branchname=findViewById(R.id.gec_branchname);
        gec_branchname.setText(LocalData.Discription);
        bucket_id=findViewById(R.id.bucket_id);
        progressBarUtil   =  new ProgressBarUtil(this);
        pay_btn=findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(PermiumSellActivity.this);
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
            }
        });
        user_id.setText(UserId);
        amount_org_id=findViewById(R.id.amount_org_id);
        org_id=findViewById(R.id.org_id);
        Checkout.preload(getApplicationContext());
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_T1TF9yvc1fd5il");

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
        Intent intent = new Intent(PermiumSellActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

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
                        SharedPrefManager.getInstance(PermiumSellActivity.this).logout();
                        startActivity(new Intent(PermiumSellActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                PermiumSellActivity.this);
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
                    Toast.makeText(PermiumSellActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(PermiumSellActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);

    }


}
