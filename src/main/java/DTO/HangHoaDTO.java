/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Nghia
 */
public class HangHoaDTO {
    private String maHangHoa;
    private String tenHangHoa;
    private int soLuongNhap;      // Hiển thị SL Nhập
    private int soLuongConLai;    // Hiển thị SL Còn
    private double giaBan;
    private double giaNhap;
    private double tongTienNhap;  // Thêm: Hiển thị Tổng tiền nhập
    private String maNhaCungCap;

    public HangHoaDTO() {
    }

    public HangHoaDTO(String maHangHoa, String tenHangHoa, int soLuongNhap, int soLuongConLai, double giaBan, double giaNhap, double tongTienNhap, String maNhaCungCap) {
        this.maHangHoa = maHangHoa;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.soLuongConLai = soLuongConLai;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.tongTienNhap = tongTienNhap;
        this.maNhaCungCap = maNhaCungCap;
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

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongConLai() {
        return soLuongConLai;
    }

    public void setSoLuongConLai(int soLuongConLai) {
        this.soLuongConLai = soLuongConLai;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getTongTienNhap() {
        return tongTienNhap;
    }

    public void setTongTienNhap(double tongTienNhap) {
        this.tongTienNhap = tongTienNhap;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    
}
