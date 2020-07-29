package com.winbee.rbclasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        private Button btn_study_material;
        private ImageView img_share,WebsiteHome;
        MessageAdapter adapter;
        FirebaseAuth auth =FirebaseAuth.getInstance();
        EditText textMessage;
        ImageView sendButton;
        RecyclerView messageRecyclerView;
        private TextView textView;
        private ProgressBarUtil progressBarUtil;
        RelativeLayout relativeLayoutLive;
        MessageAdapter messageAdapter;
        ArrayList<LiveChatModel> messagesArrayList;
        private LinearLayout layout_course, layout_test, layout_home, layout_current, layout_doubt;



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        public static String userIdAuth;
        private YouTubePlayerView youTubePlayerView;
        Boolean fullscreen=false;
        YouTubePlayer player;

        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_youtube_player);
                textMessage=findViewById(R.id.editTextMessageLive);
                sendButton=findViewById(R.id.sendMessageLive);
                messageRecyclerView=findViewById(R.id.liveMessageRecyclerViewLive);
                textView=findViewById(R.id.textNoMessageLive);
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
                layout_home = findViewById(R.id.layout_home);
                layout_home.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent home = new Intent(YoutubePlayer.this,MainActivity.class);
                                startActivity(home);
                        }
                });

                layout_course = findViewById(R.id.layout_course);
                layout_course.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent live = new Intent(YoutubePlayer.this, YouTubeVideoList.class);
                                startActivity(live);
                        }
                });
                layout_test = findViewById(R.id.layout_test);
                layout_test.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Toast.makeText(YoutubePlayer.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent doubt = new Intent(MainActivity.this,DoubtActivity.class);
//                startActivity(doubt);
                        }
                });
                layout_current = findViewById(R.id.layout_current);
                layout_current.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(YoutubePlayer.this, CurrentAffairsActivity.class);
                                startActivity(intent);
                        }
                });
                layout_doubt = findViewById(R.id.layout_doubt);
                layout_doubt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(YoutubePlayer.this, DoubtActivity.class);
                                startActivity(intent);
                        }
                });



                btn_study_material=findViewById(R.id.btn_study_material);
                btn_study_material.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(YoutubePlayer.this,StudyMaterial.class);
                                startActivity(intent);
                        }
                });
                /** Initializing YouTube Player View **/
                youTubePlayerView=findViewById(R.id.youtube_player);
                youTubePlayerView.initialize(API_KEY,this);
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
                player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                                if (b){
                                        player.play();
                                        fullscreen=true;

                                }
                                if (!b){
                                        if (!player.isPlaying()){
                                                player.play();
                                        }
                                        fullscreen=false;

                                }
                        }
                });

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
                        if (fullscreen){
                                player.setFullscreen(false);
                                player.play();

                        }
                        else {
                                super.onBackPressed();
                        }
        }
}
