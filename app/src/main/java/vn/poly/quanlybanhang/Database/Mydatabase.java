package vn.poly.quanlybanhang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {
    public Mydatabase(@Nullable Context context) {
        super(context, "data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SanPhamDAO.SQL_SANPHAM);
        sqLiteDatabase.execSQL(HoaDonDAO.SQL_HOADON);
        sqLiteDatabase.execSQL(HoaDonChiTietDAO.SQL_HDCT);
        sqLiteDatabase.execSQL(KhachHangDAO.SQL_NGUOIDUNG);
        sqLiteDatabase.execSQL(LoaiSanPhamDAO.SQL_LOAISANPHAM);
        sqLiteDatabase.execSQL(DonViTinhDAO.SQL_DONVI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table " + SanPhamDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table " + HoaDonDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table " + HoaDonChiTietDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table " + KhachHangDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table " + LoaiSanPhamDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table " + DonViTinhDAO.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
