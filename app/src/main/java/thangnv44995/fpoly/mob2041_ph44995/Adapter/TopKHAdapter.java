package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class TopKHAdapter extends RecyclerView.Adapter<TopKHAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> list;

    public TopKHAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tận dụng lại layout item_khach_hang để đồng bộ giao diện
        View view = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);

        // Hiển thị thứ hạng và tên khách hàng
        // Ví dụ: "1. admin"
        holder.txtUsername.setText((position + 1) + ". " + user.getUsername());

        // Hiển thị số tiền (Lấy từ trường password mà ta đã truyền từ cursor vào)
        holder.txtRole.setText("Tổng chi tiêu: " + user.getPassword() + " VNĐ");

        // Tùy chỉnh màu sắc cho Top 3 để nổi bật
        if (position == 0) {
            holder.txtUsername.setTextColor(Color.RED); // Hạng 1 màu đỏ
        } else if (position == 1 || position == 2) {
            holder.txtUsername.setTextColor(Color.parseColor("#FF9800")); // Hạng 2, 3 màu cam
        } else {
            holder.txtUsername.setTextColor(Color.BLACK);
        }

        // Ẩn nút xóa vì đây là màn hình thống kê, không nên cho phép xóa ở đây
        holder.imgDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtRole;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ đúng ID từ file item_khach_hang.xml của bạn
            txtUsername = itemView.findViewById(R.id.txtUsername_KH);
            txtRole = itemView.findViewById(R.id.txtRole_KH);
            imgDelete = itemView.findViewById(R.id.imgDelete_KH);
        }
    }
}