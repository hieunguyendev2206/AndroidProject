package vn.poly.quanlybanhang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.quanlybanhang.Model.GioHang;
import vn.poly.quanlybanhang.Model.HoaDonChiTiet;

public
class HoaDonChiTietDAO {
    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String SQL_HDCT = "Create table if not exists HoaDonChiTiet (" +
            "   maHDCT integer primary key autoincrement," +
            "   maHoaDon text," +
            "   tenSanPham text," +
            "   maSanPham text," +
            "   soLuong number," +
            "   gia number)";
    private final SQLiteDatabase sqLiteDatabase;

    public HoaDonChiTietDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    public long addHDCT(HoaDonChiTiet hoaDonChiTiet) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maHoaDon", hoaDonChiTiet.getMaHoaDon());
        contentValues.put("tenSanPham", hoaDonChiTiet.getGioHang().getTen());
        contentValues.put("maSanPham", hoaDonChiTiet.getGioHang().getMa());
        contentValues.put("soLuong", hoaDonChiTiet.getGioHang().getSoLuong());
        contentValues.put("gia", hoaDonChiTiet.getGioHang().getGia());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public List<HoaDonChiTiet> getAllHDCT(String maHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        // String query = "Select maHDCT, HoaDon.maHoaDon,SanPham.maSanPham FROM HoaDonChiTiet INNER JOIN HoaDon on HoaDonChiTiet.maHoaDon=HoaDon.maHoaDon INNER JOIN SanPham on SanPham.maSanPham = HoaDonChiTiet.maSanPham where HoaDonChiTiet.maHoaDon='" + maHD + "'";
        String query = " Select * from " + TABLE_NAME + " where maHoaDon = '" + maHD + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maHd = cursor.getString(3);
                String maSP = cursor.getString(3);
                String ten = cursor.getString(2);
                int soLuong = cursor.getInt(4);
                double gia = cursor.getDouble(5);
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(maHd, new GioHang(ten, maSP, gia, soLuong));
                list.add(hoaDonChiTiet);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
