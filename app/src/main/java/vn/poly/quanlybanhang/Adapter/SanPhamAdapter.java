package vn.poly.quanlybanhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1android.R;

import java.util.List;

import vn.poly.quanlybanhang.Model.SanPham;

public class SanPhamAdapter extends BaseAdapter {
    final Context context;
    List<SanPham> list;

    public SanPhamAdapter(Context context, List<SanPham> list) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMa = view.findViewById(R.id.tvMa_ItemSp);
            viewHolder.tvSanPham = view.findViewById(R.id.tvTenSanPham_ItemSp);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaBan_ItemSp);
            viewHolder.tvSoluong = view.findViewById(R.id.tvCon_ItemSp);
            viewHolder.imgSanPham = view.findViewById(R.id.imageView_ItemSp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = list.get(i);
        viewHolder.tvMa.setText("Mã : " + sanPham.getMaSanPham());
        viewHolder.tvSanPham.setText("Tên sản phẩm : " + sanPham.getTen());
        viewHolder.tvGia.setText("Giá bán : " + Math.round(sanPham.getGiaBan()) + " VNĐ");
        viewHolder.tvSoluong.setText("Còn : " + sanPham.getSoLuong());
        byte[] image = sanPham.getImage();
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            viewHolder.imgSanPham.setImageBitmap(bitmap);
        } catch (Exception e) {
            viewHolder.imgSanPham.setImageResource(R.drawable.ic_sanpham1);
        }
        return view;
    }

    private static class ViewHolder {
        TextView tvMa, tvSanPham, tvGia, tvSoluong;
        ImageView imgSanPham;
    }
}
