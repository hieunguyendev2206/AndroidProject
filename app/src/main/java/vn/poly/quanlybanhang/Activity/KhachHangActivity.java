package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Adapter.KhachHangAdapter;
import vn.poly.quanlybanhang.Database.KhachHangDAO;
import vn.poly.quanlybanhang.Model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    EditText edTimKiem;
    ListView lvList;
    List<KhachHang> list;
    KhachHangDAO khachHangDAO;
    KhachHangAdapter khachHangAdapter;
    TextView tvKhongCoNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        toolbar = findViewById(R.id.toolbar_nguoi_dung);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        anhXaView();
        doDuLieu();
        lvList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(KhachHangActivity.this, ChiTietKhachHangActivity.class);
            intent.putExtra("phoneNumber", list.get(i).getSoDienThoai());
            Log.e("SĐT", "" + list.get(i).getSoDienThoai());
            startActivity(intent);
        });
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<KhachHang> list = khachHangDAO.timKiemKhachHang(edTimKiem.getText().toString());
                KhachHangAdapter khachHangAdapter = new KhachHangAdapter(KhachHangActivity.this, list);
                lvList.setAdapter(khachHangAdapter);
                tvKhongCoNguoiDung.setVisibility(View.INVISIBLE);
                if (edTimKiem.getText().toString().equalsIgnoreCase("")) {
                    doDuLieu();
                }
                if (list.size() == 0) {
                    tvKhongCoNguoiDung.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void doDuLieu() {
        list = khachHangDAO.getAllKhachHang();
        khachHangAdapter = new KhachHangAdapter(this, list);
        lvList.setAdapter(khachHangAdapter);
    }

    public void NguoiDungThem(View view) {
        Intent intent = new Intent(KhachHangActivity.this, ThemKhachHangActivity.class);
        startActivity(intent);
    }

    public void anhXaView() {
        edTimKiem = findViewById(R.id.edTimKiemNguoiDung);
        lvList = findViewById(R.id.lvNguoiDung);
        list = new ArrayList<>();
        khachHangDAO = new KhachHangDAO(this);
        tvKhongCoNguoiDung = findViewById(R.id.tvKhongCoNguoiDung);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}