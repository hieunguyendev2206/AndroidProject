package vn.poly.quanlybanhang.Model;

public
class KhachHang {
    private String ten,email,soDienThoai,diaChi;
    private double tienNo,tienDaMua;

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
