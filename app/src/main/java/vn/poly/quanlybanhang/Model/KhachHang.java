package vn.poly.quanlybanhang.Model;

public class KhachHang {
    private final String ten;
    private final String email;
    private final String soDienThoai;
    private final String diaChi;
    private double tienNo;
    private final double tienDaMua;

    public KhachHang(String ten, String email, String soDienThoai, String diaChi, double tienNo, double tienDaMua) {
        this.ten = ten;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tienNo = tienNo;
        this.tienDaMua = tienDaMua;
    }


    public String getTen() {
        return ten;
    }

    public String getEmail() {
        return email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public double getTienNo() {
        return tienNo;
    }

    public void setTienNo(double tienNo) {
        this.tienNo = tienNo;
    }

    public double getTienDaMua() {
        return tienDaMua;
    }

}
