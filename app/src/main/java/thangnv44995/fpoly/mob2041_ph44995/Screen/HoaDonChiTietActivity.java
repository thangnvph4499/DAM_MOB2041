package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.HDCTAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.HoaDonChiTiet;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class HoaDonChiTietActivity extends AppCompatActivity {

    RecyclerView rvHDCT;
    Toolbar toolbar;
    DBHelper db;
    ArrayList<HoaDonChiTiet> list = new ArrayList<>();
    HDCTAdapter adapter;
    String maHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        // 1. Ánh xạ và nhận dữ liệu
        rvHDCT = findViewById(R.id.rvHDCT);
        toolbar = findViewById(R.id.toolbarHDCT);
        db = new DBHelper(this);

        // Nhận mã hóa đơn từ Intent gửi sang
        maHD = getIntent().getStringExtra("MA_HD");

        // 2. Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chi tiết: " + maHD);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Load dữ liệu
        loadData();

        // 4. Setup RecyclerView
        adapter = new HDCTAdapter(this, list);
        rvHDCT.setLayoutManager(new LinearLayoutManager(this));
        rvHDCT.setAdapter(adapter);
    }

    private void loadData() {
        list.clear();
        Cursor cursor = db.getChiTietHoaDon(maHD);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Thứ tự: id(0), maHD(1), tenSP(2), giaSP(3), soLuong(4)
                list.add(new HoaDonChiTiet(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                ));
            }
            cursor.close();
        }
    }
}