package com.example.booktro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.booktro.Data.DataRoom;
import com.example.booktro.ListRoomFromUser;
import com.example.booktro.Model.InfoAll;
import com.example.booktro.Model.Room;
import com.example.booktro.Model.User;
import com.example.booktro.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class CustomAdapterRoom extends ArrayAdapter<InfoAll> {

    private Context context;
    private int resoure;
    private List<InfoAll> ListRoom;

    public CustomAdapterRoom(@NonNull Context context, int resource, @NonNull List<InfoAll> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.ListRoom = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;
            if(convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lvroom,parent,false);
                viewHolder = new ViewHolder();
              //  viewHolder.tvid = (TextView)convertView.findViewById(R.id.tvid);
                viewHolder.tvhoTen = (TextView)convertView.findViewById(R.id.tvhoten);
                viewHolder.tvsdt = (TextView)convertView.findViewById(R.id.tvsdt);
                viewHolder.tvdiachi = (TextView)convertView.findViewById(R.id.tvdiachi);
                viewHolder.tvgia = (TextView)convertView.findViewById(R.id.tvgia);
                viewHolder.tvsonguoio = (TextView)convertView.findViewById(R.id.tvsonguoi);
                viewHolder.imgmota = (ImageView)convertView.findViewById(R.id.imgavt);
                convertView.setTag(viewHolder);

            }else {
                    viewHolder = (ViewHolder) convertView.getTag();
            }
                  final InfoAll infoAll = ListRoom.get(position);
                viewHolder.tvhoTen.setText("Họ tên: " + infoAll.getHoTen());
                viewHolder.tvdiachi.setText("Địa chỉ: " + infoAll.getDiaChi());
                viewHolder.tvsdt.setText("SĐT: " + infoAll.getSdt());
                viewHolder.tvgia.setText("Giá phòng:" + infoAll.getGiaphong() + "/Phòng");
                viewHolder.tvsonguoio.setText("Số người ở/Phòng :" + infoAll.getSoNguoi());
                byte[] hinhanh = infoAll.getHinhAnh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0 , hinhanh.length);
                 getResizedBitmap(bitmap,5,5);
                 viewHolder.imgmota.setImageBitmap(bitmap);
                return convertView;
    }
    public class ViewHolder{
        private TextView tvid;
        private TextView tvhoTen;
        private TextView tvsdt;
        private TextView tvdiachi;
        private TextView tvsonguoio;
        private TextView tvmota;
        private TextView tvgia;
        private ImageView imgmota;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
// create a matrix for the manipulation
        Matrix matrix = new Matrix();
// resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
// recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }
}
