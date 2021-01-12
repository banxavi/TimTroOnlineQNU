package com.example.booktro.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DataUser extends SQLiteOpenHelper {
    public DataUser(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser1(){}

    public void insertUser(String username, String password,String hoTen, String namsinh, String sdt, byte[] hinhanh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO USER2 VALUES (NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, username);
        statement.bindString(2, password);
        statement.bindString(3, hoTen);
        statement.bindString(5, namsinh);
        statement.bindString(4, sdt);
        statement.bindBlob(6, hinhanh);
        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

   // CREATE TABLE IF NOT EXISTS USER2(IdUser INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR, hoTen VARCHAR, sdt VARCHAR, namsinh VARCHAR, avatar BLOB)
    public void updateData(String hoTen, String namsinh, String sdt,byte[] hinhanh, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE USER2 SET  hoTen=?, sdt=?, namsinh=?, avatar=? WHERE IdUser=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, hoTen);
        statement.bindString(2, sdt);
        statement.bindString(3,namsinh);
        statement.bindBlob(4, hinhanh);
        statement.bindDouble(5,(double)id);
        statement.execute();
        database.close();
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Boolean checkuser(String user, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USER2 where username=? and password=?", new String[]{user,pass});
        if(cursor.getCount()>0) return true;
        else return false;
    }

}
