package vn.poly.quanlybanhang.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.poly.quanlybanhang.Model.LoaiSanPham;

import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {
    public static final String TABLE_NAME = "LoaiSanPham";
    public static final String SQL_LOAISANPHAM = "Create table if not exists LoaiSanPham (" +
            "   maLoai text primary key," +
            "   tenLoai text)";

    private final SQLiteDatabase sqLiteDatabase;

    public LoaiSanPhamDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }
    public long addLoaiSanPham(LoaiSanPham loaiSanPham){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai",loaiSanPham.getMaLoai());
        contentValues.put("tenLoai",loaiSanPham.getTenLoai());
        return sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }
    public long updateLoaiSanPham(LoaiSanPham loaiSanPham,String ma){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai",loaiSanPham.getMaLoai());
        contentValues.put("tenLoai",loaiSanPham.getTenLoai());
        return sqLiteDatabase.update(TABLE_NAME,contentValues,"maLoai = ?",new String[]{ma});
    }
    public long deleteLoaiSanPham(String ma){
        return sqLiteDatabase.delete(TABLE_NAME,"maLoai = ?",new String[]{ma});
    }
    public List<LoaiSanPham> getAllLoaiSanPham(){
        List<LoaiSanPham> list = new ArrayList<>();
        String query = "Select * from "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maLoai = cursor.getString(0);
                String tenLoai = cursor.getString(1);
                LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai,tenLoai);
                list.add(loaiSanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public List<String> getAllTenLoaiSanPham(){
        List<String> list = new ArrayList<>();
        String query = "Select tenLoai from "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String ten = cursor.getString(0);
                list.add(ten);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public List<LoaiSanPham> getAllLoaiSanPhamTheoMa(String ma){
        List<LoaiSanPham> list = new ArrayList<>();
        String query = "Select DISTINCT * from "+TABLE_NAME+ " where maLoai like '%"+ma+"%' or tenLoai like '%"+ma+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maLoai = cursor.getString(0);
                String tenLoai = cursor.getString(1);
                LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai,tenLoai);
                list.add(loaiSanPham);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
