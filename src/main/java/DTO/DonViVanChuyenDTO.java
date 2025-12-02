/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Nghia
 */
public class DonViVanChuyenDTO {
    private String maDVVC;
    private String tenCongTy;
    private String sdt;
    private String email;
    private String nguoiLienHe;
    private String loaiVanChuyen;
    private String thoiGianGiao; 
    
    public DonViVanChuyenDTO(){
    }

    public DonViVanChuyenDTO(String maDVVC, String tenCongTy, String sdt, String email, String nguoiLienHe, String loaiVanChuyen, String thoiGianGiao) {
        this.maDVVC = maDVVC;
        this.tenCongTy = tenCongTy;
        this.sdt = sdt;
        this.email = email;
        this.nguoiLienHe = nguoiLienHe;
        this.loaiVanChuyen = loaiVanChuyen;
        this.thoiGianGiao = thoiGianGiao;
    }

    public String getMaDVVC() {
        return maDVVC;
    }

    public void setMaDVVC(String maDVVC) {
        this.maDVVC = maDVVC;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNguoiLienHe() {
        return nguoiLienHe;
    }

    public void setNguoiLienHe(String nguoiLienHe) {
        this.nguoiLienHe = nguoiLienHe;
    }

    public String getLoaiVanChuyen() {
        return loaiVanChuyen;
    }

    public void setLoaiVanChuyen(String loaiVanChuyen) {
        this.loaiVanChuyen = loaiVanChuyen;
    }

    public String getThoiGianGiao() {
        return thoiGianGiao;
    }

    public void setThoiGianGiao(String thoiGianGiao) {
        this.thoiGianGiao = thoiGianGiao;
    }
}
