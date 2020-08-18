package com.winbee.rbclasses;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.winbee.rbclasses.adapter.MessageAdapter;
import com.winbee.rbclasses.model.LiveChatModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
        public static final String API_KEY = "AIzaSyBlEPocq2s2bDmWDMBRXAf8Mhf3wlFNYGI";
        public static final String VIDEO_ID = "j36wPW4bGIs";
        private ImageView fullscreen_view,fullscreen_view_exit;
        FirebaseAuth auth =FirebaseAuth.getInstance();
        EditText textMessage;
        ImageView sendButton;
        RecyclerView messageRecyclerView;
        private TextView textView;
        private ProgressBarUtil progressBarUtil;
        RelativeLayout relativeLayoutLive;
        MessageAdapter messageAdapter;
        ArrayList<LiveChatModel> messagesArrayList;


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        public static String userIdAuth;
        private YouTubePlayerView youTubePlayerView;

        YouTubePlayer player;
        private boolean fullscreen=false;

        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_youtube_player);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                textMessage=findViewById(R.id.editTextMessageLive);
                sendButton=findViewById(R.id.sendMessageLive);
                messageRecyclerView=findViewById(R.id.liveMessageRecyclerViewLive);
                textView=findViewById(R.id.textNoMessageLive);
                /** Initializing YouTube Player View **/
                youTubePlayerView=findViewById(R.id.youtube_player);
                youTubePlayerView.initialize(API_KEY,this);
                fullscreen_view=findViewById(R.id.fullscreen_view);
                fullscreen_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                fullscreen = true;
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) youTubePlayerView.getLayoutParams();
                                // fullscreen_view_exit.setVisibility(View.VISIBLE);
                                // ise wale se full screen kar raha hu okkkk
                        }
                });

