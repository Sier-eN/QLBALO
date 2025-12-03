/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.HangHoaDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class HangHoaDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";

    public Connection getConnection(){
        try { Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        return DriverManager.getConnection(DB_URL, USER, PASS); } 
        catch (Exception e) { return null; }
    }
    
    public ArrayList<HangHoaDTO> getAll() {
        ArrayList<HangHoaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM HangHoa";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(new HangHoaDTO(
                    rs.getString("MaHangHoa"),
                    rs.getString("TenHangHoa"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("GiaBan"),
                    rs.getDouble("GiaNhap"),
                    rs.getString("MaNhaCungCap")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean them(HangHoaDTO hh) {
        String sql = "INSERT INTO HangHoa VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, hh.getMaHangHoa());
            p.setString(2, hh.getTenHangHoa());
            p.setInt(3, hh.getSoLuong());
            p.setDouble(4, hh.getGiaBan());
            p.setDouble(5, hh.getGiaNhap());
            p.setString(6, hh.getMaNhaCungCap());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean sua(HangHoaDTO hh) {
        String sql = "UPDATE HangHoa SET TenHangHoa=?, SoLuong=?, GiaBan=?, GiaNhap=?, MaNhaCungCap=? WHERE MaHangHoa=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, hh.getTenHangHoa());
            p.setInt(2, hh.getSoLuong());
            p.setDouble(3, hh.getGiaBan());
            p.setDouble(4, hh.getGiaNhap());
            p.setString(5, hh.getMaNhaCungCap());
            p.setString(6, hh.getMaHangHoa());
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean xoa(String ma) {
        String sql = "DELETE FROM HangHoa WHERE MaHangHoa=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, ma);
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }
    
    // Tìm kiếm hàng hóa
    public ArrayList<HangHoaDTO> timKiem(String keyword) {
        ArrayList<HangHoaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM HangHoa WHERE TenHangHoa LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new HangHoaDTO(
                    rs.getString("MaHangHoa"),
                    rs.getString("TenHangHoa"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("GiaBan"),
                    rs.getDouble("GiaNhap"),
                    rs.getString("MaNhaCungCap")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
