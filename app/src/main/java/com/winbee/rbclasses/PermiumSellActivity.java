package com.winbee.rbclasses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.PaymentModel;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    TextView gec_branchname, someTextView,txt_total_video,txt_total_document,txt_actual_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permium_sell);


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
                Toast.makeText(PermiumSellActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
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
                Intent intent = new Intent(PermiumSellActivity.this, DoubtActivity.class);
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
                userValidation();
            }
        });
        user_id.setText(UserId);
      amount_org_id=findViewById(R.id.amount_org_id);
        org_id=findViewById(R.id.org_id);
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
