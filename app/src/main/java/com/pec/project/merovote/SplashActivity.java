package com.pec.project.merovote;

import androidx.appcompat.app.AppCompatActivity;

import com.pec.project.merovote.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Animation zoomIn, slideIn;
    ImageView logo,vote,logofin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        slideIn = AnimationUtils.loadAnimation(this,R.anim.slide_in);

        logo = findViewById(R.id.logo);
        vote = findViewById(R.id.vote);
        logofin = findViewById(R.id.logofinal);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                logo.setVisibility(View.VISIBLE);
                vote.setVisibility(View.VISIBLE);
                logo.startAnimation(zoomIn);
                vote.startAnimation(slideIn);

            }
        },2000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
            }
        },5000);




    }
}