package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.SanPham;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;
    private DBHelper db;

    // Interface để xử lý sự kiện Sửa và Xóa ở Activity
    public interface OnSanPhamClickListener {
        void onEdit(SanPham sp);
        void onDelete(SanPham sp);
    }

    private OnSanPhamClickListener listener;

    public void setOnSanPhamClickListener(OnSanPhamClickListener listener) {
        this.listener = listener;
    }

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sp = list.get(position);

        // Đổ dữ liệu lên giao diện
        holder.txtTenSP.setText(sp.getTenSP());
        holder.txtGiaSP.setText(String.format("%,d đ", sp.getGiaSP())); // Định dạng số: 15,000 đ
        holder.txtTonKho.setText("Tồn kho: " + sp.getSoLuong());

        // 1. Xử lý Thêm vào giỏ hàng
        holder.imgAddCart.setOnClickListener(v -> {
            boolean check = db.addSanPhamToGioHang(sp.getMaSP(), sp.getTenSP(), sp.getGiaSP());
            if (check) {
                Toast.makeText(context, "Đã thêm " + sp.getTenSP() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Xử lý Sửa
        holder.imgEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(sp);
        });

        // 3. Xử lý Xóa
        holder.imgDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(sp);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSP, txtGiaSP, txtTonKho;
        ImageView imgAddCart, imgEdit, imgDelete, imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTonKho = itemView.findViewById(R.id.txtTonKho);
            imgAddCart = itemView.findViewById(R.id.imgAddCart);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}