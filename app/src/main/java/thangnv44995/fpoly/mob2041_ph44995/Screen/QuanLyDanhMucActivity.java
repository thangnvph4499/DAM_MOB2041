package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import thangnv44995.fpoly.mob2041_ph44995.Adapter.DanhMucAdapter;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.Model.DanhMuc;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class QuanLyDanhMucActivity extends AppCompatActivity {
    RecyclerView rvDanhMuc;
    DBHelper db;
    ArrayList<DanhMuc> list = new ArrayList<>();
    DanhMucAdapter adapter;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_danh_muc);

        rvDanhMuc = findViewById(R.id.rvDanhMuc);
        fabAdd = findViewById(R.id.fabAdd);
        db = new DBHelper(this);

        loadData();

        adapter = new DanhMucAdapter(this, list);
        rvDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        rvDanhMuc.setAdapter(adapter);

        // 1. Sự kiện Xóa
        adapter.setOnDeleteClickListener(dm -> showDialogXoa(dm));

        // 2. Sự kiện Sửa (Khi click vào item)
        adapter.setOnItemClickListener(dm -> showDialogSua(dm));

        // 3. Sự kiện Thêm
        fabAdd.setOnClickListener(v -> showDialogThem());
    }

    // --- HÀM DIALOG SỬA DANH MỤC ---
    private void showDialogSua(DanhMuc dm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them_danhmuc, null);
        builder.setView(view);

        EditText edtMa = view.findViewById(R.id.edtMaDM_Dialog);
        EditText edtTen = view.findViewById(R.id.edtTenDM_Dialog);

        // Hiển thị dữ liệu cũ lên Dialog
        edtMa.setText(dm.getMaDM());
        edtTen.setText(dm.getTenDM());

        // Khóa ô Mã vì mã là khóa chính không được phép sửa
        edtMa.setEnabled(false);

        builder.setTitle("Cập Nhật Danh Mục");
        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String tenMoi = edtTen.getText().toString().trim();

            if (tenMoi.isEmpty()) {
                Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            } else {
                // Gọi hàm update từ DBHelper
                if (db.updateDanhMuc(dm.getMaDM(), tenMoi)) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    // --- HÀM DIALOG THÊM DANH MỤC ---
    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them_danhmuc, null);
        builder.setView(view);

        EditText edtMa = view.findViewById(R.id.edtMaDM_Dialog);
        EditText edtTen = view.findViewById(R.id.edtTenDM_Dialog);

        builder.setTitle("Thêm Danh Mục Mới");
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            } else {
                if (db.insertDanhMuc(ma, ten)) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Mã đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void showDialogXoa(DanhMuc dm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa: " + dm.getTenDM() + "?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (db.deleteDanhMuc(dm.getMaDM())) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                loadData();
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void loadData() {
        list.clear();
        Cursor cursor = db.getAllDanhMuc();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new DanhMuc(cursor.getString(0), cursor.getString(1)));
            }
            cursor.close();
        }
    }
}