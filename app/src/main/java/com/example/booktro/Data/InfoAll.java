package com.example.booktro.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class InfoAll  extends SQLiteOpenHelper {
    public  InfoAll(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void updateData(int Id,String hoTen, String namsinh, String sdt, byte[] hinhanh) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE USER2 SET IdUser = ? , username = ?, password = ?, hoTen = ?, sdt = ?, namsinh = ?, avatar = ? WHERE IdUser= ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(3, hoTen);
        statement.bindString(4, sdt);
        statement.bindString(5,namsinh);
        statement.bindBlob(6, hinhanh);
        statement.bindDouble(7,Id);
        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        if(sql != null) {
            sql = "SELECT ROOM2.diachi,ROOM2.songuoio,ROOM2.giaphong,ROOM2.mota ,ROOM2.image,USER2.hoTen,USER2.sdt,USER2.namsinh ,USER2.avatar,ROOM2.Idroom,USER2.IdUser FROM ROOM2 INNER JOIN USER2 on ROOM2.IdUser = USER2.IdUser";
        }
        return database.rawQuery(sql,null);
    }
    public Cursor getData1(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);

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

}
