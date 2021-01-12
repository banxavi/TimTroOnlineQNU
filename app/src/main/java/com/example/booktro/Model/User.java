package com.example.booktro.Model;

import android.text.Editable;

import com.example.booktro.DangkyActivity;

public class User {
    private int id;
    private String username,password,sdt,namsinh,hoTen;
    private byte[] hinhAnhUser;

    public User(int id, String username, String password, String sdt, String namsinh, String hoTen, byte[] hinhAnhUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sdt = sdt;
        this.namsinh = namsinh;
        this.hoTen = hoTen;
        this.hinhAnhUser = hinhAnhUser;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public byte[] getHinhAnhUser() {
        return hinhAnhUser;
    }

    public void setHinhAnhUser(byte[] hinhAnhUser) {
        this.hinhAnhUser = hinhAnhUser;
    }
}
