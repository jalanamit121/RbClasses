package com.winbee.rbclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.winbee.rbclasses.NewModels.LiveMessage;
import com.winbee.rbclasses.NewModels.LiveMessageArray;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.adapter.ServerMessageAdapter;
import com.winbee.rbclasses.model.LiveChatMessage;
import com.winbee.rbclasses.model.LiveChatMessageFetch;
import com.winbee.rbclasses.model.LiveChatModel;
import com.winbee.rbclasses.model.Message;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import at.huber.youtubeExtractor.YtFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


public class YoutubeLibaray extends AppCompatActivity {
    EditText editTextMessageLive;
    ImageView sendButton;
    RecyclerView messageRecyclerView;
    RelativeLayout relativeLayoutLive,message_layout;
    private TextView textView,txt_message_not;
    private ProgressBarUtil progressBarUtil;
    private YouTubePlayerView youTubePlayerView;
    String UserId,Username,android_id;
    private ArrayList<LiveMessageArray> list;
    private ServerMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_libaray);
        editTextMessageLive = findViewById(R.id.editTextMessageLive);
        sendButton = findViewById(R.id.sendMessageLive);
        txt_message_not = findViewById(R.id.txt_message_not);
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        Username=SharedPrefManager.getInstance(this).refCode().getName();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        messageRecyclerView = findViewById(R.id.liveMessageRecyclerViewLive);
        progressBarUtil = new ProgressBarUtil(this);
        relativeLayoutLive = findViewById(R.id.relative_layout_live);
        callAllMessageFetch();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSend();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        this.youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                editTextMessageLive.setVisibility(View.GONE);
                sendButton.setVisibility(View.GONE);

                // hide softkeys if it is already open

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                editTextMessageLive.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.VISIBLE);

            }
        });
        youTubePlayerView.
                addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                        YouTubePlayerUtils.loadOrCueVideo(
                                youTubePlayer, getLifecycle(),
                                LocalData.LiveId, 0f
                        );


                    }

                });

    }
    private void messageSend() {
        final String message = editTextMessageLive.getText().toString();
        LocalData.Message=message;
        if (TextUtils.isEmpty(message)) {
            editTextMessageLive.setError("enter some message");
            editTextMessageLive.requestFocus();
            return;
        }


        callMessageSendApi();

    }
    private void callMessageSendApi() {
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<LiveChatMessage> call = apiCall.getLiveMessage(1,UserId,Username,LocalData.DocumentId,LocalData.Message,android_id);
        Log.i("tag", "callMessageSendApi: " +UserId+Username+LocalData.LiveId+LocalData.Message);
        call.enqueue(new Callback<LiveChatMessage>() {
            @Override
            public void onResponse(Call<LiveChatMessage> call, Response<LiveChatMessage> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getResponse()) {
                    if (!response.body().getError()){
                        editTextMessageLive.getText().clear();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                        callAllMessageFetch();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                YoutubeLibaray.this);
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
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LiveChatMessage> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void callAllMessageFetch(){
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<LiveMessage> call = apiCall.getLiveMessageFetch(2,UserId,android_id,LocalData.DocumentId);
        call.enqueue(new Callback<LiveMessage>() {
            @Override
            public void onResponse(Call<LiveMessage> call, Response<LiveMessage> response) {
                LiveMessage liveMessage =response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    if (response.body().getResponse()==true) {
                        if (response.body().getError()==false){
                            txt_message_not.setVisibility(View.GONE);
                            list =new ArrayList<>(Arrays.asList(Objects.requireNonNull(liveMessage).getData()));
                            System.out.println("Suree body: " + list);
                            adapter = new ServerMessageAdapter(YoutubeLibaray.this, list);
                            messageRecyclerView.setAdapter(adapter);
                        }else{
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                    YoutubeLibaray.this);
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
                    }else{
                        txt_message_not.setVisibility(View.VISIBLE);
                    }


                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LiveMessage> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }




    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen()) {
            youTubePlayerView.exitFullScreen();
        } else
            super.onBackPressed();
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

    public static abstract class DoubleClickListener implements View.OnClickListener {

        private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

        long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v);
            } else {
                onSingleClick(v);
            }
            lastClickTime = clickTime;
        }

        public abstract void onSingleClick(View v);

        public abstract void onDoubleClick(View v);
    }


    public static  class YtFragmentedVideo {
        int height;
        YtFile audioFile;
        YtFile videoFile;
    }



}
