package vn.poly.quanlybanhang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.quanlybanhang.Model.SanPham;

public class SanPhamDAO {
    public static final String TABLE_NAME = "SanPham";
    public static final String SQL_SANPHAM = "Create table if not exists SanPham(" +
            "   maSanPham text primary key," +
            "   maLoai text," +
            "   tenSanPham text," +
            "   donViTinh text," +
            "   soLuong number," +
            "   giaNhap number," +
            "   giaBan number," +
            "   hinhAnh BLOB)";

    private final SQLiteDatabase sqLiteDatabase;

    public SanPhamDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    // Thêm Sản Phẩm
    public long addSanPham(SanPham sanPham) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSanPham", sanPham.getMaSanPham());
        contentValues.put("maLoai", sanPham.getMaLoai());
        contentValues.put("tenSanPham", sanPham.getTen());
        contentValues.put("donViTinh", sanPham.getDonViTinh());
        contentValues.put("soLuong", sanPham.getSoLuong());
        contentValues.put("giaNhap", sanPham.getGiaNhap());
        contentValues.put("giaBan", sanPham.getGiaBan());
        contentValues.put("hinhAnh", sanPham.getImage());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // Cập nhật sản phẩm
    public void updateSanPham(SanPham sanPham, String ma) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSanPham", sanPham.getMaSanPham());
        contentValues.put("maLoai", sanPham.getMaLoai());
        contentValues.put("tenSanPham", sanPham.getTen());
        contentValues.put("donViTinh", sanPham.getDonViTinh());
        contentValues.put("soLuong", sanPham.getSoLuong());
        contentValues.put("giaNhap", sanPham.getGiaNhap());
        contentValues.put("giaBan", sanPham.getGiaBan());
        contentValues.put("hinhAnh", sanPham.getImage());
        sqLiteDatabase.update(TABLE_NAME, contentValues, "maSanPham = ?", new String[]{ma});
    }

    // Cập nhật số lượng sản phẩm
    public void updateSLSanPham(int soLuong, String ma) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("soLuong", soLuong);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "maSanPham = ?", new String[]{ma});
    }

    // Xóa sản phẩm
    public long deleteSanPham(String ma) {
        return sqLiteDatabase.delete(TABLE_NAME, "maSanPham = ?", new String[]{ma});
    }

    // Lấy tất cả sản phẩm để hiển thị từ Database
    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maSP = cursor.getString(0);
                String maLoai = cursor.getString(1);
                String ten = cursor.getString(2);
                String donVi = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                double giaNhap = cursor.getDouble(5);
                double giaBan = cursor.getDouble(6);
                byte[] img = cursor.getBlob(7);
                SanPham sanPham = new SanPham(maSP, maLoai, ten, donVi, soLuong, giaNhap, giaBan, img);
                list.add(sanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public SanPham getSanPhamTheoMa(String ma) {
        String query = "select * from " + TABLE_NAME + " where maSanPham = '" + ma + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        SanPham sanPham = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String maSP = cursor.getString(0);
            String maLoai = cursor.getString(1);
            String ten = cursor.getString(2);
            String donVi = cursor.getString(3);
            int soLuong = cursor.getInt(4);
            double giaNhap = cursor.getDouble(5);
            double giaBan = cursor.getDouble(6);
            byte[] img = cursor.getBlob(7);
            sanPham = new SanPham(maSP, maLoai, ten, donVi, soLuong, giaNhap, giaBan, img);
        }
        cursor.close();
        return sanPham;
    }

    public List<SanPham> getAllSanPhamTheoMa(String ma) {
        List<SanPham> list = new ArrayList<>();
        String query = "select DISTINCT * from " + TABLE_NAME + " where maSanPham like '%" + ma + "%' or tenSanPham like '%" + ma + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        SanPham sanPham;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String maSP = cursor.getString(0);
            String maLoai = cursor.getString(1);
            String tensp = cursor.getString(2);
            String donVi = cursor.getString(3);
            int soLuong = cursor.getInt(4);
            double giaNhap = cursor.getDouble(5);
            double giaBan = cursor.getDouble(6);
            byte[] img = cursor.getBlob(7);
            sanPham = new SanPham(maSP, maLoai, tensp, donVi, soLuong, giaNhap, giaBan, img);
            list.add(sanPham);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<SanPham> getAllSanPhamTheoTen() {
        List<SanPham> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME + " order by tenSanPham asc";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maSP = cursor.getString(0);
                String maLoai = cursor.getString(1);
                String ten = cursor.getString(2);
                String donVi = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                double giaNhap = cursor.getDouble(5);
                double giaBan = cursor.getDouble(6);
                byte[] img = cursor.getBlob(7);
                SanPham sanPham = new SanPham(maSP, maLoai, ten, donVi, soLuong, giaNhap, giaBan, img);
                list.add(sanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public List<SanPham> getAllSanPhamTheoGiaTangDan() {
        List<SanPham> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME + " order by giaBan asc";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maSP = cursor.getString(0);
                String maLoai = cursor.getString(1);
                String ten = cursor.getString(2);
                String donVi = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                double giaNhap = cursor.getDouble(5);
                double giaBan = cursor.getDouble(6);
                byte[] img = cursor.getBlob(7);
                SanPham sanPham = new SanPham(maSP, maLoai, ten, donVi, soLuong, giaNhap, giaBan, img);
                list.add(sanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public List<SanPham> getAllSanPhamTheoGiaGiamDan() {
        List<SanPham> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME + " order by giaBan desc";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maSP = cursor.getString(0);
                String maLoai = cursor.getString(1);
                String ten = cursor.getString(2);
                String donVi = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                double giaNhap = cursor.getDouble(5);
                double giaBan = cursor.getDouble(6);
                byte[] img = cursor.getBlob(7);
                SanPham sanPham = new SanPham(maSP, maLoai, ten, donVi, soLuong, giaNhap, giaBan, img);
                list.add(sanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

}
