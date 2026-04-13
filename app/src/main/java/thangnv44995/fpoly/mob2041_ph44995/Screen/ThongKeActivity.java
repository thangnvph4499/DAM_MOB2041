package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class ThongKeActivity extends AppCompatActivity {

    EditText edtTuNgay, edtDenNgay;
    Button btnThongKe;
    TextView txtDoanhThu;
    Toolbar toolbar;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        // 1. Ánh xạ Toolbar
        toolbar = findViewById(R.id.toolbarHD);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // 2. Ánh xạ View
        edtTuNgay = findViewById(R.id.edtTuNgay);
        edtDenNgay = findViewById(R.id.edtDenNgay);
        btnThongKe = findViewById(R.id.btnThongKe);
        txtDoanhThu = findViewById(R.id.txtDoanhThu);
        db = new DBHelper(this);

        // Chặn người dùng gõ phím vào EditText, bắt buộc phải chọn qua DatePicker
        edtTuNgay.setFocusable(false);
        edtDenNgay.setFocusable(false);

        // 3. Chọn ngày
        edtTuNgay.setOnClickListener(v -> showDatePicker(edtTuNgay));
        edtDenNgay.setOnClickListener(v -> showDatePicker(edtDenNgay));

        // 4. Xử lý Thống kê
        btnThongKe.setOnClickListener(v -> {
            String tuNgayHienThi = edtTuNgay.getText().toString(); // Dạng dd/MM/yyyy
            String denNgayHienThi = edtDenNgay.getText().toString(); // Dạng dd/MM/yyyy

            if (tuNgayHienThi.isEmpty() || denNgayHienThi.isEmpty()) {
                txtDoanhThu.setText("Vui lòng chọn đầy đủ ngày!");
            } else {
                try {
                    // Chuyển đổi dd/MM/yyyy sang yyyy-MM-dd để SQLite hiểu
                    SimpleDateFormat sdfHienThi = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    String tuNgaySQL = sdfSQL.format(sdfHienThi.parse(tuNgayHienThi));
                    String denNgaySQL = sdfSQL.format(sdfHienThi.parse(denNgayHienThi));

                    // Gọi Database
                    double doanhThu = db.getDoanhThu(tuNgaySQL, denNgaySQL);

                    // Hiển thị kết quả
                    txtDoanhThu.setText(String.format("Doanh thu: %,.0f VND", doanhThu));

                } catch (Exception e) {
                    Log.e("LOI_THONG_KE", "Lỗi convert: " + e.getMessage());
                    txtDoanhThu.setText("Lỗi định dạng ngày!");
                }
            }
        });
    }

    private void showDatePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // 1. Định dạng để HIỂN THỊ cho người dùng (dd/MM/yyyy)
            String dateDisplay = String.format("%02d/%02d/%d", dayOfMonth, (month + 1), year);
            editText.setText(dateDisplay);

            // 2. Định dạng để LƯU vào database/Thống kê (yyyy-MM-dd)
            // Lưu vào Tag của EditText để tí nữa lấy ra dùng cho SQL
            String dateSql = String.format("%d-%02d-%02d", year, (month + 1), dayOfMonth);
            editText.setTag(dateSql);

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}