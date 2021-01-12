package com.example.booktro.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataRoom extends SQLiteOpenHelper {
    public  DataRoom(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

        public void insertData(String iduser, String diachi, String songuooio, String giaphong, String mota, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ROOM2 VALUES (NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, String.valueOf(iduser));
        statement.bindString(2, diachi);
        statement.bindString(3, songuooio);
        statement.bindString(4,giaphong);
        statement.bindString(5, mota);
        statement.bindBlob(6, image);
        statement.executeInsert();
    }


    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM ROOM2 WHERE Idroom = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);
        statement.execute();
        database.close();
    }

    public void updateData( String diaChi, String soNguoi,String giaphong, String moTa, byte[] hinhAnh, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE ROOM2 SET diachi = ?, songuoio = ?, giaphong = ?, mota = ?, image = ? WHERE Idroom = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, diaChi);
        statement.bindString(2, soNguoi);
        statement.bindString(3,giaphong);
        statement.bindString(4, moTa);
        statement.bindBlob(5, hinhAnh);
        statement.bindDouble(6, (double) id);
        statement.execute();
        database.close();
    }


    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

}
