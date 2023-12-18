package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import vn.poly.quanlybanhang.Adapter.LoaiSanPhamAdapter;
import vn.poly.quanlybanhang.Database.LoaiSanPhamDAO;
import vn.poly.quanlybanhang.Model.LoaiSanPham;

public class LoaiSanPhamActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    EditText edTimKiem;
    ListView lvList;
    List<LoaiSanPham> list;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    LoaiSanPhamDAO loaiSanPhamDAO;
    TextView tvKhongDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        anhXaView();
        toolbar = findViewById(R.id.toolbar_danh_muc);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        loaiSanPhamDAO = new LoaiSanPhamDAO(this);
        doDuLieu();
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<LoaiSanPham> list = loaiSanPhamDAO.getAllLoaiSanPhamTheoMa(edTimKiem.getText().toString());
                LoaiSanPhamAdapter loaiSanPhamAdapter = new LoaiSanPhamAdapter(LoaiSanPhamActivity.this, list);
                lvList.setAdapter(loaiSanPhamAdapter);
                tvKhongDuLieu.setVisibility(View.INVISIBLE);
                if (edTimKiem.getText().toString().equalsIgnoreCase("")) {
                    doDuLieu();
                }
                if (list.size() == 0) {
                    tvKhongDuLieu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
        lvList = findViewById(R.id.lvLoaiSanPham);
        edTimKiem = findViewById(R.id.edTimKiemLoaiSanPham);
        tvKhongDuLieu = findViewById(R.id.tvRongLoaiSanPham);
    }

    public void LoaiSanPhamThem(View view) {
        Intent intent = new Intent(LoaiSanPhamActivity.this, ThemLoaiSanPham.class);
        startActivity(intent);
    }

    private void doDuLieu() {
        list = loaiSanPhamDAO.getAllLoaiSanPham();
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(this, list);
        lvList.setAdapter(loaiSanPhamAdapter);
    }
}