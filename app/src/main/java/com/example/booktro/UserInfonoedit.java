package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booktro.Data.DataUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserInfonoedit extends AppCompatActivity {
    TextView id;
    TextView hoten,sdt,namsinh;
    ImageView imgaanh;
    Button btnupdate;
    String hoten1,diachi1, sdt1;
    int id1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infonoedit);
        anhxa(); //ánh xạ các
        Intent intent = getIntent(); //lấy dữ liệu được chuyển từ Class Inforoom
        if (intent != null) {
            id1 = Integer.parseInt(intent.getStringExtra("iduser"));
            hoten1 = intent.getStringExtra("hoten");
            diachi1 = intent.getStringExtra("namsinh");
            sdt1 = intent.getStringExtra("sdt");
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("avatar");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ImageView image = findViewById(R.id.avatar);
            image.setImageBitmap(bmp);
        }
        id.setText(String.valueOf(id1));
        id.setVisibility(View.INVISIBLE);
        hoten.setText(hoten1);
        namsinh.setText(diachi1);
        sdt.setText(sdt1);
    }

    public void anhxa(){
        id = findViewById(R.id.edtid);
        hoten = findViewById(R.id.hoten);
        sdt = findViewById(R.id.sdt);
        namsinh = findViewById(R.id.diachi);
        imgaanh = findViewById(R.id.avatar);
        btnupdate = findViewById(R.id.btnupdate);
    }
}