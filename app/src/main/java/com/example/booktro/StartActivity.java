package com.example.booktro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.Data.DataUser;
import com.facebook.login.Login;

public class StartActivity extends AppCompatActivity {
    private static int TIME_OUT = 2500; //Time to launch the another activity
    public static DataUser sqLite;
    public static DataRoom sqLiteHelper;
    ImageView imgst;
    TextView tvst;
    Animation topAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imgst = findViewById(R.id.imgst);
        tvst = findViewById(R.id.tvstart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sqLite = new DataUser(StartActivity.this, "room.sqlite1", null, 1);
        sqLiteHelper = new DataRoom(StartActivity.this, "room.sqlite1", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS ROOM2(Idroom INTEGER PRIMARY KEY AUTOINCREMENT,IdUser INTEGER, diachi VARCHAR, songuoio VARCHAR, giaphong VARCHAR, mota VARCHAR, image BLOB)");
        sqLite.queryData("CREATE TABLE IF NOT EXISTS USER2(IdUser INTEGER PRIMARY KEY AUTOINCREMENT, username UNIQUE, password VARCHAR, hoTen VARCHAR, sdt VARCHAR, namsinh VARCHAR, avatar BLOB)");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imgst.setAnimation(topAnim);
        tvst.setAnimation(bottomAnim);
    }
    }