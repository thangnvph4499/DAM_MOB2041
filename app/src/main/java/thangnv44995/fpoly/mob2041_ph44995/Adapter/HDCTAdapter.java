package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Model.HoaDonChiTiet;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class HDCTAdapter extends RecyclerView.Adapter<HDCTAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDonChiTiet> list;

    public HDCTAdapter(Context context, ArrayList<HoaDonChiTiet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hdct, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonChiTiet hdct = list.get(position);

        holder.txtTen.setText(hdct.getTenSP());
        holder.txtGia.setText(String.format("Giá: %,d đ", hdct.getGiaSP()));
        holder.txtSoLuong.setText("x" + hdct.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return list.size();
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