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
import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> list;
    private DBHelper db;

    public NhanVienAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Dùng chung layout item với khách hàng
        View view = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.txtUsername.setText(user.getUsername());
        holder.txtRole.setText(user.getRole());

        holder.imgDelete.setOnClickListener(v -> {
            if (db.deleteNhanVien(user.getUsername())) {
                list.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtRole;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername_KH);
            txtRole = itemView.findViewById(R.id.txtRole_KH);
            imgDelete = itemView.findViewById(R.id.imgDelete_KH);
        }
    }
}