package vn.poly.quanlybanhang.Model;

public
class GioHang {
     private String ten,ma;
     private double gia;
     private int soLuong;

     public GioHang(String ten, String ma, double gia, int soLuong) {
          this.ten = ten;
          this.ma = ma;
          this.gia = gia;
          this.soLuong = soLuong;
     }

     public GioHang() {
     }

     public String getTen() {
          return ten;
     }

     public void setTen(String ten) {
          this.ten = ten;
     }

     public String getMa() {
          return ma;
     }

     public void setMa(String ma) {
          this.ma = ma;
     }

     public double getGia() {
          return gia;
     }

     public void setGia(double gia) {
          this.gia = gia;
     }

     public int getSoLuong() {
          return soLuong;
     }

     public void setSoLuong(int soLuong) {
          this.soLuong = soLuong;
     }
}

