package com.winbee.rbclasses;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class YouTubeComplete extends AppCompatActivity implements Player.EventListener {
    private YouTubePlayerView youTubePlayerView;
    TextView chatbox, noclasses;

    //Youtube through exo

    private static final int ITAG_FOR_AUDIO = 140;
    private boolean isError;
    SimpleExoPlayer player;
    private PlayerView playerView;

    private FrameLayout exo_ffwd, exo_rew, moreButton;
    String downloadUrl;
    VideoMeta videoMeta;

    boolean fullscreen = false;
    private List<YoutubeLibaray.YtFragmentedVideo> formatsToShowList;

    int currentquality = 360;

    public static String youtubeLink;
    private ImageView fullscreenButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_complete);
        youtubeLink = LocalData.LiveId;
        noclasses = findViewById(R.id.noclasses);
        chatbox = findViewById(R.id.chatbox);
        progressBar = findViewById(R.id.progressBarOffline);

        youtubeThroughExoSteup();
        Toast.makeText(this, "Loading your video...", Toast.LENGTH_SHORT).show();
        Log.d("12345", "onMenuItemClick: " + Html.fromHtml(LocalData.Discription) + "  " + Html.fromHtml(LocalData.Subject) + " " + Html.fromHtml(LocalData.Topic));

    }

    private void youtubeThroughExoSteup() {
        String TAG = "TAG";
        playerView = findViewById(R.id.video_player_view_offline);
        exo_rew = findViewById(R.id.exoo_rew);
        exo_ffwd = findViewById(R.id.exoo_ffwd);
        fullscreenButton = findViewById(R.id.fullscreenButton);
        moreButton = findViewById(R.id.moreButton);

        moreButton.setVisibility(View.GONE);
        fullscreenButton.setVisibility(View.GONE);

        exo_ffwd.setEnabled(false);
        exo_rew.setEnabled(false);

        exo_rew.setOnClickListener(new YoutubeLibaray.DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Log.d("TAG", "onSingleClick: rew");
                playerView.showController();
            }

            @Override
            public void onDoubleClick(View v) {
                long rewtime = player.getCurrentPosition() - 10000;
                if (player.getCurrentPosition() < 10000) {
                    player.seekTo(0);
                } else {
                    player.seekTo(rewtime);

                }
                Log.d("TAG", "onDoubleClick: rew " + rewtime);


            }
        });
        exo_ffwd.setOnClickListener(new YoutubeLibaray.DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Log.d("TAG", "onSingleClick: forward");
                playerView.showController();
            }

            @Override
            public void onDoubleClick(View v) {
                Log.d("TAG", "onDoubleClick: forward ");
                long ffwwtime = player.getCurrentPosition() + 10000;
                if (player.getCurrentPosition() > player.getDuration() - 10000) {
                    player.seekTo(player.getDuration());
                } else {
                    player.seekTo(ffwwtime);

                }
            }
        });
        String url = youtubeLink;
        String completeUrl = "https://www.youtube.com/watch?v=" + url;
        Log.d(TAG, "youtubeThroughExoSteup: " + completeUrl);

