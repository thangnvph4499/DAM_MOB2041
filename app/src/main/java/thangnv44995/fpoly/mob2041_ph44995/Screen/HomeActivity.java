package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import thangnv44995.fpoly.mob2041_ph44995.R;

public class HomeActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    LinearLayout layoutThongKe, btnNhanVien, btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Xử lý Padding cho hệ thống (Status bar, Navigation bar)
        // Lưu ý: R.id.main phải khớp với android:id bên file activity_home.xml
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // 1. Ánh xạ các ID từ XML
        initViews();

        // 2. Nhận Role từ Intent (được gửi từ LoginActivity)
        String role = getIntent().getStringExtra("USER_ROLE");

        // 3. Kiểm tra và phân quyền ẩn/hiện
        phanQuyenGiaoDien(role);

        // 4. Thiết lập sự kiện click
        setClickEvents();
    }

    private void initViews() {
        layoutThongKe = findViewById(R.id.layoutThongKe);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }

    private void phanQuyenGiaoDien(String role) {
        if (role == null) return;

        // Nếu là Nhân viên: Ẩn cụm Thống kê và chức năng Quản lý nhân viên
        if (role.equalsIgnoreCase("Nhân viên")) {
            if (layoutThongKe != null) {
                layoutThongKe.setVisibility(View.GONE); // GONE: Ẩn và không chiếm diện tích
            }
            if (btnNhanVien != null) {
                btnNhanVien.setVisibility(View.GONE);
            }
        }
        // Nếu là Quản lý: Để mặc định (hiện tất cả)
    }

    private void setClickEvents() {
        // Ví dụ click Đăng xuất
        if (btnDangXuat != null) {
            btnDangXuat.setOnClickListener(v -> {
                Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại màn hình Login
            });
        }

        // Bạn có thể thêm các sự kiện click cho Sản phẩm, Khách hàng tại đây
        // findViewById(R.id.btnSanPham).setOnClickListener(v -> { ... });
    }
}