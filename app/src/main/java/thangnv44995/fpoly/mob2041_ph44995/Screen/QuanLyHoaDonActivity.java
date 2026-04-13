package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.HoaDonAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.HoaDon;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class QuanLyHoaDonActivity extends AppCompatActivity {

    RecyclerView rvHoaDon;
    Toolbar toolbar;
    DBHelper db;
    ArrayList<HoaDon> list = new ArrayList<>();
    HoaDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hoa_don);

        // 1. Ánh xạ
        rvHoaDon = findViewById(R.id.rvHoaDon);
        toolbar = findViewById(R.id.toolbarHD);
        db = new DBHelper(this);

        // 2. Toolbar quay lại
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Load dữ liệu và thiết lập RecyclerView
        loadData();

        // Truyền hàm callback vào adapter để khi xóa hóa đơn thì load lại danh sách
        adapter = new HoaDonAdapter(this, list, () -> loadData());
        rvHoaDon.setLayoutManager(new LinearLayoutManager(this));
        rvHoaDon.setAdapter(adapter);
    }

    // Hàm lấy dữ liệu từ bảng hoadon trong DBHelper
    private void loadData() {
        list.clear();
        Cursor cursor = db.getAllHoaDon();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Thứ tự cột: maHD(0), tenNV(1), tenKH(2), ngayLap(3), tongTien(4)
                list.add(new HoaDon(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            }
            cursor.close();
        }

        // Cập nhật lại giao diện nếu adapter đã tồn tại
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}