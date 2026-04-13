package thangnv44995.fpoly.mob2041_ph44995.Model;

public class SanPham {
    private String maSP;      // Mã sản phẩm (Khóa chính)
    private String tenSP;     // Tên sản phẩm
    private int giaSP;        // Giá sản phẩm
    private int soLuong;      // Số lượng tồn kho
    private String donViTinh; // Đơn vị tính (Chai, Lon, Gói...)
    private String ngayNhap;  // Ngày nhập hàng
    private String maDM;      // Mã danh mục (Khóa ngoại)

    // 1. Constructor không đối số (Dùng khi cần tạo đối tượng rỗng)
    public SanPham() {
    }

    // 2. Constructor có đầy đủ đối số (Dùng khi lấy dữ liệu từ Cursor)
    public SanPham(String maSP, String tenSP, int giaSP, int soLuong, String donViTinh, String ngayNhap, String maDM) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.ngayNhap = ngayNhap;
        this.maDM = maDM;
    }

    // 3. Getter và Setter
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public int getGiaSP() { return giaSP; }
    public void setGiaSP(int giaSP) { this.giaSP = giaSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }

    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }

    public String getMaDM() { return maDM; }
    public void setMaDM(String maDM) { this.maDM = maDM; }
}