package com.example.booktro.Model;

public class Room extends User {
        private int id,iduser;
        private String diaChi;
        private String soNguoi;
        private String giaphong;
        private String moTa;
        private byte[] hinhAnh;


    public Room(int id, int iduser, String diaChi, String soNguoi, String giaphong, String moTa, byte[] hinhAnh) {
        this.id = id;
        this.iduser = iduser;
        this.diaChi = diaChi;
        this.soNguoi = soNguoi;
        this.giaphong = giaphong;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }

    public Room(String diaChi, String soNguoi, String giaPhong, String moTa, byte[] image) {
        this.diaChi = diaChi;
        this.soNguoi = soNguoi;
        this.giaphong = giaPhong;
        this.moTa = moTa;
        this.hinhAnh = image;
    }

    public Room(int id1, String diaChi, String soNguoi, String giaPhong, String moTa, byte[] image) {
        this.id = id1;
        this.diaChi = diaChi;
        this.soNguoi = soNguoi;
        this.giaphong = giaPhong;
        this.moTa = moTa;
        this.hinhAnh = image;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(String soNguoi) {
        this.soNguoi = soNguoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(String giaphong) {
        this.giaphong = giaphong;
    }
}
