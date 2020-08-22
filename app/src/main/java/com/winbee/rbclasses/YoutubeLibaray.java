package com.winbee.rbclasses;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.winbee.rbclasses.adapter.MessageAdapter;
import com.winbee.rbclasses.model.LiveChatModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class YoutubeLibaray extends AppCompatActivity {
    public static String userIdAuth;
    MessageAdapter adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText textMessage;
    ImageView sendButton;
    RecyclerView messageRecyclerView;
    RelativeLayout relativeLayoutLive;
    MessageAdapter messageAdapter;
    ArrayList<LiveChatModel> messagesArrayList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView img_share, WebsiteHome;
    private TextView textView;
    private ProgressBarUtil progressBarUtil;
    private YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_libaray);
        textMessage = findViewById(R.id.editTextMessageLive);
        sendButton = findViewById(R.id.sendMessageLive);
        messageRecyclerView = findViewById(R.id.liveMessageRecyclerViewLive);
        textView = findViewById(R.id.textNoMessageLive);
        progressBarUtil = new ProgressBarUtil(this);
        relativeLayoutLive = findViewById(R.id.relative_layout_live);
        messagesArrayList = new ArrayList<>();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        retriveMessages();
//        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        messageAdapter = new MessageAdapter(this, messagesArrayList);
//        messageRecyclerView.setAdapter(messageAdapter);

        //  public static final String API_KEY = "AIzaSyBlEPocq2s2bDmWDMBRXAf8Mhf3wlFNYGI";
        //        public static final String VIDEO_ID = "j36wPW4bGIs";
        Button btn_study_material = findViewById(R.id.btn_study_material);
        btn_study_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YoutubeLibaray.this, StudyMaterial.class);
                startActivity(intent);
            }
        });
        this.youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                textMessage.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                sendButton.setVisibility(View.GONE);
                // hide softkeys if it is already open

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                textMessage.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
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

    private void retriveMessages() {
        progressBarUtil.showProgress();


        if (LocalData.DocumentId != null) {
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
//                                Animation in = new AlphaAnimation(0.0f, 1.0f);
//                                in.setDuration(1000);
//                                textView.setAnimation(in);
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
                                            Log.d(TAG, "onEvent: " + messagesArrayList.get(i).getDocId());
                                        }

                                        addingDataToMessagedAdapter(messagesArrayList);
                                    }
                                }
                            }
                        }
                    });


        } else
            Log.d(TAG, "retriveMessages: error");

    }

    private void addingDataToMessagedAdapter(ArrayList<LiveChatModel> messagesArrayList) {
        //messageAdapter.updateData(messagesArrayList);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messagesArrayList);
        messageRecyclerView.setAdapter(messageAdapter);



    }

    private void sendMessage() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (auth != null) {
                userIdAuth = auth.getCurrentUser().getUid();
                final String Message = textMessage.getText().toString();
                if (Message.isEmpty()) {
                    Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();


                } else {
                    final Date currentTime = Calendar.getInstance().getTime();
                    progressBarUtil.hideProgress();
                    final Map<String, String> messageInfo = new HashMap<>();
                    messageInfo.put("userId", userIdAuth);
                    messageInfo.put("message", Message);
                    messageInfo.put("time", String.valueOf(currentTime));
                    textMessage.setText("");
                    if (LocalData.DocumentId != null) {
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
                                            Toast.makeText(YoutubeLibaray.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "onComplete: failed");
                                            progressBarUtil.hideProgress();
                                        }
                                    }
                                });


                    }
                }
            }
        } else Toast.makeText(this, "something is wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen()) {
            youTubePlayerView.exitFullScreen();
        } else
            super.onBackPressed();
    }

}