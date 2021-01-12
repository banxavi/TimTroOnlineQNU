package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.booktro.Data.InfoAll;
import com.example.booktro.adapter.CustomAdapterRoom;

import java.util.ArrayList;

public class Searchroom extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public static InfoAll sqLiteHelper2;
    ListView lvroom;
    ArrayList<com.example.booktro.Model.InfoAll> list1;
    public static CustomAdapterRoom customAdapter;
    EditText edttim;
    ImageView btntim, sort;
    String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchroom);
        sqLiteHelper2 = new InfoAll(Searchroom.this, "room.sqlite1", null, 1);
        list1 = new ArrayList<>();
        anhxa();
        setAdapter();
        customAdapter = new CustomAdapterRoom(this, R.layout.item_lvroom, list1);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Searchroom.this, v);
                popup.setOnMenuItemClickListener(Searchroom.this);
                popup.inflate(R.menu.sort);
                popup.show();
            }
        });

        btntim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tim = edttim.getText().toString().trim();
                Cursor cursor = sqLiteHelper2.getData1("SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser WHERE LOWER(ROOM2.diachi) LIKE '%" + tim + "%'");
                list1.clear();
                while (cursor.moveToNext()) {
                    String diaChi = cursor.getString(0);
                    String soNguoi = cursor.getString(1);
                    String giaPhong = cursor.getString(2);
                    String moTa = cursor.getString(3);
                    byte[] image = cursor.getBlob(4);
                    String hoten = cursor.getString(5);
                    String sdt = cursor.getString(6);
                    String namsinh = cursor.getString(7);
                    byte[] avatar = cursor.getBlob(8);
                    int idroom = cursor.getInt(9);
                    int iduser = cursor.getInt(10);
                    list1.add(new com.example.booktro.Model.InfoAll(diaChi, soNguoi, giaPhong, moTa, image, hoten, sdt, namsinh, avatar, idroom, iduser));
                }
                customAdapter.notifyDataSetChanged();
                customAdapter = new CustomAdapterRoom(Searchroom.this, R.layout.item_lvroom, list1);
                lvroom.setAdapter(customAdapter);

            }
        });

    }
    public void anhxa(){
        edttim = findViewById(R.id.edttimkiem);
        btntim = findViewById(R.id.imgtim);
        sort = findViewById(R.id.sort1);
        lvroom = findViewById(R.id.listroom);
        sort = findViewById(R.id.sort1);
    }
    public void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapterRoom(this, R.layout.item_lvroom, list1);
        } else {
            customAdapter.notifyDataSetChanged();
        }
    }




    private void updateRoomList() {
        Cursor cursor = sqLiteHelper2.getData1(sql);
        list1.clear();
        list1.clear();
        while (cursor.moveToNext()) {
            String diaChi = cursor.getString(0);
            String soNguoi = cursor.getString(1);
            String giaPhong = cursor.getString(2);
            String moTa = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String hoten = cursor.getString(5);
            String sdt = cursor.getString(6);
            String namsinh = cursor.getString(7);
            byte[] avatar = cursor.getBlob(8);
            int idroom = cursor.getInt(9);
            int iduser = cursor.getInt(10);
            list1.add(new com.example.booktro.Model.InfoAll(diaChi, soNguoi, giaPhong, moTa, image, hoten, sdt, namsinh, avatar, idroom, iduser));
        }
        customAdapter.notifyDataSetChanged();
        customAdapter = new CustomAdapterRoom(Searchroom.this, R.layout.item_lvroom, list1);
        lvroom.setAdapter(customAdapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.giatang:
                sql = "SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser ORDER BY ROOM2.giaphong ASC";
                updateRoomList();
                break;
            case R.id.giagiam:
                sql = "SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser ORDER BY ROOM2.giaphong DESC";
                updateRoomList();
                break;
            case R.id.nguoitang:
                sql = "SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser ORDER BY ROOM2.songuoio ASC";
                updateRoomList();
                break;
            case R.id.nguoigiam:
                sql = "SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser ORDER BY ROOM2.songuoio DESC";
                updateRoomList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}