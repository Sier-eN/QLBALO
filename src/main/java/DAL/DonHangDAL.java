/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.DonHangDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nghia
 */
public class DonHangDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
    
    // 1. Lấy danh sách hiển thị
    public ArrayList<DonHangDTO> getAll() {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        String sql = "SELECT dh.*, hh.TenHangHoa, dv.TenCongTy, v.TenVoucher " +
                     "FROM DonHang dh " +
                     "JOIN HangHoa hh ON dh.MaHangHoa = hh.MaHangHoa " +
                     "JOIN DonViVanChuyen dv ON dh.MaDVVC = dv.MaDVVC " +
                     "LEFT JOIN Voucher v ON dh.MaVoucher = v.MaVoucher";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Tìm kiếm theo trạng thái
    public ArrayList<DonHangDTO> getByStatus(String status) {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        String sql = "SELECT dh.*, hh.TenHangHoa, dv.TenCongTy, v.TenVoucher " +
                     "FROM DonHang dh " +
                     "JOIN HangHoa hh ON dh.MaHangHoa = hh.MaHangHoa " +
                     "JOIN DonViVanChuyen dv ON dh.MaDVVC = dv.MaDVVC " +
                     "LEFT JOIN Voucher v ON dh.MaVoucher = v.MaVoucher " +
                     "WHERE dh.TrangThai = ?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, status);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // Hàm phụ để map dữ liệu cho gọn
    private DonHangDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        DonHangDTO dh = new DonHangDTO();
        dh.setMaDH(rs.getString("MaDH"));
        dh.setTenNguoiMua(rs.getString("TenNguoiMua"));
        dh.setDiaChi(rs.getString("DiaChi"));
        dh.setSdt(rs.getString("SDT"));
        dh.setEmail(rs.getString("Email"));
        dh.setMaHangHoa(rs.getString("MaHangHoa"));
        dh.setTenHangHoa(rs.getString("TenHangHoa"));
        dh.setSoLuong(rs.getInt("SoLuong"));
        dh.setTongTien(rs.getDouble("TongTien"));
        dh.setMaDVVC(rs.getString("MaDVVC"));
        dh.setTenDVVC(rs.getString("TenCongTy"));
        dh.setMaVoucher(rs.getString("MaVoucher"));
        dh.setTenVoucher(rs.getString("TenVoucher"));
        dh.setNgayDat(rs.getDate("NgayDatHang"));
        dh.setNgayDuKien(rs.getDate("NgayDuKien"));
        dh.setTrangThai(rs.getString("TrangThai"));
        return dh;
    }

    // 3. Thêm
    public boolean them(DonHangDTO dh) {
        String sql = "INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, MaDVVC, MaVoucher, NgayDatHang, TrangThai) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, dh.getMaDH());
            p.setString(2, dh.getTenNguoiMua());
            p.setString(3, dh.getDiaChi());
            p.setString(4, dh.getEmail());
            p.setString(5, dh.getSdt());
            p.setString(6, dh.getMaHangHoa());
            p.setInt(7, dh.getSoLuong());
            p.setString(8, dh.getMaDVVC());
            p.setString(9, dh.getMaVoucher());
            p.setDate(10, dh.getNgayDat());
            p.setString(11, dh.getTrangThai());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    // 4. Sửa
    public boolean sua(DonHangDTO dh) {
        String sql = "UPDATE DonHang SET TenNguoiMua=?, DiaChi=?, SDT=?, MaDVVC=?, TrangThai=?, NgayDuKien=? WHERE MaDH=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, dh.getTenNguoiMua());
            p.setString(2, dh.getDiaChi());
            p.setString(3, dh.getSdt());
            p.setString(4, dh.getMaDVVC());
            p.setString(5, dh.getTrangThai());
            p.setDate(6, dh.getNgayDuKien());
            p.setString(7, dh.getMaDH());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 5. Xóa
    public boolean xoa(String maDH) {
        String sql = "DELETE FROM DonHang WHERE MaDH=?";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, maDH);
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Load ComboBox
    public HashMap<String, String> getMapHangHoa() {
        HashMap<String, String> map = new HashMap<>();
        try (Connection conn = getConnection(); ResultSet rs = conn.createStatement().executeQuery("SELECT MaHangHoa, TenHangHoa FROM HangHoa")) {
            while(rs.next()) map.put(rs.getString("TenHangHoa"), rs.getString("MaHangHoa"));
        } catch (Exception e) {}
        return map;
    }
    public HashMap<String, String> getMapDVVC() {
        HashMap<String, String> map = new HashMap<>();
        try (Connection conn = getConnection(); ResultSet rs = conn.createStatement().executeQuery("SELECT MaDVVC, TenCongTy FROM DonViVanChuyen")) {
            while(rs.next()) map.put(rs.getString("TenCongTy"), rs.getString("MaDVVC"));
        } catch (Exception e) {}
        return map;
    }
    public HashMap<String, String> getMapVoucher() {
        HashMap<String, String> map = new HashMap<>();
        try (Connection conn = getConnection(); ResultSet rs = conn.createStatement().executeQuery("SELECT MaVoucher, TenVoucher FROM Voucher")) {
            while(rs.next()) map.put(rs.getString("TenVoucher"), rs.getString("MaVoucher"));
        } catch (Exception e) {}
        return map;
    }
}
