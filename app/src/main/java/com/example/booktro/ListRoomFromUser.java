package com.example.booktro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.Data.InfoAll;
import com.example.booktro.Model.Room;
import com.example.booktro.adapter.CustomAdapterRoom;
import com.example.booktro.adapter.CustomAdapterUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.booktro.Home.customAdapter;

public class ListRoomFromUser extends AppCompatActivity {
    public static DataRoom sqLiteHelper2;
    public static ListView lvroom;
    ImageView btnhome, btnacc;
    public  static ArrayList<Room> list;
    Room room;
    public static CustomAdapterUser customAdapter;
    public String iduser,diachi;
    public static TextView tvid;
    TextView tim1;
    static final int REQUEST_CODE_GALLERY = 999;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_from_user);
        sqLiteHelper2 = new DataRoom(ListRoomFromUser.this, "room.sqlite1", null, 1);
        list = new ArrayList<>();
        imganh = findViewById(R.id.avatar1);
        lvroom = findViewById(R.id.lvroom);
        tvid = findViewById(R.id.iduser);
         tim1 = findViewById(R.id.tim1);
         btnhome = findViewById(R.id.btnhomeroom);
         btnacc = findViewById(R.id.btnaccroom);
        setAdapter();
        customAdapter = new CustomAdapterUser(this, R.layout.itemofuser, list);
        lvroom.setAdapter(customAdapter);
        iduser = tvid.getText().toString();
      lvroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
              CharSequence[] items = {"Update", "Delete"};
              AlertDialog.Builder dialog = new AlertDialog.Builder(ListRoomFromUser.this);
              dialog.setTitle("Choose an action");
              dialog.setItems(items, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int item) {
                      if (item == 0) {
                          // update
                          Cursor c = sqLiteHelper2.getData("SELECT * FROM ROOM2 WHERE IdUser = '" + tvid.getText() + "'");
                          ArrayList<Integer> arrID = new ArrayList<Integer>();
                          while (c.moveToNext()){
                              arrID.add(c.getInt(0));
                          }
                          room = customAdapter.getItem(position);
                         /* Intent intent = new Intent(ListRoomFromUser.this, Dialog.class );
                          intent.putExtra("diachi", room.getDiaChi());
                          intent.putExtra("gia",room.getGiaphong());
                          intent.putExtra("songuoio",room.getSoNguoi());
                          intent.putExtra("mota",room.getMoTa());
                          Bitmap bmp = BitmapFactory.decodeByteArray(room.getHinhAnh(),0,room.getHinhAnh().length);
                          ByteArrayOutputStream stream = new ByteArrayOutputStream();
                          bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                          byte[] byteArray = stream.toByteArray();
                          intent.putExtra("hinhanh",byteArray);*/
                          showDialogUpdate(ListRoomFromUser.this, arrID.get(position));
                      } else {
                          // delete
                          Cursor c = sqLiteHelper2.getData("SELECT * FROM ROOM2 WHERE IdUser = '" + tvid.getText() + "'");
                          ArrayList<Integer> arrID = new ArrayList<Integer>();
                          while (c.moveToNext()) {
                              arrID.add(c.getInt(0));
                          }
                          showDialogDelete(arrID.get(position));
                      }
                  }
              });
              dialog.show();
              return;
          }
      });

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRoomFromUser.this, Home.class);
                startActivity(intent);
            }
        });

        btnacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        Intent intent = getIntent(); String id = intent.getStringExtra("id");
        tvid.setText(id);
        tvid.setVisibility(View.INVISIBLE);

        //getallRoom
        Cursor cursor = sqLiteHelper2.getData("SELECT * FROM ROOM2 WHERE IdUser = '"+tvid.getText()+"'");
        list.clear();
        while (cursor.moveToNext()) {
            int id1 = cursor.getInt(0);
            String diaChi = cursor.getString(2);
            String soNguoi = cursor.getString(3);
            String giaPhong = cursor.getString(4);
            String moTa = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            list.add(new Room(id1,diaChi, soNguoi, giaPhong, moTa, image));
        }
        customAdapter.notifyDataSetChanged();
    }

    //set layout cho adapter
    public void setAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapterUser(this,R.layout.itemofuser,list);
            lvroom.setAdapter(customAdapter);
        }
        else {
            customAdapter.notifyDataSetChanged();
        }
    }

    private void showDialogDelete(final int id) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListRoomFromUser.this); //tạo dialog delete
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {            //đồng ý
                try {
                   sqLiteHelper2.deleteData(id);    //xóa theo id
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateRoomList();
                customAdapter.notifyDataSetChanged();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//không đồng ý
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    static ImageView imganh;
    public void showDialogUpdate(Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);//tạo dialog update
        dialog.setContentView(R.layout.updateroom);//set layout cho dialog
        dialog.setTitle("Update");
        //ánh xạ
        final EditText edtDiachi = (EditText) dialog.findViewById(R.id.edtdichi1);
        final EditText edtSonguoi = (EditText) dialog.findViewById(R.id.edtsonguoio1);
        final EditText edtMota = (EditText) dialog.findViewById(R.id.edtmota1);
         Button btnUpanh = (Button) dialog.findViewById(R.id.btnUpAnh1);
         Button btnupdate = (Button) dialog.findViewById(R.id.btnxacnhan1);
        final ImageButton btnback = (ImageButton) dialog.findViewById(R.id.btnback);
         imganh = (ImageView) dialog.findViewById(R.id.avatar1);
        final EditText edtgiaPhong = (EditText) dialog.findViewById(R.id.edtgia1);

        //set width cho dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

       btnUpanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ListRoomFromUser.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add dữ liệu
                try {
                imganhtoByte(imganh);
                    sqLiteHelper2.updateData(
                        edtDiachi.getText().toString().trim(),
                        edtSonguoi.getText().toString().trim(),
                        edtgiaPhong.getText().toString().trim() + "VNĐ",
                        edtMota.getText().toString().trim(),
                        imganhtoByte(imganh),position
                );
                    dialog.dismiss(); //đóng dialog
                    Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateRoomList();//update lại listview
            }
        });
    }
    public static byte[] imganhtoByte(ImageView imageView) { //chuyển hình ảnh sang dạng byte để lưu vào sql
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static void updateRoomList() {
        Cursor cursor = sqLiteHelper2.getData("SELECT * FROM ROOM2 WHERE IdUser = '"+tvid.getText()+"'");
        list.clear();
        while (cursor.moveToNext()) {
            String diaChi = cursor.getString(2);
            String soNguoi = cursor.getString(3);
            String giaPhong = cursor.getString(4);
            String moTa = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            list.add(new Room(diaChi, soNguoi, giaPhong, moTa, image));
        }
        customAdapter.notifyDataSetChanged();
    }
    public void openActivity(){
        Intent intent = new Intent(ListRoomFromUser.this, UserInfo.class);
        startActivity(intent);
    }

    //cho phép người chung truy cập vào bộ nhớ
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
    //trả kết quả hình ảnh về Imageview
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    public void back(){
        Intent intent = new Intent(ListRoomFromUser.this, UserInfo.class);
        startActivity(intent);
    }


}