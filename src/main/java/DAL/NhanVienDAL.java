/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import App.LuuTruTK;
import DTO.NhanVienDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class NhanVienDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
    
    // A. LẤY DANH SÁCH (JOIN 2 BẢNG)
    public ArrayList<NhanVienDTO> layDanhSach() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT n.MaNhanVien, n.TenNhanVien, n.DiaChi, n.Email, n.SDT, " +
                     "t.TenDangNhap, t.MatKhau, t.VaiTro " +
                     "FROM NhanVien n " +
                     "JOIN TaiKhoan t ON n.ID_TaiKhoan = t.ID_TaiKhoan";
        
        // Sử dụng try-with-resources để tự động đóng kết nối (Connection con = getConnection())
        try (Connection con = getConnection(); 
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                    rs.getString("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("DiaChi"),
                    rs.getString("Email"),
                    rs.getString("SDT"),
                    rs.getString("TenDangNhap"),
                    rs.getString("MatKhau"),
                    rs.getString("VaiTro")
                );
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // B. THÊM NHÂN VIÊN MỚI (GỌI STORED PROCEDURE)
    public boolean themNhanVien(NhanVienDTO nv) {
        String sql = "{CALL sp_ThemNhanVienVaTaiKhoan(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection con = getConnection(); 
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, nv.getMaNV());
            cs.setString(2, nv.getTenNV());
            cs.setString(3, nv.getDiaChi());
            cs.setString(4, nv.getEmail());
            cs.setString(5, nv.getSdt());
            cs.setString(6, nv.getTenDangNhap());
            cs.setString(7, nv.getMatKhau());
            cs.setString(8, nv.getVaiTro());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // C. CẬP NHẬT NHÂN VIÊN (GỌI STORED PROCEDURE)
    public boolean capNhatNhanVien(NhanVienDTO nv) {
        String sql = "{CALL sp_CapNhatNhanVien(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection con = getConnection(); 
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, nv.getMaNV());
            cs.setString(2, nv.getTenNV());
            cs.setString(3, nv.getDiaChi());
            cs.setString(4, nv.getEmail());
            cs.setString(5, nv.getSdt());
            cs.setString(6, nv.getTenDangNhap());
            cs.setString(7, nv.getMatKhau());
            cs.setString(8, nv.getVaiTro());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // D. XÓA NHÂN VIÊN (GỌI STORED PROCEDURE)
    public boolean xoaNhanVien(String maNV) {
        String sql = "{CALL sp_XoaNhanVien(?)}";
        
        try (Connection con = getConnection(); 
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, maNV);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // E. KIỂM TRA TRÙNG MÃ
    public boolean kiemTraTrungMa(String maNV) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNhanVien = ?";
        
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // F. KIỂM TRA TRÙNG TÀI KHOẢN
    public boolean kiemTraTrungTaiKhoan(String tenDN) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE TenDangNhap = ?";
        
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, tenDN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<NhanVienDTO> timKiemNhanVien(String tuKhoa) {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        // Tìm theo Mã NV hoặc Tên NV (Kết hợp JOIN để lấy đủ thông tin)
        String sql = "SELECT n.MaNhanVien, n.TenNhanVien, n.DiaChi, n.Email, n.SDT, " +
                     "t.TenDangNhap, t.MatKhau, t.VaiTro " +
                     "FROM NhanVien n " +
                     "JOIN TaiKhoan t ON n.ID_TaiKhoan = t.ID_TaiKhoan " +
                     "WHERE n.MaNhanVien LIKE ? OR n.TenNhanVien LIKE ?";
        
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String searchStr = "%" + tuKhoa + "%"; // Thêm % để tìm gần đúng
            ps.setString(1, searchStr);
            ps.setString(2, searchStr);
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("TenNhanVien"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SDT"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("VaiTro")
                    );
                    list.add(nv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public NhanVienDTO layThongTinCaNhan(String tenDangNhap) {
        NhanVienDTO nv = null;
        String sql = "SELECT n.MaNhanVien, n.TenNhanVien, n.DiaChi, n.Email, n.SDT, " +
                     "t.TenDangNhap, t.MatKhau, t.VaiTro " +
                     "FROM NhanVien n " +
                     "JOIN TaiKhoan t ON n.ID_TaiKhoan = t.ID_TaiKhoan " +
                     "WHERE t.TenDangNhap = ?";
        
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, tenDangNhap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nv = new NhanVienDTO(
                        rs.getString("MaNhanVien"),
                        rs.getString("TenNhanVien"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SDT"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("VaiTro")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }
}
