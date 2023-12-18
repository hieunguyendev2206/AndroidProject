package vn.poly.quanlybanhang.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import vn.poly.quanlybanhang.Database.SanPhamDAO;
import vn.poly.quanlybanhang.Model.SanPham;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    TextView tvMa, tvTen, tvSoLuong, tvGiaBan, tvGiaNhap, tvDanhMuc, tvDonViTinh;
    Button btnChinhSua, btnXoa;
    ImageView imgSP;
    String ma;
    Intent intent;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXaView();
        intent = getIntent();
        ma = intent.getStringExtra("sanPham");
        final SanPhamDAO sanPhamDAO = new SanPhamDAO(this);
        SanPham sanPham = sanPhamDAO.getSanPhamTheoMa(ma);
        tvMa.setText("Mã mặt hàng : " + sanPham.getMaSanPham());
        tvTen.setText("Tên mặt hàng : " + sanPham.getTen());
        tvSoLuong.setText("Số lượng : " + sanPham.getSoLuong());
        tvDonViTinh.setText("Đơn vị tính : " + sanPham.getDonViTinh());
        tvGiaNhap.setText("Giá nhập : " + Math.round(sanPham.getGiaNhap()) + " VNĐ");
        tvGiaBan.setText("Giá bán : " + Math.round(sanPham.getGiaBan()) + " VNĐ");
        tvDanhMuc.setText("Danh mục : " + sanPham.getMaLoai());
        byte[] image = sanPham.getImage();
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgSP.setImageBitmap(bitmap);
        } catch (Exception e) {
            imgSP.setImageResource(R.drawable.ic_sanpham1);
        }


        // sự kiện update:
        btnChinhSua.setOnClickListener(view -> {
            ma = intent.getStringExtra("sanPham");
            Intent intent1 = new Intent(ChiTietSanPhamActivity.this, SuaSanPhamActivity.class);
            intent1.putExtra("ma", ma);
            startActivity(intent1);
            finish();
        });
        btnXoa.setOnClickListener(view -> {
            ma = intent.getStringExtra("sanPham");
            AlertDialog.Builder b = new AlertDialog.Builder(ChiTietSanPhamActivity.this);
            b.setTitle("Xác nhận");
            b.setMessage("Bạn có đồng ý xóa sản phẩm này không?");
            b.setPositiveButton("Đồng ý", (dialog, id) -> {
                long chk = sanPhamDAO.deleteSanPham(ma);
                if (chk > 0) {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChiTietSanPhamActivity.this, SanPhamActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            b.setNegativeButton("Không đồng ý", (dialog, id) -> dialog.cancel());
            AlertDialog al = b.create();
            al.show();
        });
    }

    private void anhXaView() {
        tvMa = findViewById(R.id.tvMaCTSP);
        tvTen = findViewById(R.id.tvTenCTSP);
        tvSoLuong = findViewById(R.id.tvSoLuongCTSP);
        tvGiaBan = findViewById(R.id.tvGiaBanCTSP);
        tvGiaNhap = findViewById(R.id.tvGiaNhapCTSP);
        tvDanhMuc = findViewById(R.id.tvDanhMucCTSP);
        tvDonViTinh = findViewById(R.id.tvDonViCTSP);
        btnChinhSua = findViewById(R.id.btnUpdateCTSP);
        btnXoa = findViewById(R.id.btnXoaSanPhamCTSP);
        imgSP = findViewById(R.id.imgCTSP);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChiTietSanPhamActivity.this, SanPhamActivity.class);
        startActivity(intent);
    }
}