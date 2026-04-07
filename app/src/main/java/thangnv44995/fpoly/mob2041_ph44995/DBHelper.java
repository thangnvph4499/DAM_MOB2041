package thangnv44995.fpoly.mob2041_ph44995;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "User.db";
    // Tăng version lên 3 để cập nhật thêm bảng Danh Mục
    public static final int DB_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tạo bảng users
        db.execSQL("CREATE TABLE users(" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "role TEXT)");

        // 2. Tạo bảng danhmuc (Dùng cho chức năng Quản lý danh mục)
        db.execSQL("CREATE TABLE danhmuc(" +
                "maDM TEXT PRIMARY KEY, " +
                "tenDM TEXT)");

        // Chèn dữ liệu mẫu
        db.execSQL("INSERT INTO users VALUES('admin', 'admin123', 'Quản lý')");
        db.execSQL("INSERT INTO users VALUES('nv01', '123', 'Nhân viên')");

        // Chèn vài danh mục mẫu để bạn test giao diện
        db.execSQL("INSERT INTO danhmuc VALUES('DM001', 'Đồ uống')");
        db.execSQL("INSERT INTO danhmuc VALUES('DM002', 'Bánh kẹo')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS danhmuc");
        onCreate(db);
    }

    // ================= PHẦN QUẢN LÝ USER =================

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean insertData(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        return db.insert("users", null, cv) != -1;
    }

    public String checkLoginReturnRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT role FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );
        if (cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            return role;
        }
        cursor.close();
        return null;
    }

    // ================= PHẦN QUẢN LÝ DANH MỤC (CRUD) =================

    // 1. Lấy tất cả danh mục để hiển thị lên danh sách
    public Cursor getAllDanhMuc() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM danhmuc", null);
    }

    // 2. Thêm danh mục mới
    public boolean insertDanhMuc(String ma, String ten) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("maDM", ma);
        cv.put("tenDM", ten);
        long result = db.insert("danhmuc", null, cv);
        return result != -1;
    }

    // 3. Cập nhật (Sửa) tên danh mục theo mã
    public boolean updateDanhMuc(String ma, String tenMoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tenDM", tenMoi);
        // Điều kiện sửa dựa trên maDM
        int result = db.update("danhmuc", cv, "maDM = ?", new String[]{ma});
        return result > 0;
    }

    // 4. Xóa danh mục
    public boolean deleteDanhMuc(String ma) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("danhmuc", "maDM = ?", new String[]{ma});
        return result > 0;
    }
}