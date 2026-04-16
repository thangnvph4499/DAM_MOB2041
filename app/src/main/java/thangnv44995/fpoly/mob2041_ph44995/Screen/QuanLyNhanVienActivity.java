package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.Adapter.NhanVienAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.User;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class QuanLyNhanVienActivity extends AppCompatActivity {
    RecyclerView rvNhanVien;
    ArrayList<User> list = new ArrayList<>();
    NhanVienAdapter adapter;
    DBHelper db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);

        rvNhanVien = findViewById(R.id.rvNhanVien);
        toolbar = findViewById(R.id.toolbarNV);
        db = new DBHelper(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        loadData();
    }

    private void loadData() {
        list.clear();
        // Gọi hàm lấy nhân viên từ DBHelper
        Cursor cursor = db.getDanhSachNhanVien();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Khớp với Constructor (String username, String role) trong model User của bạn
                list.add(new User(cursor.getString(0), cursor.getString(1)));
            }
            cursor.close();
        }
        adapter = new NhanVienAdapter(this, list);
        rvNhanVien.setLayoutManager(new LinearLayoutManager(this));
        rvNhanVien.setAdapter(adapter);
    }
}