package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Model.HoaDonChiTiet;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class HDCTAdapter extends RecyclerView.Adapter<HDCTAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<HoaDonChiTiet> list;
    // Sử dụng DecimalFormat để định dạng tiền tệ chuyên nghiệp hơn
    private final DecimalFormat formatter = new DecimalFormat("###,###,###");

    public HDCTAdapter(Context context, ArrayList<HoaDonChiTiet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng LayoutInflater đúng cách từ context của parent
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hdct, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonChiTiet hdct = list.get(position);

        if (hdct != null) {
            // Hiển thị tên sản phẩm
            holder.txtTen.setText(hdct.getTenSP());

            // Định dạng giá tiền (Tránh lỗi nếu giaSP là kiểu int/long)
            String giaFormatted = formatter.format(hdct.getGiaSP()) + " đ";
            holder.txtGia.setText("Giá: " + giaFormatted);

            // Hiển thị số lượng
            holder.txtSoLuong.setText("x" + hdct.getSoLuong());
        }
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenSP_HDCT);
            txtGia = itemView.findViewById(R.id.txtGiaSP_HDCT);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong_HDCT);
        }
    }
}