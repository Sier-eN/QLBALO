/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Nghia
 */
public class ThongKeDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    // 1. Thống kê Tồn Kho (Lấy Tên SP và Số Lượng Còn Lại)
    public HashMap<String, Integer> getTonKho() {
        HashMap<String, Integer> data = new HashMap<>();
        String sql = "SELECT TenHangHoa, SoLuongConLai FROM HangHoa";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                data.put(rs.getString("TenHangHoa"), rs.getInt("SoLuongConLai"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }

    // 2. Thống kê Doanh Thu - Chi Phí - Lợi Nhuận của Tháng
    // Trả về mảng double: [0]=DoanhThu, [1]=ChiPhi, [2]=LoiNhuan
    public double[] getTaiChinhThang(int thang, int nam) {
        double[] ketQua = {0.0, 0.0, 0.0};
        String sql = "SELECT " +
                     "   SUM(dh.TongTien) as DoanhThu, " +
                     "   SUM(dh.SoLuong * hh.GiaNhap) as ChiPhi " +
                     "FROM DonHang dh " +
                     "JOIN HangHoa hh ON dh.MaHangHoa = hh.MaHangHoa " +
                     "WHERE MONTH(dh.NgayDatHang) = ? AND YEAR(dh.NgayDatHang) = ? " +
                     "AND dh.TrangThai = N'Đã Giao Hàng'"; // Chỉ tính đơn thành công
        
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, thang);
            p.setInt(2, nam);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                ketQua[0] = rs.getDouble("DoanhThu");
                ketQua[1] = rs.getDouble("ChiPhi");
                ketQua[2] = ketQua[0] - ketQua[1]; // Lợi nhuận
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ketQua;
    }

    // 3. Top 5 Sản Phẩm Bán Chạy Nhất Tháng
    public LinkedHashMap<String, Integer> getTop5BanChay(int thang, int nam) {
        LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 hh.TenHangHoa, SUM(dh.SoLuong) as DaBan " +
                     "FROM DonHang dh " +
                     "JOIN HangHoa hh ON dh.MaHangHoa = hh.MaHangHoa " +
                     "WHERE MONTH(dh.NgayDatHang) = ? AND YEAR(dh.NgayDatHang) = ? " +
                     "AND dh.TrangThai = N'Đã Giao Hàng' " +
                     "GROUP BY hh.TenHangHoa " +
                     "ORDER BY SUM(dh.SoLuong) DESC";
        
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, thang);
            p.setInt(2, nam);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                data.put(rs.getString("TenHangHoa"), rs.getInt("DaBan"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }
}
