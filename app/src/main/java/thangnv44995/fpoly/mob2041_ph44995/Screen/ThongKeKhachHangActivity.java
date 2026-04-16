package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.TopKHAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class ThongKeKhachHangActivity extends AppCompatActivity {
    private RecyclerView rvTopKH;
    private ArrayList<User> list = new ArrayList<>();
    private TopKHAdapter adapter;
    private DBHelper db;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sử dụng layout thống kê khách hàng
        setContentView(R.layout.activity_thong_ke_khach_hang);

        // 1. Ánh xạ
        rvTopKH = findViewById(R.id.rvTopKH);
        toolbar = findViewById(R.id.toolbarTopKH);
        db = new DBHelper(this);

        // 2. Thiết lập Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Nút quay lại
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Load dữ liệu top khách hàng
        loadDataTop();
    }

    private void loadDataTop() {
        list.clear();
        Cursor cursor = db.getTopKhachHang();

        if (cursor != null) {
            // Kiểm tra Logcat xem số dòng có > 0 không
            android.util.Log.d("DEBUG_SQL", "Số dòng tìm thấy: " + cursor.getCount());

            while (cursor.moveToNext()) {
                String ten = cursor.getString(0);
                String tongTien = cursor.getString(1);

                if (ten == null || ten.isEmpty()) ten = "Ẩn danh";

                // Đảm bảo User model của bạn nhận đúng thứ tự tham số
                list.add(new User(ten, tongTien, "Khách hàng"));
            }
            cursor.close();
        }

        if (adapter == null) {
            adapter = new TopKHAdapter(this, list);
            rvTopKH.setLayoutManager(new LinearLayoutManager(this));
            rvTopKH.setAdapter(adapter);
        } else {
            // Nếu có rồi thì chỉ cần báo dữ liệu thay đổi
            adapter.notifyDataSetChanged();
        }
    }
}