package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.TopSPAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.TopSP;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class ThongKeTopActivity extends AppCompatActivity {

    EditText edtTuNgay, edtDenNgay, edtLimit;
    Button btnThongKe;
    TextView txtLoi;
    RecyclerView rvTop;
    Toolbar toolbar;
    DBHelper db;
    ArrayList<TopSP> list = new ArrayList<>();
    TopSPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_top); // Đảm bảo tên file layout đúng

        // 1. Ánh xạ
        initView();

        // 2. Thiết lập Toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Xử lý chọn ngày
        edtTuNgay.setOnClickListener(v -> showDatePicker(edtTuNgay));
        edtDenNgay.setOnClickListener(v -> showDatePicker(edtDenNgay));

        // 4. Xử lý nút Thống kê
        btnThongKe.setOnClickListener(v -> {
            // Lấy dữ liệu từ Tag (định dạng yyyy-MM-dd) và ô nhập số lượng
            String tuNgay = (edtTuNgay.getTag() != null) ? edtTuNgay.getTag().toString() : "";
            String denNgay = (edtDenNgay.getTag() != null) ? edtDenNgay.getTag().toString() : "";
            String limit = edtLimit.getText().toString().trim();

            // Kiểm tra trống
            if (tuNgay.isEmpty() || denNgay.isEmpty() || limit.isEmpty()) {
                txtLoi.setVisibility(View.VISIBLE);
                txtLoi.setText("Vui lòng nhập đầy đủ thông tin.");
            } else {
                txtLoi.setVisibility(View.GONE);
                loadData(tuNgay, denNgay, limit);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarHD);
        edtTuNgay = findViewById(R.id.edtTuNgayTop);
        edtDenNgay = findViewById(R.id.edtDenNgayTop);
        edtLimit = findViewById(R.id.edtSoLuongTop);
        btnThongKe = findViewById(R.id.btnThongKeTop);
        txtLoi = findViewById(R.id.txtLoiTop);
        rvTop = findViewById(R.id.rvTopSP);
        db = new DBHelper(this);
    }

    private void loadData(String tu, String den, String limit) {
        list.clear();
        // Gọi hàm getTopSanPham trong DBHelper đã viết ở bước trước
        Cursor cursor = db.getTopSanPham(tu, den, limit);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Cột 0: Tên SP, Cột 1: Tổng số lượng
                list.add(new TopSP(cursor.getString(0), cursor.getInt(1)));
            }
            cursor.close();
        }

        // Đổ dữ liệu vào RecyclerView
        adapter = new TopSPAdapter(this, list);
        rvTop.setLayoutManager(new LinearLayoutManager(this));
        rvTop.setAdapter(adapter);

        // Thông báo nếu không có dữ liệu
        if (list.isEmpty()) {
            txtLoi.setVisibility(View.VISIBLE);
            txtLoi.setText("Không có dữ liệu trong khoảng thời gian này.");
        }
    }

    private void showDatePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Hiển thị người dùng xem: dd/MM/yyyy
            String display = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
            editText.setText(display);

            // Lưu vào Tag để truyền vào SQL: yyyy-MM-dd
            String sqlDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
            editText.setTag(sqlDate);

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}