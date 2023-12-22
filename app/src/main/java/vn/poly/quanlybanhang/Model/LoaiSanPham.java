package vn.poly.quanlybanhang.Model;

public class LoaiSanPham {
    private final String maLoai;
    private final String tenLoai;

    public LoaiSanPham(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }


    public String getMaLoai() {
        return maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

}
