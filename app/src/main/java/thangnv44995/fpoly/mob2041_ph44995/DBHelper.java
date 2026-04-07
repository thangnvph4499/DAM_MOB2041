package thangnv44995.fpoly.mob2041_ph44995;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Tên database và Version
    public static final String DB_NAME = "User.db";
    public static final int DB_VERSION = 2; // Tăng lên 2 để cập nhật cấu trúc bảng

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users có thêm cột role
        String createTableUsers = "CREATE TABLE users(" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "role TEXT)";
        db.execSQL(createTableUsers);

        // Chèn sẵn 1 tài khoản Quản lý để có thể đăng nhập lần đầu
        db.execSQL("INSERT INTO users VALUES('admin', 'admin123', 'Quản lý')");
        db.execSQL("INSERT INTO users VALUES('nv01', '123', 'Nhân viên')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }

    // Kiểm tra username tồn tại (dùng cho Register)
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Hàm Đăng ký (Mặc định là Khách hàng)
    public boolean insertData(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role); // Truyền "Khách hàng" từ Activity vào đây

        long result = db.insert("users", null, cv);
        return result != -1;
    }

    // Hàm Kiểm tra đăng nhập và trả về VAI TRÒ (Role)
    public String checkLoginReturnRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Truy vấn lấy cột role dựa trên user và pass
        Cursor cursor = db.rawQuery(
                "SELECT role FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            String role = cursor.getString(0); // Lấy giá trị cột role
            cursor.close();
            return role; // Trả về Quản lý, Nhân viên hoặc Khách hàng
        }

        cursor.close();
        return null; // Trả về null nếu sai thông tin đăng nhập
    }
}