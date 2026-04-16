package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.KhachHangAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class QuanLyKhachHangActivity extends AppCompatActivity {
    // Khai báo đúng các thành phần theo Layout của bạn
    private RecyclerView rvKhachHang;
    private Toolbar toolbar;
    private ArrayList<User> list = new ArrayList<>();
    private KhachHangAdapter adapter;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khach_hang);


        rvKhachHang = findViewById(R.id.rvKhachHang);
        toolbar = findViewById(R.id.toolbarKH);
        db = new DBHelper(this);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(false);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Sự kiện khi bấm vào nút Back trên Toolbar
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Đổ dữ liệu lên RecyclerView
        loadData();
    }

    private void loadData() {
        list.clear();
        Cursor cursor = db.getDanhSachKhachHang();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Giả sử cursor: cột 0 là username, cột 1 là role
                list.add(new User(cursor.getString(0), cursor.getString(1)));
            }
            cursor.close();
        }

        // Khởi tạo Adapter và gán vào RecyclerView
        adapter = new KhachHangAdapter(this, list);
        rvKhachHang.setLayoutManager(new LinearLayoutManager(this));
        rvKhachHang.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load lại danh sách mỗi khi màn hình được quay lại để cập nhật dữ liệu mới nhất
        loadData();
    }
}