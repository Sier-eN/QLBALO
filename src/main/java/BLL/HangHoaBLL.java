/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.HangHoaDAL;
import DTO.HangHoaDTO;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class HangHoaBLL {
    private HangHoaDAL dal = new HangHoaDAL();

    public ArrayList<HangHoaDTO> layDanhSach() {
        return dal.getAll();
    }

    public String themHangHoa(HangHoaDTO hh) {
        if (hh.getMaHangHoa().isEmpty()) return "Vui lòng nhập Mã hàng hóa!";
        if (hh.getTenHangHoa().isEmpty()) return "Vui lòng nhập Tên hàng hóa!";
        // Sửa: Kiểm tra soLuongNhap
        if (hh.getSoLuongNhap() < 0) return "Số lượng nhập không được âm!";
        if (hh.getGiaBan() < 0 || hh.getGiaNhap() < 0) return "Giá tiền không được âm!";
        
        if (dal.them(hh)) return "Thêm thành công";
        else return "Thêm thất bại (Trùng mã hoặc lỗi dữ liệu)!";
    }

    public String suaHangHoa(HangHoaDTO hh) {
        // Sửa: Kiểm tra soLuongConLai
        if (hh.getSoLuongConLai() < 0) return "Số lượng tồn kho không được âm!";
        if (dal.sua(hh)) return "Cập nhật thành công";
        else return "Cập nhật thất bại";
    }

    public String xoaHangHoa(String ma) {
        if (dal.xoa(ma)) return "Xóa thành công";
        else return "Không thể xóa (Hàng hóa này đã có trong đơn hàng)!";
    }
    
    public ArrayList<HangHoaDTO> timKiemHH(String tuKhoa) {
        if (tuKhoa.trim().isEmpty() || tuKhoa.equals("Tìm Kiếm")) {
            return dal.getAll();
        }
        return dal.timKiem(tuKhoa);
    }
    
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
