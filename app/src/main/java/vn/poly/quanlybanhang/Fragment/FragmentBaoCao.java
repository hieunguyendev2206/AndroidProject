package vn.poly.quanlybanhang.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duan1android.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import vn.poly.quanlybanhang.Database.HoaDonDAO;


public class FragmentBaoCao extends Fragment {
    TextView tvSoHoaDon, tvGiaTriHoaDon, tvTienBan, tvTienVon, tvLoaiLoc;
    TextView tvLoiNhuan, tvDoanhThu, tvLuuBoLoc;
    ImageView imgBoLoc;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    HoaDonDAO hoaDonDAO;
    RadioButton rdoTatCa, rdoHomNay, rdoHomQua, rdoTuanNay, rdoTuanTruoc, rdoThangNay, rdoThangTruoc;
    double thang1, thang2, thang3, thang4, thang5, thang6, thang7, thang8, thang9, thang10, thang11, thang12;
    String time;
    double doanhThu;

    public FragmentBaoCao() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bao_cao, container, false);
        anhXaView(view);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view1 -> drawerLayout.openDrawer(GravityCompat.START));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXaView(view);
        thang1 = hoaDonDAO.getDoanhThuTheoThang("01");
        thang2 = hoaDonDAO.getDoanhThuTheoThang("02");
        thang3 = hoaDonDAO.getDoanhThuTheoThang("03");
        thang4 = hoaDonDAO.getDoanhThuTheoThang("04");
        thang5 = hoaDonDAO.getDoanhThuTheoThang("05");
        thang6 = hoaDonDAO.getDoanhThuTheoThang("06");
        thang7 = hoaDonDAO.getDoanhThuTheoThang("07");
        thang8 = hoaDonDAO.getDoanhThuTheoThang("08");
        thang9 = hoaDonDAO.getDoanhThuTheoThang("09");
        thang10 = hoaDonDAO.getDoanhThuTheoThang("10");
        thang11 = hoaDonDAO.getDoanhThuTheoThang("11");
        thang12 = hoaDonDAO.getDoanhThuTheoThang("12");
        getBaoCao();
        boLoc();
        navDrawer();
    }

    @Override
    public void onResume() {
        getBaoCao();
        super.onResume();
    }

    public void boLoc() {
        imgBoLoc.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.loc_bao_cao_dialog);
            dialog.show();
            anhXaViewDia(dialog);
            tvLuuBoLoc.setOnClickListener(view1 -> {
                tvLoaiLoc.setText(luuChonTime());
                getBaoCao();
                dialog.dismiss();
            });

        });
    }

    @SuppressLint("NonConstantResourceId")
    public void navDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_sendEmail:
                    Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:?subject=" + "Phản Hồi Ứng Dụng" + "&body=" + "nội dung" + "&to=" + "2100011408@nttu.edu.vn");
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
    }

    public void anhXaView(View view) {
        tvSoHoaDon = view.findViewById(R.id.tvSoHoaDon);
        tvGiaTriHoaDon = view.findViewById(R.id.tvGiaTriHoaDon);
        tvTienBan = view.findViewById(R.id.tvTienBan);
        tvTienVon = view.findViewById(R.id.tvTienVon);
        tvLoiNhuan = view.findViewById(R.id.btnLoiNhuan);
        tvDoanhThu = view.findViewById(R.id.btnDoanhThu);
        imgBoLoc = view.findViewById(R.id.imgBoLocBaoCao);
        toolbar = view.findViewById(R.id.toolbar_bao_cao);
        drawerLayout = view.findViewById(R.id.drawerLayoutBaoCao);
        hoaDonDAO = new HoaDonDAO(getContext());
        tvLoaiLoc = view.findViewById(R.id.tvLoaiLocBC);
        tvLuuBoLoc = view.findViewById(R.id.tvLuuBoLocHD);
        navigationView = view.findViewById(R.id.NavigationViewBaoCao);
    }


    @SuppressLint("SetTextI18n")
    private void getBaoCao() {
        time = tvLoaiLoc.getText().toString();
        doanhThu = hoaDonDAO.getDoanhThu(time);
        tvDoanhThu.setText(Math.round(doanhThu) + "\nDoanh thu");
        tvGiaTriHoaDon.setText(Math.round(doanhThu) + " VNĐ");
        tvTienBan.setText(Math.round(doanhThu) + " VNĐ");
        tvSoHoaDon.setText(String.valueOf(hoaDonDAO.getSoHoaDon(time)));
        tvTienVon.setText(hoaDonDAO.getSoTienVon(time) + " VNĐ");
        tvLoiNhuan.setText((Math.round(doanhThu) - hoaDonDAO.getSoTienVon(time)) + "\nLợi nhuận");
    }

    public String luuChonTime() {
        String luaChon = null;
        if (rdoTatCa.isChecked()) {
            luaChon = "Tất cả";
        } else if (rdoHomQua.isChecked()) {
            luaChon = "Hôm qua";
        } else if (rdoHomNay.isChecked()) {
            luaChon = "Hôm nay";
        } else if (rdoThangNay.isChecked()) {
            luaChon = "Tháng này";
        } else if (rdoThangTruoc.isChecked()) {
            luaChon = "Tháng trước";
        } else if (rdoTuanTruoc.isChecked()) {
            luaChon = "Tuần trước";
        } else if (rdoTuanNay.isChecked()) {
            luaChon = "Tuần này";
        }
        return luaChon;
    }

    public void anhXaViewDia(Dialog dialog) {
        rdoTatCa = dialog.findViewById(R.id.radTatCaThoiGianBC);
        rdoHomNay = dialog.findViewById(R.id.radHomNayBC);
        rdoHomQua = dialog.findViewById(R.id.radHomQuaBC);
        rdoTuanNay = dialog.findViewById(R.id.radTuanNayBC);
        rdoTuanTruoc = dialog.findViewById(R.id.radTuanTruocBC);
        rdoThangNay = dialog.findViewById(R.id.radThangNayBC);
        rdoThangTruoc = dialog.findViewById(R.id.radThangTruocBC);
        tvLuuBoLoc = dialog.findViewById(R.id.tvLuuBoLocHD);

    }


}