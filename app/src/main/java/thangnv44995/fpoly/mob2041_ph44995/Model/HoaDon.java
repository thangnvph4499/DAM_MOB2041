package thangnv44995.fpoly.mob2041_ph44995.Model;

public class HoaDon {
    private String maHD;      // Mã hóa đơn
    private String tenNV;     // Tên nhân viên lập hóa đơn
    private String tenKH;     // Tên khách hàng
    private String ngayLap;    // Ngày lập hóa đơn
    private double tongTien;  // Tổng số tiền thanh toán

    // Constructor không đối số
    public HoaDon() {
    }

    // Constructor đầy đủ đối số
    public HoaDon(String maHD, String tenNV, String tenKH, String ngayLap, double tongTien) {
        this.maHD = maHD;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}