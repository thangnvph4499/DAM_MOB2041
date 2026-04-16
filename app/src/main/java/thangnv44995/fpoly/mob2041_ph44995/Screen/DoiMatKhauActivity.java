package thangnv44995.fpoly.mob2041_ph44995.Screen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import thangnv44995.fpoly.mob2041_ph44995.DBHelper;
import thangnv44995.fpoly.mob2041_ph44995.R;

public class DoiMatKhauActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        EditText edtOld = findViewById(R.id.edtOldPass);
        EditText edtNew = findViewById(R.id.edtNewPass);
        EditText edtReNew = findViewById(R.id.edtReNewPass);
        Button btnSave = findViewById(R.id.btnSavePass);
        DBHelper db = new DBHelper(this);

        String user = getIntent().getStringExtra("USER_NAME");

        btnSave.setOnClickListener(v -> {
            String oldP = edtOld.getText().toString();
            String newP = edtNew.getText().toString();
            String reP = edtReNew.getText().toString();

            if (oldP.isEmpty() || newP.isEmpty() || reP.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else if (!newP.equals(reP)) {
                Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            } else {
                int check = db.updatePassword(user, oldP, newP);
                if (check == 1) {
                    Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng màn hình quay về Home
                } else {
                    Toast.makeText(this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}