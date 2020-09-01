package com.winbee.rbclasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class SplashPageActivity extends AppCompatActivity {
  PrefManager prefManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash_page);
    Thread timer = new Thread() {
      public void run() {
        try {
          sleep(2 * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {

          prefManager = new PrefManager(SplashPageActivity.this);
          if (prefManager.isFirstTimeLaunch()) {

            startActivity(new Intent(getApplicationContext(), IntroSliderActivity.class));
            finish();
          } else {
            launchHomeScreen();
            finish();
          }
        }
      }
    };
    timer.start();
  }


  private void launchHomeScreen() {
    if (SharedPrefManager.getInstance(this).isLoggedIn()) {
      finish();
      startActivity(new Intent(this, MainActivity.class));
      return;
    }else {
      finish();
      Intent intent = new Intent(this,LoginActivity.class);
      startActivity(intent);
    }
  }
}
