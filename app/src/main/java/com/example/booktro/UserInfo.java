package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.Data.DataUser;
import com.example.booktro.Model.Room;
import com.example.booktro.Model.User;
import com.example.booktro.adapter.CustomAdapterRoom;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {
    EditText id;
    EditText hoten;
    EditText sdt;
    EditText namsinh;
    ImageView imgaanh;
    ImageView back;
    Button btnupdate,btnshow;
    ImageView home;
    String hoten1,diachi1, sdt1;
    int id1;
    Dialog dialog;
    CustomAdapterRoom customAdapterRoom;
    static final int REQUEST_CODE_GALLERY=999;
    private DataUser sqLiteHelper1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        anhxa();
        sqLiteHelper1 = new DataUser(UserInfo.this, "room.sqlite1", null, 1); // gán biến để thao tác trong DataUser


         final Intent intent = getIntent(); //get dữ liệu đưuọc truyền từ class home
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserInfo.this, Home.class);
                startActivity(intent1);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserInfo.this, Home.class);
                startActivity(intent1);
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 imganhtoByte(imgaanh);
                try {
                    sqLiteHelper1.updateData(
                            hoten.getText().toString().trim(),
                            sdt.getText().toString().trim(),
                            namsinh.getText().toString().trim(),
                            DangkyInfoUser.imganhtoByte(imgaanh),
                            Integer.parseInt(id.getText().toString())
                    );
                    Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Home.customAdapter.notifyDataSetChanged();

            }
        });
        imgaanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        UserInfo.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, ListRoomFromUser.class);
                intent.putExtra("id", id.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgaanh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] imganhtoByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



    public void anhxa(){
        id = (EditText)findViewById(R.id.edtid);
        hoten = (EditText)findViewById(R.id.hoten);
        sdt = (EditText)findViewById(R.id.sdt);
        namsinh = (EditText)findViewById(R.id.diachi);
        imgaanh = findViewById(R.id.avatar);
        btnupdate = findViewById(R.id.btnupdate);
        btnshow  = findViewById(R.id.btnshowroom);
        back = findViewById(R.id.btnback1);
        home = findViewById(R.id.btnhomeinfo);
    }
}