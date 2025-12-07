/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author Nghia
 */
public class DonHangDTO {
    private String maDH;
    private String tenNguoiMua;
    private String diaChi;
    private String email;
    private String sdt;
    
    // Khóa ngoại (Lưu Mã để lưu DB, lưu Tên để hiện lên bảng)
    private String maHangHoa;
    private String tenHangHoa; 
    
    private int soLuong;
    private double tongTien;
    
    private String maDVVC;
    private String tenDVVC; 
    
    private String maVoucher;
    private String tenVoucher; 
    
    private Date ngayDat;
    private Date ngayDuKien;
    private String trangThai;
    
    public DonHangDTO(){
    }

    public DonHangDTO(String maDH, String tenNguoiMua, String diaChi, String email, String sdt, String maHangHoa, String tenHangHoa, int soLuong, double tongTien, String maDVVC, String tenDVVC, String maVoucher, String tenVoucher, Date ngayDat, Date ngayDuKien, String trangThai) {
        this.maDH = maDH;
        this.tenNguoiMua = tenNguoiMua;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.maHangHoa = maHangHoa;
        this.tenHangHoa = tenHangHoa;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.maDVVC = maDVVC;
        this.tenDVVC = tenDVVC;
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.ngayDat = ngayDat;
        this.ngayDuKien = ngayDuKien;
        this.trangThai = trangThai;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getTenNguoiMua() {
        return tenNguoiMua;
    }

    public void setTenNguoiMua(String tenNguoiMua) {
        this.tenNguoiMua = tenNguoiMua;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void setMaHangHoa(String maHangHoa) {
        this.maHangHoa = maHangHoa;
    }

    public String getTenHangHoa() {
        return tenHangHoa;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaDVVC() {
        return maDVVC;
    }

    public void setMaDVVC(String maDVVC) {
        this.maDVVC = maDVVC;
    }

    public String getTenDVVC() {
        return tenDVVC;
    }

    public void setTenDVVC(String tenDVVC) {
        this.tenDVVC = tenDVVC;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public Date getNgayDuKien() {
        return ngayDuKien;
    }

    public void setNgayDuKien(Date ngayDuKien) {
        this.ngayDuKien = ngayDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
    