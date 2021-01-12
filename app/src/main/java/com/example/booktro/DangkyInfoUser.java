package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booktro.Data.DataUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DangkyInfoUser extends AppCompatActivity {
    EditText hoten, sdt, namsinh;
    ImageView avatar,back;
    Button xacnhan,btnupanh;
    public static DataUser sqLite;
    String username,password;
    public static final String PREFS_NAME = "MyPrefsFile";
   final int  REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_info_user);
        anhxa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
        sqLite = new DataUser(DangkyInfoUser.this, "room.sqlite1", null, 1);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final String username = settings.getString("username", "defaultName");
        final String password = settings.getString("pass", "defaultName");
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_hoten =hoten.getText().toString();
                String txt_namsinh =namsinh.getText().toString();
                String txt_sdt =sdt.getText().toString();
                imganhtoByte(avatar);

                if (TextUtils.isEmpty(txt_hoten) || TextUtils.isEmpty(txt_namsinh) ||TextUtils.isEmpty(txt_sdt)){
                    Toast.makeText(DangkyInfoUser.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {

                        sqLite.insertUser(
                     username,password,txt_hoten,txt_namsinh,txt_sdt,imganhtoByte(avatar)
                        );
                        Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DangkyInfoUser.this, "Please Enter different name....", Toast.LENGTH_LONG).show();
                    }
                   openActivity();
                }
            }
        });
        btnupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        DangkyInfoUser.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
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
                avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void anhxa(){
        btnupanh = findViewById(R.id.btnanh);
        avatar = findViewById(R.id.avatar);
        hoten = (EditText)findViewById(R.id.edhoTen);
        namsinh = (EditText)findViewById(R.id.edtnamsinh);
        sdt = (EditText)findViewById(R.id.edtsdt);
        avatar = (ImageView) findViewById(R.id.avatar);
        xacnhan = findViewById(R.id.btnxacnhan);
        back = findViewById(R.id.btnbackdk);
        }
    public void openActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public static byte[] imganhtoByte(ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void openActivity1() {
        Intent intent = new Intent(this, DangkyActivity.class);
        startActivity(intent);
    }
}