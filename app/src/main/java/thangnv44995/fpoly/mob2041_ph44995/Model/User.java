package thangnv44995.fpoly.mob2041_ph44995.Model;

public class User {
    private String username;
    private String password;
    private String role;

    // 1. Constructor mặc định (Nên có để tránh lỗi một số thư viện)
    public User() {
    }

    // 2. Constructor dùng cho Danh sách (Khách hàng / Nhân viên)
    // Khớp với code: list.add(new User(cursor.getString(0), cursor.getString(1)));
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // 3. Constructor đầy đủ các trường
    // Dùng khi bạn thực hiện Insert hoặc cập nhật User mới
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter và Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}