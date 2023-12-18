package vn.poly.quanlybanhang.Model;

public
class HoaDonChiTiet {
    private String maHoaDon,maHDCT;
    private GioHang gioHang;

    public HoaDonChiTiet(String maHoaDon, GioHang gioHang) {
        this.maHoaDon = maHoaDon;
        this.gioHang = gioHang;
    }

    public HoaDonChiTiet() {
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public GioHang getGioHang() {
        return gioHang;
    }

    public void setGioHang(GioHang gioHang) {
        this.gioHang = gioHang;
    }
}
