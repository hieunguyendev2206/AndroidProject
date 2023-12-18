package vn.poly.quanlybanhang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.poly.quanlybanhang.Model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public
class KhachHangDAO {
    public static final String TABLE_NAME = "KhachHang";
    public static final String SQL_NGUOIDUNG = "Create table if not exists KhachHang (" +
            "   soDienThoai text primary key," +
            "   hoTen text," +
            "   email text," +
            "   diaChi text," +
            "   tienNo number," +
            "   tienDaMua number" +
            "   )";

    private final SQLiteDatabase sqLiteDatabase;

    public KhachHangDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    public long addKhachHang(KhachHang khachHang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", khachHang.getTen());
        contentValues.put("soDienThoai", khachHang.getSoDienThoai());
        contentValues.put("email", khachHang.getEmail());
        contentValues.put("diaChi", khachHang.getDiaChi());
        contentValues.put("tienNo", khachHang.getTienNo());
        contentValues.put("tienDaMua", khachHang.getTienDaMua());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public long updateKhacHang(KhachHang khachHang, String sdt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", khachHang.getTen());
        contentValues.put("soDienThoai", khachHang.getSoDienThoai());
        contentValues.put("email", khachHang.getEmail());
        contentValues.put("diaChi", khachHang.getDiaChi());
        contentValues.put("tienNo", khachHang.getTienNo());
        contentValues.put("tienDaMua", khachHang.getTienDaMua());
        return sqLiteDatabase.update(TABLE_NAME, contentValues, "soDienThoai = ?", new String[]{sdt});
    }

    public long updateTienNo(KhachHang khachHang, String sdt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tienNo", khachHang.getTienNo());
        return sqLiteDatabase.update(TABLE_NAME, contentValues, "soDienThoai = ?", new String[]{sdt});
    }


    public long updateTien(double tienNo,double tienMua,String sdt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tienNo",tienNo);
        contentValues.put("tienDaMua",tienMua);
        return sqLiteDatabase.update(TABLE_NAME, contentValues, "soDienThoai = ?", new String[]{sdt});
    }
    public long deleteKhachHang(String sdt) {
        return sqLiteDatabase.delete(TABLE_NAME, "soDienThoai = ?", new String[]{sdt});
    }

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ten = cursor.getString(1);
                String soDienThoai = cursor.getString(0);
                String email = cursor.getString(2);
                String diaChi = cursor.getString(3);
                double tienNo = cursor.getDouble(4);
                double tienDaMua = cursor.getDouble(5);

                KhachHang khachHang = new KhachHang(ten, email, soDienThoai, diaChi, tienNo, tienDaMua);
                list.add(khachHang);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public KhachHang getKhachHangBySDT(String sdt) {
        String query = "Select * from " + TABLE_NAME + " where soDienThoai = '"+sdt+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        KhachHang khachHang = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String ten = cursor.getString(1);
            String soDienThoai = cursor.getString(0);
            String email = cursor.getString(2);
            String diaChi = cursor.getString(3);
            double tienNo = cursor.getDouble(4);
            double tienDaMua = cursor.getDouble(5);
            khachHang = new KhachHang(ten, email, soDienThoai, diaChi, tienNo, tienDaMua);
        }
        cursor.close();
        return khachHang;
    }
    public List<KhachHang> timKiemKhachHang(String text) {
        List<KhachHang> list = new ArrayList<>();
        String query = "Select DISTINCT * from " + TABLE_NAME + " where soDienThoai like '%"+text+"%' or hoTen like '%"+text+"%'"  ;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ten = cursor.getString(1);
                String soDienThoai = cursor.getString(0);
                String email = cursor.getString(2);
                String diaChi = cursor.getString(3);
                double tienNo = cursor.getDouble(4);
                double tienDaMua = cursor.getDouble(5);
                KhachHang khachHang = new KhachHang(ten, email, soDienThoai, diaChi, tienNo, tienDaMua);
                list.add(khachHang);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
}
