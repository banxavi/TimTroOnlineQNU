package com.example.booktro.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.Data.DataUser;
import com.example.booktro.ListRoomFromUser;
import com.example.booktro.MainActivity;
import com.example.booktro.Model.Room;
import com.example.booktro.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomAdapterUser extends ArrayAdapter<Room> {

    private static final int RESULT_OK = 1;
    public static DataRoom sqLiteHelper2;
    private Context context;
    private int resoure;
    private List<Room> Room;
    CustomAdapterUser customAdapterUser;
    private ListView lvroom;
    ArrayList<Room> list;
    public final int REQUEST_CODE_GALLERY = 999;


    public CustomAdapterUser(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.Room = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemofuser, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvdiachi = (TextView) convertView.findViewById(R.id.tvdiachi);
            viewHolder.tvgia = (TextView) convertView.findViewById(R.id.tvgia);
            viewHolder.tvsonguoio = (TextView) convertView.findViewById(R.id.tvsonguoi);
            viewHolder.tvmota = convertView.findViewById(R.id.tvmota);
            viewHolder.imgmota = (ImageView) convertView.findViewById(R.id.imgavt);
            viewHolder.txtmenu = convertView.findViewById(R.id.txtmenu);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Room room = Room.get(position);
        viewHolder.tvdiachi.setText("Địa chỉ: " + room.getDiaChi());
        viewHolder.tvgia.setText("Giá phòng: " + room.getGiaphong() + "/Phòng");
        viewHolder.tvsonguoio.setText("Số người ở/Phòng: " + room.getSoNguoi());
        viewHolder.tvmota.setText("Mô tả: " + room.getMoTa());
        byte[] hinhanh = room.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        viewHolder.imgmota.setImageBitmap(bitmap);
        return convertView;
    }

    public class ViewHolder {
        private TextView id;
        private TextView tvdiachi;
        private TextView tvsonguoio;
        private TextView tvmota;
        private TextView tvgia;
        private ImageView imgmota;
        private TextView txtmenu;
    }



}

