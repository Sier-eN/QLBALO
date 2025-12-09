/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.DonHangDAL;
import DTO.DonHangDTO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nghia
 */
public class DonHangBLL {
    private DonHangDAL dal = new DonHangDAL();

    public ArrayList<DonHangDTO> layDanhSach() { return dal.getAll(); }
    public ArrayList<DonHangDTO> timKiemTheoTrangThai(String status) { return dal.getByStatus(status); }

    public String themDonHang(DonHangDTO dh) {
        if (dh.getMaDH().isEmpty()) return "Mã ĐH trống!";
        if (dh.getSoLuong() <= 0) return "Số lượng phải > 0";
        
        double[] thongTin = dal.getThongTinHangHoa(dh.getMaHangHoa());
        int tonKho = (int) thongTin[1];
        if (tonKho < dh.getSoLuong()) {
            return "Kho không đủ hàng! (Hiện còn: " + tonKho + ")";
        }
        if (dal.them(dh)) {
            return "Thêm thành công";
        }
        return "Thêm thất bại (Có thể trùng mã ĐH)";
    }

    public String capNhatDonHang(DonHangDTO dh) {
        if (dal.sua(dh)) return "Cập nhật thành công";
        return "Cập nhật thất bại";
    }

    public String xoaDonHang(String ma) {
        if (dal.xoa(ma)) return "Xóa thành công";
        return "Xóa thất bại";
    }

    public HashMap<String, String> layMapHangHoa() { return dal.getMapHangHoa(); }
    public HashMap<String, String> layMapDVVC() { return dal.getMapDVVC(); }
    
    public ArrayList<String> layDanhSachTenVoucher() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Không có"); // Mặc định
        
        // Lấy Map từ DAL về và chỉ lấy phần Tên (Key) hoặc Mã (Value) tùy bạn hiển thị
        // Ở đây giả sử MapVoucher lưu <TenVoucher, MaVoucher>
        HashMap<String, String> map = dal.getMapVoucher();
        for (String tenVoucher : map.keySet()) {
            list.add(tenVoucher);
        }
        return list;
    }
    
    public HashMap<String, String> layMapVoucher() { return dal.getMapVoucher(); }
    
    // 1. Kiểm tra logic chuyển đổi trạng thái
    public String kiemTraTrangThaiHople(String cu, String moi) {
        if (cu.equals(moi)) return "OK"; 
        
        // Logic chặt chẽ
        switch (cu) {
            case "Chờ Duyệt":
                if (moi.equals("Chờ Giao Hàng") || moi.equals("Hủy Đơn")) return "OK";
                return "Đơn 'Chờ Duyệt' chỉ được sang 'Chờ Giao' hoặc 'Hủy'!";
            case "Chờ Giao Hàng":
                if (moi.equals("Đã Giao Hàng") || moi.equals("Hủy Đơn")) return "OK";
                return "Đơn 'Chờ Giao' chỉ được sang 'Đã Giao' hoặc 'Hủy'!";
            case "Đã Giao Hàng": // Đã trừ kho rồi
                if (moi.equals("Hủy Đơn")) return "OK"; // Cho phép hủy để hoàn kho (nhờ Trigger)
                return "Đơn đã giao chỉ có thể Hủy (Hoàn hàng)!";
                
            case "Hủy Đơn":
                return "Đơn đã hủy không thể khôi phục!";
                
            default: return "OK";
        }
    }

    // 2. Kiểm tra quyền xóa đơn hàng
    public boolean kiemTraQuyenXoa(String status, String role) {
        // Nếu vai trò null hoặc rỗng -> chặn luôn
        if (role == null || role.isEmpty()) return false;

        // Admin: Quyền lực tối thượng (Xóa gì cũng được)
        if (role.equalsIgnoreCase("Admin")) {
            return true;
        }
        return false; // Các trường hợp còn lại không được xóa
    }
}
