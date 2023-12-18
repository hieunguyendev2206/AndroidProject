package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.duan1android.R;

import java.util.Objects;

import vn.poly.quanlybanhang.Database.KhachHangDAO;
import vn.poly.quanlybanhang.Model.KhachHang;

public class ThemKhachHangActivity extends AppCompatActivity {
    EditText edTen, edSoDienThoai, edDiaChi, edEmail;
    Toolbar toolbar;
    KhachHangDAO khachHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        anhXaView();
        toolbar = findViewById(R.id.toolbar_them_nguoi_dung);
        setSupportActionBar(toolbar);
        anhXaView();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhXaView() {
        edTen = findViewById(R.id.edThemHoTenKH);
        edSoDienThoai = findViewById(R.id.edThemSoDienThoaiKH);
        edDiaChi = findViewById(R.id.edThemDiaChiKH);
        edEmail = findViewById(R.id.edThemEmailKH);
        khachHangDAO = new KhachHangDAO(this);
    }

    public void ThemNguoiDungLuu(View view) {
        String ten = edTen.getText().toString();
        String soDienThoai = edSoDienThoai.getText().toString();
        String diaChi = edDiaChi.getText().toString();
        String email = edEmail.getText().toString();
        if (ten.equalsIgnoreCase("") || soDienThoai.equalsIgnoreCase("")) {
            Toast.makeText(ThemKhachHangActivity.this, "Tên , số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!email.matches(regexEmail)) {
            Toast.makeText(ThemKhachHangActivity.this, "Email không đúng định dạng ", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneNumberRegex = "^0[0-9]{9}$";
        if (!soDienThoai.matches(phoneNumberRegex)) {
            Toast.makeText(ThemKhachHangActivity.this, "Số điện thoại không đúng định dạng ", Toast.LENGTH_SHORT).show();
            return;
        }
        KhachHang khachHang = new KhachHang(ten, email, soDienThoai, diaChi, 0, 0);
        long chk = khachHangDAO.addKhachHang(khachHang);
        if (chk > 0) {
            Toast.makeText(ThemKhachHangActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ThemKhachHangActivity.this, KhachHangActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ThemKhachHangActivity.this, "Thêm thất bại , người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ThemKhachHangActivity.this, KhachHangActivity.class));
    }
}