package com.example.plasma_4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
Animation top_anim,bottom_anim;
ImageView image;
TextView slog,mainslog;
private static int SPLASH_SCREEN=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        top_anim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image=findViewById(R.id.logo);
        mainslog=findViewById(R.id.mainslogan);
        slog=findViewById(R.id.slogan);
        image.setAnimation(top_anim);
        mainslog.setAnimation(bottom_anim);
        slog.setAnimation(bottom_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}