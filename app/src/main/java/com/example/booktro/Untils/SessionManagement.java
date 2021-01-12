package com.example.booktro.Untils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private static String TAG = SessionManagement.class.getName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private int PRE_MODE = 1;
    private final static String NAME = "android_login";
    private final static String SESSION_KEY = "is_login";

    @SuppressLint("WrongConstant")
    public SessionManagement(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLogin ){
        editor.putBoolean(SESSION_KEY,isLogin);
        editor.commit();
    }
    public boolean check(){
       return sharedPreferences.getBoolean(SESSION_KEY,false);
    }

}
