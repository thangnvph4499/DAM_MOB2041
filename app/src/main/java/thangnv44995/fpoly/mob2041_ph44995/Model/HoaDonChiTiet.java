package thangnv44995.fpoly.mob2041_ph44995.Model;

public class HoaDonChiTiet {
    private int id;           // ID tự tăng trong database
    private String maHD;      // Mã hóa đơn để biết sản phẩm này thuộc hóa đơn nào
    private String tenSP;     // Tên sản phẩm tại thời điểm mua
    private int giaSP;        // Giá sản phẩm tại thời điểm mua
    private int soLuong;      // Số lượng mua

    // Constructor không đối số
    public HoaDonChiTiet() {
    }

    // Constructor đầy đủ đối số (dùng khi load dữ liệu từ Database)
    public HoaDonChiTiet(int id, String maHD, String tenSP, int giaSP, int soLuong) {
        this.id = id;
        this.maHD = maHD;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.soLuong = soLuong;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}