//        Log.d(TAG, "youtubeThroughExoSteup: "+LocalData.CourseName+" "+LocalData.SubjectName+" "+LocalData.TopicName);
//        Log.d(TAG, "youtubeThroughExoSteup: "+LocalData.CourseName+" "+LocalData.TopicName+" "+LocalData.SubjectName);

        Log.d("TAG", "downloadFromUrl: " + SharedPrefManager.getInstance(this).refCode().getUsername()
                + "  " + SharedPrefManager.getInstance(this).refCode().getUserId());

        playVideo(completeUrl);

        setOnclickListnertoMoreButton();

        setOnclickListnertoFullscreenButton();


    }


    private void addFormatToList(YtFile ytFile, SparseArray<YtFile> ytFiles) {
        //   Log.d("TAG1", "addFormatToList: " + ytFiles.size());


        int height = ytFile.getFormat().getHeight();
        if (height != -1) {
            for (YoutubeLibaray.YtFragmentedVideo frVideo : formatsToShowList) {
                if (frVideo.height == height && (frVideo.videoFile == null ||
                        frVideo.videoFile.getFormat().getFps() == ytFile.getFormat().getFps())) {
                    return;
                }
            }
        }
        YoutubeLibaray.YtFragmentedVideo frVideo = new YoutubeLibaray.YtFragmentedVideo();
        frVideo.height = height;
        if (ytFile.getFormat().isDashContainer()) {
            if (height > 0) {
                frVideo.videoFile = ytFile;
                frVideo.audioFile = ytFiles.get(ITAG_FOR_AUDIO);
            } else {
                frVideo.audioFile = ytFile;
            }
        } else {
            frVideo.videoFile = ytFile;
        }
        formatsToShowList.add(frVideo);
    }

    private void playThatVideo(String downloadUrl, boolean isError, String url, YoutubeLibaray.YtFragmentedVideo ytFragmentedVideo) {

        Log.d("1234", "playThatVideo: " + ytFragmentedVideo.audioFile);
        Log.d("1234", "playThatVideo: " + ytFragmentedVideo.videoFile);

        if (ytFragmentedVideo.audioFile == null) {
            if (player == null) {
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);

                player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
                playerView.setPlayer(player);

                DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(
                        this, Util.getUserAgent(this, "exoplayer"));
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(downloadUrl));

                player.prepare(mediaSource);
                player.addListener(this);
                player.setPlayWhenReady(true);
            } else {


                long currentduration = player.getCurrentPosition();
                player.stop();
                player = null;


                DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
                player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
                playerView.setPlayer(player);

                DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(
                        this, Util.getUserAgent(this, "exoplayer"));
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(downloadUrl));

                player.prepare(mediaSource);
                player.addListener(this);
                player.setPlayWhenReady(true);
                player.seekTo(currentduration);

            }

        } else {
            long currentduration = player.getCurrentPosition();
            player.stop();
            player = null;


            DefaultTrackSelector trackSelectorforvideo = new DefaultTrackSelector(this);
            player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelectorforvideo).build();
            playerView.setPlayer(player);

            DefaultDataSourceFactory defaultDataSourceFactoryforvideo = new DefaultDataSourceFactory(
                    this, Util.getUserAgent(this, "exoplayer"));

            MediaSource mediaSourcevideo = new ProgressiveMediaSource.Factory(defaultDataSourceFactoryforvideo)
                    .createMediaSource(Uri.parse(ytFragmentedVideo.videoFile.getUrl()));
            MediaSource mediaSourceforAudio = new ProgressiveMediaSource.Factory(defaultDataSourceFactoryforvideo)
                    .createMediaSource(Uri.parse(ytFragmentedVideo.audioFile.getUrl()));

            MergingMediaSource mergedSource = new MergingMediaSource(mediaSourcevideo, mediaSourceforAudio);


            player.prepare(mergedSource);
            player.addListener(this);
            player.setPlayWhenReady(true);
            player.seekTo(currentduration);


        }
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);


    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case SimpleExoPlayer.STATE_READY:
                /// qualitybutton.setEnabled(true);
                exo_ffwd.setEnabled(true);
                exo_rew.setEnabled(true);
                moreButton.setEnabled(true);
                fullscreenButton.setEnabled(true);
                moreButton.setVisibility(View.VISIBLE);
                fullscreenButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(GONE);
                break;

            case SimpleExoPlayer.STATE_BUFFERING:
                progressBar.setVisibility(GONE);
        }
    }

    public void playbackspeed(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.speed, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String id = String.valueOf(item.getTitle());
                String value = id.substring(0, id.length() - 1);
                String playbackspeed = value + "f";

                Log.d("TAG", "onMenuItemClick: " + id);
                Log.d("TAG", "onMenuItemClick: " + value);
                Log.d("TAG", "onMenuItemClick: " + Float.parseFloat(playbackspeed));

                PlaybackParameters param = new PlaybackParameters(Float.parseFloat(playbackspeed));
                player.setPlaybackParameters(param);
                return false;
            }
        });
        popupMenu.show();

    }

    public void Quality(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        for (int i = 0; i < formatsToShowList.size(); i++) {
            YoutubeLibaray.YtFragmentedVideo ytFrVideo = formatsToShowList.get(i);
            if (ytFrVideo.height == -1) {
                Log.d("TAG", "Audio" + ytFrVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s");
            } else {
                Log.d("TAG", (ytFrVideo.videoFile.getFormat().getFps() == 60) ? ytFrVideo.height + "p60" :
                        ytFrVideo.height + "p");
                popupMenu.getMenu().add(ytFrVideo.height + "p");
            }
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int index;
                String title = item.getTitle().toString().substring(0, item.getTitle().length() - 1);
                Log.d("TAG", "onMenuItemClick: " + title);
                for (int i = 0; i < formatsToShowList.size(); i++) {
                    YoutubeLibaray.YtFragmentedVideo ytFrVideo = formatsToShowList.get(i);
                    if (ytFrVideo.height == -1) {
                        Log.d("TAG", "Audio" + ytFrVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s");
                    } else {
                        Log.d("TAG", (ytFrVideo.videoFile.getFormat().getFps() == 60) ? ytFrVideo.height + "p60" :
                                ytFrVideo.height + "p");
                        if (ytFrVideo.height == Integer.parseInt(title)) {
                            if (ytFrVideo.height != currentquality) {
                                currentquality = Integer.parseInt(title);
                                playThatVideo(ytFrVideo.videoFile.getUrl(), false, formatsToShowList.get(0).audioFile.getUrl(), formatsToShowList.get(i));
                            }
                        }
                    }


                }
                if (fullscreen) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    public void playVideo(String youtube) {

        youtubeLink = youtube;
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {

                    downloadUrl = "";
                    try {
                        isError = false;
                        //   downloadUrl = ytFiles.get(itag).getUrl();
                        videoMeta = vMeta;
                        formatsToShowList = new ArrayList<>();
                        for (int i = 0, itag; i < ytFiles.size(); i++) {
                            itag = ytFiles.keyAt(i);
                            YtFile ytFile = ytFiles.get(itag);


                            if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 0) {
                                addFormatToList(ytFile, ytFiles);
                            }
                        }
                        Collections.sort(formatsToShowList, new Comparator<YoutubeLibaray.YtFragmentedVideo>() {
                            @Override
                            public int compare(YoutubeLibaray.YtFragmentedVideo lhs, YoutubeLibaray.YtFragmentedVideo rhs) {
                                return lhs.height - rhs.height;
                            }
                        });
                        // playThatVideo(formatsToShowList.get(2).videoFile.getUrl(),false);
                        Log.d("TAG", "onExtractionComplete: " + formatsToShowList.size());
                        for (int i = 0; i < formatsToShowList.size(); i++) {
                            YoutubeLibaray.YtFragmentedVideo ytFrVideo = formatsToShowList.get(i);
                            if (ytFrVideo.height == -1) {
                                Log.d("TAG", "Audio" + ytFrVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s");
                            } else {
                                Log.d("TAG", (ytFrVideo.videoFile.getFormat().getFps() == 60) ? ytFrVideo.height + "p60" :
                                        ytFrVideo.height + "p");
                                if (ytFrVideo.height == currentquality) {
                                    playThatVideo(ytFrVideo.videoFile.getUrl(), false, formatsToShowList.get(0).audioFile.getUrl(), formatsToShowList.get(i));
                                }
                            }


                        }

                    } catch (Exception e) {
                        isError = true;
                        downloadUrl = "error";
                        Log.d("TAG", "onExtractionComplete: " + e.getMessage());
                    }
                    //playThatVideo(downloadUrl, isError);
                    // Log.d("TAG", "onExtractionComplete: " + downloadUrl);
                }
            }
        }.extract(youtubeLink, true, true);

    }

    private void setOnclickListnertoFullscreenButton() {


        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(YouTubeComplete.this, R.drawable.exo_controls_fullscreen_enter));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = (int) (250 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);
                    noclasses.setVisibility(View.VISIBLE);
                    chatbox.setVisibility(View.VISIBLE);


                    fullscreen = false;
                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(YouTubeComplete.this, R.drawable.exo_controls_fullscreen_exit));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                    noclasses.setVisibility(GONE);
                    chatbox.setVisibility(GONE);

                    playerView.setLayoutParams(params);


                    fullscreen = true;
                }
            }
        });

    }

    private void setOnclickListnertoMoreButton() {

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(YouTubeComplete.this, v);
                final String quality, playbackSpeed, download;
                quality = "Quality";
                playbackSpeed = "Playback Speed";
                download = "Download";

                popupMenu.getMenu().add(quality);
                popupMenu.getMenu().add(playbackSpeed);
                popupMenu.getMenu().add(download);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString() == quality) {
                            Quality(v);

                        }
                        if (item.getTitle().toString() == playbackSpeed) {
                            playbackspeed(v);

                        }
                        if (item.getTitle().toString() == download) {

                            gotodownload();
                            Log.d("12345", "onMenuItemClick: " + LocalData.CourseName1 + "  " + LocalData.Topic + " " + LocalData.Subject);
                            // matlab boss problem kya ho rahi he sahi naam kyu nahi aarha
                            //mene phone me download kr liya//login karo ek esec
                            //sahi namm lekhpal hi hai
                            // me aay 5 min me kr liya

                        }

                        if (fullscreen) {
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                            if (getSupportActionBar() != null) {
                                getSupportActionBar().hide();
                            }
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }


                        return false;
                    }
                });

                popupMenu.show();

            }
        });


    }

    private void gotodownload() {
        startActivity(new Intent(this, DownloadVideo.class));
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
                player.release();
            } else {
                player.stop();
                player.release();
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            if (player.isPlaying()) {
                player.setPlayWhenReady(false);
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (player != null) {
            if (player.isPlaying()) {
                player.setPlayWhenReady(true);
            }
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!fullscreen) {
            super.onBackPressed();
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
            playerView.setLayoutParams(params);
            noclasses.setVisibility(View.VISIBLE);
            chatbox.setVisibility(View.VISIBLE);

            fullscreen = false;
        }
    }

}