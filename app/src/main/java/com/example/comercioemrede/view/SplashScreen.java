package com.example.comercioemrede.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.comercioemrede.R;

public class SplashScreen extends AppCompatActivity {

    Animation middleAnimation;
    private ImageView logomarca;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        logomarca = findViewById(R.id.imgLogoN);


        logomarca.setAnimation(middleAnimation);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent it = new Intent(SplashScreen.this, CatalogoActivity.class);
                startActivity(it);
                finish();

            }
        },SPLASH_TIME_OUT);

    }
}
