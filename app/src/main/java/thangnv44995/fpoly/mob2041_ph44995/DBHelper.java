package thangnv44995.fpoly.mob2041_ph44995;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "User.db";
    public static final int DB_VERSION = 4;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Bảng users
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT, role TEXT)");

        // 2. Bảng danhmuc
        db.execSQL("CREATE TABLE danhmuc(maDM TEXT PRIMARY KEY, tenDM TEXT)");

        // 3. Bảng sanpham
        db.execSQL("CREATE TABLE sanpham(" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT, " +
                "giaSP INTEGER, " +
                "soLuong INTEGER, " +
                "donViTinh TEXT, " +
                "ngayNhap TEXT, " +
                "maDM TEXT, " +
                "FOREIGN KEY (maDM) REFERENCES danhmuc(maDM))");

        // 4. Bảng giohang
        db.execSQL("CREATE TABLE giohang(" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT, " +
                "giaSP INTEGER, " +
                "soLuongMua INTEGER)");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO users VALUES('admin', 'admin123', 'Quản lý')");
        db.execSQL("INSERT INTO users VALUES('nv01', '123', 'Nhân viên')");
        db.execSQL("INSERT INTO danhmuc VALUES('DM001', 'Đồ uống'), ('DM002', 'Bánh kẹo')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS danhmuc");
        db.execSQL("DROP TABLE IF EXISTS sanpham");
        db.execSQL("DROP TABLE IF EXISTS giohang");
        onCreate(db);
    }

    // ================= 1. QUẢN LÝ USER (ĐẦY ĐỦ) =================

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
        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0);
        }
        cursor.close();
        return role;
    }

    // ================= 2. QUẢN LÝ DANH MỤC =================

    public Cursor getAllDanhMuc() {
        return getReadableDatabase().rawQuery("SELECT * FROM danhmuc", null);
    }

    public boolean insertDanhMuc(String ma, String ten) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("maDM", ma);
        cv.put("tenDM", ten);
        return db.insert("danhmuc", null, cv) != -1;
    }

    public boolean updateDanhMuc(String ma, String tenMoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tenDM", tenMoi);
        return db.update("danhmuc", cv, "maDM = ?", new String[]{ma}) > 0;
    }

    public boolean deleteDanhMuc(String ma) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("danhmuc", "maDM = ?", new String[]{ma}) > 0;
    }

    // ================= 3. QUẢN LÝ SẢN PHẨM =================

    public Cursor getAllSanPham() {
        return getReadableDatabase().rawQuery("SELECT * FROM sanpham", null);
    }

    public boolean insertSanPham(String ma, String ten, int gia, int sl, String dvt, String ngay, String maDM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("maSP", ma); cv.put("tenSP", ten); cv.put("giaSP", gia);
        cv.put("soLuong", sl); cv.put("donViTinh", dvt); cv.put("ngayNhap", ngay);
        cv.put("maDM", maDM);
        return db.insert("sanpham", null, cv) != -1;
    }

    public boolean updateSanPham(String ma, String ten, int gia, int sl, String dvt, String ngay, String maDM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tenSP", ten); cv.put("giaSP", gia); cv.put("soLuong", sl);
        cv.put("donViTinh", dvt); cv.put("ngayNhap", ngay); cv.put("maDM", maDM);
        return db.update("sanpham", cv, "maSP = ?", new String[]{ma}) > 0;
    }

    public boolean deleteSanPham(String ma) {
        return getWritableDatabase().delete("sanpham", "maSP = ?", new String[]{ma}) > 0;
    }

    // ================= 4. QUẢN LÝ GIỎ HÀNG =================

    public boolean addSanPhamToGioHang(String ma, String ten, int gia) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT soLuongMua FROM giohang WHERE maSP = ?", new String[]{ma});

        if (cursor.moveToFirst()) {
            int slHienTai = cursor.getInt(0);
            ContentValues cv = new ContentValues();
            cv.put("soLuongMua", slHienTai + 1);
            db.update("giohang", cv, "maSP = ?", new String[]{ma});
        } else {
            ContentValues cv = new ContentValues();
            cv.put("maSP", ma); cv.put("tenSP", ten);
            cv.put("giaSP", gia); cv.put("soLuongMua", 1);
            db.insert("giohang", null, cv);
        }
        cursor.close();
        return true;
    }

    public Cursor getGioHang() {
        return getReadableDatabase().rawQuery("SELECT * FROM giohang", null);
    }

    public void xoaGioHang() {
        getWritableDatabase().execSQL("DELETE FROM giohang");
    }
}