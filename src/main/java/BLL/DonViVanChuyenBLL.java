/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.DonViVanChuyenDAL;
import DTO.DonViVanChuyenDTO;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class DonViVanChuyenBLL {
    private DonViVanChuyenDAL dal = new DonViVanChuyenDAL();

    public ArrayList<DonViVanChuyenDTO> layDanhSach() {
        return dal.getAll();
    }

    public String themDVVC(DonViVanChuyenDTO dv) {
        if (dv.getMaDVVC().isEmpty()) return "Mã ĐVVC không được để trống!";
        if (dv.getTenCongTy().isEmpty()) return "Tên công ty không được để trống!";
        
        if (dal.them(dv)) return "Thêm thành công";
        else return "Thêm thất bại (Có thể trùng mã)";
    }

    public String suaDVVC(DonViVanChuyenDTO dv) {
        if (dv.getMaDVVC().isEmpty()) return "Vui lòng chọn ĐVVC cần sửa!";
        
        if (dal.sua(dv)) return "Cập nhật thành công";
        else return "Cập nhật thất bại";
    }

    public String xoaDVVC(String maDVVC) {
        if (maDVVC.isEmpty()) return "Vui lòng chọn ĐVVC cần xóa!";
        
        if (dal.xoa(maDVVC)) return "Xóa thành công";
        else return "Xóa thất bại (Đang có đơn hàng sử dụng ĐVVC này)";
    }
    
    public ArrayList<DonViVanChuyenDTO> timKiemDVVC(String tuKhoa) {
        // Nếu từ khóa rỗng hoặc là chữ gợi ý "Tìm Kiếm" thì trả về tất cả
        if (tuKhoa.trim().isEmpty() || tuKhoa.equals("Tìm Kiếm")) {
            return dal.getAll();
        }
        return dal.timKiem(tuKhoa);
    }
}
