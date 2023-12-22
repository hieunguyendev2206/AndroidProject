package vn.poly.quanlybanhang.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.poly.quanlybanhang.Model.HoaDon;

public class HoaDonDAO {
    public static final String TABLE_NAME = "HoaDon";
    public static final String SQL_HOADON = "Create table if not exists HoaDon (" +
            "   maHoaDon text primary key," +
            "   ngayBan date ," +
            "   tenKhachHang text," +
            "   chietKhau text," +
            "   khachTra number," +
            "   traLai number ," +
            "   tongTien number ," +
            "   trangThai text)";
    @SuppressLint("SimpleDateFormat")
    final
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SQLiteDatabase sqLiteDatabase;

    public HoaDonDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    public long addHoaDon(HoaDon hoaDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maHoaDon", hoaDon.getMaHD());
        contentValues.put("ngayBan", simpleDateFormat.format(hoaDon.getNgayBan()));
        contentValues.put("tenKhachHang", hoaDon.getTenKhachHang());
        contentValues.put("chietKhau", hoaDon.getChietKhau());
        contentValues.put("khachTra", hoaDon.getKhachTra());
        contentValues.put("traLai", hoaDon.getTraLai());
        contentValues.put("tongTien", hoaDon.getTongTien());
        contentValues.put("trangThai", hoaDon.getTrangThai());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void updateHoaDon(HoaDon hoaDon, String ma) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maHoaDon", hoaDon.getMaHD());
        contentValues.put("ngayBan", simpleDateFormat.format(hoaDon.getNgayBan()));
        contentValues.put("tenKhachHang", hoaDon.getTenKhachHang());
        contentValues.put("chietKhau", hoaDon.getChietKhau());
        contentValues.put("khachTra", hoaDon.getKhachTra());
        contentValues.put("traLai", hoaDon.getTraLai());
        contentValues.put("tongTien", hoaDon.getTongTien());
        contentValues.put("trangThai", hoaDon.getTrangThai());
        sqLiteDatabase.update(TABLE_NAME, contentValues, "maHoaDon = ?", new String[]{ma});
    }

    public long deleteHoaDon(String ma) {
        return sqLiteDatabase.delete(TABLE_NAME, "maHoaDon = ?", new String[]{ma});
    }

    public List<HoaDon> getAllHoaDon() throws ParseException {
        List<HoaDon> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maHD = cursor.getString(0);
                String ngayBan = cursor.getString(1);
                String tenKhach = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                HoaDon hoaDon = new HoaDon(maHD, tenKhach, simpleDateFormat.parse(ngayBan), khachTra, traLai, tongTien, chietKhau);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public List<HoaDon> getAllHoaDonTheoMa(String ma) throws ParseException {
        List<HoaDon> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME + " where maHoaDon like '%" + ma + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maHD = cursor.getString(0);
                String ngayBan = cursor.getString(1);
                String tenKhach = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                HoaDon hoaDon = new HoaDon(maHD, tenKhach, simpleDateFormat.parse(ngayBan), khachTra, traLai, tongTien, chietKhau);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public HoaDon getHoaDonTheoMa(String ma) throws ParseException {
        String query = "Select * from " + TABLE_NAME + " where maHoaDon = '" + ma + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        HoaDon hoaDon = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String maHD = cursor.getString(0);
            String ngayBan = cursor.getString(1);
            String tenKhach = cursor.getString(2);
            int chietKhau = cursor.getInt(3);
            int khachTra = cursor.getInt(4);
            int traLai = cursor.getInt(5);
            int tongTien = cursor.getInt(6);
            hoaDon = new HoaDon(maHD, tenKhach, simpleDateFormat.parse(ngayBan), khachTra, traLai, tongTien, chietKhau);
            cursor.close();
        }
        return hoaDon;
    }

    public double getDoanhThu(String time) {
        String query;
        if (time.equalsIgnoreCase("Tất Cả")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME;
        } else if (time.equalsIgnoreCase("Hôm nay")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("Hôm qua")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tuần này")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tuần trước")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1)";
        } else if (time.equalsIgnoreCase("Tháng này")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        double tien = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tien += cursor.getDouble(0);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return tien;
    }

    public int getSoHoaDon(String time) {
        String query;
        if (time.equalsIgnoreCase("Tất Cả")) {
            query = "Select maHoaDon from " + TABLE_NAME;
        } else if (time.equalsIgnoreCase("Hôm nay")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("Hôm qua")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tuần này")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tuần trước")) {
            query = "Select maHoaDon from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1)";
        } else if (time.equalsIgnoreCase("Tháng này")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "Select maHoaDon from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int soLuong = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                soLuong++;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return soLuong;
    }

