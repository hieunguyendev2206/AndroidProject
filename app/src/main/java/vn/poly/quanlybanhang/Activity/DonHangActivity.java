package vn.poly.quanlybanhang.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Adapter.DonHangAdapter;
import vn.poly.quanlybanhang.Adapter.KhachHangAdapter;
import vn.poly.quanlybanhang.Database.HoaDonChiTietDAO;
import vn.poly.quanlybanhang.Database.HoaDonDAO;
import vn.poly.quanlybanhang.Database.KhachHangDAO;
import vn.poly.quanlybanhang.Database.SanPhamDAO;
import vn.poly.quanlybanhang.Model.HoaDon;
import vn.poly.quanlybanhang.Model.HoaDonChiTiet;
import vn.poly.quanlybanhang.Model.KhachHang;
import vn.poly.quanlybanhang.Model.SanPham;


public class DonHangActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ListView lvList;
    EditText edMaDonHang;
    TextView tvChietKhau, tvTongTien, tvKhachHang;
    ImageView imgChietKhau, imgXoaDonHang;
    DonHangAdapter donHangAdapter;
    ToggleButton toggleButton;
    EditText edChietKhau;
    int tongTien = 0;
    String khachHang = "Khách lẻ";
    List<KhachHang> listKH;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    HoaDonDAO hoaDonDAO;
    KhachHangDAO khachHangDAO;
    int chietKhau = 0;
    int tienFinal = 0;
    KhachHang KH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        anhXaView();
        toolbar = findViewById(R.id.toolbar_ban_hang);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        donHangAdapter = new DonHangAdapter(DonHangActivity.this, MatHangActivity.gioHangList);
        lvList.setAdapter(donHangAdapter);
        getTongTien();
        addChietKhau();
        xoaDonHang();

    }


    public void anhXaView() {
        lvList = findViewById(R.id.lvListHang_GH);
        edMaDonHang = findViewById(R.id.edMaDonHang_GH);
        tvKhachHang = findViewById(R.id.edKhachHang_GH);
        tvChietKhau = findViewById(R.id.tvChietKhau_GH);
        tvTongTien = findViewById(R.id.tvTongTien_GH);
        imgChietKhau = findViewById(R.id.imgChietKhau_GH);
        imgXoaDonHang = findViewById(R.id.imgxoaDonHang);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
        khachHangDAO = new KhachHangDAO(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("SetTextI18n")
    private void addChietKhau() {
        imgChietKhau.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(DonHangActivity.this);
            dialog.setContentView(R.layout.chiet_khau_dialog);
            dialog.show();

            toggleButton = dialog.findViewById(R.id.toggleButton);
            edChietKhau = dialog.findViewById(R.id.edChietKhau);
            Button btnOk = dialog.findViewById(R.id.btnOkCK);
            Button btnHuy = dialog.findViewById(R.id.btnHuyCK);
            //   toogle true = tiền mặt ; false = %
            btnOk.setOnClickListener(view1 -> {
                if (edChietKhau.getText().toString().equalsIgnoreCase("")) {
                    tvChietKhau.setText("0");
                    dialog.dismiss();
                    return;
                }
                if (toggleButton.isChecked()) {
                    if (Integer.parseInt(edChietKhau.getText().toString()) > tongTien) {
                        Toast.makeText(DonHangActivity.this, "Chiết khẩu không được lớn hơn số tiền thanh toán", Toast.LENGTH_SHORT).show();
                        edChietKhau.setText("");
                        return;
                    } else {
                        chietKhau = Integer.parseInt(edChietKhau.getText().toString());
                        tvChietKhau.setText(edChietKhau.getText().toString() + " VNĐ");
                        tvTongTien.setText((tongTien - Integer.parseInt(edChietKhau.getText().toString())) + " VNĐ");
                        tienFinal = (tongTien - Integer.parseInt(edChietKhau.getText().toString()));
                    }
                } else {
                    if (Integer.parseInt(edChietKhau.getText().toString()) < 0 || Integer.parseInt(edChietKhau.getText().toString()) > 100) {
                        Toast.makeText(DonHangActivity.this, "Chiết khẩu phải từ 0-100%", Toast.LENGTH_SHORT).show();
                        edChietKhau.setText("");
                        return;
                    } else {
                        tvChietKhau.setText(edChietKhau.getText().toString() + " %");
                        tvTongTien.setText((tongTien - (tongTien * (Double.parseDouble(edChietKhau.getText().toString()) / 100))) + " VNĐ");
                        tienFinal = (int) (tongTien - (tongTien * (Double.parseDouble(edChietKhau.getText().toString()) / 100)));
                        chietKhau = (int) (tongTien * (Double.parseDouble(edChietKhau.getText().toString()) / 100));
                    }
                }
                dialog.dismiss();
            });
            btnHuy.setOnClickListener(view12 -> dialog.dismiss());

        });
    }

    @SuppressLint("SetTextI18n")
    private void getTongTien() {
        for (int i = 0; i < MatHangActivity.gioHangList.size(); i++) {
            tongTien += (MatHangActivity.gioHangList.get(i).getGia() * MatHangActivity.gioHangList.get(i).getSoLuong());
        }
        tvTongTien.setText(tongTien + " VNĐ");
        tienFinal = tongTien;
    }

    private void xoaDonHang() {
        imgXoaDonHang.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DonHangActivity.this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc muốn xóa đơn hàng này ?");
            builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                MatHangActivity.gioHangList.clear();
                tvTongTien.setText("0");
                DonHangAdapter donHangAdapter = new DonHangAdapter(DonHangActivity.this, MatHangActivity.gioHangList);
                lvList.setAdapter(donHangAdapter);
            });
            builder.setNegativeButton("Không đồng ý", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @SuppressLint("SetTextI18n")
    public void ThanhToanDonHang(View view) {
        if (MatHangActivity.gioHangList.size() == 0) {
            Toast.makeText(getApplicationContext(), "Giỏ hàng rỗng , hãy thêm sản phẩm trước khi thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        String mahoadon = edMaDonHang.getText().toString();
        if (mahoadon.equalsIgnoreCase("")) {
            Toast.makeText(DonHangActivity.this, "Mã đơn hàng không dược trống", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(DonHangActivity.this, android.R.style.Theme);
        dialog.setContentView(R.layout.dialog_thanh_toan);
        dialog.show();
        final TextView tvTongTien = dialog.findViewById(R.id.tvTongTien);
        final EditText edTienTra = dialog.findViewById(R.id.edThanhToan);
        final TextView tvTienTra = dialog.findViewById(R.id.tvTienTra);
        Button btnThanhToan = dialog.findViewById(R.id.btnThanhToan);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCK);
        final Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(c);
        btnHuy.setOnClickListener(view1 -> dialog.dismiss());
        btnThanhToan.setOnClickListener(view12 -> {
            long chk = -1;
            hoaDonDAO = new HoaDonDAO(getApplicationContext());
            String mahoadon1 = edMaDonHang.getText().toString();
            HoaDon hoaDon = null;

            try {
                hoaDon = new HoaDon(mahoadon1, khachHang, df.parse(formattedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert hoaDon != null;
            long chk1 = hoaDonDAO.addHoaDon(hoaDon);
            if (chk1 > 0) {
                //cập nhật lại số lượng đã bán
                SanPhamDAO sanPhamDAO = new SanPhamDAO(getApplicationContext());
                List<SanPham> sanPhamList;
                sanPhamList = sanPhamDAO.getAllSanPham();
                int soluong;
                for (int i = 0; i < MatHangActivity.gioHangList.size(); i++) {
                    for (SanPham sanPham : sanPhamList) {
                        if (MatHangActivity.gioHangList.get(i).getMa().equalsIgnoreCase(sanPham.getMaSanPham())) {
                            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(mahoadon1, MatHangActivity.gioHangList.get(i));
                            chk = hoaDonChiTietDAO.addHDCT(hoaDonChiTiet);
                            if (chk > 0) {
                                soluong = sanPhamList.get(i).getSoLuong() - MatHangActivity.gioHangList.get(i).getSoLuong();
                                sanPhamDAO.updateSLSanPham(soluong, sanPhamList.get(i).getMaSanPham());
                            }
                        }
                    }
                }

                if (chk > 0) {
                    String trangthai = null;
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    if (Integer.parseInt(edTienTra.getText().toString()) > 0) {
                        trangthai = "Đã Thanh Toán";
                    }
                    if (Integer.parseInt(edTienTra.getText().toString()) <= 0) {
                        trangthai = "Chưa Thanh Toán";
                    }
                    hoaDon.setKhachTra(Integer.parseInt(edTienTra.getText().toString()));
                    hoaDon.setTraLai(Integer.parseInt(tvTienTra.getText().toString()));
                    hoaDon.setChietKhau(chietKhau);
                    hoaDon.setTongTien(tongTien);
                    hoaDon.setTrangThai(trangthai);
                    hoaDonDAO.updateHoaDon(hoaDon, hoaDon.getMaHD());
                    try {
                        if (!khachHang.equalsIgnoreCase("Khách lẻ")) {
                            double tien = Double.parseDouble(edTienTra.getText().toString());
                            double tienNo = KH.getTienNo();
                            if (tienFinal - tien > 0) {
                                tienNo += (tienFinal - tien);
                            }
                            double tienDaMua = KH.getTienDaMua() + tongTien;
                            khachHangDAO.updateTien(tienNo, tienDaMua, KH.getSoDienThoai());
                        }
                    } catch (Exception ignored) {

                    }
                    Intent intent = new Intent(DonHangActivity.this, MatHangActivity.class);
                    startActivity(intent);
                    MatHangActivity.gioHangList.clear();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    hoaDonDAO.deleteHoaDon(mahoadon1);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại , mã hóa đơn đã tồn tại", Toast.LENGTH_SHORT).show();
            }


        });
        tvTongTien.setText(String.valueOf(tienFinal));
        edTienTra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int tientra = Integer.parseInt(edTienTra.getText().toString()) - tienFinal;
                    tvTienTra.setText(String.valueOf(tientra));
                } catch (Exception ignored) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    public void ChonKhachHang(View view) {
        final Dialog dialog = new Dialog(DonHangActivity.this, android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_khach_hang);
        dialog.show();
        final ListView listView = dialog.findViewById(R.id.lvKhachHang);
        KhachHangDAO khachHangDAO = new KhachHangDAO(this);
        listKH = new ArrayList<>();
        listKH = khachHangDAO.getAllKhachHang();
        KhachHangAdapter khachHangAdapter = new KhachHangAdapter(this, listKH);
        listView.setAdapter(khachHangAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            KH = listKH.get(i);
            khachHang = KH.getTen();
            tvKhachHang.setText(khachHang);
            dialog.dismiss();
        });

    }


}