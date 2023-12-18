package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Adapter.DonViTinhAdapter;
import vn.poly.quanlybanhang.Database.DonViTinhDAO;
import vn.poly.quanlybanhang.Model.DonViTinh;

public class DonViTinhActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ListView lvList;
    DonViTinhAdapter donViTinhAdapter;
    DonViTinhDAO donViTinhDAO;
    List<DonViTinh> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_vi_tinh);
        toolbar = findViewById(R.id.toolbar_don_vi_tinh);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        anhXaView();
        list = donViTinhDAO.getAllDonViTinh2();
        donViTinhAdapter = new DonViTinhAdapter(this, list);
        lvList.setAdapter(donViTinhAdapter);
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
        lvList = findViewById(R.id.lvDonViTinh);
        donViTinhDAO = new DonViTinhDAO(this);
        list = new ArrayList<>();
    }

    public void DonViTinhThem(View view) {
        Intent intent = new Intent(DonViTinhActivity.this, ThemDonViTinh.class);
        startActivity(intent);
    }

}