package vn.poly.quanlybanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import vn.poly.quanlybanhang.Database.DonViTinhDAO;

public class ThemDonViTinh extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    EditText edThemDonViTinh;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_don_vi_tinh);
        toolbar = findViewById(R.id.toolbar_them_don_vi_tinh);
        anhXaView();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    public void anhXaView() {
        edThemDonViTinh = findViewById(R.id.edThemDonViTinh);
        linearLayout = findViewById(R.id.lnThemDonViTinh);

    }

    public void ThemDonViTinhLuu(View view) {
        String donVi = edThemDonViTinh.getText().toString();
        if (donVi.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(linearLayout, "Vui lòng điền đầy đủ thông tin", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        DonViTinhDAO donViTinhDAO = new DonViTinhDAO(this);
        long chk = donViTinhDAO.addDonVi(donVi);
        if (chk > 0) {
            Toast.makeText(getApplicationContext(), "Thêm đơn vị thành công", Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(this, DonViTinhActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Thêm Không thành công", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThemDonViTinh.this, DonViTinhActivity.class);
        startActivity(intent);
    }
}