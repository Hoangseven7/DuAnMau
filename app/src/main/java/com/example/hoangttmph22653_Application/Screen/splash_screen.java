package com.example.hoangttmph22653_Application.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.hoangttmph22653_Application.MainActivity;
import com.example.hoangttmph22653_Application.R;


public class splash_screen extends AppCompatActivity {
    LinearLayout splash_screen;
    ImageView imageView;
    EditText id_MSSV;
    Button btnVao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_screen = findViewById(R.id.splash_screen);
        id_MSSV = findViewById(R.id.id_MSSV);
        btnVao = findViewById(R.id.btnVao);
        Handler handler = new Handler();
        imageView = findViewById(R.id.img_splash);
       Glide.with(this).load(R.drawable.hello).into(imageView);
        btnVao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mssv = "PH22653";
                if (id_MSSV.getText().toString().equals(Mssv)){
                    Toast.makeText(getApplicationContext(),"Vào Thành Công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(splash_screen.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Vào Thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}