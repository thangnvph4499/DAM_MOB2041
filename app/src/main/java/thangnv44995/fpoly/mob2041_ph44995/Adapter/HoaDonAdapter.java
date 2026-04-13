package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.HoaDon;
import thangnv44995.fpoly.mob2041_ph44995.R;
import thangnv44995.fpoly.mob2041_ph44995.Screen.HoaDonChiTietActivity;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDon> list;
    private DBHelper db;
    private Runnable onDataChanged;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list, Runnable onDataChanged) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
        this.onDataChanged = onDataChanged;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hd = list.get(position);

        // Đổ dữ liệu lên giao diện
        holder.txtMaHD.setText(hd.getMaHD());
        holder.txtTenNV.setText(hd.getTenNV());
        holder.txtTenKH.setText(hd.getTenKH());
        holder.txtNgayLap.setText(hd.getNgayLap());
        holder.txtTongTien.setText(String.format("%,.0f đ", hd.getTongTien()));

        // 1. Sự kiện Xóa hóa đơn
        holder.imgDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa hóa đơn " + hd.getMaHD() + "?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        if (db.deleteHoaDon(hd.getMaHD())) {
                            Toast.makeText(context, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
                            onDataChanged.run(); // Load lại danh sách ở Activity
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        // 2. Sự kiện Click vào item để xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HoaDonChiTietActivity.class);
            // Gửi mã hóa đơn sang màn hình chi tiết để truy vấn sản phẩm
            intent.putExtra("MA_HD", hd.getMaHD());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaHD, txtTenNV, txtTenKH, txtNgayLap, txtTongTien;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHD = itemView.findViewById(R.id.txtMaHD);
            txtTenNV = itemView.findViewById(R.id.txtTenNV);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtNgayLap = itemView.findViewById(R.id.txtNgayLap);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            imgDelete = itemView.findViewById(R.id.imgDeleteHD);
        }
    }
}