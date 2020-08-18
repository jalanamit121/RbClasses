package com.winbee.rbclasses;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;

public class VimeoActivity extends AppCompatActivity {
    VimeoPlayerView vimeoPlayer;
    TextView playerStateTextView, playerCurrentTimeTextView, playerVolumeTextView;
    SeekBar volumeSeekBar;
    Button playButton, pauseButton, getCurrentTimeButton, loadVideoButton, colorButton;
    boolean fullscreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_vimeo);
        vimeoPlayer = findViewById(R.id.vimeoPlayer);
        setupView();


    }

    private void setupView() {
        getLifecycle().addObserver(vimeoPlayer);
        vimeoPlayer.initialize(Integer.parseInt(LocalData.LiveId));
        // vimeoPlayer.initialize(441740538);
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.setFullscreenClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullscreen){
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    vimeoPlayer.setLayoutParams(params);
                    fullscreen = false;

                }else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    vimeoPlayer.setLayoutParams(params);
                    fullscreen = true;
                }
            }
        });


    }

    @Override
    public void onBackPressed(){
//        if (fullscreen){
//            }
//        else{
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
//        vimeoPlayer.setLayoutParams(params);
//        fullscreen = false;}
        super.onBackPressed();

    }
}

