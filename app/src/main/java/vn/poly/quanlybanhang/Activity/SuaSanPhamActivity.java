package vn.poly.quanlybanhang.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.duan1android.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Database.DonViTinhDAO;
import vn.poly.quanlybanhang.Database.LoaiSanPhamDAO;
import vn.poly.quanlybanhang.Database.SanPhamDAO;
import vn.poly.quanlybanhang.Model.SanPham;

public class SuaSanPhamActivity extends AppCompatActivity {
    final int REQUEST_CODE_FOLDER = 456;
    androidx.appcompat.widget.Toolbar toolbar;
    EditText edMa, edTen, edSoLuong, edGiaBan, edGiaNhap;
    String ma, ten, soLuong, giaBan, giaNhap, donViTinh, theLoai;
    ImageView imgThemAnh;
    Spinner spnDonViTinh, spnDanhMuc;
    List<String> listDonVi, listTheLoai;
    ImageView imgSuaSanPham, imgThemSuaDanhMuc, imgThemSuaDonVi;
    byte[] hinhAnh;
    SanPhamDAO sanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        toolbar = findViewById(R.id.toolbar_sua_san_pham);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        anhXaView();
        sanPhamDAO = new SanPhamDAO(this);
        doDuLieu();
        themDanhMuc_DonVi();
        //Đổ dữu liệu cho spinner:
        listDonVi = new ArrayList<>();
        listTheLoai = new ArrayList<>();
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(this);
        DonViTinhDAO donViTinhDAO = new DonViTinhDAO(this);
        listDonVi = donViTinhDAO.getAllDonViTinh();
        listTheLoai = loaiSanPhamDAO.getAllTenLoaiSanPham();
        ArrayAdapter<String> adapterDonVi = new ArrayAdapter<>(this, R.layout.spinner_item, listDonVi);
        spnDonViTinh.setAdapter(adapterDonVi);
        ArrayAdapter<String> adapterTheLoai = new ArrayAdapter<>(this, R.layout.spinner_item, listTheLoai);
        spnDanhMuc.setAdapter(adapterTheLoai);
        spnDonViTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                donViTinh = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theLoai = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //sự kiện load ảnh
        imgThemAnh.setOnClickListener(view -> ActivityCompat.requestPermissions(SuaSanPhamActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER));
        imgSuaSanPham.setOnClickListener(view -> updateSanPham());
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
        startActivity(new Intent(SuaSanPhamActivity.this, SanPhamActivity.class));
    }

    private void themDanhMuc_DonVi() {
        imgThemSuaDanhMuc.setOnClickListener(view -> startActivity(new Intent(SuaSanPhamActivity.this, ThemLoaiSanPham.class)));
        imgThemSuaDonVi.setOnClickListener(view -> startActivity(new Intent(SuaSanPhamActivity.this, ThemDonViTinh.class)));
    }

    private void anhXaView() {
        imgSuaSanPham = findViewById(R.id.imgLuuSuaSanPham);
        imgThemAnh = findViewById(R.id.suaAnhSanPham);
        edMa = findViewById(R.id.edSuaMaMatHang);
        edTen = findViewById(R.id.edSuaTenMatHang);
        edSoLuong = findViewById(R.id.edSuaSoLuong);
        edGiaBan = findViewById(R.id.edSuaGiaBan);
        edGiaNhap = findViewById(R.id.edSuaGiaNhap);
        spnDonViTinh = findViewById(R.id.spnSuaDonViTinh);
        spnDanhMuc = findViewById(R.id.spnSuaDanhMuc);
        imgThemSuaDanhMuc = findViewById(R.id.themDanhMucSuaSanPham);
        imgThemSuaDonVi = findViewById(R.id.themDonViSuaSanPham);
    }


    public void updateSanPham() {
        ma = edMa.getText().toString();
        ten = edTen.getText().toString();
        soLuong = edSoLuong.getText().toString();
        giaBan = edGiaBan.getText().toString();
        giaNhap = edGiaNhap.getText().toString();
        if (ma.equalsIgnoreCase("") || ten.equalsIgnoreCase("") || soLuong.equalsIgnoreCase("")
                || giaBan.equalsIgnoreCase("") || giaNhap.equalsIgnoreCase("")) {
            Toast.makeText(SuaSanPhamActivity.this, "Vui lòng điền đủ dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgThemAnh.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            hinhAnh = byteArray.toByteArray();

            if (hinhAnh.length > 2000000) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap1, (int) (bitmap1.getWidth() * 0.1), (int) (bitmap1.getHeight() * 0.1), true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                hinhAnh = stream.toByteArray();
            }
        } catch (Exception ignored) {

        }
        SanPham sanPham = new SanPham(ma, theLoai, ten, donViTinh, Integer.parseInt(soLuong), Double.parseDouble(giaNhap), Double.parseDouble(giaBan), hinhAnh);
        Intent intent = getIntent();
        String ma = intent.getStringExtra("ma");
        try {
            sanPhamDAO.updateSanPham(sanPham, ma);
            Toast.makeText(SuaSanPhamActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            Intent myIten = new Intent(SuaSanPhamActivity.this, SanPhamActivity.class);
            startActivity(myIten);
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Lưu thất bại , mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FOLDER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            InputStream inputStream;
            try {
                assert uri != null;
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgThemAnh.setImageBitmap(bitmap);
                //
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @SuppressLint("SetTextI18n")
    private void doDuLieu() {
        Intent intent = getIntent();
        String ma = intent.getStringExtra("ma");
        try {
            SanPham sanPham = sanPhamDAO.getSanPhamTheoMa(ma);
            edTen.setText(sanPham.getTen());
            edGiaBan.setText("" + Math.round(sanPham.getGiaBan()));
            edGiaNhap.setText("" + Math.round(sanPham.getGiaNhap()));
            edMa.setText(sanPham.getMaSanPham());
            edSoLuong.setText("" + sanPham.getSoLuong());
        } catch (Exception e) {
            Toast.makeText(SuaSanPhamActivity.this, "Không lấy đc dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}