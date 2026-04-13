package thangnv44995.fpoly.mob2041_ph44995.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.Model.TopSP;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class TopSPAdapter extends RecyclerView.Adapter<TopSPAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TopSP> list;

    public TopSPAdapter(Context context, ArrayList<TopSP> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_top_sp, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopSP top = list.get(position);
        if (top != null) {
            holder.txtTen.setText(top.getTenSP());
            holder.txtSL.setText("Tổng số lượng: " + top.getSoLuongBan());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtSL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenSP_Top);
            txtSL = itemView.findViewById(R.id.txtSoLuong_Top);
        }
    }
}