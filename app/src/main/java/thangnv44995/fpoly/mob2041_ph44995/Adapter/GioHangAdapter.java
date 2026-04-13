package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.GioHang;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    Context context;
    ArrayList<GioHang> list;
    DBHelper db;
    Runnable onDataChanged; // Để báo Activity load lại data và tính lại tổng tiền

    public GioHangAdapter(Context context, ArrayList<GioHang> list, Runnable onDataChanged) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
        this.onDataChanged = onDataChanged;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gh = list.get(position);

        // Đổ dữ liệu
        holder.txtTen.setText(gh.getTenSP());
        holder.txtGia.setText(String.format("%,d đ", gh.getGiaSP()));
        holder.txtSoLuong.setText(String.valueOf(gh.getSoLuongMua()));

        // 1. Xử lý nút tăng số lượng (+)
        holder.btnPlus.setOnClickListener(v -> {
            // Gọi hàm cộng vào DB
            db.addSanPhamToGioHang(gh.getMaSP(), gh.getTenSP(), gh.getGiaSP());

            // Chạy callback để Activity load lại toàn bộ danh sách và tổng tiền
            if (onDataChanged != null) {
                onDataChanged.run();
            }
        });

        // 2. Xử lý nút giảm số lượng (-)
        holder.btnMinus.setOnClickListener(v -> {
            // Gọi hàm giảm số lượng (hoặc xóa nếu sl = 1) đã viết trong DBHelper
            boolean result = db.minusSanPhamTrongGioHang(gh.getMaSP());

            if (result) {
                // Chạy callback để Activity load lại dữ liệu mới
                if (onDataChanged != null) {
                    onDataChanged.run();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtSoLuong, btnMinus, btnPlus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenSP_GH);
            txtGia = itemView.findViewById(R.id.txtGiaSP_GH);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong_GH);
            btnMinus = itemView.findViewById(R.id.btnMinus_GH);
            btnPlus = itemView.findViewById(R.id.btnPlus_GH);
        }
    }
}