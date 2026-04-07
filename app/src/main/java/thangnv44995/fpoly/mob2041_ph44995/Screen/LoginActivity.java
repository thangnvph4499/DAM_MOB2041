package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnLogin, btnGoRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        db = new DBHelper(this);

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi hàm checkLogin mới trả về chuỗi Role (như đã hướng dẫn ở câu trước)
            String role = db.checkLoginReturnRole(user, pass);

            if (role != null) {
                Toast.makeText(this, "Đăng nhập: " + role, Toast.LENGTH_SHORT).show();

                // Gửi tên đăng nhập và vai trò sang HomeActivity để xử lý hiển thị giao diện khác nhau
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("USER_NAME", user);
                intent.putExtra("USER_ROLE", role);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}