package vn.poly.quanlybanhang.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.quanlybanhang.Activity.HoaDonChiTietActivity;
import vn.poly.quanlybanhang.Adapter.HoaDonAdapter;
import vn.poly.quanlybanhang.Database.HoaDonDAO;
import vn.poly.quanlybanhang.Model.HoaDon;


public class FragmentHoaDon extends Fragment {
    public TextView tvLoaiLoc, tvTimeLoaiLoc;
    ImageView imgBoLoc;
    ListView lvListHoaDon;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RadioButton rdoTatCa, rdoHomNay, rdoHomQua, rdoTuanNay, rdoTuanTruoc, rdoThangNay, rdoThangTruoc, rdoTatCaHd, rdoChuaThanhToan, rdoDaThanhToan;
    TextView tvLuuBoLoc, tvTimKiem;
    EditText edTimKiem;
    List<HoaDon> hoaDonList = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    HoaDonAdapter hoaDonAdapter;

    public FragmentHoaDon() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        anhXaView(view);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view1 -> drawerLayout.openDrawer(GravityCompat.START));
        hoaDonDAO = new HoaDonDAO(getContext());
        try {
            hoaDonList = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hoaDonAdapter = new HoaDonAdapter(getContext(), hoaDonList);
        lvListHoaDon.setAdapter(hoaDonAdapter);
        lvListHoaDon.setOnItemClickListener((adapterView, view12, i, l) -> {
            Intent intent = new Intent(getActivity(), HoaDonChiTietActivity.class);
            intent.putExtra("maHD", hoaDonList.get(i).getMaHD());
            startActivity(intent);
        });
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
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXaView(view);
        tvTimKiem.setVisibility(View.INVISIBLE);
        lvListHoaDon.setVisibility(View.VISIBLE);
        //chọn bộ lọc
        imgBoLoc.setOnClickListener(view1 -> {
            final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.activity_bo_loc);
            dialog.show();
            anhXaViewDia(dialog);
            tvLuuBoLoc.setOnClickListener(view11 -> {

                try {
                    tvLoaiLoc.setText(luuChonHoaDon());
                    tvTimeLoaiLoc.setText(luuChonTime());
                    HoaDonDAO hoaDonDAO = new HoaDonDAO(getContext());
                    hoaDonList = hoaDonDAO.getAllHoaDonTime(tvTimeLoaiLoc.getText().toString(), tvLoaiLoc.getText().toString());
                    HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), hoaDonList);
                    lvListHoaDon.setAdapter(hoaDonAdapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

        });
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    hoaDonList = hoaDonDAO.getAllHoaDonTheoMa(edTimKiem.getText().toString());
                    if (hoaDonList.size() > 0) {
                        lvListHoaDon.setVisibility(View.VISIBLE);
                        tvTimKiem.setVisibility(View.INVISIBLE);
                        hoaDonAdapter = new HoaDonAdapter(getContext(), hoaDonList);
                        lvListHoaDon.setAdapter(hoaDonAdapter);
                    } else {
                        lvListHoaDon.setVisibility(View.INVISIBLE);
                        tvTimKiem.setVisibility(View.VISIBLE);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void anhXaView(View view) {
        tvLoaiLoc = view.findViewById(R.id.tvLoaiLoc);
        imgBoLoc = view.findViewById(R.id.imgChonLoaiLoc);
        lvListHoaDon = view.findViewById(R.id.lvListHoaDon);
        toolbar = view.findViewById(R.id.toolbar_hoa_don);
        drawerLayout = view.findViewById(R.id.drawerLayoutHoaDon);
        tvTimeLoaiLoc = view.findViewById(R.id.tvTimeLoaiLoc);
        tvTimKiem = view.findViewById(R.id.tvRongHoaDon);
        edTimKiem = view.findViewById(R.id.edTimKiemHoaDon);
        navigationView = view.findViewById(R.id.NavigationViewHoaDon);
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

    public String luuChonHoaDon() {
        String luaChon = null;
        if (rdoTatCaHd.isChecked()) {
            luaChon = "Tất cả";
        } else if (rdoChuaThanhToan.isChecked()) {
            luaChon = "Chưa Thanh Toán";
        } else if (rdoDaThanhToan.isChecked()) {
            luaChon = "Đã thanh toán";
        }
        return luaChon;
    }

    public void anhXaViewDia(Dialog dialog) {
        rdoTatCa = dialog.findViewById(R.id.radTatCaThoiGian);
        rdoHomNay = dialog.findViewById(R.id.radHomNay);
        rdoHomQua = dialog.findViewById(R.id.radHomQua);
        rdoTuanNay = dialog.findViewById(R.id.radTuanNay);
        rdoTuanTruoc = dialog.findViewById(R.id.radTuanTruoc);
        rdoThangNay = dialog.findViewById(R.id.radThangNay);
        rdoThangTruoc = dialog.findViewById(R.id.radThangTruoc);
        rdoTatCaHd = dialog.findViewById(R.id.radTatCaHoaDon);
        rdoChuaThanhToan = dialog.findViewById(R.id.radChuaThanhToan);
        rdoDaThanhToan = dialog.findViewById(R.id.radDaThanhToan);
        tvLuuBoLoc = dialog.findViewById(R.id.tvLuuBoLocHD);

    }

    @Override
    public void onResume() {
        super.onResume();
        hoaDonList.clear();
        try {
            hoaDonList = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hoaDonAdapter.changeDataSet(hoaDonList);
    }

}