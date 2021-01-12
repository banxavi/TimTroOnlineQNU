package com.example.booktro.Model;

public class InfoAll {
    private String diaChi,soNguoi,giaphong,moTa,sdt,namsinh,hoTen;
    private byte[] hinhAnh;
    private byte[] hinhAnhUser;
    int idroom, iduser;






    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public InfoAll(String diaChi, String soNguoi, String giaPhong, String moTa, byte[] image, String hoten, String sdt, String namsinh, byte[] avatar, int idroom, int iduser) {
        this.diaChi = diaChi;
        this.soNguoi = soNguoi;
        this.giaphong = giaPhong;
        this.moTa = moTa;
         this.hinhAnh = image;
         this.hoTen = hoten;
         this.sdt = sdt;
         this.namsinh = namsinh;
         this.hinhAnhUser = avatar;
         this.idroom = idroom;
         this.iduser = iduser;
    }

    public int getIdroom() {
        return idroom;
    }

    public void setIdroom(int idroom) {
        this.idroom = idroom;
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

    public String getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(String giaphong) {
        this.giaphong = giaphong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public byte[] getHinhAnhUser() {
        return hinhAnhUser;
    }

    public void setHinhAnhUser(byte[] hinhAnhUser) {
        this.hinhAnhUser = hinhAnhUser;
    }
}
