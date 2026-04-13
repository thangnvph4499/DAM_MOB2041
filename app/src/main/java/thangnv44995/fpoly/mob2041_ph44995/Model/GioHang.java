package thangnv44995.fpoly.mob2041_ph44995.Model;

public class GioHang {
    private String maSP;      // Mã sản phẩm để định danh
    private String tenSP;     // Tên hiển thị trong giỏ
    private int giaSP;        // Giá tại thời điểm thêm vào giỏ
    private int soLuongMua;   // Số lượng người dùng đã chọn

    // Constructor không đối số
    public GioHang() {
    }

    // Constructor đầy đủ đối số
    public GioHang(String maSP, String tenSP, int giaSP, int soLuongMua) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.soLuongMua = soLuongMua;
    }

    // Getter và Setter
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}