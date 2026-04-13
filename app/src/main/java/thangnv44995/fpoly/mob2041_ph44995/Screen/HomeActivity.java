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

    // 1. Khai báo thêm btnSanPham
    LinearLayout layoutThongKe, btnNhanVien, btnDangXuat, btnDanhMuc, btnSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
        String role = getIntent().getStringExtra("USER_ROLE");
        phanQuyenGiaoDien(role);
        setClickEvents();
    }

    private void initViews() {
        layoutThongKe = findViewById(R.id.layoutThongKe);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnDanhMuc = findViewById(R.id.btnDanhMuc); // Ánh xạ Danh mục
        btnSanPham = findViewById(R.id.btnSanPham); // Ánh xạ Sản phẩm (Kiểm tra ID này trong XML của bạn)
    }

    private void phanQuyenGiaoDien(String role) {
        if (role == null) return;
        if (role.equalsIgnoreCase("Nhân viên")) {
            if (layoutThongKe != null) layoutThongKe.setVisibility(View.GONE);
            if (btnNhanVien != null) btnNhanVien.setVisibility(View.GONE);
        }
    }

    private void setClickEvents() {
        // Chuyển sang màn hình Quản lý Danh mục
        if (btnDanhMuc != null) {
            btnDanhMuc.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLyDanhMucActivity.class);
                startActivity(intent);
            });
        }

        // 2. Chuyển sang màn hình Quản lý Sản phẩm
        if (btnSanPham != null) {
            btnSanPham.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLySanPhamActivity.class);
                startActivity(intent);
            });
        }

        if (btnDangXuat != null) {
            btnDangXuat.setOnClickListener(v -> {
                Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}