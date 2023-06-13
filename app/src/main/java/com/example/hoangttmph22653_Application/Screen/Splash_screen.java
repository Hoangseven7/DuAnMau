package com.example.hoangttmph22653_Application.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.hoangttmph22653_Application.R;


public class Splash_screen extends AppCompatActivity {
    LinearLayout splash_screen;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_screen = findViewById(R.id.splash_screen);
        Handler handler = new Handler();
        imageView = findViewById(R.id.img_splash);
       Glide.with(this).load(R.drawable.hello).into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_screen.this,MainActivity_Login.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}