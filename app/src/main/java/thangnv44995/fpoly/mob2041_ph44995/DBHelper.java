package thangnv44995.fpoly.mob2041_ph44995;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "User.db";
    public static final int DB_VERSION = 7;

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

        // 5. Bảng hoadon (MỚI THÊM)
        db.execSQL("CREATE TABLE hoadon(" +
                "maHD TEXT PRIMARY KEY, " +
                "tenNV TEXT, " +
                "tenKH TEXT, " +
                "ngayLap TEXT, " +
                "tongTien REAL)");

        // 6. Bảng hoadon_chitiet
        db.execSQL("CREATE TABLE hoadon_chitiet(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD TEXT, " +
                "tenSP TEXT, " +
                "giaSP INTEGER, " +
                "soLuong INTEGER, " +
                "FOREIGN KEY (maHD) REFERENCES hoadon(maHD) ON DELETE CASCADE)");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO users VALUES('admin', 'admin123', 'Quản lý')");
        db.execSQL("INSERT INTO users VALUES('nv01', '123', 'Nhân viên')");
        db.execSQL("INSERT INTO users VALUES('nv02', '123', 'Nhân viên')");
        db.execSQL("INSERT INTO users VALUES('nv03', '123', 'Nhân viên')");
        db.execSQL("INSERT INTO users VALUES('nv04', '123', 'Nhân viên')");
        db.execSQL("INSERT INTO danhmuc VALUES('DM001', 'Đồ uống'), ('DM002', 'Bánh kẹo')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS danhmuc");
        db.execSQL("DROP TABLE IF EXISTS sanpham");
        db.execSQL("DROP TABLE IF EXISTS giohang");
        db.execSQL("DROP TABLE IF EXISTS hoadon");
        db.execSQL("DROP TABLE IF EXISTS hoadon_chitiet");
        onCreate(db);
    }

    // ================= 1. QUẢN LÝ USER =================

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

    // ================= 2. QUẢN LÝ DANH MỤC  =================

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

    // ================= 3. QUẢN LÝ SẢN PHẨM  =================

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

    // ================= 4. QUẢN LÝ GIỎ HÀNG  =================

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

    public boolean minusSanPhamTrongGioHang(String ma) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT soLuongMua FROM giohang WHERE maSP = ?", new String[]{ma});
        if (cursor.moveToFirst()) {
            int slHienTai = cursor.getInt(0);
            if (slHienTai > 1) {
                ContentValues cv = new ContentValues();
                cv.put("soLuongMua", slHienTai - 1);
                db.update("giohang", cv, "maSP = ?", new String[]{ma});
            } else {
                db.delete("giohang", "maSP = ?", new String[]{ma});
            }
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

    // ================= 5. QUẢN LÝ HÓA ĐƠN  =================

    public Cursor getAllHoaDon() {
        return getReadableDatabase().rawQuery("SELECT * FROM hoadon", null);
    }

    public boolean deleteHoaDon(String maHD) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("hoadon", "maHD = ?", new String[]{maHD}) > 0;
    }

    public boolean insertHoaDon(String maHD, String tenNV, String tenKH, String ngay, double tong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("maHD", maHD);
        cv.put("tenNV", tenNV);
        cv.put("tenKH", tenKH);
        cv.put("ngayLap", ngay); // Lưu ý: 'ngay' phải là định dạng yyyy-MM-dd
        cv.put("tongTien", tong);

        long result = db.insert("hoadon", null, cv);
        if (result == -1) return false;

        // SỬA LẠI ĐOẠN NÀY: Dùng mảng Object để tránh lỗi SQL Injection
        String sqlInsertChiTiet = "INSERT INTO hoadon_chitiet (maHD, tenSP, giaSP, soLuong) " +
                "SELECT ?, tenSP, giaSP, soLuongMua FROM giohang";
        db.execSQL(sqlInsertChiTiet, new Object[]{maHD});

        return true;
    }

    public Cursor getChiTietHoaDon(String maHD) {
        return getReadableDatabase().rawQuery("SELECT * FROM hoadon_chitiet WHERE maHD = ?", new String[]{maHD});
    }

    // ================= 6. THỐNG KÊ DOANH THU (MỚI) =================

    public double getDoanhThu(String tuNgay, String denNgay) {
        double doanhThu = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        // Sử dụng IFNULL để trả về 0 nếu không có kết quả, tránh lỗi NullPointerException
        String sql = "SELECT IFNULL(SUM(tongTien), 0) FROM hoadon WHERE ngayLap BETWEEN ? AND ?";

        // Log để bạn kiểm tra trong Logcat xem ngày truyền xuống có đúng yyyy-MM-dd không
        Log.d("SQL_CHECK", "Query: " + tuNgay + " to " + denNgay);

        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay, denNgay});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                doanhThu = cursor.getDouble(0);
            }
            cursor.close();
        }
        return doanhThu;
    }
    // ================= 7. THỐNG KÊ TOP SẢN PHẨM (MỚI) =================

    public Cursor getTopSanPham(String tuNgay, String denNgay, String limit) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT hc.tenSP, SUM(hc.soLuong) AS tongSoLuong " +
                "FROM hoadon_chitiet hc " +
                "INNER JOIN hoadon h ON hc.maHD = h.maHD " +
                "WHERE h.ngayLap BETWEEN ? AND ? " +
                "GROUP BY hc.tenSP " +
                "ORDER BY tongSoLuong DESC " +
                "LIMIT ?";

        return db.rawQuery(sql, new String[]{tuNgay, denNgay, limit});
    }

    // ================= 8. QUẢN LÝ KHÁCH HÀNG =================

    // Lấy danh sách tài khoản có role là 'Khách hàng'
    public Cursor getDanhSachKhachHang() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT username, role FROM users WHERE role = 'Khách hàng'", null);
    }

    // Xóa tài khoản khách hàng
    public boolean deleteKhachHang(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Không cho phép xóa admin từ chức năng này để bảo mật
        return db.delete("users", "username = ? AND role = 'Khách hàng'", new String[]{username}) > 0;
    }

    // Lấy danh sách hóa đơn theo tên khách hàng (để hiển thị lịch sử mua hàng của riêng họ)
    public Cursor getHoaDonTheoKhachHang(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM hoadon WHERE tenKH = ?", new String[]{username});
    }

    // ================= 9. QUẢN LÝ NHÂN VIÊN =================

    // Lấy danh sách tài khoản có role là 'Nhân viên'
    public Cursor getDanhSachNhanVien() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Lọc những user có vai trò là Nhân viên
        return db.rawQuery("SELECT username, role FROM users WHERE role = 'Nhân viên'", null);
    }

    // Xóa tài khoản nhân viên
    public boolean deleteNhanVien(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Thực hiện xóa nhân viên theo username
        return db.delete("users", "username = ? AND role = 'Nhân viên'", new String[]{username}) > 0;
    }

    // Cập nhật thông tin nhân viên (Ví dụ đổi mật khẩu hoặc cập nhật lại đúng role)
    public boolean updateNhanVien(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", newPassword);
        return db.update("users", cv, "username = ? AND role = 'Nhân viên'", new String[]{username}) > 0;
    }

    // Kiểm tra xem một user có phải là nhân viên hay không
    public boolean isNhanVien(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND role = 'Nhân viên'", new String[]{username});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
}