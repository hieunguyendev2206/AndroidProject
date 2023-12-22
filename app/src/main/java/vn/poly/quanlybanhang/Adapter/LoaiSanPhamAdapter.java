package vn.poly.quanlybanhang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.duan1android.R;

import java.util.List;

import vn.poly.quanlybanhang.Database.LoaiSanPhamDAO;
import vn.poly.quanlybanhang.Model.LoaiSanPham;

public
class LoaiSanPhamAdapter extends BaseAdapter {
    final Context context;
    final List<LoaiSanPham> list;
    EditText edMa, edTen;

    public LoaiSanPhamAdapter(Context context, List<LoaiSanPham> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_loai_san_pham, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imgDelete = view.findViewById(R.id.imgDeleteLSP);
            viewHolder.imgUpdate = view.findViewById(R.id.imgUpdateLSP);
            viewHolder.tvTen = view.findViewById(R.id.tvTenLSP);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        final LoaiSanPham loaiSanPham = list.get(i);
        viewHolder.tvTen.setText(loaiSanPham.getTenLoai());
        viewHolder.imgDelete.setOnClickListener(view1 -> {
            AlertDialog.Builder b = new AlertDialog.Builder(context);
            b.setTitle("Xác nhận");
            b.setMessage("Bạn có đồng ý xóa loại hàng này không?");
            b.setPositiveButton("Đồng ý", (dialog, id) -> {
                long chk = loaiSanPhamDAO.deleteLoaiSanPham(list.get(i).getMaLoai());
                if (chk > 0) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.remove(i);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            });
            b.setNegativeButton("Hủy", (dialog, id) -> dialog.cancel());
            AlertDialog al = b.create();
            al.show();
        });
        viewHolder.imgUpdate.setOnClickListener(view12 -> {
            final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.activity_sua_loai_san_pham);
            dialog.show();
            edMa = dialog.findViewById(R.id.edSuaMaMatHang);
            edTen = dialog.findViewById(R.id.edSuaTenMatHang);
            edMa.setText(list.get(i).getMaLoai());
            edTen.setText(list.get(i).getTenLoai());
            ImageView imgLuu = dialog.findViewById(R.id.imgLuuSuaLSP);
            imgLuu.setOnClickListener(view121 -> {
                String ma = edMa.getText().toString();
                String ten = edTen.getText().toString();
                if (ma.equalsIgnoreCase("") || ten.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoaiSanPham loaiSanPham1 = new LoaiSanPham(ma, ten);
                try {
                    loaiSanPhamDAO.updateLoaiSanPham(loaiSanPham1, list.get(i).getMaLoai());
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    list.set(i, loaiSanPham1);
                    notifyDataSetChanged();
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(context, "Lưu không thành công, mã loại đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            });
        });
        return view;
    }

    private static class ViewHolder {
        TextView tvTen;
        ImageView imgDelete, imgUpdate;
    }
}
