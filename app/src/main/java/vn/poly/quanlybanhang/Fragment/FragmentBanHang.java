package vn.poly.quanlybanhang.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duan1android.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Activity.DonHangActivity;
import vn.poly.quanlybanhang.Activity.MatHangActivity;
import vn.poly.quanlybanhang.Adapter.SanPhamAdapter;
import vn.poly.quanlybanhang.Database.SanPhamDAO;
import vn.poly.quanlybanhang.Model.GioHang;
import vn.poly.quanlybanhang.Model.SanPham;


public class FragmentBanHang extends Fragment {
    static int tong = 0;
    final String[] danhSachLC = {"Theo tên", "Giá ↑", "Giá ↓"};
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    EditText edTimKiem;
    ListView lvList;
    Spinner spnLocDanhSach;
    ImageView imageView;
    List<SanPham> list;
    SanPhamDAO sanPhamDAO;
    SanPhamAdapter sanPhamAdapter;
    TextView tvNull;
    int soLuong;
    TextView tvSoLuongBanHang;

    public FragmentBanHang() {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ban_hang, container, false);
        toolbar = view.findViewById(R.id.toolbar_ban_hang);
        imageView = view.findViewById(R.id.imgBanHang);
        navigationView = view.findViewById(R.id.NavigationViewBanHang);
        drawerLayout = view.findViewById(R.id.drawerLayoutBanHang);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);

        //onClick item navigatinonView
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_sendEmail:
                    Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:?subject=" + "Phản Hồi Ứng Dụng" + "&body=" + "nội dung" + "&to=" + "nduc99911@gmail.com");
                    mailIntent.setData(data);
                    startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                    break;
                case R.id.nav_thoat:
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory(Intent.CATEGORY_HOME);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    break;
            }
            return false;
        });
        toolbar.setNavigationOnClickListener(view1 -> drawerLayout.openDrawer(GravityCompat.START));
        imageView.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), DonHangActivity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXaView(view);
        sanPhamDAO = new SanPhamDAO(getActivity());
        list = new ArrayList<>();
        setHasOptionsMenu(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, danhSachLC);
        spnLocDanhSach.setAdapter(adapter);
        doDuLieuTheoSpinner();
        timKiem();
        themSanPhamVaoGio();
    }

    @Override
    public void onResume() {
        if (MatHangActivity.gioHangList.size() == 0) {
            tong = 0;
            soLuong = 0;
            tvSoLuongBanHang.setVisibility(View.INVISIBLE);
            doDuLieuTheoSpinner();
        }
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    private void themSanPhamVaoGio() {
        lvList.setOnItemClickListener((adapterView, view, i, l) -> {
            if (list.get(i).getSoLuong() <= 0) {
                Toast.makeText(getContext(), "Mặt hàng này đã hết", Toast.LENGTH_SHORT).show();
                return;
            }
            final Dialog dialog = new Dialog(requireContext(), android.R.style.Theme);
            dialog.setContentView(R.layout.chon_so_luong_dialog);
            dialog.show();
            TextView tvTen = dialog.findViewById(R.id.tvTenSanPhamSL);
            TextView tvSoLuongSP = dialog.findViewById(R.id.tvSoLuongSanPhamSL);
            TextView tvGia = dialog.findViewById(R.id.tvGiaSanPhamSL);
            ImageView imgCong = dialog.findViewById(R.id.imgCongSoLuong);
            ImageView imgTru = dialog.findViewById(R.id.imgTruSoLuong);
            ImageView imgSPDia = dialog.findViewById(R.id.imgSPDia);
            Button btnOk = dialog.findViewById(R.id.btnThemVaoGio);
            Button btnHuy = dialog.findViewById(R.id.btnHuyThemVaoGio);
            soLuong = 1;
            final TextView tvSoLuongMua = dialog.findViewById(R.id.tvSoLuongChonMua);
            byte[] image = list.get(i).getImage();
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgSPDia.setImageBitmap(bitmap);
            } catch (Exception e) {
                imgSPDia.setImageResource(R.drawable.ic_sanpham1);
            }
            tvTen.setText(list.get(i).getTen());
            tvSoLuongSP.setText("Còn :" + list.get(i).getSoLuong());
            tvGia.setText(list.get(i).getGiaBan() + " VNĐ");
            imgCong.setOnClickListener(view1 -> {
                if (soLuong >= list.get(i).getSoLuong()) {
                    soLuong = list.get(i).getSoLuong();
                    tvSoLuongMua.setText(String.valueOf(soLuong));
                    Toast.makeText(getContext(), "Đã đạt giới hạn số lượng", Toast.LENGTH_SHORT).show();
                } else {
                    soLuong++;
                    tvSoLuongMua.setText(String.valueOf(soLuong));
                }
            });
            imgTru.setOnClickListener(view12 -> {
                if (soLuong <= 0) {
                    soLuong = 0;
                } else {
                    soLuong--;

                }
                tvSoLuongMua.setText(String.valueOf(soLuong));
            });
            btnHuy.setOnClickListener(view13 -> dialog.dismiss());
            btnOk.setOnClickListener(view14 -> {
                tong += soLuong;
                if (tong > 0) {
                    tvSoLuongBanHang.setText(String.valueOf(tong));
                    tvSoLuongBanHang.setVisibility(View.VISIBLE);
                    boolean chk = false;
                    if (MatHangActivity.gioHangList.size() > 0) {
                        for (int j = 0; j < MatHangActivity.gioHangList.size(); j++) {
                            if (MatHangActivity.gioHangList.get(j).getMa().equals(list.get(i).getMaSanPham())) {
                                MatHangActivity.gioHangList.get(j).setSoLuong(MatHangActivity.gioHangList.get(j).getSoLuong() + soLuong);
                                chk = true;
                            }
                        }
                        if (!chk) {
                            SanPham sanPham = list.get(i);
                            GioHang gioHang = new GioHang();
                            gioHang.setMa(sanPham.getMaSanPham());
                            gioHang.setTen(sanPham.getTen());
                            gioHang.setGia(sanPham.getGiaBan());
                            gioHang.setSoLuong(soLuong);
                            MatHangActivity.gioHangList.add(gioHang);
                        }
                    } else {
                        SanPham sanPham = list.get(i);
                        GioHang gioHang = new GioHang();
                        gioHang.setMa(sanPham.getMaSanPham());
                        gioHang.setTen(sanPham.getTen());
                        gioHang.setGia(sanPham.getGiaBan());
                        gioHang.setSoLuong(soLuong);
                        MatHangActivity.gioHangList.add(gioHang);
                    }
                } else {
                    tvSoLuongBanHang.setVisibility(View.INVISIBLE);
                }
                list.get(i).setSoLuong(list.get(i).getSoLuong() - soLuong);
                String txt = edTimKiem.getText().toString();
                if (txt.equals("")) {
                    doDuLieuTheoSpinner();
                } else {
                    list = sanPhamDAO.getAllSanPhamTheoMa(txt);
                    sanPhamAdapter = new SanPhamAdapter(getContext(), list);
                    lvList.setAdapter(sanPhamAdapter);
                }
                dialog.dismiss();
            });
        });
    }

    private void timKiem() {
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<SanPham> list = sanPhamDAO.getAllSanPhamTheoMa(edTimKiem.getText().toString());
                SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(getActivity(), list);
                lvList.setAdapter(sanPhamAdapter);
                tvNull.setVisibility(View.INVISIBLE);
                if (edTimKiem.getText().toString().equalsIgnoreCase("")) {
                    doDuLieuTheoSpinner();
                }
                if (list.size() == 0) {
                    tvNull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void anhXaView(View view) {
        edTimKiem = view.findViewById(R.id.edTimKiemSanPham);
        lvList = view.findViewById(R.id.lvListMatHang);
        spnLocDanhSach = view.findViewById(R.id.spnLocTimKiem);
        tvNull = view.findViewById(R.id.tvNull);
        tvSoLuongBanHang = view.findViewById(R.id.tvSoLuongBanHang);
    }

    public void doDuLieuTheoSpinner() {
        spnLocDanhSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i) == adapterView.getItemAtPosition(0)) {
                    list = sanPhamDAO.getAllSanPhamTheoTen();
                } else if (adapterView.getItemAtPosition(i) == adapterView.getItemAtPosition(1)) {
                    list = sanPhamDAO.getAllSanPhamTheoGiaTangDan();
                } else {
                    list = sanPhamDAO.getAllSanPhamTheoGiaGiamDan();
                }
                sanPhamAdapter = new SanPhamAdapter(getContext(), list);
                lvList.setAdapter(sanPhamAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}