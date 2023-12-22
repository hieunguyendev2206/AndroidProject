package vn.poly.quanlybanhang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.quanlybanhang.Model.DonViTinh;

public class DonViTinhDAO {
    public static final String TABLE_NAME = "DonViTinh";
    public static final String SQL_DONVI = "Create table if not exists DonViTinh (" + "   tenDonVi text primary key )";

    final SQLiteDatabase sqLiteDatabase;

    public DonViTinhDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    public long addDonVi(String ten) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDonVi", ten);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void updateDonVi(String tenMoi, String tenCu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDonVi", tenMoi);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "tenDonVi = ?", new String[]{tenCu});
    }

    public long deleteDonVi(String ten) {
        return sqLiteDatabase.delete(TABLE_NAME, "tenDonVi = ?", new String[]{ten});
    }

    public List<DonViTinh> getAllDonViTinh2() {
        List<DonViTinh> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ten = cursor.getString(0);
                DonViTinh donViTinh = new DonViTinh(ten);
                list.add(donViTinh);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public List<String> getAllDonViTinh() {
        List<String> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ten = cursor.getString(0);
                list.add(ten);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
