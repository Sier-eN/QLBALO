/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.NhaCungCapDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class NhaCungCapDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";

    public Connection getConnection(){
        try { Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        return DriverManager.getConnection(DB_URL, USER, PASS); } 
        catch (Exception e) { return null; }
    }
    
    // 1. Lấy danh sách
    public ArrayList<NhaCungCapDTO> getAll() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(new NhaCungCapDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("TenCongTy"),
                    rs.getString("DiaChi"),
                    rs.getString("SDT_LienHe"),
                    rs.getString("Email"),
                    rs.getString("NguoiLienHeChinh")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm
    public boolean them(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, ncc.getMaNCC());
            p.setString(2, ncc.getTenCongTy());
            p.setString(3, ncc.getDiaChi());
            p.setString(4, ncc.getSdt());
            p.setString(5, ncc.getEmail());
            p.setString(6, ncc.getNguoiLienHe());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 3. Sửa
    public boolean sua(NhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenCongTy=?, DiaChi=?, SDT_LienHe=?, Email=?, NguoiLienHeChinh=? WHERE MaNhaCungCap=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, ncc.getTenCongTy());
            p.setString(2, ncc.getDiaChi());
            p.setString(3, ncc.getSdt());
            p.setString(4, ncc.getEmail());
            p.setString(5, ncc.getNguoiLienHe());
            p.setString(6, ncc.getMaNCC()); // Điều kiện WHERE
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 4. Xóa
    public boolean xoa(String ma) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNhaCungCap=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, ma);
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; } // Trả về false nếu dính khóa ngoại
    }

    // 5. Tìm kiếm
    public ArrayList<NhaCungCapDTO> timKiem(String keyword) {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE TenCongTy LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new NhaCungCapDTO(
                    rs.getString("MaNhaCungCap"),
                    rs.getString("TenCongTy"),
                    rs.getString("DiaChi"),
                    rs.getString("SDT_LienHe"),
                    rs.getString("Email"),
                    rs.getString("NguoiLienHeChinh")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
