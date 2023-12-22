package vn.poly.quanlybanhang.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import vn.poly.quanlybanhang.Database.KhachHangDAO;
import vn.poly.quanlybanhang.Model.KhachHang;

public class ChiTietKhachHangActivity extends AppCompatActivity {
    final Context context = this;
    TextView tvTen, tvSDT, tvDiaChi, tvEmail, tvTienNo, tvTienDaMua;
    KhachHangDAO khachHangDAO;
    Button btnUpdate, btnDelete, btnSuaNo;
    KhachHang khachHang;
    EditText edTen, edSoDienThoai, edEmail, edDiaChi, edTienNo;
    ImageView imgLuu;
    String phone;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_hang);
        anhXaView();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phoneNumber");
        khachHang = khachHangDAO.getKhachHangBySDT(phone);
        tvTen.setText("Tên khách hàng : " + khachHang.getTen());
        tvSDT.setText("Số điện thoại : " + khachHang.getSoDienThoai());
        tvDiaChi.setText("Địa chỉ : " + khachHang.getDiaChi());
        tvEmail.setText("Email : " + khachHang.getEmail());
        tvTienNo.setText("Tiền còn nợ : " + Math.round(khachHang.getTienNo()) + " VNĐ");
        tvTienDaMua.setText("Tiền đã thanh toán : " + Math.round(khachHang.getTienDaMua()) + " VNĐ");
        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder b = new AlertDialog.Builder(context);
            b.setTitle("Xác nhận");
            b.setMessage("Bạn có đồng ý xóa khách hàng này không?");
            b.setPositiveButton("Đồng ý", (dialog, id) -> {
                long chk = khachHangDAO.deleteKhachHang(khachHang.getSoDienThoai());
                if (chk > 0) {
                    Toast.makeText(ChiTietKhachHangActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChiTietKhachHangActivity.this, KhachHangActivity.class));
                    dialog.dismiss();
                    startActivity(new Intent(ChiTietKhachHangActivity.this, KhachHangActivity.class));
                } else {
                    Toast.makeText(ChiTietKhachHangActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            b.setNegativeButton("Hủy", (dialog, id) -> dialog.dismiss());
            AlertDialog al = b.create();
            al.show();
        });
        btnUpdate.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_sua_khach_hang);
            dialog.show();
            anhXaViewDia(dialog);
            edTen.setText(khachHang.getTen());
            edSoDienThoai.setText(khachHang.getSoDienThoai());
            edDiaChi.setText(khachHang.getDiaChi());
            edEmail.setText(khachHang.getEmail());
            edTienNo.setText(String.valueOf(Math.round(khachHang.getTienNo())));
            imgLuu.setOnClickListener(view1 -> {
                String ten = edTen.getText().toString();
                String soDienThoai = edSoDienThoai.getText().toString();
                String diaChi = edDiaChi.getText().toString();
                String email = edEmail.getText().toString();
                String tienNo = edTienNo.getText().toString();
                if (ten.equalsIgnoreCase("") || soDienThoai.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Tên , số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                KhachHang khachHang = new KhachHang(ten, email, soDienThoai, diaChi, Integer.parseInt(tienNo), khachHangDAO.getKhachHangBySDT(phone).getTienDaMua());
                try {
                    khachHangDAO.updateKhachHang(khachHang, phone);
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(ChiTietKhachHangActivity.this, KhachHangActivity.class));
                } catch (Exception e) {
                    Toast.makeText(context, "Lưu thất bại , số điện thoại này đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            });
        });
        btnSuaNo.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_sua_no);
            dialog.show();
            Button btnLuu = dialog.findViewById(R.id.btnLuuNo);
            Button btnnHuy = dialog.findViewById(R.id.btnHuySuaNo);
            final EditText edSuaNo = dialog.findViewById(R.id.edSuaTienNoRieng);
            btnnHuy.setOnClickListener(view12 -> dialog.dismiss());
            btnLuu.setOnClickListener(view13 -> {
                String no = edSuaNo.getText().toString();
                if (no.equals("")) {
                    Toast.makeText(ChiTietKhachHangActivity.this, "Vui lòng nhập số tiền ", Toast.LENGTH_SHORT).show();
                    return;
                }
                khachHang.setTienNo(Double.parseDouble(no));
                long chk = khachHangDAO.updateTienNo(khachHang, phone);
                if (chk > 0) {
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    tvTienNo.setText("Tiền còn nợ : " + no + " VNĐ");
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void anhXaView() {
        tvTen = findViewById(R.id.tvTenKH);
        tvDiaChi = findViewById(R.id.tvDiaChiKH);
        tvEmail = findViewById(R.id.tvEmailKH);
        tvTienNo = findViewById(R.id.tvTienNoKH);
        tvTienDaMua = findViewById(R.id.tvTienDaMuaKH);
        tvSDT = findViewById(R.id.tvSDTKH);
        khachHangDAO = new KhachHangDAO(this);
        btnUpdate = findViewById(R.id.btnUpdateKH);
        btnDelete = findViewById(R.id.btnXoaKH);
        btnSuaNo = findViewById(R.id.btnSuaNo);
    }

    private void anhXaViewDia(Dialog dialog) {
        edTen = dialog.findViewById(R.id.edSuaHoTenKH);
        edSoDienThoai = dialog.findViewById(R.id.edSuaSoDienThoaiKH);
        edEmail = dialog.findViewById(R.id.edSuaEmailKH);
        edDiaChi = dialog.findViewById(R.id.edSuaDiaChiKH);
        edTienNo = dialog.findViewById(R.id.edSuaTienNo);
        imgLuu = dialog.findViewById(R.id.imgLuuKH);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChiTietKhachHangActivity.this, KhachHangActivity.class));
    }
}