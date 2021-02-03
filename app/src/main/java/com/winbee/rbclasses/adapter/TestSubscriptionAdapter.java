package com.winbee.rbclasses.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.NewModels.TestSeriesPayment;
import com.winbee.rbclasses.NewModels.TestSubscriptionArray;
import com.winbee.rbclasses.ProgressBarUtil;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.rbclasses.SharedPrefManager;
import com.winbee.rbclasses.WebApi.ClientApi;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestSubscriptionAdapter extends RecyclerView.Adapter<TestSubscriptionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TestSubscriptionArray> list;

    public TestSubscriptionAdapter(Context context, ArrayList<TestSubscriptionArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_sub_details,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.txt_days.setText(list.get(position).getTenure()+" Days");
        holder.txt_discount.setText(String.valueOf(list.get(position).getDiscountPrice()));
        //Picasso.get().load(list.get(position).getBucket_Image()).into(holder.live_image);

        holder.branch_live1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView online_testname,txt_discount,txt_actual_price,txt_days;
        private RelativeLayout branch_live1,layout_buy;
        private ImageView live_image,img_rupee1,img_rupee;
        private ProgressBarUtil progressBarUtil;
        private ViewHolder mContext;
        String UserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Checkout.preload(context.getApplicationContext());
            img_rupee1 = itemView.findViewById(R.id.img_rupee1);
            img_rupee = itemView.findViewById(R.id.img_rupee);
            branch_live1 = itemView.findViewById(R.id.branch_live1);
            live_image = itemView.findViewById(R.id.live_image);
            UserName= SharedPrefManager.getInstance(context).refCode().getName();
            progressBarUtil = new ProgressBarUtil(context);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            txt_days=itemView.findViewById(R.id.txt_days);
            layout_buy=itemView.findViewById(R.id.layout_buy);
            layout_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_payment_alert_test);
                    TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                    TextView txt_course=dialog.findViewById(R.id.txt_course);
                    TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                    TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                    TextView txt_discount_price=dialog.findViewById(R.id.txt_discount_price);
                    txt_discount_price.setText(String.valueOf(list.get(getAdapterPosition()).getTotalDiscount()));
                    txt_actual_price.setText(String.valueOf(list.get(getAdapterPosition()).getDiscountPrice()));

                    txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    txt_discount.setText(String.valueOf(list.get(getAdapterPosition()).getDisplayPrice()));

                    txt_course.setText(LocalData.TestName);
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

        }



        private void userValidation() {
            final String Course_id = LocalData.TestBuckedId;
            final String User_id = SharedPrefManager.getInstance(context).refCode().getUserId();
            final String Amount_org_id =String.valueOf(list.get(getAdapterPosition()).getDiscountPrice());
            //final String Amount_org_id ="100";
            final String Org_id = LocalData.OrgId;
            final String subscription_id=list.get(getAdapterPosition()).getSubscriptionId();



            TestSeriesPayment testSeriesPayment = new TestSeriesPayment();
            testSeriesPayment.setCourse_id(Course_id);
            testSeriesPayment.setUser_id(User_id);
            testSeriesPayment.setAmount_org_id(Amount_org_id);
            testSeriesPayment.setOrg_id(Org_id);
            testSeriesPayment.setSubscriptionId(subscription_id);




            callPayment(testSeriesPayment);

        }
        private void callPayment(final TestSeriesPayment testSeriesPayment){
            progressBarUtil.showProgress();
            ClientApi apiCall = OnlineTestApiClient.getClient().create(ClientApi.class);
            Call<TestSeriesPayment> call =apiCall.fetchTestPaymentData(testSeriesPayment.getCourse_id(),testSeriesPayment.getUser_id(),testSeriesPayment.getAmount_org_id(),testSeriesPayment.getOrg_id(),testSeriesPayment.getSubscriptionId());
            Log.i("tag", "callPayment: "+testSeriesPayment.getCourse_id()+" "+testSeriesPayment.getUser_id()+" "+testSeriesPayment.getAmount_org_id());
            call.enqueue(new Callback<TestSeriesPayment>() {
                @Override
                public void onResponse(Call<TestSeriesPayment> call, Response<TestSeriesPayment> response) {
                    int statusCode = response.code();
                    if(statusCode==200 && response.body()!=null){
                        LocalData.Org_id=response.body().getOrgOrderId();
                        LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
                        Log.i("tag", "onResponse: "+response.body().getRazorpayOrderId());
                        Toast.makeText(context,"Payment Page Loading..." , Toast.LENGTH_SHORT).show();
                        startPayment();
                        progressBarUtil.hideProgress();
                    }
                    else{
                        System.out.println("Sur: response code"+response.message());
                        Toast.makeText(context,"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<TestSeriesPayment> call, Throwable t) {
                    System.out.println("Suree: "+t.getMessage());

                    Toast.makeText(context,"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();

                }
            });
        }

        public void startPayment() {

            Checkout checkout = new Checkout();
//            checkout.setKeyID("rzp_live_T1TF9yvc1fd5il");
            checkout.setKeyID(LocalData.razorPayKey);

            String str = String.valueOf(list.get(getAdapterPosition()).getDiscountPrice());
            Double inum = Double.parseDouble(str);
            Double sum = inum*100;
            String str1 = Double.toString(sum);
           final Activity activity= (Activity) context;

            try {
                JSONObject options = new JSONObject();

                options.put("name", UserName);




                options.put("description", "Purchase Test series");
                options.put("order_id",LocalData.RazorpayOrderId);
                // options.put("image", "http://edu.rbclasses.com/api/images/RBClasses-logo.jpeg");
                options.put("currency", "INR");
                //  options.put("amount",str1);
                options.put("amount",str1);

                checkout.open(activity, options);
            } catch(Exception e) {
                Log.e("tag", "Error in starting Razorpay Checkout", e);
            }
        }



    }
}


