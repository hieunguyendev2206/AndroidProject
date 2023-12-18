package vn.poly.quanlybanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vn.poly.quanlybanhang.Model.KhachHang;
import com.example.duan1android.R;

import java.util.List;

public
class KhachHangAdapter extends BaseAdapter {
    final Context context;
    final List<KhachHang> list;

    public KhachHangAdapter(Context context, List<KhachHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_thiet_lap_khach_hang, viewGroup, false);
            viewHolder.imgHinhAnh = view.findViewById(R.id.imgNguoiDung);
            viewHolder.tvTen = view.findViewById(R.id.tvTenKhachHang);
            viewHolder.tvSDT = view.findViewById(R.id.tvSDT);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhachHang khachHang = list.get(i);
        viewHolder.tvTen.setText("Họ tên : "+khachHang.getTen());
        viewHolder.tvSDT.setText("Số điện thoại : "+khachHang.getSoDienThoai());
        viewHolder.imgHinhAnh.setImageResource(R.drawable.profile);
        return view;
    }
    private static class ViewHolder{
        ImageView imgHinhAnh;
        TextView tvTen,tvSDT;
    }
}
