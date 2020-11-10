package com.winbee.rbclasses;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
public class YouTubeComplete extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    TextView chatbox,noclasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_complete);
        noclasses=findViewById(R.id.noclasses);
        chatbox=findViewById(R.id.chatbox);
        this.youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                chatbox.setVisibility(GONE);
                noclasses.setVisibility(GONE);

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                chatbox.setVisibility(VISIBLE);
                noclasses.setVisibility(VISIBLE);

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

    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen()) {
            youTubePlayerView.exitFullScreen();
        } else
            super.onBackPressed();
    }

}