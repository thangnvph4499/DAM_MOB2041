package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUser, edtPass, edtRePass;
    Button btnRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRePass);
        btnRegister = findViewById(R.id.btnRegister);

        db = new DBHelper(this);

        btnRegister.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            String repass = edtRePass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(repass)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUsername(user)) {
                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                // ĐĂNG KÝ MẶC ĐỊNH LÀ KHÁCH HÀNG
                // Lưu ý: Tên hàm phải khớp với hàm bạn viết trong DBHelper
                boolean insert = db.insertData(user, pass, "Khách hàng");

                if (insert) {
                    Toast.makeText(this, "Đăng ký Khách hàng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Lỗi hệ thống khi đăng ký", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}