//                fullscreen_view_exit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) youTubePlayerView.getLayoutParams();
//                                fullscreen_view.setVisibility(View.VISIBLE);
//                                fullscreen_view_exit.setVisibility(View.GONE);
//                        }
//                });

                //  progressBar=findViewById(R.id.progressBarLive);
                progressBarUtil = new ProgressBarUtil(this);
                relativeLayoutLive=findViewById(R.id.relative_layout_live);
                messagesArrayList=new ArrayList<>();
                sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                sendMessage();
                        }
                });

                retriveMessages();

        }

        private void setSupportActionBar(Toolbar toolbar) {
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
                Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
                /** add listeners to YouTubePlayer instance **/
                player.setPlayerStateChangeListener(playerStateChangeListener);
                player.setPlaybackEventListener(playbackEventListener);
                player.play();
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                this.player=player;
//                player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
//                        @Override
//                        public void onFullscreen(boolean b) {
//                                if (b){
//                                        player.play();
//                                        fullscreen=true;
//
//                                }
//                                if (!b){
//                                        if (!player.isPlaying()){
//                                                player.play();
//                                        }
//                                        fullscreen=false;
//
//                                }
//                        }
//                });

                /** Start buffering **/
                if (!wasRestored) {
                        //player.cueVideo(topicName.getURL());
                        player.cueVideo(LocalData.LiveId);
                        // player.cueVideo(VIDEO_ID);
                }
        }

        private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onBuffering(boolean arg0) {
                }

                @Override
                public void onPaused() {
                }

                @Override
                public void onPlaying() {
                }

                @Override
                public void onSeekTo(int arg0) {
                }

                @Override
                public void onStopped() {
                }
        };

        private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onAdStarted() {
                }

                @Override
                public void onError(YouTubePlayer.ErrorReason arg0) {
                }

                @Override
                public void onLoaded(String arg0) {
                }

                @Override
                public void onLoading() {
                }

                @Override
                public void onVideoEnded() {
                }

                @Override
                public void onVideoStarted() {
                }
        };


        private void retriveMessages() {
                progressBarUtil.showProgress();



                if (LocalData.DocumentId!=null) {
                        db.collection(LocalData.DocumentId)
                                .orderBy("time", Query.Direction.DESCENDING)

                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                messagesArrayList = new ArrayList<>();
                                                if (e != null) {
                                                        progressBarUtil.hideProgress();
                                                        Log.w("YourTag", "Listen failed.", e);
                                                        return;

                                                }

                                                if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() == 0) {
                                                        messagesArrayList = new ArrayList<>();
                                                        progressBarUtil.hideProgress();
                                                        //   progressBar.setVisibility(View.GONE);
                                                        Animation in = new AlphaAnimation(0.0f, 1.0f);
                                                        in.setDuration(1000);
                                                        textView.setAnimation(in);
                                                        textView.setText("No Messages Yet...");
                                                        addingDataToMessagedAdapter(messagesArrayList);
                                                        messageAdapter.notifyDataSetChanged();

                                                } else {
                                                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                                                if (doc.exists()) {
                                                                        // messagesArrayList = new ArrayList<>();
                                                                        progressBarUtil.hideProgress();
                                                                        LiveChatModel message = doc.toObject(LiveChatModel.class);
                                                                        messagesArrayList.add(message);
                                                                        textView.setText("");
                                                                        for (int i = 0; i < messagesArrayList.size(); i++) {
                                                                                //    Log.d(TAG, "onEvent: " + messagesArrayList.get(i).getMessage());
                                                                                Log.d(TAG, "onEvent: " + messagesArrayList.get(i).getDocId());
                                                                        }
                                                                        //progressBar.setVisibility(View.GONE);
                                                                        //  Log.d(TAG, "onEvent: "+messagesArrayList.size());
                                                                        addingDataToMessagedAdapter(messagesArrayList);
                                                                        messageAdapter.notifyDataSetChanged();
                                                                }
                                                        }
                                                }
                                        }
                                });


                }else
                        Log.d(TAG, "retriveMessages: error");

        }

        private void addingDataToMessagedAdapter(ArrayList<LiveChatModel> messagesArrayList) {
                messageRecyclerView.setHasFixedSize(true);
                messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                messageAdapter=new MessageAdapter(this,messagesArrayList);
                messageRecyclerView.setAdapter(messageAdapter);


        }





        private void sendMessage() {
                FirebaseUser user= auth.getCurrentUser();
                if (user!=null){
                        if (auth!=null){
                                userIdAuth = auth.getCurrentUser().getUid();
                                final String Message= textMessage.getText().toString();
                                if (Message.isEmpty()){
                                        Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();


                                }else{
                                        final Date currentTime = Calendar.getInstance().getTime();
                                        progressBarUtil.hideProgress();
                                        final Map<String,String> messageInfo = new HashMap<>();
                                        messageInfo.put("userId",userIdAuth);
                                        messageInfo.put("message",Message);
                                        messageInfo.put("time", String.valueOf(currentTime));
                                        textMessage.setText("");

                                        if (LocalData.DocumentId!=null) {
                                                db.collection(LocalData.DocumentId)
                                                        .add(messageInfo)
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                        if (task.isSuccessful()) {
                                                                                String docId = Objects.requireNonNull(task.getResult()).getId();

                                                                                Log.d(TAG, "onComplete: message sent" + Message);
                                                                                final Map<String, String> updatedMessageInfo = new HashMap<>();
                                                                                updatedMessageInfo.put("docId", docId);
                                                                                updatedMessageInfo.put("userId", userIdAuth);
                                                                                updatedMessageInfo.put("message", Message);
                                                                                updatedMessageInfo.put("time", String.valueOf(currentTime));


                                                                                db.collection(LocalData.DocumentId)
                                                                                        .document(docId)
                                                                                        .set(updatedMessageInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                        Log.d(TAG, "onComplete: done");
                                                                                                        Log.d(TAG, "onComplete: " + updatedMessageInfo);
                                                                                                } else {
                                                                                                        Log.d(TAG, "onComplete: error");
                                                                                                }
                                                                                        }
                                                                                });


                                                                                progressBarUtil.hideProgress();
                                                                        } else {
                                                                                Toast.makeText(YoutubePlayer.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                                                                                Log.d(TAG, "onComplete: failed");
                                                                                progressBarUtil.hideProgress();
                                                                        }
                                                                }
                                                        });


                                        }}}
                }else Toast.makeText(this, "something is wrong", Toast.LENGTH_SHORT).show();}
        @Override
        public void onBackPressed() {

                if (!fullscreen){
                        super.onBackPressed();

                }
                else{
                        player.setFullscreen(false);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        player.play();
                        fullscreen=false;

                }
        }
}
