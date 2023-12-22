package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import java.util.Objects;

import vn.poly.quanlybanhang.Database.LoaiSanPhamDAO;
import vn.poly.quanlybanhang.Model.LoaiSanPham;

public class ThemLoaiSanPham extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    EditText edMa, edTen;
    LoaiSanPhamDAO loaiSanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_san_pham);
        anhXaView();
        toolbar = findViewById(R.id.toolbar_them_loai_san_pham);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void anhXaView() {
        edMa = findViewById(R.id.edSuaMaMatHang);
        edTen = findViewById(R.id.edThemTenMatHang);
        loaiSanPhamDAO = new LoaiSanPhamDAO(this);
    }

    public void ThemLoaiSanPhamLuu(View view) {
        String ma = edMa.getText().toString();
        String ten = edTen.getText().toString();
        if (ma.equalsIgnoreCase("") || ten.equalsIgnoreCase("")) {
            Toast.makeText(this, "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        LoaiSanPham loaiSanPham = new LoaiSanPham(ma, ten);
        long chk = loaiSanPhamDAO.addLoaiSanPham(loaiSanPham);
        if (chk > 0) {
            Toast.makeText(getApplicationContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoaiSanPhamActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Thêm thất bại,mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ThemLoaiSanPham.this, LoaiSanPhamActivity.class));
    }
}