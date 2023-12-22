package vn.poly.quanlybanhang.Model;

public class HoaDonChiTiet {
    private final String maHoaDon;
    private final GioHang gioHang;

    public HoaDonChiTiet(String maHoaDon, GioHang gioHang) {
        this.maHoaDon = maHoaDon;
        this.gioHang = gioHang;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public GioHang getGioHang() {
        return gioHang;
    }

}
