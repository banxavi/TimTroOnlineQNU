package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booktro.Data.DataUser;
import com.example.booktro.Model.Room;
import com.example.booktro.Model.User;
import com.example.booktro.Untils.SessionManagement;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btn_login, btnloginfb;
   SessionManagement sessionManagement;
    TextView forgot_password, signup;
    public static DataUser sqLite;
    private CallbackManager fbcallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        sqLite = new DataUser(LoginActivity.this, "room.sqlite1", null, 1);
        sessionManagement = new SessionManagement(getApplication());
        //sự kiện nút signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DangkyActivity.class));
            }
        });
        //sự kiện nút đăng nhập
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_password = password.getText().toString();
                Boolean checkacc = sqLite.checkuser(txt_username,txt_password);
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(checkacc==false){
                    Toast.makeText(getApplicationContext(),"Username hoặc Password không đúng", Toast.LENGTH_SHORT).show();
                }
                else if(checkacc==true){
                    sessionManagement.setLogin(true);
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("user", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user", txt_username);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                else
                Toast.makeText(getApplicationContext(),"Username hoặc password không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
        checkSession();
    }
    public void anhxa(){
        username = findViewById(R.id.user);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.signup);

    }
    private void checkSession() {
        if(!sessionManagement.check()){
        }
        else {
            Intent intent = new Intent(getApplication(),Home.class);
            startActivity(intent);
            finish();
        }
    }

    }

