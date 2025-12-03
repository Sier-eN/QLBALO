/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.VoucherDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class VoucherDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa";
    private final String PASS = "123";

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) { return null; }
    }
    public ArrayList<VoucherDTO> getAll() {
        ArrayList<VoucherDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Voucher";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(new VoucherDTO(
                    rs.getString("MaVoucher"),
                    rs.getString("TenVoucher"),
                    rs.getString("ChucNang"),
                    rs.getDouble("GiaTri"),
                    rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean them(VoucherDTO v) {
        String sql = "INSERT INTO Voucher VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, v.getMaVoucher());
            p.setString(2, v.getTenVoucher());
            p.setString(3, v.getChucNang());
            p.setDouble(4, v.getGiaTri());
            p.setInt(5, v.getSoLuong());
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean sua(VoucherDTO v) {
        String sql = "UPDATE Voucher SET TenVoucher=?, ChucNang=?, GiaTri=?, SoLuong=? WHERE MaVoucher=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, v.getTenVoucher());
            p.setString(2, v.getChucNang());
            p.setDouble(3, v.getGiaTri());
            p.setInt(4, v.getSoLuong());
            p.setString(5, v.getMaVoucher());
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean xoa(String ma) {
        String sql = "DELETE FROM Voucher WHERE MaVoucher=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, ma);
            return p.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public ArrayList<VoucherDTO> timKiem(String keyword) {
        ArrayList<VoucherDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Voucher WHERE TenVoucher LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new VoucherDTO(
                    rs.getString("MaVoucher"),
                    rs.getString("TenVoucher"),
                    rs.getString("ChucNang"),
                    rs.getDouble("GiaTri"),
                    rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
