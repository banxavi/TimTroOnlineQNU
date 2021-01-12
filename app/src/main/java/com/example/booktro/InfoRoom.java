package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booktro.Model.InfoAll;
import com.example.booktro.Model.Room;
import com.example.booktro.Model.User;
import com.example.booktro.adapter.CustomAdapterRoom;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.booktro.Home.customAdapter;
import static com.example.booktro.Home.sqLiteHelper1;
public class InfoRoom extends AppCompatActivity {

    ListView list;
    private TextView edthoten;
    private TextView edtmota, edtgia,edtid,edidiachi, edtsdt,edtsonguoio;
    private Button btnlienhe,btnhome;
    private TextView tvmap;
    ImageView imghinhanh,back;
    int id;
    String hoten,songuoio,diachi,sdt,mota,gia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_room);
        anhxa();
        list.setAdapter(customAdapter);

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openActivity();
           }
       });
       tvmap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String url = "http://maps.google.com/maps?daddr=" + edidiachi.getText().toString();
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
               intent.setPackage("com.google.android.apps.maps");
               startActivity(intent);
           }
       });
       btnhome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(InfoRoom.this, Home.class);
               startActivity(intent);
           }
       });

       edthoten.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String text = edtid.getText().toString().trim();
               Cursor cursor = sqLiteHelper1.getData("SELECT * FROM USER2 where IdUser LIKE '%" + text + "%'");
               while (cursor.moveToNext()) {
                   Intent intent = new Intent(InfoRoom.this, UserInfonoedit.class);
                   intent.putExtra("iduser", cursor.getString(0));
                   intent.putExtra("hoten", cursor.getString(3));
                   intent.putExtra("sdt", cursor.getString(4));
                   intent.putExtra("namsinh", cursor.getString(5));
                   Bitmap bmp = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length);
                   ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                   byte[] byteArray = stream.toByteArray();
                   intent.putExtra("avatar", byteArray);
                   startActivity(intent);
               }
           }
       });

        final Intent intent = getIntent();
        if (intent != null) {
                id = Integer.parseInt(intent.getStringExtra("id"));
                hoten = intent.getStringExtra("hoten");
                diachi = intent.getStringExtra("diachi");
                sdt = intent.getStringExtra("sdt");
                songuoio = intent.getStringExtra("songuoio");
                gia = intent.getStringExtra("gia");
                mota = intent.getStringExtra("mota");
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("hinhanh");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ImageView image = findViewById(R.id.avatar);
            Bitmap resized = Bitmap.createScaledBitmap(bmp, 200, 300, true);
            image.setImageBitmap(resized);
            }
        edtid.setText(String.valueOf(id));
        edtid.setEnabled(false);
        edtid.setVisibility(View.INVISIBLE);
        edthoten.setText(hoten);
        edidiachi.setText(diachi);
        edtsdt.setText(sdt);
        edtmota.setText(mota);
        edtsonguoio.setText(songuoio);
        edtgia.setText(gia);

        btnlienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = edtsdt.getText().toString();//The phone number you want to text
                String sms= "";//The message you want to text to the phone
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNo, null));
                smsIntent.putExtra("sms_body",sms);
                startActivity(smsIntent);
            }
        });
    }

    public void openActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void anhxa() {
          edtid =  findViewById(R.id.edtid);
        edthoten =  findViewById(R.id.edtchuphong);
        edidiachi =  findViewById(R.id.edtdiachi);
        edtsonguoio =  findViewById(R.id.edtsonguoio);
        edtmota =  findViewById(R.id.edtmota);
        edtsdt =  findViewById(R.id.edtsdt);
       // btnupdate = (Button) findViewById(R.id.btnupdate);
        back =  findViewById(R.id.btnback);
       // btndelete = (Button) findViewById(R.id.btndlt);
        imghinhanh = (ImageView) findViewById(R.id.avatar);
        edtgia = findViewById(R.id.edtgia);
        btnlienhe = findViewById(R.id.btnlienhe);
        tvmap = findViewById(R.id.txmap);
        list = findViewById(R.id.list1);
        btnhome = findViewById(R.id.btnhomeinfo1);
    }

}

