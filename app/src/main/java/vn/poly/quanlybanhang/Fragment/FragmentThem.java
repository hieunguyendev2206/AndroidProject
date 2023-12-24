package vn.poly.quanlybanhang.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import vn.poly.quanlybanhang.Activity.DonViTinhActivity;
import vn.poly.quanlybanhang.Activity.KhachHangActivity;
import vn.poly.quanlybanhang.Activity.LoaiSanPhamActivity;
import vn.poly.quanlybanhang.Activity.SanPhamActivity;


public class FragmentThem extends Fragment {
    TextView tvMatHang, tvPhanLoai, tvDonViTinh, tvNguoiDung;
    ImageView imgMatHang, imgPhanLoai, imgDonViTinh, imgNguoiDung;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        anhXaView(view);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view1 -> drawerLayout.openDrawer(GravityCompat.START));
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
        //chuyển sang sản phẩm activity
        imgMatHang.setOnClickListener(view12 -> chuyenAct(SanPhamActivity.class));
        tvMatHang.setOnClickListener(view13 -> chuyenAct(SanPhamActivity.class));

        //chuyển sang loại sản phẩm activity
        imgPhanLoai.setOnClickListener(view14 -> chuyenAct(LoaiSanPhamActivity.class));
        tvPhanLoai.setOnClickListener(view15 -> chuyenAct(LoaiSanPhamActivity.class));

        //chuyển sang đơn vị tính activity
        imgDonViTinh.setOnClickListener(view16 -> chuyenAct(DonViTinhActivity.class));
        tvDonViTinh.setOnClickListener(view17 -> chuyenAct(DonViTinhActivity.class));

        //-> người dùng act
        imgNguoiDung.setOnClickListener(view18 -> chuyenAct(KhachHangActivity.class));
        tvNguoiDung.setOnClickListener(view19 -> chuyenAct(KhachHangActivity.class));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXaView(view);
    }

    public void anhXaView(View view) {
        tvMatHang = view.findViewById(R.id.tvMatHang);
        tvPhanLoai = view.findViewById(R.id.tvPhanLoai);
        tvDonViTinh = view.findViewById(R.id.tvDonViTinh);
        tvNguoiDung = view.findViewById(R.id.tvNguoiDung);
        imgMatHang = view.findViewById(R.id.imgMatHang);
        imgPhanLoai = view.findViewById(R.id.imgPhanLoai);
        imgDonViTinh = view.findViewById(R.id.imgDonViTinh);
        imgNguoiDung = view.findViewById(R.id.imgNguoiDung);
        toolbar = view.findViewById(R.id.toolbar_thong_tin);
        drawerLayout = view.findViewById(R.id.drawerLayoutThem);
        navigationView = view.findViewById(R.id.NavigationViewThem);
    }

    public void chuyenAct(Class aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }

}