    public int getSoTienVon(String time) {
        //String query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet ON SanPham.maSanPham = HoaDonChiTiet.masanpham";
        String query;
        if (time.equalsIgnoreCase("Tất Cả")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet" +
                    " ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon";
        } else if (time.equalsIgnoreCase("Hôm nay")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("Hôm qua")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tuần này")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tuần trước")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1);";
        } else if (time.equalsIgnoreCase("Tháng này")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int tongTienVon = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tongTienVon = cursor.getInt(0);
        }
        cursor.close();
        return tongTienVon;
    }

    public double getDoanhThuTheoThang(String thang) {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongTien)  from HoaDon where strftime('%m',ngayBan)='" + thang + "' ";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu += c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public List<HoaDon> getAllHoaDonTime(String time, String trangthaihd) throws ParseException {
        String query = null;
        List<HoaDon> list = new ArrayList<>();
        //Tất cả
        if (time.equalsIgnoreCase("Tất Cả") && trangthaihd.equalsIgnoreCase("Tất Cả")) {
            query = "Select * from " + TABLE_NAME;
        }
        if (time.equalsIgnoreCase("Tất Cả") && trangthaihd.equalsIgnoreCase("Chưa thanh Toán")) {
            query = "Select * from " + TABLE_NAME + " where  trangThai like 'Chưa Thanh Toán'";
        }
        if (time.equalsIgnoreCase("Tất Cả") && trangthaihd.equalsIgnoreCase("Đã thanh Toán")) {
            query = "Select * from " + TABLE_NAME + " where  trangThai like 'Đã Thanh Toán'";
        }
        //Hôm nay
        if (time.equalsIgnoreCase("Hôm nay") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') ";
        }
        if (time.equalsIgnoreCase("Hôm nay") && trangthaihd.equalsIgnoreCase("Chưa thanh Toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') and trangThai like 'Chưa Thanh Toán'";
        }
        if (time.equalsIgnoreCase("Hôm nay") && trangthaihd.equalsIgnoreCase("Đã thanh Toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') and trangThai like 'Đã Thanh Toán'";
        }

        //hôm qua
        if (time.equalsIgnoreCase("Hôm qua") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        if (time.equalsIgnoreCase("Hôm qua") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        if (time.equalsIgnoreCase("Hôm qua") && trangthaihd.equalsIgnoreCase("đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day') ";
        }
        //tuần này
        if (time.equalsIgnoreCase("Tuần này") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)= strftime('%W','now') ";
        }
        if (time.equalsIgnoreCase("Tuần này") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)  and trangThai like 'Chưa Thanh Toán' ";
        }
        if (time.equalsIgnoreCase("Tuần này") && trangthaihd.equalsIgnoreCase("đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan) and trangThai like 'Đã Thanh Toán' ";
        }
        //tuần trước
        if (time.equalsIgnoreCase("Tuần trước") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1);";
        }
        if (time.equalsIgnoreCase("Tuần trước") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) and trangThai like 'Chưa Thanh Toán' ";
        }
        if (time.equalsIgnoreCase("Tuần trước") && trangthaihd.equalsIgnoreCase("đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) and trangThai like 'Đã Thanh Toán' ";
        }
        //tháng này
        if (time.equalsIgnoreCase("Tháng này") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        }
        if (time.equalsIgnoreCase("Tháng này") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) and trangThai like 'Chưa Thanh Toán' ";
        }
        if (time.equalsIgnoreCase("Tháng này") && trangthaihd.equalsIgnoreCase("đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) and trangThai like 'Đã Thanh Toán' ";
        }
        //tháng trước
        if (time.equalsIgnoreCase("Tháng trước") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        if (time.equalsIgnoreCase("Tháng trước") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') and trangThai like 'Chưa Thanh Toán' ";
        }
        if (time.equalsIgnoreCase("Tháng trước") && trangthaihd.equalsIgnoreCase("Đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') and trangThai like 'Đã Thanh Toán' ";
        }
        //Tất cả time
        if (time.equalsIgnoreCase("Tất cả") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME;
        }
        // hôm nay
        if (time.equalsIgnoreCase("Hôm Nay") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') ";
        }
        //hôm qua
        if (time.equalsIgnoreCase("hôm qua") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        //tuần này
        if (time.equalsIgnoreCase("tuần này") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)= strftime('%W','now') ";
        }
        //tuần trước
        if (time.equalsIgnoreCase("tuần trước") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) ";
        }
        //tháng này
        if (time.equalsIgnoreCase("tháng này") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        }
        if (time.equalsIgnoreCase("Tháng trước") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month')" +
                    "AND ngayban < date('now','start of month')";
        }
        //trang thai hoa don
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("Tất cả")) {
            query = "Select * from " + TABLE_NAME;
        }
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("Chưa thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where trangThai like 'Chưa thanh toán' ";
        }
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("Đã thanh toán")) {
            query = "Select * from " + TABLE_NAME + " where trangThai like 'đã thanh toán'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maHD = cursor.getString(0);
                String ngayBan = cursor.getString(1);
                String tenKhach = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                HoaDon hoaDon = new HoaDon(maHD, tenKhach, simpleDateFormat.parse(ngayBan), khachTra, traLai, tongTien, chietKhau);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }


}
