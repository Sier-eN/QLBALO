/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

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
    
    private LocalDateTime ngayDatHang; 
    private LocalDateTime ngayDuKien;
    private LocalDateTime ngayNhanHang;
    
    private String trangThai;
    
    private String maHangHoa;
    private String maDVVC;
    private String maVoucher;
    
    public DonHangDTO(){
    }

    public DonHangDTO(String maDH, String tenNguoiMua, String diaChi, String email, String sdt, LocalDateTime ngayDatHang, LocalDateTime ngayDuKien, LocalDateTime ngayNhanHang, String trangThai, String maHangHoa, String maDVVC, String maVoucher) {
        this.maDH = maDH;
        this.tenNguoiMua = tenNguoiMua;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.ngayDatHang = ngayDatHang;
        this.ngayDuKien = ngayDuKien;
        this.ngayNhanHang = ngayNhanHang;
        this.trangThai = trangThai;
        this.maHangHoa = maHangHoa;
        this.maDVVC = maDVVC;
        this.maVoucher = maVoucher;
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

    public LocalDateTime getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(LocalDateTime ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public LocalDateTime getNgayDuKien() {
        return ngayDuKien;
    }

    public void setNgayDuKien(LocalDateTime ngayDuKien) {
        this.ngayDuKien = ngayDuKien;
    }

    public LocalDateTime getNgayNhanHang() {
        return ngayNhanHang;
    }

    public void setNgayNhanHang(LocalDateTime ngayNhanHang) {
        this.ngayNhanHang = ngayNhanHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void setMaHangHoa(String maHangHoa) {
        this.maHangHoa = maHangHoa;
    }

    public String getMaDVVC() {
        return maDVVC;
    }

    public void setMaDVVC(String maDVVC) {
        this.maDVVC = maDVVC;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }
    
}
