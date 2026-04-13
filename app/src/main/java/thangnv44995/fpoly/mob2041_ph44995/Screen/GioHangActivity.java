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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    int tongTien = 0; // Khai báo biến toàn cục để lưu tổng tiền

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

        // XỬ LÝ LOGIC THANH TOÁN
        btnThanhToan.setOnClickListener(v -> {
            if (list.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Tạo dữ liệu hóa đơn
            String maHD = "HD" + System.currentTimeMillis();

            // SỬA TẠI ĐÂY: Lưu định dạng chuẩn YYYY-MM-DD để SQLite thống kê được
            String ngayLap = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            String tenNV = "Admin";
            String tenKH = "Khách hàng vãng lai";

            // 2. Gọi hàm insertHoaDon
            boolean check = db.insertHoaDon(maHD, tenNV, tenKH, ngayLap, (double) tongTien);

            if (check) {
                db.xoaGioHang();
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGioHang() {
        list.clear();
        tongTien = 0; // Reset tổng tiền mỗi lần load
        Cursor c = db.getGioHang();
        while (c.moveToNext()) {
            String ma = c.getString(0);
            String ten = c.getString(1);
            int gia = c.getInt(2);
            int sl = c.getInt(3);
            list.add(new GioHang(ma, ten, gia, sl));
            tongTien += gia * sl;
        }
        c.close();

        txtTongTien.setText(String.format("Tổng: %,d đ", tongTien));

        if (adapter == null) {
            adapter = new GioHangAdapter(this, list, this::loadGioHang);
            rvGioHang.setLayoutManager(new LinearLayoutManager(this));
            rvGioHang.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}