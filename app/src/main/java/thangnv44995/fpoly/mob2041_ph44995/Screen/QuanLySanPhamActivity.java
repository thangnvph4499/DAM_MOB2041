package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Thêm import này
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thangnv44995.fpoly.mob2041_ph44995.Adapter.SanPhamAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.SanPham;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class QuanLySanPhamActivity extends AppCompatActivity {
    RecyclerView rvSanPham;
    FloatingActionButton fabAdd;
    DBHelper db;
    ArrayList<SanPham> list = new ArrayList<>();
    SanPhamAdapter adapter;
    Toolbar toolbar;
    ImageView btnMoGioHang; // 1. Khai báo nút giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        // 1. Ánh xạ các thành phần
        rvSanPham = findViewById(R.id.rvSanPham);
        fabAdd = findViewById(R.id.fabAddSP);
        toolbar = findViewById(R.id.toolbarSP);
        btnMoGioHang = findViewById(R.id.btnMoGioHang); // 2. Ánh xạ từ Toolbar
        db = new DBHelper(this);

        // 2. Thiết lập Toolbar và nút Quay lại
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(""); // Để trống để không đè lên cái TextView mình tự tạo trong Toolbar
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // 3. Xử lý chuyển sang màn hình Giỏ hàng
        if (btnMoGioHang != null) {
            btnMoGioHang.setOnClickListener(v -> {
                Intent intent = new Intent(QuanLySanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
            });
        }

        // 4. Đổ dữ liệu lên RecyclerView
        loadData();
        adapter = new SanPhamAdapter(this, list);
        rvSanPham.setLayoutManager(new LinearLayoutManager(this));
        rvSanPham.setAdapter(adapter);

        // 5. Lắng nghe sự kiện từ Adapter (Sửa/Xóa)
        adapter.setOnSanPhamClickListener(new SanPhamAdapter.OnSanPhamClickListener() {
            @Override
            public void onEdit(SanPham sp) {
                showDialogSanPham(sp);
            }

            @Override
            public void onDelete(SanPham sp) {
                showDialogXoa(sp);
            }
        });

        // 6. Nút Thêm sản phẩm mới
        fabAdd.setOnClickListener(v -> showDialogSanPham(null));
    }

    // Các hàm loadData, showDialogSanPham, showDialogXoa giữ nguyên như cũ...
    private void loadData() {
        list.clear();
        Cursor cursor = db.getAllSanPham();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new SanPham(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            }
            cursor.close();
        }
    }

    private void showDialogSanPham(SanPham spSua) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them_sanpham, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtMa = view.findViewById(R.id.edtMaSP_Dialog);
        EditText edtTen = view.findViewById(R.id.edtTenSP_Dialog);
        EditText edtGia = view.findViewById(R.id.edtGiaSP_Dialog);
        EditText edtSoLuong = view.findViewById(R.id.edtSoLuong_Dialog);
        EditText edtDVT = view.findViewById(R.id.edtDVT_Dialog);
        EditText edtNgay = view.findViewById(R.id.edtNgay_Dialog);
        Spinner spnDM = view.findViewById(R.id.spnDanhMuc_Dialog);
        Button btnLuu = view.findViewById(R.id.btnLuuSP);
        Button btnHuy = view.findViewById(R.id.btnHuySP);

        ArrayList<String> listMaDM = new ArrayList<>();
        ArrayList<String> listTenDM = new ArrayList<>();
        Cursor c = db.getAllDanhMuc();
        while (c.moveToNext()) {
            listMaDM.add(c.getString(0));
            listTenDM.add(c.getString(1));
        }
        c.close();
        ArrayAdapter<String> adapterDM = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listTenDM);
        spnDM.setAdapter(adapterDM);

        if (spSua != null) {
            edtMa.setText(spSua.getMaSP());
            edtMa.setEnabled(false);
            edtTen.setText(spSua.getTenSP());
            edtGia.setText(String.valueOf(spSua.getGiaSP()));
            edtSoLuong.setText(String.valueOf(spSua.getSoLuong()));
            edtDVT.setText(spSua.getDonViTinh());
            edtNgay.setText(spSua.getNgayNhap());

            int position = listMaDM.indexOf(spSua.getMaDM());
            spnDM.setSelection(position);
            btnLuu.setText("Cập nhật");
        }

        btnLuu.setOnClickListener(v -> {
            try {
                String ma = edtMa.getText().toString();
                String ten = edtTen.getText().toString();
                int gia = Integer.parseInt(edtGia.getText().toString());
                int sl = Integer.parseInt(edtSoLuong.getText().toString());
                String dvt = edtDVT.getText().toString();
                String ngay = edtNgay.getText().toString();
                String maDMSelected = listMaDM.get(spnDM.getSelectedItemPosition());

                boolean check;
                if (spSua == null) {
                    check = db.insertSanPham(ma, ten, gia, sl, dvt, ngay, maDMSelected);
                } else {
                    check = db.updateSanPham(ma, ten, gia, sl, dvt, ngay, maDMSelected);
                }

                if (check) {
                    Toast.makeText(this, "Thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Thất bại!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Nhập đầy đủ thông tin số!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showDialogXoa(SanPham sp) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa sản phẩm " + sp.getTenSP() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (db.deleteSanPham(sp.getMaSP())) {
                        loadData();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}