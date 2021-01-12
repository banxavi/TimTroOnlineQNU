package com.example.booktro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booktro.Data.DataUser;

public class DangkyActivity extends AppCompatActivity {
    EditText edthoten, edtpass, edtpass1;
    Button btndk,btnlogin;
    public static DataUser sqLite;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        anhxa();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        sqLite = new DataUser(DangkyActivity.this, "room.sqlite1", null, 1);
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=edthoten.getText().toString();
                String txt_password=edtpass.getText().toString();
                String txt_passconfirm = edtpass1.getText().toString();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username",txt_username);
                editor.putString("pass",txt_password);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), DangkyInfoUser.class);

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(DangkyActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length()<6 && txt_passconfirm.length()<6) {
                    Toast.makeText(DangkyActivity.this, "Mật khẩu của bạn phải nhiều hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else if(!txt_password.equals(txt_passconfirm)){
                    Toast.makeText(DangkyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
           else startActivity(intent);
            }
            });
    }
    public void anhxa(){
        edthoten = (EditText)findViewById(R.id.edtname);
        edtpass1 = (EditText)findViewById(R.id.edtpassword2);
        edtpass = (EditText)findViewById(R.id.edtpassword);
        btndk = (Button)findViewById(R.id.btn_register);
        btnlogin = (Button)findViewById(R.id.btndangnhap);
    }
    public void openActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}