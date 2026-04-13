package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.Adapter.GioHangAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.GioHang;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class GioHangActivity extends AppCompatActivity {
    RecyclerView rvGioHang;
    TextView txtTongTien;
    Button btnThanhToan;
    DBHelper db;
    ArrayList<GioHang> list = new ArrayList<>();
    GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        db = new DBHelper(this);
        rvGioHang = findViewById(R.id.rvGioHang);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        Toolbar toolbar = findViewById(R.id.toolbarGioHang);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        loadGioHang();

        btnThanhToan.setOnClickListener(v -> {
            db.xoaGioHang(); // Hàm xóa toàn bộ giỏ hàng đã viết trong DBHelper
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadGioHang() {
        list.clear();
        int tong = 0;
        Cursor c = db.getGioHang();
        while (c.moveToNext()) {
            String ma = c.getString(0);
            String ten = c.getString(1);
            int gia = c.getInt(2);
            int sl = c.getInt(3);
            list.add(new GioHang(ma, ten, gia, sl));
            tong += gia * sl;
        }
        c.close();

        txtTongTien.setText(String.format("Tổng: %,d đ", tong));

        if (adapter == null) {
            adapter = new GioHangAdapter(this, list, this::loadGioHang);
            rvGioHang.setLayoutManager(new LinearLayoutManager(this));
            rvGioHang.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}