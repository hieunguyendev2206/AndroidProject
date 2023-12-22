package vn.poly.quanlybanhang.Model;

import java.util.Date;

public class HoaDon {
    private final String maHD;
    private final String tenKhachHang;
    private String trangThai;
    private final Date ngayBan;
    private int khachTra;
    private int traLai;
    private int tongTien;
    private int chietKhau;

    public HoaDon(String maHD, String tenKhachHang, Date ngayBan, int khachTra, int traLai, int tongTien, int chietKhau) {
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.ngayBan = ngayBan;
        this.khachTra = khachTra;
        this.traLai = traLai;
        this.tongTien = tongTien;
        this.chietKhau = chietKhau;
    }

    public HoaDon(String maHD, String tenKhachHang, Date ngayBan) {
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.ngayBan = ngayBan;
    }


    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public int getKhachTra() {
        return khachTra;
    }

    public void setKhachTra(int khachTra) {
        this.khachTra = khachTra;
    }

    public int getTraLai() {
        return traLai;
    }

    public void setTraLai(int traLai) {
        this.traLai = traLai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(int chietKhau) {
        this.chietKhau = chietKhau;
    }
}


