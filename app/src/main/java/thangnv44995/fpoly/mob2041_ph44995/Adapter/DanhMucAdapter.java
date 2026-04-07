package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.Model.DanhMuc;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhMuc> list;

    // --- 1. KHAI BÁO CÁC INTERFACE ---

    // Interface cho chức năng Xóa
    public interface OnDeleteClickListener {
        void onDeleteClick(DanhMuc dm);
    }

    // Interface cho chức năng Sửa (Click vào item)
    public interface OnItemClickListener {
        void onItemClick(DanhMuc dm);
    }

    private OnDeleteClickListener deleteListener;
    private OnItemClickListener itemClickListener;

    // Các hàm Setter để Activity có thể gọi
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc dm = list.get(position);
        holder.txtMa.setText(dm.getMaDM());
        holder.txtTen.setText(dm.getTenDM());

        // --- 2. XỬ LÝ SỰ KIỆN ---

        // Click vào biểu tượng thùng rác để XÓA
        holder.imgDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(dm);
            }
        });

        // Click vào nguyên cả dòng (itemView) để SỬA
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(dm);
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtTen;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMa = itemView.findViewById(R.id.txtMaDM);
            txtTen = itemView.findViewById(R.id.txtTenDM);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}