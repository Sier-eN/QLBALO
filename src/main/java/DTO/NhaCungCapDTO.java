/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Nghia
 */
public class NhaCungCapDTO {
    private String maNCC;
    private String tenCongTy;
    private String diaChi;
    private String sdt;
    private String email;
    private String nguoiLienHe;

    public NhaCungCapDTO() {
    }

    public NhaCungCapDTO(String maNCC, String tenCongTy, String diaChi, String sdt, String email, String nguoiLienHe) {
        this.maNCC = maNCC;
        this.tenCongTy = tenCongTy;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.nguoiLienHe = nguoiLienHe;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
}
