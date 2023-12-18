package vn.poly.quanlybanhang.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.duan1android.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Adapter.ViewPageAdapter;
import vn.poly.quanlybanhang.Fragment.FragmentBanHang;
import vn.poly.quanlybanhang.Fragment.FragmentBaoCao;
import vn.poly.quanlybanhang.Fragment.FragmentHoaDon;
import vn.poly.quanlybanhang.Fragment.FragmentThem;
import vn.poly.quanlybanhang.Model.GioHang;

public class MatHangActivity extends AppCompatActivity {
    public static List<GioHang> gioHangList;
    public TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang);
        tabLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.viewPage);
        if (gioHangList == null) {
            gioHangList = new ArrayList<>();
        }


        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new FragmentBanHang(), "Bán Hàng");
        viewPageAdapter.addFragment(new FragmentHoaDon(), "Hóa Đơn");
        viewPageAdapter.addFragment(new FragmentBaoCao(), "Báo Cáo");
        viewPageAdapter.addFragment(new FragmentThem(), "Thêm");
        viewPager.setAdapter(viewPageAdapter);

        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.iconbanhangactivity);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.iconhoadonactivity);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.iconbaocaoactivity);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.iconthemactivity);


    }


}