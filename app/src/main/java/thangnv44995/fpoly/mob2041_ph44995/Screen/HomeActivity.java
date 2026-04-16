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

    // 1. Thêm btnHoaDon vào danh sách khai báo
    LinearLayout layoutThongKe, btnNhanVien, btnDangXuat, btnDanhMuc, btnSanPham, btnHoaDon,btnDoanhThu,btnTopSanPham,btnKhachHang;

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
        btnDanhMuc = findViewById(R.id.btnDanhMuc);
        btnSanPham = findViewById(R.id.btnSanPham);
        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnDoanhThu = findViewById(R.id.btnDoanhThu);
        btnTopSanPham = findViewById(R.id.btnTopSanPham);
        btnKhachHang = findViewById(R.id.btnKhachHang);
    }

    private void phanQuyenGiaoDien(String role) {
        if (role == null) return;

        if (role.equalsIgnoreCase("Khách hàng")) {
            // 1. Ẩn thống kê và quản lý nhân viên
            if (layoutThongKe != null) layoutThongKe.setVisibility(View.GONE);
            if (btnNhanVien != null) btnNhanVien.setVisibility(View.GONE);


        } else if (role.equalsIgnoreCase("Nhân viên")) {
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

        // Chuyển sang màn hình Quản lý Sản phẩm
        if (btnSanPham != null) {
            btnSanPham.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLySanPhamActivity.class);
                startActivity(intent);
            });
        }

        // 3. Chuyển sang màn hình Quản lý Hóa đơn
        if (btnHoaDon != null) {
            btnHoaDon.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLyHoaDonActivity.class);
                startActivity(intent);
            });
        }
        // 4. Chuyển sang màn hình Thống kê
        if (btnDoanhThu != null) {
            btnDoanhThu.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ThongKeActivity.class);
                startActivity(intent);
            });
        }
        // 5. Chuyển sang màn hình Thống kê top sp
        if (btnTopSanPham != null) {
            btnTopSanPham.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ThongKeTopActivity.class);
                startActivity(intent);
            });
        }
        // 6. Chuyển sang màn hình Quản lý khách hàng
        if (btnKhachHang != null) {
            btnKhachHang.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLyKhachHangActivity.class);
                startActivity(intent);
            });
        }
        // 7. Chuyển sang màn hình Quản lý nhân viên
        if (btnNhanVien != null) {
            btnNhanVien.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, QuanLyNhanVienActivity.class);
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