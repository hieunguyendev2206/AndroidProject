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

import vn.poly.quanlybanhang.Adapter.SanPhamAdapter;
import vn.poly.quanlybanhang.Database.SanPhamDAO;
import vn.poly.quanlybanhang.Model.SanPham;

public class SanPhamActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ListView lvList;
    EditText edTimKiem;
    SanPhamDAO sanPhamDAO;
    List<SanPham> list;
    SanPhamAdapter sanPhamAdapter;
    TextView tvKhongTimThay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        anhXaView();
        toolbar = findViewById(R.id.toolbar_san_pham);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        sanPhamDAO = new SanPhamDAO(this);
        list = new ArrayList<>();
        doDuLieu();
        lvList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(SanPhamActivity.this, ChiTietSanPhamActivity.class);
            SanPham sanPham = list.get(i);
            intent.putExtra("sanPham", sanPham.getMaSanPham());
            startActivity(intent);
        });
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<SanPham> list = sanPhamDAO.getAllSanPhamTheoMa(edTimKiem.getText().toString());
                SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(SanPhamActivity.this, list);
                lvList.setAdapter(sanPhamAdapter);
                tvKhongTimThay.setVisibility(View.INVISIBLE);
                if (edTimKiem.getText().toString().equalsIgnoreCase("")) {
                    doDuLieu();
                }
                if (list.size() == 0) {
                    tvKhongTimThay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void anhXaView() {
        lvList = findViewById(R.id.lvSanPham_actSP);
        edTimKiem = findViewById(R.id.edTimKiemSanPham1);
        tvKhongTimThay = findViewById(R.id.tvKhongTimThay);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SanPhamThem(View view) {
        Intent intent = new Intent(this, ThemSanPhamActivity.class);
        startActivity(intent);
    }

    private void doDuLieu() {
        list = sanPhamDAO.getAllSanPham();
        sanPhamAdapter = new SanPhamAdapter(this, list);
        lvList.setAdapter(sanPhamAdapter);
    }


}