package vn.poly.quanlybanhang.Adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.duan1android.R;

        import java.util.List;

        import vn.poly.quanlybanhang.Model.GioHang;

public
class DonHangAdapter extends BaseAdapter {
    final Context context;
    final List<GioHang> list;

    public DonHangAdapter(Context context, List<GioHang> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_don_hang, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvSanPham = view.findViewById(R.id.tvSanPhamGH);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaGH);
            viewHolder.tvSoluong = view.findViewById(R.id.tvSoLuongGH);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = list.get(i);
        viewHolder.tvSanPham.setText(""+gioHang.getTen());
        viewHolder.tvGia.setText(""+Math.round(gioHang.getSoLuong()*gioHang.getGia()));
        viewHolder.tvSoluong.setText(""+gioHang.getSoLuong());
        return view;
    }


    private static class ViewHolder{
        TextView tvSanPham,tvSoluong,tvGia;
    }

}
