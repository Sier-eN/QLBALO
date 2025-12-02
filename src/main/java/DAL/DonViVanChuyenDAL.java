/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.DonViVanChuyenDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class DonViVanChuyenDAL {
    
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";
    
    public Connection getConnection(){
        Connection ketnoi = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ketnoi = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ketnoi;
    }
    
    public ArrayList<DonViVanChuyenDTO> getAll() {
        ArrayList<DonViVanChuyenDTO> list = new ArrayList<>();
        
        String sql = "Select * from DonViVanChuyen";
        try (Connection ketnoi = getConnection(); PreparedStatement p = ketnoi.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(new DonViVanChuyenDTO (
                    rs.getString("MaDVVC"),
                    rs.getString("TenCongTy"),
                    rs.getString("SDT_LienHe"),
                    rs.getString("Email"),
                    rs.getString("NguoiLienHeChinh"),
                    rs.getString("LoaiVanChuyen"),
                    rs.getString("ThoiGianGiaoHang")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<DonViVanChuyenDTO> timKiem(String keyword) {
        ArrayList<DonViVanChuyenDTO> list = new ArrayList<>();
        
        // Dùng dấu % để tìm kiếm gần đúng (chứa từ khóa)
        String sql = "SELECT * FROM DonViVanChuyen WHERE TenCongTy LIKE ?";
        
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            // Thêm % vào trước và sau từ khóa
            p.setString(1, "%" + keyword + "%");
            
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new DonViVanChuyenDTO(
                    rs.getString("MaDVVC"),
                    rs.getString("TenCongTy"),
                    rs.getString("SDT_LienHe"),
                    rs.getString("Email"),
                    rs.getString("NguoiLienHeChinh"),
                    rs.getString("LoaiVanChuyen"),
                    rs.getString("ThoiGianGiaoHang")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    public boolean them(DonViVanChuyenDTO dv) {
        String sql = "INSERT INTO DonViVanChuyen VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, dv.getMaDVVC());
            p.setString(2, dv.getTenCongTy());
            p.setString(3, dv.getSdt());
            p.setString(4, dv.getEmail());
            p.setString(5, dv.getNguoiLienHe());
            p.setString(6, dv.getLoaiVanChuyen());
            p.setString(7, dv.getThoiGianGiao());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    public boolean sua(DonViVanChuyenDTO dv) {
        String sql = "UPDATE DonViVanChuyen SET TenCongTy=?, SDT_LienHe=?, Email=?, NguoiLienHeChinh=?, LoaiVanChuyen=?, ThoiGianGiaoHang=? WHERE MaDVVC=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, dv.getTenCongTy());
            p.setString(2, dv.getSdt());
            p.setString(3, dv.getEmail());
            p.setString(4, dv.getNguoiLienHe());
            p.setString(5, dv.getLoaiVanChuyen());
            p.setString(6, dv.getThoiGianGiao());
            p.setString(7, dv.getMaDVVC()); // Điều kiện WHERE nằm cuối
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    public boolean xoa(String maDVVC) {
        String sql = "DELETE FROM DonViVanChuyen WHERE MaDVVC=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, maDVVC);
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
