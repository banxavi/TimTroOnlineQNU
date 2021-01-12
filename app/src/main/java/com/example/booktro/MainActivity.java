package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.Untils.SessionManagement;
import com.example.booktro.adapter.CustomAdapterRoom;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText edtHoten, edtDiachi, edtSdt, edtSonguoi,edtgiaPhong,iduser;
    EditText edtMota;
    Button btnUpanh, btnPost;
    ImageView imganh,btnback;
    private CustomAdapterRoom customAdapter;
    public static DataRoom sqLiteHelper;
    public static UserData sqLiteHelperUser;
    final int REQUEST_CODE_CAMERA = 1888;
    final int REQUEST_CODE_GALLERY = 999;
    int id1;
    SessionManagement sessionManagement;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        sqLiteHelper = new DataRoom(MainActivity.this, "room.sqlite1", null, 1);
        sessionManagement = new SessionManagement(getApplication());
        if (sessionManagement.check()) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("iduser",0);
            String user = sharedPreferences.getString("iduser",null);
            iduser.setText(String.valueOf(user));}
        iduser.setVisibility(View.INVISIBLE);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sqLiteHelper.insertData(
                            iduser.getText().toString().trim(),
                            edtDiachi.getText().toString().trim(),
                            edtSonguoi.getText().toString().trim(),
                            edtgiaPhong.getText().toString().trim() + "VNĐ",
                            edtMota.getText().toString().trim(),
                            imganhtoByte(imganh)
                    );
                    customAdapter.notifyDataSetChanged();
                    edtDiachi.setText("");
                    edtSonguoi.setText("");
                    edtgiaPhong.setText("");
                    edtMota.setText("");
                    imganh.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        btnUpanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
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
               imganh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void openActivity1() {
        Intent intent = new Intent(this, DangkyActivity.class);
        startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    public void anhxa() {
        edtHoten = (EditText) findViewById(R.id.edtchuphong);
        edtDiachi = (EditText) findViewById(R.id.edtdichi);
        edtSdt = (EditText) findViewById(R.id.edtsdt);
        edtSonguoi = (EditText) findViewById(R.id.edtsonguoio);
        edtMota = (EditText) findViewById(R.id.edtmota);
        btnUpanh = (Button) findViewById(R.id.btnUpAnh);
        btnPost = (Button) findViewById(R.id.btnxacnhan);
        btnback = findViewById(R.id.btnback);
        imganh = (ImageView)findViewById(R.id.avatar);
        edtgiaPhong = (EditText)findViewById(R.id.edtgia);
        iduser = (EditText)findViewById(R.id.edtuserid);

    }
    public static byte[] imganhtoByte(ImageView imageView) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }
}