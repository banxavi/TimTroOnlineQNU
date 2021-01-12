package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.booktro.Data.DataRoom;
import com.example.booktro.Data.DataUser;
import com.example.booktro.Data.InfoAll;
import com.example.booktro.Model.Room;
import com.example.booktro.Model.User;
import com.example.booktro.Untils.SessionManagement;
import com.example.booktro.adapter.CustomAdapterRoom;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.booktro.R.drawable.arrow_icon;
import static com.example.booktro.R.drawable.profile;
import static com.example.booktro.R.menu.sort;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public  ListView lvroom;
   static ArrayList<com.example.booktro.Model.InfoAll> list1;
    public  static CustomAdapterRoom customAdapter;
    private Button btndangphong, btntim, btnmap;
    private ImageView infouser, sort,home;
    CircleImageView info;
    private EditText edttim;
    private TextView username;
    com.example.booktro.Model.InfoAll rm;
    SessionManagement sessionManagement;
    SessionManagement ss1;
    public static DataRoom sqLiteHelper;
    public static DataUser sqLiteHelper1;
    public static InfoAll sqLiteHelper2;
    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sqLiteHelper = new DataRoom(Home.this, "room.sqlite1", null, 1);
        sqLiteHelper1 = new DataUser(Home.this, "room.sqlite1", null, 1);
        sqLiteHelper2 = new InfoAll(Home.this, "room.sqlite1", null, 1);
        list1 = new ArrayList<>();
        anhxa();
        setAdapter();
        customAdapter = new CustomAdapterRoom(this, R.layout.item_lvroom, list1);
        lvroom.setAdapter(customAdapter);
        sessionManagement = new SessionManagement(getApplication());
        ss1 = new SessionManagement(getApplication());
        info.setImageBitmap(retrieveImageFromDB());
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels =  new ArrayList<>();
        slideModels.add(new SlideModel("https://didaudalat.com/wp-content/uploads/2019/07/Hinh-anh-Homestay-Bungalow-TP-da-Lat-di-dau-da-Lat.jpg"));
        slideModels.add(new SlideModel("https://didaudalat.com/wp-content/uploads/2020/02/Anh-cua-Kombi-Land-xu-so-xuong-rong-Thanh-pho-da-Lat-di-dau-da-Lat.jpg"));
        slideModels.add(new SlideModel("https://didaudalat.com/wp-content/uploads/2019/07/Xem-Anh-Bungalow-An-Moc-Gia-Trang-TP-da-Lat-di-dau-da-Lat.jpg"));
        imageSlider.setImageList(slideModels,true);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.check()) {
                    Submitlogout();
                } else openActivity2();
            }
        });
        infouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = username.getText().toString().trim();
                Cursor cursor = sqLiteHelper1.getData("SELECT * FROM USER2 where username LIKE '%" + text + "%'");
                while (cursor.moveToNext()) {
                    Intent intent = new Intent(Home.this, UserInfo.class);
                    intent.putExtra("iduser", cursor.getString(0));
                    intent.putExtra("hoten", cursor.getString(3));
                    intent.putExtra("namsinh", cursor.getString(5));
                    intent.putExtra("sdt", cursor.getString(4));
                    Bitmap bmp = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("avatar", byteArray);
                    startActivity(intent);
                }
            }
        });

        //Kiểm tra đã đăng nhập chưa, nếu đã đăng nhập thì thực hiện các thao tác ở trên, còn chưa thì set tất cả về mặc định.
        if (sessionManagement.check()) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("user", 0);
            String user = sharedPreferences.getString("user", null);
            username.setText(user);
            btndangphong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = username.getText().toString().trim();
                    Cursor cursor = sqLiteHelper1.getData("SELECT * FROM USER2 where username LIKE '%" + text + "%'"); // kiểm tra username home có trùng khớp username trong sql không
                    while (cursor.moveToNext()) {
                        SharedPreferences preferences = getSharedPreferences("iduser", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("iduser", cursor.getString(0)); // lấy iduser và truyền qua class main để đăng phòng theo id
                        editor.commit();
                        openActivity1();
                    }
                }
            });
        } else {
            username.setText("Guest");
            info.setImageResource(profile);
            btndangphong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            infouser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity2();
                }
            });
        }
        // get all data join từ 2 bảng DataRoom và DataUser
        Cursor cursor = sqLiteHelper2.getData("SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong, ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser");
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
            list1.add(new com.example.booktro.Model.InfoAll(diaChi, soNguoi, giaPhong, moTa, image, hoten, sdt, namsinh, avatar, idroom, iduser)); //add vào model infoall để adapter lấy dữ liệu hiển thị lên convertview
        }
        customAdapter.notifyDataSetChanged(); //cập nhật adapter

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Map.class);
                startActivity(intent);
            }
        });

        //truyền dữ liệu của phòng qua class inforoom khi chọn vào item
        lvroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rm = customAdapter.getItem(position);
                Intent intent = new Intent(Home.this, InfoRoom.class);
                intent.putExtra("id", String.valueOf(rm.getIduser()));
                intent.putExtra("hoten", rm.getHoTen());
                intent.putExtra("diachi", rm.getDiaChi());
                intent.putExtra("sdt", rm.getSdt());
                intent.putExtra("songuoio", rm.getSoNguoi());
                intent.putExtra("gia", rm.getGiaphong());
                intent.putExtra("mota", rm.getMoTa());
                Bitmap bmp = BitmapFactory.decodeByteArray(rm.getHinhAnh(), 0, rm.getHinhAnh().length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("hinhanh", byteArray);
                startActivity(intent);
            }
        });
        //tìm theo địa chỉ
        btntim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tim = edttim.getText().toString().trim();
                Cursor cursor = sqLiteHelper2.getData1("SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser WHERE ROOM2.diachi LIKE '%"+tim+"%' ");
                if (TextUtils.isEmpty(tim)) {
                    list1.clear();
                }
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
                customAdapter = new CustomAdapterRoom(Home.this, R.layout.item_lvroom, list1);
                lvroom.setAdapter(customAdapter);
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Home.this, v);
                popup.setOnMenuItemClickListener(Home.this);
                popup.inflate(R.menu.sort);
                popup.show();
            }
        });
    }

    private void anhxa() {
        username = findViewById(R.id.txtusername);
        btndangphong = findViewById(R.id.btndang);
        btntim = findViewById(R.id.timbtn);
        infouser = findViewById(R.id.btnlogout);
        edttim = findViewById(R.id.tim2);
        btnmap = findViewById(R.id.btnmap);
        lvroom = findViewById(R.id.lvroom);
        info = findViewById(R.id.imageView);
        sort = findViewById(R.id.sort);
        home = findViewById(R.id.btnhome);
    }

    //phương thích lấy ảnh từ datauser
    public Bitmap retrieveImageFromDB() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", 0);
        String user = sharedPreferences.getString("user", null);
        username.setText(user);
        String text = username.getText().toString().trim();
        Cursor cursor = sqLiteHelper1.getData("SELECT avatar FROM USER2 WHERE username LIKE '%" + text + "%'");
        if (cursor == null) {
        } else if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return null;
    }

    public void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapterRoom(this, R.layout.item_lvroom, list1);
            lvroom.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
        }
    }

    public void openActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void Submitlogout() {
        // Tạo một Dialog để hiển thị
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Logout");
        // Layout dialog_add
        dialog.setContentView(R.layout.dialog_logout);
        // Ánh xạ các thuộc tính bên layout dialog_logout
        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnhuy = (Button) dialog.findViewById(R.id.btn_no);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Hiển thị show
        dialog.show();
    }

    public void Logout() {
        //check tài khoản đã đăng nhập chưa
        if (sessionManagement.check()) {
            sessionManagement.setLogin(false);
            openActivity();
            finish();
            Toast toast = Toast.makeText(Home.this, "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            View view = toast.getView();
            view.setBackgroundResource(R.color.colorAccent);
            try {
                toast.getView().isShown();
                toast.setText("ĐĂNG XUẤT THÀNH CÔNG");
            } catch (Exception e) {
            }
            toast.show();
        }
    }
   // sql = "SELECT ROOM2.diachi, ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser ORDER BY ROOM2.giaphong ASC";

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
        customAdapter = new CustomAdapterRoom(Home.this, R.layout.item_lvroom, list1);
        lvroom.setAdapter(customAdapter);
    }

}
