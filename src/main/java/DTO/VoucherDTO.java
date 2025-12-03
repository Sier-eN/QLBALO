/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Nghia
 */
public class VoucherDTO {
    private String maVoucher;
    private String tenVoucher;
    private String chucNang; // Free Ship hoặc Giảm Giá...
    private double giaTri;
    private int soLuong;

    public VoucherDTO() {
    }

    public VoucherDTO(String maVoucher, String tenVoucher, String chucNang, double giaTri, int soLuong) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.chucNang = chucNang;
        this.giaTri = giaTri;
        this.soLuong = soLuong;
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

    public String getChucNang() {
        return chucNang;
    }

    public void setChucNang(String chucNang) {
        this.chucNang = chucNang;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
