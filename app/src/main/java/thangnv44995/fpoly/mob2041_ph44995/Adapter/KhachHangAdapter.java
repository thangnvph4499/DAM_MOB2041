package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
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
import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> list;
    private DBHelper db;

    public KhachHangAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // SỬA: Đã đổi sang layout item_khach_hang
        View view = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);

        // Hiển thị thông tin khớp với ID mới
        holder.txtUsername.setText("Tài khoản: " + user.getUsername());
        holder.txtRole.setText("Vai trò: " + user.getRole());

        // Xử lý sự kiện xóa khách hàng
        holder.imgDelete.setOnClickListener(v -> {
            showDialogXoa(user, position);
        });
    }

    private void showDialogXoa(User user, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản: " + user.getUsername() + "?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (db.deleteKhachHang(user.getUsername())) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                Toast.makeText(context, "Đã xóa khách hàng thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Lỗi khi xóa tài khoản", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername, txtRole;
        ImageView imgDelete, imgUserIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername_KH);
            txtRole = itemView.findViewById(R.id.txtRole_KH);
            imgDelete = itemView.findViewById(R.id.imgDelete_KH);
            imgUserIcon = itemView.findViewById(R.id.imgUserIcon);
        }
    }
}